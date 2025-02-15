package com.alessio.scopa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner; // Import Scanner for input
import java.util.concurrent.TimeUnit;

public class GameController {
    private final GameState gameState; // The state of the game
    private NeapolitanDeck deck; // The deck
    private boolean isPlayerTurn; // Indicates if it's the player's turn
    private static final int winningScore = 11; // Default value, can be changed if needed

    private record Pair<T, U>(T first, U second) {} // Used a record to make sure that from the method "getBestCaptureForCard"
                                                    // that should return 2 values (the best capture and its respective score)

    // Constructor
    public GameController() {
        this.gameState = new GameState();
        this.isPlayerTurn = true; // The player always starts, but we can make a random start by the deck
    }

    // Starts the game
    public void startGame() {
        logStartGame();
        //gameState.resetGame(); // Reset the game state, including scores and the deck
        // The game continues until one player reaches the WINNING_SCORE
        while (gameState.getPlayerScore() < winningScore && gameState.getAiScore() < winningScore) {
            resetRound();
            initializeRound();
            playRound();   // Execute a round of the game
            // Check if a player has reached the winning score
            if (gameState.getPlayerScore() >= winningScore || gameState.getAiScore() >= winningScore) {
                // In case both achieve the same winning score, ends when results are different (one greater than the other)
                if (gameState.getPlayerScore() != gameState.getAiScore()) {
                    //announceWinner();
                    break; // End the game
                }
            }
        }
        // Outside the while the game ends with final message and choices whether to restart or exit
        // Maybe even see results, save match...
    }

    // Initializes a new round by shuffling the deck and dealing cards to players and on the table
    private void initializeRound() {
        deck = new NeapolitanDeck();
        logAction("Shuffling deck...", 0);
        deck.shuffleDeck(); // Shuffles the deck
        wait(4);
        logAction("Dealing 3 cards to the player and to AI...", 0);
        dealCardsToPlayers(); // Deals the 3 cards to player and AI
        wait(4);
        logAction("Dealing 4 cards on the table...", 0);
        dealCardsOnTable(); // Deals the 4 cards on the table
        wait(2);
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
        logNewRound();
        int turnNumber = 1;
        while (deck.remainingCards() > 0 || !gameState.getPlayerHand().isEmpty() || !gameState.getAiHand().isEmpty()) {
            logPrint("_____________________________________________________________________________________________________________________\n");
            String turnString = "TURN " + turnNumber;
            logTitle(turnString);
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
            if (isRoundEnded()) {
                endRound();
                calculatePoints(); // Calculates points at the end of every round
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
        int bestScore = Integer.MIN_VALUE; // A score to evaluate the best capture with a card (higher value = better choice)

        // Check each card of the AI hand and we see which one makes the best capture
        for (Card aiCard : aiHand) {
            ArrayList<ArrayList<Card>> captures = findPossibleCaptures(aiCard, tableCards); // All possible captures with one card
            ArrayList<ArrayList<Card>> identicalCaptures = new ArrayList<>();

            // Check for identical captures : if presents, they will be saved in identicalCaptures
            for (ArrayList<Card> capture : captures) {
                if (capture.size() == 1 && capture.get(0).getValue() == aiCard.getValue()) {
                    identicalCaptures.add(capture);
                }
            }
            if (!identicalCaptures.isEmpty()) { // If some identical capture are presents,
                captures = identicalCaptures; // AI must consider to consider only this(those) for the card choice
            }
            // If a catch is possible with that card, let’s see which capture gives the best result
            if (!captures.isEmpty()) {
                Pair<ArrayList<Card>, Integer> captureInfo = getBestCaptureForCard(aiCard, tableCards, captures); // Best capture and score for that card
                int currentScore = captureInfo.second();    // But here we need only the score information to define the best card choice

                    // If this current score is the best found, update the choice
                    if (currentScore > bestScore) {
                        bestScore = currentScore; // This current score become the best score
                        bestCard = aiCard; // So the card for that capture become the best card
                    }
            } else {
                // If a catch isn't possible, let’s see which card to play is the least dangerous

                // A score to evaluate the best card to discard on the table (higher value = not bad choice)
                int discardScore = getDiscardCardScore(aiCard,tableCards);

                // If this discard score is the best found, update your choice
                if (discardScore > bestScore) {
                    bestScore = discardScore; // This discard score become the best score
                    bestCard = aiCard; // So the respective card become the best card to play
                }
            }
        }
        return bestCard; // return the best card chose by AI
    }

    // Return the list of cards that corresponds to the best capture out of all possible ones and its respective score
    private Pair<ArrayList<Card>, Integer> getBestCaptureForCard(Card aiCard, ArrayList<Card> tableCards, ArrayList<ArrayList<Card>> captures) {
        ArrayList<Card> bestCapture = new ArrayList<>();
        int bestScore = Integer.MIN_VALUE;

        // If a catch is possible with that card, let’s see which capture gives the best result
        if (!captures.isEmpty()) {
            for (ArrayList<Card> capture : captures) { // Checks for every possible capture
                ArrayList<Card> captureWithAiCard = new ArrayList<>(capture); // Capture that will also include the card being played
                captureWithAiCard.add(aiCard); // For some checks, the card that will be played must also to be considered for calculations
                int score = 0; // Temporary score to evaluate priority

                // Now there will be a list of conditions for which scores are given to the captures that will define which is the best
                // 1) Points for the Scopa (if it empties the table)
                if (capture.size() == tableCards.size()) {
                    return new Pair<>(capture, 1000); // If it's a scopa, no need to continue searching and scoring, directly gives this capture
                }

                // 2) Points for the capture of the settebello (the seven of Coins)
                for (Card c : captureWithAiCard) {
                    if ((c.getSuit().equals("Coins")) && (c.getValue() == 7)) {
                        score += 20; // Gives a high score for the settebello
                    }
                }

                // 3) Points for the capture of good cards for the prime
                for (Card c : captureWithAiCard) {
                    score += c.getPrime(); // Gives a score based on its value in the prime (higher is better)
                }

                // 4) Points for the capture of Coins cards
                for (Card c : captureWithAiCard) {
                    if (c.getSuit().equals("Coins")) {
                        score += 10; // Gives a good score for each Coins card earned
                    }
                }

                // 5) Points for the total number of captured cards
                score += capture.size() * 3; // Gives a linear score for each card earned

                // If this current score is the best found, update the choice
                if (score > bestScore) {
                    bestScore = score; // This current score become the best score
                    bestCapture = new ArrayList<>(capture); // And so also the capture become the best capture
                }
            }
        }
        return new Pair<>(bestCapture, bestScore);
    }

    // Return the score of the card that corresponds to the best card to discard on the table
    private Integer getDiscardCardScore(Card aiCard, ArrayList<Card> tableCards) {

        ArrayList<Card> tableCardsWithAiCard = new ArrayList<>(tableCards); // Table cards if the card was just placed on table
        tableCardsWithAiCard.add(aiCard); // For some checks, the card must also to be considered if it was just placed on table

        // List of all possible captures with a seven value card of the opponent if the AI card was on table
        ArrayList<ArrayList<Card>> capturesWithSeven = findPossibleCaptures(new Card(7, "Coins"), tableCardsWithAiCard); // Suit is irrelevant

        // List of all possible captures with a six value card of the opponent if the AI card was on table
        ArrayList<ArrayList<Card>> capturesWithSix = findPossibleCaptures(new Card(6, "Coins"), tableCardsWithAiCard); // Suit is irrelevant

        int score = 0; // // A score to assess the danger of placing on table that card (lower value = worst choice)
        int sumTableCards = 0;

        // Only to calculate the sum of the values of the cards on the table to avoid leaving a scopa to the opponent
        for (Card card : tableCards) {
            sumTableCards += card.getValue();
        }

        // 1) Points or malus to avoid or leave a chance to make a scopa
        if ((sumTableCards + aiCard.getValue()) > 10) {
            score += 50; // Gives a good score to avoid the chance of making scopa to the opponent
        } else if ((sumTableCards + aiCard.getValue()) <= 10) {
            score -= 50; // Gives a malus to favor the chance of making scopa to the opponent
        }

        // 2) Malus to leave a chance to capture best prime cards from opponent's hand
        if (!capturesWithSeven.isEmpty()) { // Gives a malus to favor the chance of capturing good cards to the opponent
            score -= 20; // Gives a bad malus to leave a chance to capture a seven value card to the opponent
        }

        // 3) Malus to leave a chance to capture second-best prime cards from opponent's hand
        if (!capturesWithSix.isEmpty()) { // Gives a malus to favor the chance of capturing good cards to the opponent
            score -= 15; // Gives a bad malus to leave a chance to capture a six value card to the opponent
        }

        // 4) Malus to leave a chance to capture good prime cards
        score -= aiCard.getPrime(); // Gives a malus based on which prime value leave at the opponent (higher is worst)

        // 5) Malus to leave a chance to capture coins cards
        if (aiCard.getSuit().equals("Coins")) {
            score -= 5; // Gives a malus to leave a Coins card to the opponent
        }

        return score;
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
        ArrayList<Card> selectedCapture;
        ArrayList<ArrayList<Card>> identicalCaptures = new ArrayList<>();

        // Check for identical card: if present(s),
        for (ArrayList<Card> capture : possibleCaptures) {
            if (capture.size() == 1 && capture.get(0).getValue() == selectedCard.getValue()) {
                identicalCaptures.add(capture);
            }
        }
        if (!identicalCaptures.isEmpty()) { // If some identical capture are presents,
            possibleCaptures = identicalCaptures; // Players must consider to take only this(those) by force
        }

        // Logic to calculate card captures
        if (!possibleCaptures.isEmpty()) {
            // If there are multiple possible captures
            if (possibleCaptures.size() > 1) {
                if (isPlayer) { // For player there is a choice
                    selectedCapture = selectPlayerCapture(selectedCard, possibleCaptures);
                } else { // and for AI there is used the best capture logic
                    selectedCapture = getBestCaptureForCard(selectedCard,tableCards,possibleCaptures).first();
                }
            } else { // If there is only one possible capture, it will be taken
                selectedCapture = possibleCaptures.get(0);
            }
            tableCards.removeAll(selectedCapture);
            playerHand.remove(selectedCard);

            // Adds captured cards (the selected one and the others on the table) to the player's pile
            capturedCards.addAll(selectedCapture);
            capturedCards.add(selectedCard);
            gameState.setLastCaptureByPlayer(isPlayer);
            logCapture((isPlayer ? "PLAYER" : "AI"), selectedCapture, selectedCard);
            wait(2);

            // Check if it's a Scopa (the table is clear after a capture)
            if (tableCards.isEmpty()) {
                // But first checks if the last player in the last turn takes all the cards on the table with a capture
                // If it is so then it never counts as a scopa
                if (deck.remainingCards() == 0 && gameState.getPlayerHand().isEmpty() && gameState.getAiHand().isEmpty()) {
                    logAction("This one doesn't counts as a scopa.",1);
                } else {
                    logScopa((isPlayer ? "PLAYER" : "AI"));
                    if (isPlayer) gameState.addPlayerScore(1); // Increment the player's score for the Scopa
                    else gameState.addAiScore(1); // Increment the AI's score for the Scopa
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

    private ArrayList<Card> selectPlayerCapture(Card selectedCard, ArrayList<ArrayList<Card>> possibleCaptures) {
        Scanner scanner = new Scanner(System.in);

        logMessage("PLAYER", "With the " + selectedCard + " you have these capture options:",1);
        for (int i = 0; i < possibleCaptures.size(); i++) {
            logPrint((i + 1) + ") " + possibleCaptures.get(i) + "\n");
        }

        int captureChoice;
        while (true) {
            logMessage("PLAYER", "-Choose a capture (1-" + possibleCaptures.size() + "): ", 0);
            if (scanner.hasNextInt()) {
                captureChoice = scanner.nextInt();
                if (captureChoice >= 1 && captureChoice <= possibleCaptures.size()) {
                    break; // Valid input
                }
            }
            scanner.nextLine(); // Clean the scanner buffer
            logAction("Invalid choice. Try again.", 0);
        }
        return possibleCaptures.get(captureChoice - 1);
    }

    // Checks if the round is over
    private boolean isRoundEnded() {
        if (gameState.getPlayerHand().isEmpty() && gameState.getAiHand().isEmpty()) { // If hands have no cards
            // If the deck still has cards, deals more
            if (deck.remainingCards() > 0) {
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
        // If there are cards on the table, give them to the last player who captures
        if (!gameState.getTableCards().isEmpty()) {
            if (gameState.isLastCaptureByPlayer()) {
                gameState.getPlayerCapturedCards().addAll(gameState.getTableCards());
            } else {
                gameState.getAiCapturedCards().addAll(gameState.getTableCards());
            }
            logAction("Giving the remaining table cards " + gameState.getTableCards().toString() + " to " + (gameState.isLastCaptureByPlayer() ? "PLAYER" : "AI"), 0);
            gameState.getTableCards().clear(); // Clear table
        } else { logAction("Table clear, nobody collects cards from it", 0); }
        logNewline();
        logEndRound();
    }

    private void resetRound() {
        logAction("Resetting round for a new match...", 0);

        gameState.clearTableCards();
        gameState.clearPlayerHand();
        gameState.clearAiHand();
        gameState.clearPlayerCapturedCards();
        gameState.clearAiCapturedCards();
        gameState.resetLastCaptureByPlayer();
    }

    // Calculates points at the end of the round
    private void calculatePoints() {
        logNewline();
        logAction("Calculating points for this round...", 0);
        wait(4);

        ArrayList<Card> playerCards = gameState.getPlayerCapturedCards();
        ArrayList<Card> aiCards = gameState.getAiCapturedCards();

        int playerScore = 0;
        int aiScore = 0;

        // Here the logic to calculate points (cards, coins, settebello and prime)

        // 1) Most of the cards
        logPrint(" -Majority of cards owned by");
        if (playerCards.size() > aiCards.size()) {
            playerScore++;
            logPoint("PLAYER");
        } else if (aiCards.size() == playerCards.size()) {

            logNoPoint();
        } else {
            aiScore++;
            logPoint("AI");
        }
        logPrint(" --> (PLAYER: " + playerCards.size() + " | AI: " + aiCards.size() + ")\n");
        wait(4);

        // 2) Most of the coins cards
        int playerCoins = (int) playerCards.stream().filter(c -> c.getSuit().equals("Coins")).count();
        int aiCoins = (int) aiCards.stream().filter(c -> c.getSuit().equals("Coins")).count();
        logPrint(" -Majority of Coins suit cards owned by");
        if (playerCoins > aiCoins) {
            playerScore++;
            logPoint("PLAYER");
        } else if (playerCoins == aiCoins) {

            logNoPoint();
        } else {
            aiScore++;
            logPoint("AI");
        }
        logPrint(" --> (PLAYER: " + playerCoins + " | AI: " + aiCoins + ")\n");
        wait(4);

        // 3) Settebello
        boolean playerHasSettebello = playerCards.stream().anyMatch(c -> c.getSuit().equals("Coins") && c.getValue() == 7);
        logPrint(" -Settebello (Seven of Coins) owned by");
        if (playerHasSettebello) {
            playerScore++;
            logPoint("PLAYER");
        } else {
            aiScore++;
            logPoint("AI");
        }
        logNewline();
        wait(4);

        // 4) Prime (or "primiera")
        ArrayList<Card> playerPrimeCards = new ArrayList<>();
        ArrayList<Card> aiPrimeCards = new ArrayList<>();
        int playerPrime = calculateBestPrime(playerCards, playerPrimeCards);
        int aiPrime = calculateBestPrime(aiCards, aiPrimeCards);
        logPrint(" -Best prime owned by");
        if (playerPrime > aiPrime) {
            playerScore++;
            logPoint("PLAYER");
        } else if (aiPrime == playerPrime) {
            aiScore++;
            logNoPoint();
        } else {
            logPoint("AI");
        }
        logPrint("\n(PLAYER: " + playerPrime + " with these " + playerPrimeCards + ")\n(AI: " + aiPrime + " with these " + aiPrimeCards + ")\n");
        wait(4);

        // Assign points to the gameState scores
        logAction("Adding points to the players...", 0);
        gameState.addPlayerScore(playerScore);
        gameState.addAiScore(aiScore);
        wait(4);

        // Print the current score updated
        logAction("Scores updated:", 0);
        logMessage("PLAYER", "\tscore: " + gameState.getPlayerScore(), 1);
        logMessage("AI", "\tscore: " + gameState.getAiScore(), 2);
    }

    private int calculateBestPrime(ArrayList<Card> capturedCards, ArrayList<Card> primeCards) {
        // Map to store the highest prime-value card found for each suit
        Map<String, Integer> bestPrimeValues = new HashMap<>();
        Map<String, Card> bestPrimeList = new HashMap<>(); // Stores the best card for each suit
        bestPrimeValues.put("Coins", 0);
        bestPrimeValues.put("Swords", 0);
        bestPrimeValues.put("Cups", 0);
        bestPrimeValues.put("Clubs", 0);

        for (Card c : capturedCards) {
            String suitCard = c.getSuit();
            int primeValueCard = c.getPrime(); // Assuming getPrime() returns the correct prime value
            if (primeValueCard > bestPrimeValues.get(suitCard)) { // Compare prime value of card with the one in the Map in the same suit (at beginning it is 0)
                bestPrimeValues.put(suitCard, primeValueCard);
                bestPrimeList.put(suitCard, c);
            }
        }

        // Store the best prime cards in the provided list
        primeCards.clear();
        primeCards.addAll(bestPrimeList.values());

        // Sum the highest prime values of each suit to get the total prime score
        return bestPrimeValues.values().stream().mapToInt(Integer::intValue).sum();
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
        System.out.println("*-------------------------------------------------------------------------------------------------------------------*");
        System.out.println("|                                                   GAME STARTED                                                    |");
        System.out.println("*-------------------------------------------------------------------------------------------------------------------*");
    }

    private void logNewRound() {
        System.out.println("*-------------------------------------------------------------------------------------------------------------------*");
        System.out.println("|                                                    NEW ROUND                                                      |");
        System.out.println("*-------------------------------------------------------------------------------------------------------------------*");
    }

    private void logEndRound() {
        System.out.println("*-------------------------------------------------------------------------------------------------------------------*");
        System.out.println("|                                                    END ROUND                                                      |");
        System.out.println("*-------------------------------------------------------------------------------------------------------------------*");
    }

    private void logTitle(String message) {
        System.out.println("[" + message + "]");
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

    private void logPoint(String player) {
        System.out.print(" |" + player + "| who gets a point (+1)");
    }

    private void logNoPoint() {
        System.out.print(" no one. It's a draw, nobody gets the point;");
    }

    private void logAiHand() {
        System.out.print("[▬");
        for (int i = 1; i < gameState.getAiHand().size(); i++) { System.out.print(", ▬"); }
        System.out.print("]\n");
    }

    //----------------------------------------------------------------------------------------
    // Main test
    public static void main(String[] args) {
        // Here goes any tests to be done for the changes made
    }
}