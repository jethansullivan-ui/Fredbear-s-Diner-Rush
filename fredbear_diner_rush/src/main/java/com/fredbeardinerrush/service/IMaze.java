package com.fredbeardinerrush.service;

import com.fredbeardinerrush.model.Position;

public interface IMaze {
    boolean isWall(Position pos);

    boolean hasDot(Position pos, boolean isPlayer1Side);

    boolean hasCupcake(Position pos, boolean isPlayer1Side);

    void removeDot(Position pos);

    void removeCupcake(Position pos);

    int getRemainingDots(boolean isPlayer1Side);

    int getWidth();

    int getHeight();

    void resetMaze();
}