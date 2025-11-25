package com.gomoku.service;

import com.gomoku.model.Board;
import com.gomoku.util.DatabaseUtil;

/**
 * Service class that manages the core game logic and state for the Gomoku game.
 *
 * This class is responsible for:
 * <ul>
 *   <li>Managing the game board state</li>
 *   <li>Handling player moves and validating them</li>
 *   <li>Determining game over conditions (win/draw)</li>
 *   <li>Managing game history and persistence</li>
 *   <li>Implementing computer opponent logic</li>
 * </ul>
 *
 * @author Mohammed Ba Dhib
 * @version 1.0
 * @since 2025-11-24
 */
public class GameService {
    private final Board board;
    private char currentPlayer;
    private boolean gameOver;
    private String winner;
    private int moveCount;

    /**
     * Constructs a GameService with a specified board size.
     *
     * @param size the size of the board (NxN).
     */
    public GameService(int size) {
        this.board = new Board(size);
        this.currentPlayer = 'X'; // Human player is X
        this.gameOver = false;
        this.moveCount = 0;
    }

    /**
     * Attempts to make a move for the current player at the specified location.
     * Updates game state and checks for game over conditions (win/draw).
     *
     * @param row the row index (0-based).
     * @param col the column index (0-based).
     * @return true if the move is valid and made, false otherwise.
     */
    public boolean makeMove(int row, int col) {
        if (gameOver || !board.makeMove(row, col, currentPlayer)) {
            return false;
        }
        moveCount++;
        if (checkWin(row, col)) {
            gameOver = true;
            winner = currentPlayer == 'X' ? "Human" : "Computer";
            DatabaseUtil.saveGameResult(winner, board.getSize(), moveCount);
            return true;
        }
        if (board.isBoardFull()) {
            gameOver = true;
            winner = "Draw";
            DatabaseUtil.saveGameResult(winner, board.getSize(), moveCount);
            return true;
        }
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        return true;
    }

    /**
     * Makes a random valid move for the computer (player 'O') and updates game state.
     * Only acts if the game is not already over.
     */
    public void makeComputerMove() {
        if (gameOver) return;
        int size = board.getSize();
        int attempts = 0;
        final int MAX_ATTEMPTS = size * size;
        while (attempts < MAX_ATTEMPTS) {
            int row = (int) (Math.random() * size);
            int col = (int) (Math.random() * size);
            if (board.makeMove(row, col, 'O')) {
                moveCount++;
                if (checkWin(row, col)) {
                    gameOver = true;
                    winner = "Computer";
                    DatabaseUtil.saveGameResult(winner, board.getSize(), moveCount);
                } else if (board.isBoardFull()) {
                    gameOver = true;
                    winner = "Draw";
                    DatabaseUtil.saveGameResult(winner, board.getSize(), moveCount);
                } else {
                    currentPlayer = 'X'; // Switch back to human player
                }
                return;
            }
            attempts++;
        }
    }

    /**
     * Checks whether the current player has won after their last move.
     *
     * @param row the row of the last move.
     * @param col the column of the last move.
     * @return true if the current player has won, false otherwise.
     */
    private boolean checkWin(int row, int col) {
        return board.checkWin(row, col, currentPlayer);
    }

    /**
     * Indicates whether the game is over (win or draw).
     *
     * @return true if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Returns the winner of the game ("Human", "Computer", or "Draw").
     *
     * @return the winner as a string, or null if game is not over.
     */
    public String getWinner() {
        return winner;
    }

    /**
     * Returns which player's turn it is ('X' for human, 'O' for computer).
     *
     * @return character representing the current player.
     */
    public char getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Returns the mark on the board at the specified position.
     *
     * @param row the row index.
     * @param col the column index.
     * @return the character at the specified cell ('X', 'O', or blank).
     */
    public char getCell(int row, int col) {
        return board.getCell(row, col);
    }

    /**
     * Returns the board size used in the current game.
     *
     * @return the board size.
     */
    public int getBoardSize() {
        return board.getSize();
    }

    /**
     * Resets the game state, clears the board, and sets starting player to human.
     */
    public void reset() {
        board.clear();
        currentPlayer = 'X';
        gameOver = false;
        winner = null;
        moveCount = 0;
    }
}
