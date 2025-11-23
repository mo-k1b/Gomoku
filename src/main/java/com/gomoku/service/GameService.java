package com.gomoku.service;

import com.gomoku.model.Board;
import com.gomoku.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Service class that manages the core game logic and state for the Gomoku game.
 * 
 * <p>This class is responsible for:
 * <ul>
 *   <li>Managing the game board state</li>
 *   <li>Handling player moves and validating them</li>
 *   <li>Determining game over conditions (win/draw)</li>
 *   <li>Managing game history and persistence</li>
 *   <li>Implementing AI opponent logic</li>
 * </ul></p>
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

    public GameService(int size) {
        this.board = new Board(size);
        this.currentPlayer = 'X'; // Human player is X
        this.gameOver = false;
        this.moveCount = 0;
    }

    public boolean makeMove(int row, int col) {
        if (gameOver || !board.makeMove(row, col, currentPlayer)) {
            return false;
        }
        
        moveCount++;
        
        if (checkWin(row, col)) {
            gameOver = true;
            winner = currentPlayer == 'X' ? "Human" : "AI";
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
    
    public void makeAIMove() {
        if (gameOver) return;
        
        // Simple AI: make a random valid move
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
                    winner = "AI";
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
    
    private boolean checkWin(int row, int col) {
        return board.checkWin(row, col, currentPlayer);
    }
    
    public boolean isGameOver() {
        return gameOver;
    }
    
    public String getWinner() {
        return winner;
    }
    
    public char getCurrentPlayer() {
        return currentPlayer;
    }
    
    public char getCell(int row, int col) {
        return board.getCell(row, col);
    }
    
    public int getBoardSize() {
        return board.getSize();
    }
    
    public void reset() {
        board.clear();
        currentPlayer = 'X';
        gameOver = false;
        winner = null;
        moveCount = 0;
    }
}
