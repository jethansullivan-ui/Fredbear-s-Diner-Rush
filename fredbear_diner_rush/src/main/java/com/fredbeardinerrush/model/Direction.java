package com.fredbeardinerrush.model;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    public boolean isOpposite(Direction other) {
        if (this == UP && other == DOWN)
            return true;
        if (this == DOWN && other == UP)
            return true;
        if (this == LEFT && other == RIGHT)
            return true;
        return this == RIGHT && other == LEFT;

    }
}
