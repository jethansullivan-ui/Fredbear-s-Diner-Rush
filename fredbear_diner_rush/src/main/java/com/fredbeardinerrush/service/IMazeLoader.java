package com.fredbeardinerrush.service;

import com.fredbeardinerrush.model.Position;

public interface IMazeLoader {
    void loadLayout(IMazeGridWriter maze);

    Position getSpawnPosition();
}
