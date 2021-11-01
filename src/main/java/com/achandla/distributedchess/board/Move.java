package com.achandla.distributedchess.board;

public class Move {
  public Position start;
  public Position end;

  public Move(Position start, Position end) {
    this.start = start;
    this.end = end;
  }

  @Override
  public String toString() {
    return String.format("%s -> %s", start, end);
  }
}
