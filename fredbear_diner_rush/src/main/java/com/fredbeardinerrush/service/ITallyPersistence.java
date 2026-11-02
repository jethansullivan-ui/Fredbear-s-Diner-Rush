package com.fredbeardinerrush.service;

public interface ITallyPersistence {
    int[] loadTally(); // Returns [p1Wins, p2Wins]

    void saveTally(int p1Wins, int p2Wins);
}