package com.achandla.distributedchess.board;

public enum Color {
  BLACK,
  WHITE;

  public static Color invert(Color color) {
    return color == Color.WHITE ? Color.BLACK : Color.WHITE;
  }
}
