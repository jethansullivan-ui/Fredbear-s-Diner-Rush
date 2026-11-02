package com.fredbeardinerrush.service;

import com.fredbeardinerrush.model.MazeGrid;
import com.fredbeardinerrush.model.Player;
import com.fredbeardinerrush.model.Position;

public class GameEngine {
    private final IMaze maze;
    private final IPlayer player1;
    private final IPlayer player2;
    private boolean gameOver;
    private String winnerText;

    // Tally tracking & persistence
    private int p1Wins;
    private int p2Wins;
    private final ITallyPersistence persistence;

    private final Position p1Spawn;
    private final Position p2Spawn;

    public GameEngine(int width, int height, IMazeLoader loader, ITallyPersistence persistence) {
        this.maze = new MazeGrid(width, height, loader);
        this.p1Spawn = loader != null ? loader.getSpawnPosition() : new Position(1, 1);
        this.p2Spawn = new Position(width - 2, 1);

        this.player1 = new Player(p1Spawn, true);
        this.player2 = new Player(p2Spawn, false);
        this.gameOver = false;
        this.winnerText = "";

        // Load saved tally
        this.persistence = persistence;
        if (this.persistence != null) {
            int[] loadedTallies = this.persistence.loadTally();
            this.p1Wins = loadedTallies[0];
            this.p2Wins = loadedTallies[1];
        } else {
            this.p1Wins = 0;
            this.p2Wins = 0;
        }
    }

    public void update() {
        if (gameOver)
            return;

        player1.move(maze);
        player2.move(maze);

        if (maze.getRemainingDots(true) == 0) {
            gameOver = true;
            p1Wins++;
            saveTally();
            winnerText = "Fredbear (Player 1) cleared their side and Wins!";
        } else if (maze.getRemainingDots(false) == 0) {
            gameOver = true;
            p2Wins++;
            saveTally();
            winnerText = "Springbonnie (Player 2) cleared their side and Wins!";
        }
    }

    private void saveTally() {
        if (persistence != null) {
            persistence.saveTally(p1Wins, p2Wins);
        }
    }

    public void resetMatch(Position p1Start, Position p2Start) {
        maze.resetMaze();
        player1.setPosition(p1Start);
        player2.setPosition(p2Start);
        player1.setDirection(com.fredbeardinerrush.model.Direction.DOWN);
        player2.setDirection(com.fredbeardinerrush.model.Direction.UP);
        gameOver = false;
        winnerText = "";
    }

    public IMaze getMaze() {
        return maze;
    }

    public IPlayer getPlayer1() {
        return player1;
    }

    public IPlayer getPlayer2() {
        return player2;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public String getWinnerText() {
        return winnerText;
    }

    public int getP1Wins() {
        return p1Wins;
    }

    public int getP2Wins() {
        return p2Wins;
    }
}