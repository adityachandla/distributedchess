package com.achandla.distributedchess.board;

public class Piece {
  private PieceType type;
  private Color color;

  public Piece(PieceType type, Color color) {
    this.type = type;
    this.color = color;
  }

  public PieceType getType() {
    return this.type;
  }

  public Color getColor() {
    return this.color;
  }

  @Override
  public String toString() {
    return String.format("%c %c", this.type.name().charAt(0), this.color.name().charAt(0));
  }
}
