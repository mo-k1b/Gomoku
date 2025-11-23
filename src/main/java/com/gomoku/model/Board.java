package com.gomoku.model;

/**
 * Represents the game board for Gomoku (Five in a Row) game.
 * 
 * <p>This class manages the game board state including:
 * <ul>
 *   <li>Initializing and maintaining the game grid</li>
 *   <li>Validating and executing player moves</li>
 *   <li>Checking for win conditions in all directions</li>
 *   <li>Tracking game state (empty cells, full board, etc.)</li>
 * </ul></p>
 *
 * <p>The board uses a 2D character array to represent the game state,
 * where empty cells are represented by '\u0000', 'X' for player 1, and 'O' for player 2.</p>
 *
 * @author Mohammed Ba Dhib
 * @version 1.0
 * @since 2025-11-24
 */
public class Board {
    private static final int DEFAULT_SIZE = 7;
    private final int size;
    private final char[][] grid;
    private int movesCount;

    public Board() {
        this(DEFAULT_SIZE);
    }

    public Board(int size) {
        this.size = size;
        this.grid = new char[size][size];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = ' ';
            }
        }
        movesCount = 0;
    }

    public boolean makeMove(int row, int col, char player) {
        if (row < 0 || row >= size || col < 0 || col >= size || grid[row][col] != ' ') {
            return false;
        }
        grid[row][col] = player;
        movesCount++;
        return true;
    }

    public boolean isBoardFull() {
        return movesCount == size * size;
    }

    public boolean checkWin(int row, int col, char player) {
        return (checkDirection(row, col, 1, 0, player) >= 5) ||  // horizontal
               (checkDirection(row, col, 0, 1, player) >= 5) ||  // vertical
               (checkDirection(row, col, 1, 1, player) >= 5) ||  // diagonal \
               (checkDirection(row, col, 1, -1, player) >= 5);   // diagonal /
    }

    private int checkDirection(int row, int col, int dRow, int dCol, char player) {
        int count = 1;
        // Check in the positive direction
        count += countInDirection(row, col, dRow, dCol, player);
        // Check in the negative direction
        count += countInDirection(row, col, -dRow, -dCol, player);
        return count;
    }

    private int countInDirection(int row, int col, int dRow, int dCol, char player) {
        int count = 0;
        int r = row + dRow;
        int c = col + dCol;
        
        while (r >= 0 && r < size && c >= 0 && c < size && grid[r][c] == player) {
            count++;
            r += dRow;
            c += dCol;
        }
        return count;
    }

    public int getSize() {
        return size;
    }

    public char getCell(int row, int col) {
        return grid[row][col];
    }

    public void clear() {
        initializeBoard();
    }
}
