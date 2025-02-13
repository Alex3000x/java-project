package com.alessio.scopa;

import java.util.ArrayList;

public class GameState {
    // Cards on the table
    private ArrayList<Card> tableCards;

    // Cards in the players' hand
    private ArrayList<Card> playerCards;
    private ArrayList<Card> aiCards;

    // Captured cards by players
    private ArrayList<Card> playerCapturedCards;
    private ArrayList<Card> aiCapturedCards;

    private boolean lastCaptureByPlayer;

    // players scores
    private int playerScore;
    private int aiScore;

    // Constructor
    public GameState() {
        tableCards = new ArrayList<>();
        playerCards = new ArrayList<>();
        aiCards = new ArrayList<>();
        playerCapturedCards = new ArrayList<>();
        aiCapturedCards = new ArrayList<>();
        playerScore = 0;
        aiScore = 0;
    }

    public void addPlayerScore(int pointsToAdd) {
        playerScore += pointsToAdd;
    }

    public void addAiScore(int pointsToAdd) {
        aiScore += pointsToAdd;
    }

    public void resetPlayerScore (int newScore) {
        playerScore = 0;
    }

    public void resetAiScore (int newScore) {
        aiScore = 0;
    }

    // Getter
    public ArrayList<Card> getTableCards() {
        return tableCards;
    }

    public ArrayList<Card> getPlayerHand() {
        return playerCards;
    }

    public ArrayList<Card> getAiHand() {
        return aiCards;
    }

    public ArrayList<Card> getPlayerCapturedCards() {
        return playerCapturedCards;
    }

    public ArrayList<Card> getAiCapturedCards() {
        return aiCapturedCards;
    }

    public boolean isLastCaptureByPlayer() {
        return lastCaptureByPlayer;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public int getAiScore() {
        return aiScore;
    }

    // Setter
    public void setTableCards(ArrayList<Card> cardsOnTable) {
        tableCards = cardsOnTable;
    }

    public void setPlayerHand(ArrayList<Card> playerHand) {
        playerCards = playerHand;
    }

    public void setAiHand(ArrayList<Card> aiHand) {
        aiCards = aiHand;
    }

    public void setLastCaptureByPlayer(boolean lastCaptureByPlayer) {
        this.lastCaptureByPlayer = lastCaptureByPlayer;
    }
}
