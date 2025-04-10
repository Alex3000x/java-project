package com.alessio.scopa;

import java.util.ArrayList;

public class GameLogger {

    // Log methods
    public static void logPrint(String message) {
        System.out.print(message);
    }

    public static void logNewline(int numberLines) {
        for(int i = 0; i < numberLines; i++) System.out.println();
    }

    public static void logMainMenu() {
        System.out.println("*-------------------------------------------------------------------------------------------------------------------*");
        System.out.println("|                                              WELCOME TO SCOPA GAME!                                               |");
        System.out.println("*-------------------------------------------------------------------------------------------------------------------*");
    }

    public static void logStartGame() {
        System.out.println("*-------------------------------------------------------------------------------------------------------------------*");
        System.out.println("|                                                   GAME STARTED                                                    |");
        System.out.println("*-------------------------------------------------------------------------------------------------------------------*");
    }

    public static void logGameOver() {
        System.out.println("*-------------------------------------------------------------------------------------------------------------------*");
        System.out.println("|                                                    GAME OVER                                                      |");
        System.out.println("*-------------------------------------------------------------------------------------------------------------------*");
    }

    public static void logNewRound() {
        System.out.println("*-------------------------------------------------------------------------------------------------------------------*");
        System.out.println("|                                                    NEW ROUND                                                      |");
        System.out.println("*-------------------------------------------------------------------------------------------------------------------*");
    }

    public static void logEndRound() {
        System.out.println("*-------------------------------------------------------------------------------------------------------------------*");
        System.out.println("|                                                    END ROUND                                                      |");
        System.out.println("*-------------------------------------------------------------------------------------------------------------------*");
    }

    public static void logEndGameMenu() {
        System.out.println("*------------------------------------------------- END GAME MENU ---------------------------------------------------*");
        System.out.println("1) Play another match");
        System.out.println("2) View match statistics");
        System.out.println("3) Return to main menu");
    }

    public static void logTitle(String message) {
        System.out.println("[" + message + "]");
    }

    public static void logAction(String message, int newlines) {
        System.out.print(">" + message);
        for(int i = 0; i < newlines; i++) { System.out.print("\n"); }
    }

    public static void logMessage(String player, String message, int newlines) {
        System.out.print(" |" + player + "|  " + message);
        for(int i = 0; i < newlines; i++) { System.out.print("\n"); }
    }

    public static void logCapture(String player, ArrayList<Card> capture, Card card) {
        System.out.println(" |" + player + "|  captured " + capture + " with the " + card);
        logNewline(1);
    }

    public static void logMove(String player, Card card) {
        System.out.println(" |" + player + "|  just placed the " + card + " on the table");
        logNewline(1);
    }

    public static void logScopa(String player) {
        System.out.println(" |" + player + "|  made a Scopa! (+1)");
        logNewline(1);
    }

    public static void logPoint(String player) {
        System.out.print(" |" + player + "| who gets a point (+1)");
    }

    public static void logNoPoint() {
        System.out.print(" no one. It's a draw, nobody gets the point;");
    }

    public static void logAiHand(int handSize) {
        System.out.print("[▬");
        for (int i = 1; i < handSize; i++) { System.out.print(", ▬"); }
        System.out.print("]\n");
    }
}
