package com.fredbeardinerrush.model;

import com.fredbeardinerrush.service.IMaze;
import com.fredbeardinerrush.service.IMazeGridWriter;
import com.fredbeardinerrush.service.IMazeLoader;

public class MazeGrid implements IMaze, IMazeGridWriter {
    private final int width;
    private final int height;
    private final int[][] grid;
    private final int[][] originalGrid; // Saved for resets/replays
    private int p1RemainingDots;
    private int p2RemainingDots;

    public MazeGrid(int width, int height, IMazeLoader loader) {
        this.width = width;
        this.height = height;
        this.grid = new int[width][height];
        this.originalGrid = new int[width][height];
        if (loader != null) {
            loader.loadLayout(this);
        }
        saveOriginalGrid();
        recalculateDots();
    }

    private void saveOriginalGrid() {
        for (int x = 0; x < width; x++) {
            System.arraycopy(grid[x], 0, originalGrid[x], 0, height);
        }
    }

    @Override
    public void resetMaze() {
        for (int x = 0; x < width; x++) {
            System.arraycopy(originalGrid[x], 0, grid[x], 0, height);
        }
        recalculateDots();
    }

    @Override
    public void setCell(Position pos, int value) {
        if (pos.x() >= 0 && pos.x() < width && pos.y() >= 0 && pos.y() < height) {
            grid[pos.x()][pos.y()] = value;
        }
    }

    @Override
    public void recalculateDots() {
        p1RemainingDots = 0; // Bottom half (Fredbear)
        p2RemainingDots = 0; // Top half (Springbonnie)
        int midY = height / 2;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (grid[x][y] == 2 || grid[x][y] == 3) {
                    if (y > midY) {
                        p1RemainingDots++;
                    } else {
                        p2RemainingDots++;
                    }
                }
            }
        }
    }

    @Override
    public boolean isWall(Position pos) {
        if (pos.x() < 0 || pos.x() >= width || pos.y() < 0 || pos.y() >= height)
            return true;
        int val = grid[pos.x()][pos.y()];
        return val == 1 || val == 4 || val == 5;
    }

    @Override
    public boolean hasDot(Position pos, boolean isPlayer1Side) {
        if (pos.x() < 0 || pos.x() >= width || pos.y() < 0 || pos.y() >= height)
            return false;
        int midY = height / 2;
        boolean onBottomSide = pos.y() > midY;
        if (isPlayer1Side != onBottomSide)
            return false;
        return grid[pos.x()][pos.y()] == 2;
    }

    @Override
    public boolean hasCupcake(Position pos, boolean isPlayer1Side) {
        if (pos.x() < 0 || pos.x() >= width || pos.y() < 0 || pos.y() >= height)
            return false;
        int midY = height / 2;
        boolean onBottomSide = pos.y() > midY;
        if (isPlayer1Side != onBottomSide)
            return false;
        return grid[pos.x()][pos.y()] == 3;
    }

    @Override
    public void removeDot(Position pos) {
        if (grid[pos.x()][pos.y()] == 2) {
            grid[pos.x()][pos.y()] = 0;
            recalculateDots();
        }
    }

    @Override
    public void removeCupcake(Position pos) {
        if (grid[pos.x()][pos.y()] == 3) {
            grid[pos.x()][pos.y()] = 0;
            recalculateDots();
        }
    }

    @Override
    public int getRemainingDots(boolean isPlayer1Side) {
        return isPlayer1Side ? p1RemainingDots : p2RemainingDots;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}