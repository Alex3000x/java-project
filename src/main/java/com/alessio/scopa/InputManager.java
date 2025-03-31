package com.alessio.scopa;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class InputManager {
    private final Scanner scanner;
    private final GameController controller;

    public InputManager(Scanner scanner, GameController controller) {
        this.scanner = scanner;
        this.controller = controller;
    }

    public String readLineWithQuit(String namePlayer, String message) {
        while (true) {
            GameLogger.logMessage(namePlayer, message, 0);
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("quit")) {
                GameLogger.logAction("Are you sure you want to quit and return to the main menu? (yes/no): ", 0);
                String confirm = scanner.nextLine().trim().toLowerCase();
                if (confirm.equals("yes")) {
                    GameLogger.logAction("Returning to main menu...", 0);
                    wait(2);
                    GameLogger.logNewline(14);
                    controller.setQuitToMenu(true);  // Notify to GameController
                    return null;    // Exit the selection and the stream detects it
                } else {
                    GameLogger.logAction("Returning to game.", 1);
                    wait(0);
                    continue;   // Back to ask the player the input
                }
            }

            return input;
        }
    }

    private static void wait(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
                throw new RuntimeException(e);
        }
    }
}
