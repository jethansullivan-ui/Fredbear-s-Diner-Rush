package com.fredbeardinerrush.view;

import com.fredbeardinerrush.model.Position;
import com.fredbeardinerrush.service.IMaze;
import com.fredbeardinerrush.service.IPlayer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class SplitScreenMazePane extends Canvas {
    private final IMaze maze;
    private final IPlayer player1; // Fredbear (Bottom)
    private final IPlayer player2; // Springbonnie (Top)
    private final int tileSize;

    // Sprites
    private final Image fredUp, fredDown, fredLeft, fredRight, fredWall;
    private final Image springUp, springDown, springLeft, springRight, springWall;
    private final Image cupcakeImg;

    public SplitScreenMazePane(IMaze maze, IPlayer player1, IPlayer player2, int tileSize) {
        this.maze = maze;
        this.player1 = player1;
        this.player2 = player2;
        this.tileSize = tileSize;
        setWidth(maze.getWidth() * tileSize);
        setHeight(maze.getHeight() * tileSize);

        // Load Fredbear assets
        this.fredUp = loadImage("fredbearUp.gif");
        this.fredDown = loadImage("fredbearDown.gif");
        this.fredLeft = loadImage("fredbearLeft.gif");
        this.fredRight = loadImage("fredbearRight.gif");
        this.fredWall = loadImage("fredWall.png");

        // Load Springbonnie assets
        this.springUp = loadImage("springbonnieUp.gif");
        this.springDown = loadImage("springbonnieDown.gif");
        this.springLeft = loadImage("springbonnieLeft.gif");
        this.springRight = loadImage("springbonnieRight.gif");
        this.springWall = loadImage("springWall.png");

        // Load Cupcake
        this.cupcakeImg = loadImage("Cupcake.png");
    }

    private Image loadImage(String path) {
        try {
            return new Image(getClass().getResourceAsStream("/com/fredbeardinerrush/" + path));
        } catch (Exception e) {
            return null;
        }
    }

    public void render() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, getWidth(), getHeight());

        int midY = maze.getHeight() / 2;

        // 1. Draw Maze Terrain & Collectibles
        for (int x = 0; x < maze.getWidth(); x++) {
            for (int y = 0; y < maze.getHeight(); y++) {
                Position pos = new Position(x, y);
                if (maze.isWall(pos)) {
                    // Render Springbonnie's wall for '@' and Fredbear's wall for '#'
                    Image activeWall = maze.isSpringWall(pos) ? springWall : fredWall;
                    if (activeWall != null) {
                        gc.drawImage(activeWall, x * tileSize, y * tileSize, tileSize, tileSize);
                    } else {
                        gc.setFill(Color.DARKBLUE);
                        gc.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                    }
                } else if (maze.hasDot(pos, y <= midY ? false : true)) {
                    gc.setFill(Color.LIGHTYELLOW);
                    gc.fillOval(x * tileSize + tileSize / 3.0, y * tileSize + tileSize / 3.0, tileSize / 3.0,
                            tileSize / 3.0);
                } else if (maze.hasCupcake(pos, y <= midY ? false : true)) {
                    if (cupcakeImg != null) {
                        gc.drawImage(cupcakeImg, x * tileSize, y * tileSize, tileSize, tileSize);
                    } else {
                        gc.setFill(Color.MAGENTA);
                        gc.fillOval(x * tileSize + 4, y * tileSize + 4, tileSize - 8, tileSize - 8);
                    }
                }
            }
        }

        // 2. Draw Player 1 (Fredbear - Bottom Half)
        Position p1Pos = player1.getPosition();
        Image p1Img = switch (player1.getDirection()) {
            case UP -> fredUp;
            case DOWN -> fredDown;
            case LEFT -> fredLeft;
            case RIGHT -> fredRight;
        };
        if (p1Img != null) {
            gc.drawImage(p1Img, p1Pos.x() * tileSize, p1Pos.y() * tileSize, tileSize, tileSize);
        } else {
            gc.setFill(Color.YELLOW);
            gc.fillOval(p1Pos.x() * tileSize, p1Pos.y() * tileSize, tileSize, tileSize);
        }

        // 3. Draw Player 2 (Springbonnie - Top Half)
        Position p2Pos = player2.getPosition();
        Image p2Img = switch (player2.getDirection()) {
            case UP -> springUp;
            case DOWN -> springDown;
            case LEFT -> springLeft;
            case RIGHT -> springRight;
        };
        if (p2Img != null) {
            gc.drawImage(p2Img, p2Pos.x() * tileSize, p2Pos.y() * tileSize, tileSize, tileSize);
        } else {
            gc.setFill(Color.ORANGE);
            gc.fillOval(p2Pos.x() * tileSize, p2Pos.y() * tileSize, tileSize, tileSize);
        }
    }
}