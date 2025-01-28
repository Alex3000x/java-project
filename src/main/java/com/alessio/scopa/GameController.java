package com.alessio.scopa;

import java.util.ArrayList;

public class GameController
{
    private final NeapolitanDeck deck;
    private final GameState gameState; // The state of the game
    private boolean isPlayerTurn; // Indicates if it's the player's turn

    // Constructor
    public GameController() {
        this.deck = new NeapolitanDeck();
        this.gameState = new GameState();
        this.isPlayerTurn = true; // The player always starts
    }

    // Starts the game
    private void startGame() {
        logStartGame();
        deck.shuffleDeck(); // Shuffles the deck
        logAction("►Deck successfully shuffled");
        dealCardsToPlayers(); // Deals the 3 cards to player and AI
        logAction("►3 cards dealt to the player and to AI");
        dealCardsOnTable(); // Deals the 4 cards on the table
        logAction("►4 cards dealt on the table\n");
        playRound();
    }

    public void playRound() {
        int turnNumber = 1;
        logAction("[TURN " + turnNumber + "]");
        while (deck.remainingCards() > 0 || !gameState.getPlayerHand().isEmpty() || !gameState.getAiHand().isEmpty()) {
            logAction("\n►TABLE:\n" + gameState.getTableCards());
            if (isPlayerTurn) {
                logAction("►PLAYER's hand:\n" + gameState.getPlayerHand());
                logAction(" |PLAYER| -What are you going to do?");
                // Simulate player action with the first card (replace with actual card selection)
                logAction(" |PLAYER| choose to play the " + gameState.getPlayerHand().get(0) + ".");
                playerMove(gameState.getPlayerHand().get(0));
            } else {
                logAction("►AI's hand:");
                logAiHand();
                logAction(" |AI| -What you going to do?");
                // Simulate AI action with the first card (replace with actual card selection algorithm)
                logAction(" |AI| choose to play the " + gameState.getAiHand().get(0) + ".");
                aiMove(gameState.getAiHand().get(0));
            }
            logAction("\n");
            if(checkRoundEnd(turnNumber)) {
                calculatePoints(); // Calculate points and reset for the next round
                endRound();
            } else {
                isPlayerTurn = !isPlayerTurn; // Switch to the next turn (player's turn)
            }

        }
    }

    // Deals the cards (3 to each player)
    private void dealCardsToPlayers() {
        ArrayList<Card> playerHand = new ArrayList<>();
        ArrayList<Card> aiHand = new ArrayList<>();

        // Draw cards and deal to player and AI
        for (int i = 0; i < 3; i++) {
            playerHand.add(deck.drawCard());
            aiHand.add(deck.drawCard());
        }

        // Update the game state
        gameState.setPlayerHand(playerHand);
        gameState.setAiHand(aiHand);
    }

    // Deals the cards on the table (4 cards at the start)
    private void dealCardsOnTable() {
        ArrayList<Card> tableCards = new ArrayList<>();

        // Draw cards and deal on the table
        for (int i = 0; i < 4; i++) {
            tableCards.add(deck.drawCard());
        }

        // Update the game state
        gameState.setTableCards(tableCards);
    }

    // Player's move
    private void playerMove(Card selectedCard) {
        ArrayList<Card> tableCards = gameState.getTableCards();
        ArrayList<Card> capturedCards = gameState.getPlayerCapturedCards();
        ArrayList<ArrayList<Card>> possibleCaptures = findPossibleCaptures(selectedCard, tableCards);

        // Logic to calculate card captures
        if (!possibleCaptures.isEmpty()) {
            // For now, choose the first capture (must add logic and priority for player choice later)
            ArrayList<Card> selectedCapture = possibleCaptures.get(0);
            tableCards.removeAll(selectedCapture);
            gameState.getPlayerHand().remove(selectedCard);

            // Add captured cards (the selected one and the others on the table) to the player's pile
            capturedCards.addAll(selectedCapture);
            capturedCards.add(selectedCard);
            System.out.print(" |PLAYER| captured " + selectedCapture + " with the " + selectedCard);

            // Check if it's a Scopa
            if (tableCards.isEmpty()) {
                logAction(" and made a Scopa! (+1)");
                gameState.incrementPlayerScore(); // Increment the player's score for the Scopa
            }
        } else {
            // If no captures, add the card to the table
            tableCards.add(selectedCard);
            gameState.getPlayerHand().remove(selectedCard);
            logAction(" |PLAYER| just placed the " + selectedCard + " on the table");
        }
    }

    // AI's move
    private void aiMove(Card chosenCard) {
        ArrayList<Card> tableCards = gameState.getTableCards();
        ArrayList<Card> capturedCards = gameState.getAiCapturedCards();
        ArrayList<ArrayList<Card>> possibleCaptures = findPossibleCaptures(chosenCard, tableCards);

        // Basic logic for the AI to choose the best capture
        if (!possibleCaptures.isEmpty()) {
            // For now, choose the first capture (must add logic and priority for player choice later)
            ArrayList<Card> chosenCapture = possibleCaptures.get(0);
            tableCards.removeAll(chosenCapture);
            gameState.getAiHand().remove(chosenCard);

            // Add captured cards (the selected one and the others on the table) to the player's pile
            capturedCards.addAll(chosenCapture);
            capturedCards.add(chosenCard);
            System.out.print(" |AI| captured " + chosenCapture + " with the " + chosenCard);

            // Check if it's a Scopa
            if (tableCards.isEmpty()) {
                logAction(" and made a Scopa! (+1)");
                gameState.incrementAiScore(); // Increment the player's score for the Scopa
            }
        } else {
            // If no captures, add the card to the table
            tableCards.add(chosenCard);
            gameState.getAiHand().remove(chosenCard);
            logAction(" |AI| just placed the " + chosenCard + " on the table");
        }
    }

    // Finds all possible captures for the selected card
    private ArrayList<ArrayList<Card>> findPossibleCaptures(Card selectedCard, ArrayList<Card> tableCards) {
        ArrayList<ArrayList<Card>> possibleCaptures = new ArrayList<>();

        // Check all subsets of the table cards for captures
        int n = tableCards.size();
        for (int i = 1; i < (1 << n); i++) { // Iterate over all subsets of the table cards
            ArrayList<Card> subset = new ArrayList<>();
            int sum = 0;
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) { // Check if the j-th card is in the subset
                    subset.add(tableCards.get(j));
                    sum += tableCards.get(j).getValue();
                }
            }

            // If the sum matches the selected card's value, it's a valid capture
            if (sum == selectedCard.getValue()) {
                possibleCaptures.add(subset);
            }
        }
        return possibleCaptures;
    }

    // Checks if the round is over
    private boolean checkRoundEnd(int turnNumber) {
        if (gameState.getPlayerHand().isEmpty() && gameState.getAiHand().isEmpty()) {
            turnNumber += 1;
            // If the deck still has cards, deals more
            if (deck.remainingCards() > 0) {
                logAction("\n[TURN " + turnNumber + "]");
                logAction("►New distribution of cards");
                dealCardsToPlayers(); // Deals new cards to player and AI
                return false;
            }
            // If the deck is empty, the round is over
            else {
                return true;
            }
        }
        return false;
    }

    // Calculates points at the end of the round
    private void calculatePoints() {
        // Logic to calculate points (scopa, prime, etc.)
        logAction("\n►Calculating points...");
        logAction("►Current scores of the players");
        logAction("     Player score: " + gameState.getPlayerScore());
        logAction("     AI score: " + gameState.getAiScore());
        logAction("\n");
    }

    // Ends the game
    private void endRound() {
        logAction("►Round over!");
    }

    //----------------------------------------------------------------------------------------

    private void logAiHand() {
        System.out.print("[ ▬");
        for (int i = 1; i < gameState.getAiHand().size(); i++) { System.out.print(" , ▬"); }
        System.out.print(" ]\n");
    }

    private void logAction(String message) {
        System.out.println(message);
    }

    private void logStartGame() {
        logAction("*-------------------*");
        logAction("|   GAME STARTED    |");
        logAction("*-------------------*");
    }

    //----------------------------------------------------------------------------------------

    public static void main(String[] args) {
        GameController gameController = new GameController();
        gameController.startGame(); // Will just play a round for now
    }
}