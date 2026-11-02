package com.fredbeardinerrush.service;

import com.fredbeardinerrush.model.Position;

public class CustomLayoutLoader implements IMazeLoader {
    private final String[] asciiLayout;
    private Position spawnPos = new Position(1, 1);

    public CustomLayoutLoader(String[] asciiLayout, char spawnChar) {
        this.asciiLayout = asciiLayout;
        parseSpawnPosition(spawnChar);
    }

    private void parseSpawnPosition(char spawnChar) {
        for (int y = 0; y < asciiLayout.length; y++) {
            String row = asciiLayout[y];
            for (int x = 0; x < row.length(); x++) {
                if (row.charAt(x) == spawnChar) {
                    spawnPos = new Position(x, y);
                    return;
                }
            }
        }
    }

    @Override
    public void loadLayout(IMazeGridWriter maze) {
        int height = Math.min(asciiLayout.length, maze.getHeight());
        for (int y = 0; y < height; y++) {
            String row = asciiLayout[y];
            int width = Math.min(row.length(), maze.getWidth());
            for (int x = 0; x < width; x++) {
                char c = row.charAt(x);
                Position pos = new Position(x, y);
                if (c == '@') {
                    maze.setCell(pos, 4); // Springbonnie wall texture style
                } else if (c == '#') {
                    maze.setCell(pos, 5); // Fredbear wall texture style
                } else if (c == '.') {
                    maze.setCell(pos, 2); // Small Dot
                } else if (c == 'C') {
                    maze.setCell(pos, 3); // Cupcake
                } else {
                    maze.setCell(pos, 0); // Empty path or spawn points
                }
            }
        }
        maze.recalculateDots();
    }

    @Override
    public Position getSpawnPosition() {
        return spawnPos;
    }
}