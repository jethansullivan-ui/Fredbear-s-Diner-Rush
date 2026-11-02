package com.fredbeardinerrush.service;

import com.fredbeardinerrush.model.Position;

public interface IMazeGridWriter {
    void setCell(Position pos, int value);

    void recalculateDots();

    int getWidth();

    int getHeight();
}