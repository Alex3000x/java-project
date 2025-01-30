package com.alessio.scopa;

import java.util.ArrayList;
import java.util.Scanner; // Import Scanner for input
import java.util.concurrent.TimeUnit;

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
        logAction("Shuffling deck...", 0);
        deck.shuffleDeck(); // Shuffles the deck
        wait(4);
        logAction("Dealing 3 cards to the player and to AI...", 0);
        dealCardsToPlayers(); // Deals the 3 cards to player and AI
        wait(4);
        logAction("Dealing 4 cards on the table...", 1);
        dealCardsOnTable(); // Deals the 4 cards on the table
        wait(2);
        // Here goes the while until one of them reaches the final score
        playRound();
        // Outside the while the game ends with final message and choices whether to restart or exit
        // Maybe even see results, save match...
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
                Card selectedCard = selectPlayerCard(); // Card selection from player
                logMessage("PLAYER", "choose to play the " + selectedCard, 1);
                playerMove(selectedCard);
            } else {
                logPrint("►AI's hand: ");
                logAiHand();
                logMessage("AI", "is thinking what to do...", 1);
                wait(4);
                // Simulate AI action with the first card (replace with actual card selection algorithm)
                logMessage("AI", "choose to play the " + gameState.getAiHand().get(0), 1);
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

    private Card selectPlayerCard() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Card> playerHand = gameState.getPlayerHand();

        // Shows available cards in the player's hand
        logAction("PLAYER's hand:",0);
        for (int i = 0; i < playerHand.size(); i++) {
            logPrint((i + 1) + ") " + playerHand.get(i) + "\n");
        }

        // Asks the player which card he wants to play
        int playerChoice;
        while (true) {
            logMessage("PLAYER","-Choose a card to play (1-" + playerHand.size() + "): ", 0);
            if (scanner.hasNextInt()) {
                playerChoice = scanner.nextInt();
                if (playerChoice >= 1 && playerChoice <= playerHand.size()) {
                    break; // Valid input
                }
            }
            scanner.nextLine(); // Clean the scanner buffer
            logAction("Invalid choice. Try again.",0);
        }
        return playerHand.get(playerChoice - 1);
    }

    // Player's move
    private void playerMove(Card selectedCard) {
        performMove(selectedCard, gameState.getPlayerHand(), gameState.getPlayerCapturedCards(), true);
    }

    // AI's move
    private void aiMove(Card selectedCard) {
        performMove(selectedCard, gameState.getAiHand(), gameState.getAiCapturedCards(), false);
    }

    // Generic move
    private void performMove(Card selectedCard, ArrayList<Card> playerHand, ArrayList<Card> capturedCards, boolean isPlayer) {
        ArrayList<Card> tableCards = gameState.getTableCards();
        ArrayList<ArrayList<Card>> possibleCaptures = findPossibleCaptures(selectedCard, tableCards);

        // Logic to calculate card captures
        if (!possibleCaptures.isEmpty()) {
            // For now, chooses the first capture (it must add choice for player and logic for AI)
            ArrayList<Card> selectedCapture = possibleCaptures.get(0);
            tableCards.removeAll(selectedCapture);
            playerHand.remove(selectedCard);

            // Adds captured cards (the selected one and the others on the table) to the player's pile
            capturedCards.addAll(selectedCapture);
            capturedCards.add(selectedCard);
            logCapture((isPlayer ? "PLAYER" : "AI"), selectedCapture, selectedCard);
            wait(2);

            // Check if it's a Scopa
            if (tableCards.isEmpty()) {
                logScopa((isPlayer ? "PLAYER" : "AI"));
                if (isPlayer) gameState.incrementPlayerScore(); // Increment the player's score for the Scopa
                else gameState.incrementAiScore(); // Increment the AI's score for the Scopa
                wait(2);
            }
        } else {
            // If no captures, add the card to the table
            tableCards.add(selectedCard);
            playerHand.remove(selectedCard);
            logMove((isPlayer ? "PLAYER" : "AI"), selectedCard);
            wait(2);
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
            // If the deck still has cards, deals more
            if (deck.remainingCards() > 0) {
                logNewline();
                String turnString = "TURN " + turnNumber;
                logTitle(turnString);
                logPrint("");
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
        wait(4);
        logAction("Current scores of the players", 0);
        logAction("     Player score: " + gameState.getPlayerScore(), 0);
        logAction("     AI score: " + gameState.getAiScore(), 1);
        logNewline();
    }

    // Ends the game
    private void endRound() {
        wait(2);
        logAction("Round over!", 1);
    }

    //----------------------------------------------------------------------------------------
    // Other methods

    private static void wait(int seconds)
    {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //----------------------------------------------------------------------------------------
    // Log methods
    private void logPrint(String message) {
        System.out.print(message);
    }

    private void logNewline() {
        System.out.println();
    }

    private void logStartGame() {
        System.out.println("                                        *-------------------*");
        System.out.println("                                        |   GAME STARTED    |");
        System.out.println("                                        *-------------------*");
    }

    private void logTitle(String message) {
        System.out.println("[" + message + "]");
        //for(int i = 0; i < newline; i++) { System.out.println("\n"); }
    }

    private void logAction(String message, int newlines) {
        System.out.println("►" + message);
        for(int i = 0; i < newlines; i++) { System.out.println("\n"); }
    }

    private void logMessage(String player, String message, int newlines) {
        System.out.print(" |" + player + "|  " + message);
        for(int i = 0; i < newlines; i++) { System.out.print("\n"); }
    }

    private void logCapture(String player, ArrayList<Card> capture, Card card) {
        System.out.println(" |" + player + "|  captured " + capture + " with the " + card);
    }

    private void logMove(String player, Card card) {
        System.out.println(" |" + player + "|  just placed the " + card + " on the table");
    }

    private void logScopa(String player) {
        System.out.println(" |" + player + "|  made a Scopa! (+1)");
    }

    private void logAiHand() {
        System.out.print("[▬");
        for (int i = 1; i < gameState.getAiHand().size(); i++) { System.out.print(", ▬"); }
        System.out.print("]\n");
    }

    //----------------------------------------------------------------------------------------
    // Main test
    public static void main(String[] args) {
        GameController gameController = new GameController();
        gameController.startGame(); // Will just play a round for now
    }
}