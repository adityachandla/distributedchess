package com.achandla.distributedchess.move;

import com.achandla.distributedchess.board.Position;

@FunctionalInterface
public interface PositionChanger {
    Position change(Position initial);
}
