package com.alessio.scopa;

import java.util.ArrayList;
import java.util.Scanner; // Import Scanner for input
import java.util.concurrent.TimeUnit;

public class GameController {
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

    public void playRound() {
        int turnNumber = 1;
        String turnString = "TURN " + turnNumber;
        logPrint("____________________________________________________________________________\n");
        logTitle(turnString);
        while (deck.remainingCards() > 0 || !gameState.getPlayerHand().isEmpty() || !gameState.getAiHand().isEmpty()) {
            logNewline();
            logAction("TABLE: " + gameState.getTableCards(), 0);
            if (isPlayerTurn) {
                Card selectedPlayerCard = selectPlayerCard(); // Card selection from player
                logMessage("PLAYER", "choose to play the " + selectedPlayerCard, 1);
                playerMove(selectedPlayerCard);
            } else {
                Card selectedAiCard = chooseAiCard(); // Card selection from AI
                logMessage("AI", "choose to play the " + selectedAiCard, 1);
                aiMove(selectedAiCard);
            }
            logNewline();
            if (gameState.getPlayerHand().isEmpty() && gameState.getAiHand().isEmpty()) {
                turnNumber += 1;
            }
            if (checkRoundEnd(turnNumber)) {
                endRound();
                calculatePoints(); // Calculate points and reset for the next round
            } else {
                isPlayerTurn = !isPlayerTurn; // Switch to the next turn (player's turn)
            }
        }
    }

    // Card selection from the player
    private Card selectPlayerCard() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Card> playerHand = gameState.getPlayerHand();

        // Shows available cards in the player's hand
        logAction("PLAYER's hand:", 0);
        for (int i = 0; i < playerHand.size(); i++) {
            logPrint((i + 1) + ") " + playerHand.get(i) + "\n");
        }

        // Asks the player which card he wants to play
        int playerChoice;
        while (true) {
            logMessage("PLAYER", "-Choose a card to play (1-" + playerHand.size() + "): ", 0);
            if (scanner.hasNextInt()) {
                playerChoice = scanner.nextInt();
                if (playerChoice >= 1 && playerChoice <= playerHand.size()) {
                    break; // Valid input
                }
            }
            scanner.nextLine(); // Clean the scanner buffer
            logAction("Invalid choice. Try again.", 0);
        }
        return playerHand.get(playerChoice - 1);
    }

    // This would be the best move choice from AI
    private Card chooseAiCard() {
        ArrayList<Card> aiHand = gameState.getAiHand();
        ArrayList<Card> tableCards = gameState.getTableCards();

        // Shows graphically the number of remaining cards in the AI's hand and the waiting message
        logPrint("►AI's hand: ");
        logAiHand();
        logMessage("AI", "is thinking what to do...", 1);
        wait(4); // To emulate the AI’s thinking time

        Card bestCard = aiHand.get(0); // It's the card will be returned, and it will be updated from time to time [first card to default]
        ArrayList<Card> bestCapture = new ArrayList<>(); /** try to find a way to give this to performMove **/
        int bestScore = -1; // A score to evaluate the priority of a move (higher value = better choice)

        // Check each card of the AI and we see which one makes the best capture
        for (Card aiCard : aiHand) {
            ArrayList<ArrayList<Card>> captures = findPossibleCaptures(aiCard, tableCards); // All possible captures with each card

            // If a catch is possible with that card, let’s see which capture gives the best result
            if (!captures.isEmpty()) {
                for (ArrayList<Card> capture : captures) { // Checks for every possible capture
                    ArrayList<Card> captureWithAiCard = new ArrayList<>(capture); // Capture that will also include the card being played
                    captureWithAiCard.add(aiCard); // For some checks, the card that will be played must also to be considered for calculations
                    int currentScore = 0; // Temporary score to evaluate priority

                    // 1) Points for the Scopa (if it empties the table)
                    if (capture.size() == tableCards.size()) {
                        return aiCard; // If it's a scopa, no need to continue searching
                    }

                    // 2) Points for the capture of the seven of Coins (or "settebello")
                    for (Card c : captureWithAiCard) {
                        if ((c.getSuit().equals("Coins")) && (c.getValue() == 7)) {
                            currentScore += 20; // Gives a high score for the seven of Coins
                        }
                    }

                    // 3) Points for the capture of good cards for the prime
                    for (Card c : captureWithAiCard) {
                        currentScore += c.getPrime(); // Gives a score based on its value in the prime (higher is better)
                    }

                    // 4) Points for the capture of Coins cards
                    for (Card c : captureWithAiCard) {
                        if (c.getSuit().equals("Coins")) {
                            currentScore += 10; // Gives a good score for each Coins card earned
                        }
                    }

                    // 5) Points for the total number of captured cards
                    currentScore += capture.size() * 3; // Gives a linear score for each card earned

                    // If this current score is the best found, update the choice
                    if (currentScore > bestScore) {
                        bestScore = currentScore; // This current score become the best score
                        bestCard = aiCard; // So the card for that capture become the best card
                        bestCapture = capture; // And also the capture become the best capture
                    }
                }
            } else {
                // If a catch isn't possible, let’s see which card to play is the least dangerous
                ArrayList<Card> tableCardsWithAiCard = new ArrayList<>(tableCards); // Table cards if the card was just placed on table
                tableCardsWithAiCard.add(aiCard); // For some checks, the card must also to be considered if it was just placed on table

                // List of all possible captures with a seven value card of the opponent if the AI card was on table
                ArrayList<ArrayList<Card>> capturesWithSeven = findPossibleCaptures(new Card(7, "Coins"), tableCardsWithAiCard); // Suit is irrelevant

                // List of all possible captures with a six value card of the opponent if the AI card was on table
                ArrayList<ArrayList<Card>> capturesWithSix = findPossibleCaptures(new Card(6, "Coins"), tableCardsWithAiCard); // Suit is irrelevant

                int discardScore = 0; // // A score to assess the danger of placing on table that card (lower value = worst choice)
                int sumTableCards = 0;

                // Only to calculate the sum of the values of the cards on the table to avoid leaving a scopa to the opponent
                for (Card card : tableCards) {
                    sumTableCards += card.getValue();
                }

                // 1) Points or malus to avoid or leave a chance to make a scopa
                if ((sumTableCards + aiCard.getValue()) > 10) {
                    discardScore += 50; // Gives a good score to avoid the chance of making scopa to the opponent
                } else if ((sumTableCards + aiCard.getValue()) <= 10) {
                    discardScore -= 50; // Gives a malus to favor the chance of making scopa to the opponent
                }

                // 2) Malus to leave a chance to capture best prime cards from opponent's hand
                if (!capturesWithSeven.isEmpty()) { // Gives a malus to favor the chance of capturing good cards to the opponent
                    discardScore -= 20; // Gives a bad malus to leave a chance to capture a seven value card to the opponent
                }

                // 3) Malus to leave a chance to capture second-best prime cards from opponent's hand
                if (!capturesWithSix.isEmpty()) { // Gives a malus to favor the chance of capturing good cards to the opponent
                    discardScore -= 15; // Gives a bad malus to leave a chance to capture a six value card to the opponent
                }

                // 4) Malus to leave a chance to capture good prime cards
                discardScore -= aiCard.getPrime(); // Gives a malus based on which prime value leave at the opponent (higher is worst)

                // 5) Malus to leave a chance to capture coins cards
                if (aiCard.getSuit().equals("Coins")) {
                    discardScore -= 5; // Gives a malus to leave a Coins card to the opponent
                }

                // If this discard score is the best found, update your choice
                if (discardScore > bestScore) {
                    bestScore = discardScore; // This discard score become the best score
                    bestCard = aiCard; // So the respective card become the best card to play
                }
            }
        }
        return bestCard; // return the best card chose by AI
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
            ArrayList<Card> selectedCapture = possibleCaptures.get(0); /** here goes choice of player and bestCapture for AI **/
            tableCards.removeAll(selectedCapture);
            playerHand.remove(selectedCard);

            // Adds captured cards (the selected one and the others on the table) to the player's pile
            capturedCards.addAll(selectedCapture);
            capturedCards.add(selectedCard);
            gameState.setLastCaptureByPlayer(isPlayer);
            logCapture((isPlayer ? "PLAYER" : "AI"), selectedCapture, selectedCard);
            wait(2);

            // Check if it's a Scopa
            if (tableCards.isEmpty()) {
                if (deck.remainingCards() == 0) {
                    logPrint("");
                } else {
                    logScopa((isPlayer ? "PLAYER" : "AI"));
                    if (isPlayer) gameState.incrementPlayerScore(); // Increment the player's score for the Scopa
                    else gameState.incrementAiScore(); // Increment the AI's score for the Scopa
                    wait(2);
                }
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
        if (gameState.getPlayerHand().isEmpty() && gameState.getAiHand().isEmpty()) { // If hands have no cards
            // If the deck still has cards, deals more
            if (deck.remainingCards() > 0) {
                logPrint("____________________________________________________________________________\n");
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

    // Ends the game
    private void endRound() {
        wait(2);
        logPrint("____________________________________________________________________________\n");
        logAction("Round over!", 0);
        // If there are cards on the table, give them to the last player who captures
        if (!gameState.getTableCards().isEmpty()) {
            if (gameState.isLastCaptureByPlayer()) {
                gameState.getPlayerCapturedCards().addAll(gameState.getTableCards());
            } else {
                gameState.getAiCapturedCards().addAll(gameState.getTableCards());
            }
            logAction("Giving the remaining table cards to " + (gameState.isLastCaptureByPlayer() ? "PLAYER" : "AI"), 0);
            gameState.getTableCards().clear(); // Clear table
        } else { logAction("Table clear, nobody collects cards from it", 0); }
    }

    // Calculates points at the end of the round
    private void calculatePoints() {
        // Logic to calculate points (scopa, prime, etc.)
        logNewline();
        logAction("Calculating points...", 0);
        wait(4);
        logAction("Current scores of the players", 0);
        logMessage("PLAYER", "score: " + gameState.getPlayerScore(), 1);
        logMessage("AI", "score: " + gameState.getAiScore(), 1);
        logNewline();
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