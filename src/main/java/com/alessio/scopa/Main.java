package com.alessio.scopa;

import java.util.Scanner; // Import Scanner for input

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while (true) {
            GameLogger.logNewline(14);
            GameLogger.logMainMenu();
            while (true) {
                System.out.println("1) Start Game");
                System.out.println("2) Exit");
                System.out.print("Choose an option: ");
                String choice = input.nextLine().trim();

                if (choice.equals("1")) {
                    GameController controller = new GameController();
                    controller.startGame();
                    break;
                } else if (choice.equals("2")) {
                    System.out.println("Thanks for playing! Goodbye.");
                    input.close();
                    System.exit(0);
                } else {
                    System.out.println("Invalid option. Please enter 1 or 2.");
                }
            }
        }
    }
}
