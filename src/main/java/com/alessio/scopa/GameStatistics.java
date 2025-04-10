package com.alessio.scopa;

import com.alessio.scopa.enums.EarnedBy;
import com.alessio.scopa.enums.PointType;

public class GameStatistics {
    private int totalCards = 0;
    private int totalCoins = 0;
    private int totalRounds = 0;

    private int finalScorePlayer = 0;
    private int finalScoreAI = 0;

    private int CardsPointsPlayer = 0;
    private int CoinsPointsPlayer = 0;
    private int SettebelloPointsPlayer = 0;
    private int PrimePointsPlayer = 0;
    private int totalScopaPlayer = 0;

    private int CardsPointsAI = 0;
    private int CoinsPointsAI = 0;
    private int SettebelloPointsAI = 0;
    private int PrimePointsAI = 0;
    private int totalScopaAI = 0;

    private int totalCoinsPlayer = 0;
    private int totalCoinsAI = 0;

    private int totalCardsPlayer = 0;
    private int totalCardsAI = 0;

    //private int HighestCapturePlayer = 0;
    //private int HighestCaptureAI = 0;

    public void updateRoundStatistics(GameState gameState) {
        int standardPointsPlayer = 0;
        int standardPointsAI = 0;

        totalRounds++;
        totalCards += 40;
        totalCoins += 10;

        int currentScorePlayer = gameState.getPlayerScore() - finalScorePlayer;
        int currentScoreAI = gameState.getAiScore() - finalScoreAI;
        finalScorePlayer = gameState.getPlayerScore();
        finalScoreAI = gameState.getAiScore();


        // Estimate the standard 4 points
        EarnedBy cardsWinner = gameState.getPointWinners().get(PointType.CARDS);
        if (cardsWinner == EarnedBy.PLAYER) {
            CardsPointsPlayer++;
            standardPointsPlayer++;

        } else if (cardsWinner == EarnedBy.AI) {
            CardsPointsAI++;
            standardPointsAI++;
        }
        EarnedBy coinsWinner = gameState.getPointWinners().get(PointType.COINS);
        if (coinsWinner == EarnedBy.PLAYER) {
            CoinsPointsPlayer++;
            standardPointsPlayer++;
        } else if (coinsWinner == EarnedBy.AI) {
            CoinsPointsAI++;
            standardPointsAI++;
        }
        EarnedBy settebelloWinner = gameState.getPointWinners().get(PointType.SETTEBELLO);
        if (settebelloWinner == EarnedBy.PLAYER) {
            SettebelloPointsPlayer++;
            standardPointsPlayer++;
        } else if (settebelloWinner == EarnedBy.AI) {
            SettebelloPointsAI++;
            standardPointsAI++;
        }
        EarnedBy primeWinner = gameState.getPointWinners().get(PointType.PRIME);
        if (primeWinner == EarnedBy.PLAYER) {
            PrimePointsPlayer++;
            standardPointsPlayer++;
        } else if (primeWinner == EarnedBy.AI) {
            PrimePointsAI++;
            standardPointsAI++;
        }

        // Estimate the scopas (score minus standard points)
        totalScopaPlayer += currentScorePlayer - standardPointsPlayer;
        totalScopaAI += currentScoreAI - standardPointsAI;

        // Count Coins cards
        totalCoinsPlayer += (int) gameState.getPlayerCapturedCards().stream().filter(c -> c.getSuit().equals("Coins")).count();
        totalCoinsAI += (int) gameState.getAiCapturedCards().stream().filter(c -> c.getSuit().equals("Coins")).count();

        // Count total cards
        totalCardsPlayer += gameState.getPlayerCapturedCards().size();
        totalCardsAI += gameState.getAiCapturedCards().size();
    }

    public void showFinalStatistics(String playerName) {
        GameLogger.logNewline(2);
        System.out.println("*---------------------------------------------- END GAME STATISTICS ------------------------------------------------*");
        GameLogger.logPrint(" -Total rounds played: " + totalRounds); GameLogger.logNewline(2);

        GameLogger.logPrint(" -Final score:"); GameLogger.logNewline(1);
        GameLogger.logPrint("\t" + playerName + ": " + finalScorePlayer); GameLogger.logNewline(1);
        GameLogger.logPrint("\t\t├Cards points: " + CardsPointsPlayer); GameLogger.logNewline(1);
        GameLogger.logPrint("\t\t├Coins points: " + CoinsPointsPlayer); GameLogger.logNewline(1);
        GameLogger.logPrint("\t\t├Number of Settebello: " + SettebelloPointsPlayer); GameLogger.logNewline(1);
        GameLogger.logPrint("\t\t├Prime points: " + PrimePointsPlayer); GameLogger.logNewline(1);
        GameLogger.logPrint("\t\t└Number of scopa: " + totalScopaPlayer); GameLogger.logNewline(1);
        GameLogger.logPrint("\tAI: " + finalScoreAI); GameLogger.logNewline(1);
        GameLogger.logPrint("\t\t├Cards points: " + CardsPointsAI); GameLogger.logNewline(1);
        GameLogger.logPrint("\t\t├Coins points: " + CoinsPointsAI); GameLogger.logNewline(1);
        GameLogger.logPrint("\t\t├Number of Settebello: " + SettebelloPointsAI); GameLogger.logNewline(1);
        GameLogger.logPrint("\t\t├Prime points: " + PrimePointsAI); GameLogger.logNewline(1);
        GameLogger.logPrint("\t\t└Number of scopa: " + totalScopaAI); GameLogger.logNewline(2);

        GameLogger.logPrint(" -" + playerName + " statistics:"); GameLogger.logNewline(1);
        GameLogger.logPrint("\t├Coins cards: " + totalCoinsPlayer);
        GameLogger.logPrint(" (" + (totalCoins == 0 ? 0 : (totalCoinsPlayer * 100 / totalCoins)) + "%)"); GameLogger.logNewline(1);
        GameLogger.logPrint("\t└Total cards captured: " + totalCardsPlayer);
        GameLogger.logPrint(" (" + (totalCards == 0 ? 0 : (totalCardsPlayer * 100 / totalCards)) + "%)"); GameLogger.logNewline(2);
        //GameLogger.logPrint("\t└Highest number of cards captured in a single move: " + HighestCapturePlayer); GameLogger.logNewline(2);

        GameLogger.logPrint(" -AI statistics:"); GameLogger.logNewline(1);
        GameLogger.logPrint("\t├Coins cards: " + totalCoinsAI);
        GameLogger.logPrint(" (" + (totalCoins == 0 ? 0 : (totalCoinsAI * 100 / totalCoins)) + "%)"); GameLogger.logNewline(1);
        GameLogger.logPrint("\t└Total cards captured: " + totalCardsAI);
        GameLogger.logPrint(" (" + (totalCards == 0 ? 0 : (totalCardsAI * 100 / totalCards)) + "%)"); GameLogger.logNewline(2);
        //GameLogger.logPrint("\t└Highest number of cards captured in a single move: " + HighestCaptureAI); GameLogger.logNewline(2);
    }

    public void resetStatistics() {
        totalCards = 0;
        totalRounds = 0;
        finalScorePlayer = 0;
        finalScoreAI = 0;
        CardsPointsPlayer = 0;
        CoinsPointsPlayer = 0;
        SettebelloPointsPlayer = 0;
        PrimePointsPlayer = 0;
        totalScopaPlayer = 0;
        CardsPointsAI = 0;
        CoinsPointsAI = 0;
        SettebelloPointsAI = 0;
        PrimePointsAI = 0;
        totalScopaAI = 0;
        totalCoinsPlayer = 0;
        totalCoinsAI = 0;
        totalCardsPlayer = 0;
        totalCardsAI = 0;
        //HighestCapturePlayer = 0;
        //HighestCaptureAI = 0;
    }
}
