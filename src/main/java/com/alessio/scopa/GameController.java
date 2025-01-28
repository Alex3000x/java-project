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
        logAction("Deck successfully shuffled", 0);
        dealCardsToPlayers(); // Deals the 3 cards to player and AI
        logAction("3 cards dealt to the player and to AI", 0);
        dealCardsOnTable(); // Deals the 4 cards on the table
        logAction("4 cards dealt on the table", 1);
        playRound();
    }

    public void playRound() {
        int turnNumber = 1;
        String turnString = "TURN " + turnNumber;
        logTitle(turnString);
        while (deck.remainingCards() > 0 || !gameState.getPlayerHand().isEmpty() || !gameState.getAiHand().isEmpty()) {
            if (gameState.getPlayerHand().isEmpty() && gameState.getAiHand().isEmpty()) { turnNumber += 1;}
            logNewline();
            logAction("TABLE: " + gameState.getTableCards(), 0);
            if (isPlayerTurn) {
                logAction("PLAYER's hand: " + gameState.getPlayerHand(), 0);
                logMessage("PLAYER", " -What are you going to do?");
                // Simulate player action with the first card (replace with actual card selection)
                logMessage("PLAYER", " -You choose to play the " + gameState.getPlayerHand().get(0));
                playerMove(gameState.getPlayerHand().get(0));
            } else {
                log("AI's hand: ");
                logAiHand();
                logMessage("AI", " -What you going to do?");
                // Simulate AI action with the first card (replace with actual card selection algorithm)
                logMessage("AI", " -You choose to play the " + gameState.getAiHand().get(0));
                aiMove(gameState.getAiHand().get(0));
            }
            logNewline();
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

    // Generic move
    private void performMove(Card selectedCard, ArrayList<Card> playerHand, ArrayList<Card> capturedCards, boolean isPlayer) {
        ArrayList<Card> tableCards = gameState.getTableCards();
        ArrayList<ArrayList<Card>> possibleCaptures = findPossibleCaptures(selectedCard, tableCards);

        // Logic to calculate card captures
        if (!possibleCaptures.isEmpty()) {
            // For now, choose the first capture (it must add choice for player and logic for AI)
            ArrayList<Card> selectedCapture = possibleCaptures.get(0);
            tableCards.removeAll(selectedCapture);
            playerHand.remove(selectedCard);

            // Add captured cards (the selected one and the others on the table) to the player's pile
            capturedCards.addAll(selectedCapture);
            capturedCards.add(selectedCard);
            logCapture((isPlayer ? "PLAYER" : "AI"), selectedCapture, selectedCard);

            // Check if it's a Scopa
            if (tableCards.isEmpty()) {
                logScopa((isPlayer ? "PLAYER" : "AI"));
                if (isPlayer) gameState.incrementPlayerScore(); // Increment the player's score for the Scopa
                else gameState.incrementAiScore(); // Increment the AI's score for the Scopa
            }
        } else {
            // If no captures, add the card to the table
            tableCards.add(selectedCard);
            playerHand.remove(selectedCard);
            logMove((isPlayer ? "PLAYER" : "AI"), selectedCard);
        }
    }

    // Player's move
    private void playerMove(Card selectedCard) {
        performMove(selectedCard, gameState.getPlayerHand(), gameState.getPlayerCapturedCards(), true);
    }

    // AI's move
    private void aiMove(Card selectedCard) {
        performMove(selectedCard, gameState.getAiHand(), gameState.getAiCapturedCards(), false);
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
            // If the deck still has cards, deals more
            if (deck.remainingCards() > 0) {
                logNewline();
                String turnString = "TURN " + turnNumber;
                logTitle(turnString);
                log("");
                logAction("New distribution of cards", 0);
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
        logAction("Calculating points...", 0);
        logAction("Current scores of the players", 0);
        logAction("     Player score: " + gameState.getPlayerScore(), 0);
        logAction("     AI score: " + gameState.getAiScore(), 1);
        logNewline();
    }

    // Ends the game
    private void endRound() {
        logAction("Round over!", 1);
    }

    //----------------------------------------------------------------------------------------
    // Log methods
    private void log(String message) {
        System.out.print(message);
    }

    private void logNewline() {
        System.out.println();
    }

    private void logStartGame() {
        System.out.println("        *-------------------*");
        System.out.println("        |   GAME STARTED    |");
        System.out.println("        *-------------------*");
    }

    private void logTitle(String message) {
        System.out.println("[ " + message + " ]");
        //for(int i = 0; i < newline; i++) { System.out.println("\n"); }
    }

    private void logAction(String message, int newline) {
        System.out.println("►" + message);
        for(int i = 0; i < newline; i++) { System.out.println("\n"); }
    }

    private void logMessage(String player, String message) {
        System.out.println(" |" + player + "| " + message);
    }

    private void logCapture(String player, ArrayList<Card> capture, Card card) {
        System.out.println(" |" + player + "| captured " + capture + " with the " + card);
    }

    private void logMove(String player, Card card) {
        System.out.println(" |" + player + "| just placed the " + card + " on the table");
    }

    private void logScopa(String player) {
        System.out.println(" |" + player + "| made a Scopa! (+1)");
    }

    private void logAiHand() {
        System.out.print("[ ▬");
        for (int i = 1; i < gameState.getAiHand().size(); i++) { System.out.print(" , ▬"); }
        System.out.print(" ]\n");
    }

    //----------------------------------------------------------------------------------------
    // Main test
    public static void main(String[] args) {
        GameController gameController = new GameController();
        gameController.startGame(); // Will just play a round for now
    }
}