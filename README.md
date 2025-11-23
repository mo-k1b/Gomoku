# Gomoku Game

A Java implementation of the classic Gomoku game (Five in a Row) featuring a command-line interface and a simple AI opponent.

**Author**: Mohammed Ba Dhib  
**Version**: 1.0  
**Date**: November 24, 2025

## 🎮 Features
- Play Gomoku on a 7x7 board
- Play against an AI opponent with simple random move generation
- Game history saved to an embedded H2 database
- View previous game results and statistics
- Clean, object-oriented design with separation of concerns
- Input validation and error handling

## 🛠 Requirements
- Java 17 or higher
- Maven 3.8.1 or higher

## 🚀 Getting Started

### Prerequisites
1. Ensure you have Java 17+ installed:
   ```bash
   java -version
   ```
2. Ensure Maven is installed:
   ```bash
   mvn -v
   ```

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/gomoku-game.git
   cd gomoku-game
   ```

2. Build the project:
   ```bash
   mvn clean package
   ```
4. Run the game:
   ```
   mvn exec:java -Dexec.mainClass="com.gomoku.GomokuGame"
   ```

## 🎮 How to Play
1. Launch the game and select option 1 to start a new game
2. You are 'X' and the AI is 'O'
3. On your turn, enter your move by typing the row and column numbers (e.g., "3 4" for row 3, column 4)
4. First to get 5 in a row (horizontally, vertically, or diagonally) wins!
5. View game history by selecting option 2 from the main menu

## 🏗 Project Structure
```
src/main/java/com/gomoku/
├── GomokuGame.java         # Main game class and entry point
├── model/
│   └── Board.java          # Game board implementation
├── service/
│   └── GameService.java    # Game logic and AI
└── util/
    └── DatabaseUtil.java   # Database operations
```

## 📝 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments
- Gomoku, also known as Five in a Row, is a classic strategy board game
- Built with Java and Maven
- Uses H2 Database for game history storage
3. The AI will make its move automatically
4. First to get 5 in a row (horizontally, vertically, or diagonally) wins
5. Select option 2 to view game history
6. Select option 3 to exit

## Database
Game results are stored in an H2 database file in the `data` directory.

## Author
Mohammed Ba Dhib
