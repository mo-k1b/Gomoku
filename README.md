# Gomoku Game

A Java implementation of the classic Gomoku game (Five in a Row) featuring a command-line interface and a simple AI opponent.

**Author**: Mohammed Ba Dhib  
**Version**: 1.0  
**Date**: November 24, 2025

## Features
- Play Gomoku on a 7x7 board
- Play against an computer opponent with simple random move generation
- Clean, object-oriented design with separation of concerns
- Input validation and error handling

### Installation
1. Clone the repository:
   ```bash
   git [clone https://github.com/mo-k1b/Gomoku
   cd Gomoku
   ```

2. Run the game:


## How to Play
1. Launch the game and select option 1 to start a new game
2. You are 'X' and the computer is 'O'
3. On your turn, enter your move by typing the row and column numbers (e.g., "3 4" for row 3, column 4)
4. First to get 5 in a row (horizontally, vertically, or diagonally) wins!
5. View game history by selecting option 2 from the main menu

## Project Structure
```
src/main/java/com/gomoku/
├── GomokuGame.java         # Main game class and entry point
├── model/
│   └── Board.java          # Game board implementation
├── service/
│   └── GameService.java    # Game logic and computer moves
└── util/
    └── DatabaseUtil.java   # Database operations
```

## Database
Game results are stored in an H2 database file in the `data` directory.

## Author
Mohammed Ba Dhib
