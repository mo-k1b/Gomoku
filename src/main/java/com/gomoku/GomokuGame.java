package com.gomoku;

import com.gomoku.service.GameService;
import com.gomoku.util.DatabaseUtil;

import java.util.List;
import java.util.Scanner;

/**
 * Gomoku Game - A Java implementation of the classic board game Gomoku (Five in a Row).
 *
 * <p>This class serves as the main entry point for the Gomoku game application.
 * It handles the game flow, user input, and displays the game state to the console.</p>
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

    public GomokuGame() {
        this.gameService = new GameService(BOARD_SIZE);
        this.scanner = new Scanner(System.in);
    }

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

    private void printMenu() {
        System.out.println("\n--- Gomoku Menu ---");
        System.out.println("1. New Game");
        System.out.println("2. View Game History");
        System.out.println("3. Exit");
    }

    public static void main(String[] args) {
        new GomokuGame().start();
    }
}
