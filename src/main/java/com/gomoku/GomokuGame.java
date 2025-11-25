package com.gomoku;

import com.gomoku.service.GameService;
import com.gomoku.util.DatabaseUtil;

import java.util.List;
import java.util.Scanner;


/**
 * Gomoku Game - A Java implementation of the classic board game Gomoku (Five in a Row).
 *
 * This class serves as the main entry point for the Gomoku game application.
 * It handles the game flow, user input, and displays the game state to the console.
 *
 * <b>Rules of Gomoku:</b>
 * <ul>
 *   <li>Players take turns placing their marks (X for human, O for Computer) on a 7x7 grid</li>
 *   <li>The first player to get 5 in a row (horizontally, vertically, or diagonally) wins</li>
 *   <li>If the board is full and no player has won, the game is a draw</li>
 * </ul>
 *
 * @author Mohammed Ba Dhib
 * @version 1.0
 * @since 2025-11-24
 */
public class GomokuGame {
    private static final int BOARD_SIZE = 7;
    private final GameService gameService;
    private final Scanner scanner;

    /**
     * Constructs a new GomokuGame object, initializing the game service and input scanner.
     */
    public GomokuGame() {
        this.gameService = new GameService(BOARD_SIZE);
        this.scanner = new Scanner(System.in);
    }

    /**
     * Starts the Gomoku game menu loop, allowing the user to play, view history, or exit.
     * Collects user input and responds to their selections.
     */
    public void start() {
        try (Scanner inputScanner = new Scanner(System.in)) {
            while (true) {
                printMenu();
                System.out.print("Select an option: ");

                String input = inputScanner.nextLine().trim().toLowerCase();

                switch (input) {
                    case "1":
                        playGame();
                        break;
                    case "2":
                        showHistory();
                        break;
                    case "3":
                        System.out.println("Thanks for playing!");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        }
    }

    /**
     * Manages a single session of Gomoku, alternating turns between human and computer until the game ends.
     * Handles move validation and prints the winner or draw result.
     */
    private void playGame() {
        gameService.reset();
        printBoard();

        while (!gameService.isGameOver()) {
            if (gameService.getCurrentPlayer() == 'X') {
                humanTurn();
            } else {
                gameService.makeComputerMove();
                printBoard();
            }
        }

        System.out.println("Game Over!");
        System.out.println("Winner: " +
                (gameService.getWinner() != null ? gameService.getWinner() : "It's a draw!"));
    }

    /**
     * Receives and validates human player's move input, allowing retries for invalid entries.
     * Updates the board after valid moves.
     */
    private void humanTurn() {
        while (true) {
            System.out.print("Enter row and column (e.g., '3 4'): ");
            String input = scanner.nextLine().trim();

            try {
                String[] parts = input.split("\\s+");
                if (parts.length != 2) {
                    throw new IllegalArgumentException("Please enter two numbers separated by space.");
                }

                int row = Integer.parseInt(parts[0]) - 1;
                int col = Integer.parseInt(parts[1]) - 1;

                if (gameService.makeMove(row, col)) {
                    printBoard();
                    return;
                } else {
                    System.out.println("Invalid move. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter valid numbers.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Displays the current game board on the console, showing row and column numbers and cell contents.
     */
    private void printBoard() {
        int size = gameService.getBoardSize();

        // Print column numbers
        System.out.print("   ");
        for (int i = 1; i <= size; i++) {
            System.out.printf("%2d ", i);
        }
        System.out.println("\n   " + "---".repeat(size));

        // Print each row with row numbers and cell contents
        for (int i = 0; i < size; i++) {
            System.out.printf("%2d|", i + 1);
            for (int j = 0; j < size; j++) {
                System.out.printf(" %c ", gameService.getCell(i, j));
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Prints the saved game history retrieved from the database, or notifies if none found.
     */
    private void showHistory() {
        System.out.println("\n--- Game History ---");
        List<String> history = DatabaseUtil.getGameHistory();
        if (history.isEmpty()) {
            System.out.println("No game history found.");
        } else {
            for (String game : history) {
                System.out.println(game);
            }
        }
        System.out.println("------------------\n");
    }

    /**
     * Displays the Gomoku main menu options to the user.
     */
    private void printMenu() {
        System.out.println("\n--- Gomoku Menu ---");
        System.out.println("1. New Game");
        System.out.println("2. View Game History");
        System.out.println("3. Exit");
    }

    /**
     * Main entry point. Creates and starts a new GomokuGame application.
     *
     * */
    public static void main(String[] args) {
        new GomokuGame().start();
    }
}
