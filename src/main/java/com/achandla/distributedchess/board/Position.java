package com.achandla.distributedchess.board;

public class Position {
  public int row;
  public int col;

  public Position(int row, int col){
    this.row = row;
    this.col = col;
  }

  public Position copy() {
    return new Position(this.row, this.col);
  }

  public Position straight(int diff) {
    return new Position(this.row+diff, this.col);
  }

  public Position lateral(int diff) {
    return new Position(this.row, this.col+diff);
  }

  public Position diagonal(int rowDiff, int colDiff) {
    return new Position(this.row+rowDiff, this.col+colDiff);
  }

  public static boolean isValid(Position position) {
    return position.row >= 0 && position.row < 8 &&
        position.col >= 0 && position.col < 8;
  }

  @Override
  public String toString() {
    return String.format("(%d,%d)", row, col);
  }
}
