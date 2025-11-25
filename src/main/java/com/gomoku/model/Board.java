package com.gomoku.model;

/**
 * Represents the game board for Gomoku (Five in a Row) game.
 *
 * <p>This class manages the game board state including:</p>
 * <ul>
 *   <li>Initializing and maintaining the game grid</li>
 *   <li>Validating and executing player moves</li>
 *   <li>Checking for win conditions in all directions</li>
 *   <li>Tracking game state (empty cells, full board, etc.)</li>
 * </ul>
 *
 * The board uses a 2D character array to represent the game state,
 * where empty cells are represented by ' ', 'X' for player 1, and 'O' for player 2.
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

    /**
     * Constructs a Board with the default size.
     */
    public Board() {
        this(DEFAULT_SIZE);
    }

    /**
     * Constructs a Board with a given size.
     *
     * @param size The size of one side of the square board.
     */
    public Board(int size) {
        this.size = size;
        this.grid = new char[size][size];
        initializeBoard();
    }

    /**
     * Initializes the board, setting all cells to empty and resetting the move count.
     */
    private void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = ' ';
            }
        }
        movesCount = 0;
    }

    /**
     * Attempts to place a player's mark at the specified position on the board.
     *
     * @param row Row index (0-based).
     * @param col Column index (0-based).
     * @param player The player's mark, 'X' or 'O'.
     * @return true if the move was executed successfully, false otherwise.
     */
    public boolean makeMove(int row, int col, char player) {
        if (row < 0 || row >= size || col < 0 || col >= size || grid[row][col] != ' ') {
            return false;
        }
        grid[row][col] = player;
        movesCount++;
        return true;
    }

    /**
     * Checks if the board is completely filled.
     *
     * @return true if all cells are occupied, false otherwise.
     */
    public boolean isBoardFull() {
        return movesCount == size * size;
    }

    /**
     * Determines if a player has won by forming a line of at least 5 consecutive marks from the last move.
     *
     * @param row The row of the last move.
     * @param col The column of the last move.
     * @param player The player's mark ('X' or 'O').
     * @return true if the player has won, false otherwise.
     */
    public boolean checkWin(int row, int col, char player) {
        return (checkDirection(row, col, 1, 0, player) >= 5) ||  // horizontal
                (checkDirection(row, col, 0, 1, player) >= 5) ||  // vertical
                (checkDirection(row, col, 1, 1, player) >= 5) ||  // diagonal \
                (checkDirection(row, col, 1, -1, player) >= 5);   // diagonal /
    }

    /**
     * Counts the total number of consecutive cells for a player in both directions from the last move.
     *
     * @param row Starting row index.
     * @param col Starting column index.
     * @param dRow Row increment per step (direction vector).
     * @param dCol Column increment per step (direction vector).
     * @param player The player's mark.
     * @return The total count of consecutive player's marks in this direction.
     */
    private int checkDirection(int row, int col, int dRow, int dCol, char player) {
        int count = 1;
        // Check in the positive direction
        count += countInDirection(row, col, dRow, dCol, player);
        // Check in the negative direction
        count += countInDirection(row, col, -dRow, -dCol, player);
        return count;
    }

    /**
     * Counts consecutive player's marks in a single direction from a starting cell.
     *
     * @param row Starting row.
     * @param col Starting column.
     * @param dRow Row step per move.
     * @param dCol Column step per move.
     * @param player The player's mark.
     * @return The number of same-player marks found continuously in this direction.
     */
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

    /**
     * Returns the board size.
     *
     * @return The side length of the square board.
     */
    public int getSize() {
        return size;
    }

    /**
     * Gets the contents of the cell at the specified position.
     *
     * @param row Row index.
     * @param col Column index.
     * @return Character representing the cell ('X', 'O', or blank).
     */
    public char getCell(int row, int col) {
        return grid[row][col];
    }

    /**
     * Clears the board, making all cells empty and resetting the move count.
     */
    public void clear() {
        initializeBoard();
    }
}
