package com.alessio.scopa;

import com.alessio.scopa.enums.EarnedBy;
import com.alessio.scopa.enums.PointType;

import java.util.ArrayList;
import java.util.EnumMap;

public class GameState {
    // Cards on the table
    private ArrayList<Card> tableCards;

    // Cards in the players' hand
    private ArrayList<Card> playerHand;
    private ArrayList<Card> aiHand;

    // Captured cards by players
    private ArrayList<Card> playerCapturedCards;
    private ArrayList<Card> aiCapturedCards;

    private boolean lastCaptureByPlayer;
    private String playerName;

    // Players scores
    private int playerScore;
    private int aiScore;

    private EnumMap<PointType, EarnedBy> pointWinners;

    // Constructor
    public GameState() {
        tableCards = new ArrayList<>();
        playerHand = new ArrayList<>();
        aiHand = new ArrayList<>();
        playerCapturedCards = new ArrayList<>();
        aiCapturedCards = new ArrayList<>();
        playerScore = 0;
        aiScore = 0;
    }

    // Methods
    public void clearTableCards() {
        tableCards.clear();
    }

    public void clearPlayerHand() {
        playerHand.clear();
    }

    public void clearAiHand() {
        aiHand.clear();
    }

    public void clearPlayerCapturedCards() {
        playerCapturedCards.clear();
    }

    public void clearAiCapturedCards() {
        aiCapturedCards.clear();
    }

    public void resetLastCaptureByPlayer() {
        lastCaptureByPlayer = true;
    }

    public void resetPlayerScore () {
        playerScore = 0;
    }

    public void resetAiScore () {
        aiScore = 0;
    }

    public void addPlayerScore(int pointsToAdd) {
        playerScore += pointsToAdd;
    }

    public void addAiScore(int pointsToAdd) {
        aiScore += pointsToAdd;
    }

    public void resetPointWinners() {
        pointWinners = new EnumMap<>(PointType.class);
    }

    // Getter
    public ArrayList<Card> getTableCards() {
        return tableCards;
    }

    public ArrayList<Card> getPlayerHand() {
        return playerHand;
    }

    public ArrayList<Card> getAiHand() {
        return aiHand;
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

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public int getAiScore() {
        return aiScore;
    }

    public EnumMap<PointType, EarnedBy> getPointWinners() {
        return pointWinners;
    }

    // Setter
    public void setTableCards(ArrayList<Card> tableCards) {
        this.tableCards = tableCards;
    }

    public void setPlayerHand(ArrayList<Card> playerHand) {
        this.playerHand = playerHand;
    }

    public void setAiHand(ArrayList<Card> aiHand) {
        this.aiHand = aiHand;
    }

    public void setPlayerCapturedCards(ArrayList<Card> playerCapturedCards) {
        this.playerCapturedCards = playerCapturedCards;
    }

    public void setAiCapturedCards(ArrayList<Card> aiCapturedCards) {
        this.aiCapturedCards = aiCapturedCards;
    }

    public void setLastCaptureByPlayer(boolean lastCaptureByPlayer) {
        this.lastCaptureByPlayer = lastCaptureByPlayer;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public void setAiScore(int aiScore) {
        this.aiScore = aiScore;
    }

    public void setPointWinners(PointType type, EarnedBy winner) {
        pointWinners.put(type, winner);
    }
}
