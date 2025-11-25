package com.gomoku.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles database operations for the Gomoku game, including initialization,
 * saving results, and fetching game history.
 */
public class DatabaseUtil {
    private static final String DB_URL = "jdbc:h2:./data/gomoku";
    private static final String CREATE_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS game_results (
                id INT AUTO_INCREMENT PRIMARY KEY,
                winner VARCHAR(10) NOT NULL,
                board_size INT NOT NULL,
                move_count INT NOT NULL,
                played_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;

    static {
        initializeDatabase();
    }

    /**
     * Initializes the game database and creates the results table if it doesn't exist.
     * Called automatically on class load.
     */
    private static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(CREATE_TABLE_SQL);
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    /**
     * Returns a connection to the local H2 game database.
     *
     * @return A Connection object to the Gomoku game database.
     * @throws SQLException If a database access error occurs.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, "sa", "");
    }

    /**
     * Saves the result of a finished game to the database.
     *
     * @param winner    The name or mark of the winning player.
     * @param boardSize The size of the board used in the game.
     * @param moveCount Total number of moves played in the game.
     */
    public static void saveGameResult(String winner, int boardSize, int moveCount) {
        String sql = "INSERT INTO game_results (winner, board_size, move_count) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, winner);
            pstmt.setInt(2, boardSize);
            pstmt.setInt(3, moveCount);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error saving game result: " + e.getMessage());
        }
    }

    /**
     * Retrieves the history of all completed games from the database.
     *
     * @return A list of game history entries in human-readable format.
     */
    public static List<String> getGameHistory() {
        List<String> history = new ArrayList<>();
        String sql = "SELECT winner, board_size, move_count, played_at FROM game_results ORDER BY played_at DESC";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String entry = String.format("Winner: %s | Board: %dx%d | Moves: %d | %s",
                        rs.getString("winner"),
                        rs.getInt("board_size"),
                        rs.getInt("board_size"),
                        rs.getInt("move_count"),
                        rs.getTimestamp("played_at"));
                history.add(entry);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving game history: " + e.getMessage());
        }
        return history;
    }
}
