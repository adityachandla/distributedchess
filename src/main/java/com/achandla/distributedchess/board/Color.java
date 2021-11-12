package com.achandla.distributedchess.board;

public enum Color {
  BLACK,
  WHITE;

  public Color invert() {
    return this == Color.WHITE ? Color.BLACK : Color.WHITE;
  }
}
