package com.gomoku.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board(7);
    }

    @Test
    void testInitialBoardIsEmpty() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                assertEquals(' ', board.getCell(i, j), "Cell should be empty initially");
            }
        }
    }

    @Test
    void testMakeValidMove() {
        assertTrue(board.makeMove(0, 0, 'X'), "Valid move should be accepted");
        assertEquals('X', board.getCell(0, 0), "Cell should contain player's mark");
    }

    @Test
    void testMakeInvalidMove() {
        board.makeMove(0, 0, 'X');
        assertFalse(board.makeMove(0, 0, 'O'), "Move to occupied cell should be rejected");
        assertFalse(board.makeMove(-1, 0, 'X'), "Move outside board should be rejected");
        assertFalse(board.makeMove(7, 7, 'X'), "Move outside board should be rejected");
    }

    @Test
    void testCheckWinHorizontal() {
        // Place 5 X's in a row horizontally
        for (int i = 0; i < 5; i++) {
            board.makeMove(0, i, 'X');
            if (i < 4) {
                assertFalse(board.checkWin(0, i, 'X'), "Should not win with less than 5 in a row");
            }
        }
        assertTrue(board.checkWin(0, 4, 'X'), "Should win with 5 in a row");
    }

    @Test
    void testCheckWinVertical() {
        // Place 5 O's in a column vertically
        for (int i = 0; i < 5; i++) {
            board.makeMove(i, 0, 'O');
            if (i < 4) {
                assertFalse(board.checkWin(i, 0, 'O'), "Should not win with less than 5 in a column");
            }
        }
        assertTrue(board.checkWin(4, 0, 'O'), "Should win with 5 in a column");
    }

    @Test
    void testIsBoardFull() {
        assertFalse(board.isBoardFull(), "New board should not be full");
        
        // Fill the board
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                board.makeMove(i, j, 'X');
            }
        }
        
        assertTrue(board.isBoardFull(), "Fully occupied board should be full");
    }
}
