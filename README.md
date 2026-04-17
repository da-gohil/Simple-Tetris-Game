# Tetris Game Coded in Java Core (Open Source)

A high-performance, open-source Tetris implementation built from scratch using Java. This project demonstrates core game development principles, including custom game loops, collision detection, and real-time state management using the Java Swing framework

## Overview
This version of Tetris focuses on clean code and modular architecture. It includes a fully functional game engine, SRS-style logic, and a dedicated audio manager.

## Tech Stack
- **Language:** Java
- **GUI Framework:** Swing & AWT (2D Rendering)
- **Audio:** Java Sound API (javax.sound.sampled)
- **Architecture:** Component-based design with a centralized Game Loop

## Core Implementation Details

### 1) The Game Loop & Timing
The engine utilizes a dedicated thread to manage the game heartbeat, ensuring consistent frame rates and smooth movement.
- **Auto-Drop:** Implemented using a delta-time approach where gravity increases as the level rises.
- **The "Sliding" Mechanic:** Includes a lock-delay system, allowing players a brief window to move the Tetromino horizontally once it touches a surface before it becomes static.

### 2) Tetromino Logic & Rotation
The game features all seven standard Tetromino shapes (I, J, L, O, S, T, Z).
- **Coordinate System:** Each Tetromino is managed as a set of blocks relative to a pivot point.
- **Rotation:** Handled by transforming relative coordinates, accompanied by collision checks to prevent rotating into walls or existing structures.

### 3) Collision Detection & Grid Management
- **Play Area:** A 2D grid manages the state of the "Static Blocks."
- **Line Deletion:** After a piece is placed, the engine scans the grid. Full rows are removed, and all rows above are shifted down using a cascading update.
- **Collision Types:** - **Wall/Floor:** Prevents pieces from leaving the bounds.
    - **Static Collision:** Detects interaction with previously placed blocks.

### 4) Audio & Visuals
- **Sound Manager:** Handles background music and sound effects (SFX) for rotation, clearing lines, and game-over states.
- **UI:** Features a "Next Piece" preview, real-time score tracking, and level progression.

## 🕹 Controls

| Key | Action |
| :--- | :--- |
| **Left Arrow** | Move Left |
| **Right Arrow** | Move Right |
| **Up Arrow** | Rotate |
| **Down Arrow** | Soft Drop |
| **Space** | Hard Drop |

## 🚀 Getting Started

### Prerequisites
- Java Development Kit (JDK) 11 / 15 / 17 or higher

### Running the Game
1) Clone the repository:
   ```bash
   git clone [https://github.com/da-gohil/Simple-Tetris-Game.git]
2) cd tetris-java/src
3) Run the project javac Main.java , java Main

You can also create a jar file of the entire project and use it for playing everytime you are bored and want to play a game locally without installations of any software

### 🤝 Contributing
Feel free to open a pull request! Whether it's adding new themes, improving the rotation logic, or adding a high-score leaderboard, all enhancements are welcome! 
