package com.fredbeardinerrush.model;

import com.fredbeardinerrush.service.IMaze;
import com.fredbeardinerrush.service.IPlayer;

public class Player implements IPlayer {
    private Position position;
    private Direction direction;
    private Direction nextDirection;
    private int score;
    private final boolean isPlayer1;

    public Player(Position startPos, boolean isPlayer1) {
        this.position = startPos;
        this.direction = Direction.RIGHT;
        this.nextDirection = Direction.RIGHT;
        this.score = 0;
        this.isPlayer1 = isPlayer1;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction direction) {
        this.nextDirection = direction;
    }

    @Override
    public void move(IMaze maze) {
        Position nextPos = position.move(nextDirection);
        if (!maze.isWall(nextPos)) {
            direction = nextDirection;
            position = nextPos;
        } else {
            Position currentNext = position.move(direction);
            if (!maze.isWall(currentNext)) {
                position = currentNext;
            }
        }

        if (maze.hasDot(position, isPlayer1)) {
            maze.removeDot(position);
            incrementScore(10);
        } else if (maze.hasCupcake(position, isPlayer1)) {
            maze.removeCupcake(position);
            incrementScore(50);
        }
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void incrementScore(int points) {
        score += points;
    }

    @Override
    public boolean hasWon() {
        return false;
    }
}