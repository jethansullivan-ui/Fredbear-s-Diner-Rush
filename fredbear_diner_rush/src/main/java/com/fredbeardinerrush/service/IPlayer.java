package com.fredbeardinerrush.service;

import com.fredbeardinerrush.model.Position;

import com.fredbeardinerrush.model.Direction;

public interface IPlayer {
    Position getPosition();

    void setPosition(Position position);

    Direction getDirection();

    void setDirection(Direction direction);

    void move(IMaze maze);

    int getScore();

    void incrementScore(int points);

    boolean hasWon();

}
