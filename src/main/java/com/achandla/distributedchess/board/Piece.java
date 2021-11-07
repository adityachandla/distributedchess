package com.achandla.distributedchess.board;

public class Piece {
  private PieceType type;
  private Color color;
  private int value;

  public Piece(PieceType type, Color color) {
    this.type = type;
    this.color = color;
    this.value = getValue(type);
  }

  private int getValue(PieceType type) {
    return switch (type) {
      case PAWN -> 1;
      case QUEEN -> 9;
      case KING -> 0;
      case ROOK -> 5;
      case BISHOP, KNIGHT -> 3;
    };
  }

  public int getValue() {
    return this.value;
  }

  public PieceType getType() {
    return this.type;
  }

  public void setType(PieceType pieceType) {
    this.type = pieceType;
  }

  public Color getColor() {
    return this.color;
  }

  @Override
  public String toString() {
    if(this.type == PieceType.KING) {
      return "K" + this.color.name().charAt(0);
    }
    return String.format("%c %c", this.type.name().charAt(0), this.color.name().charAt(0));
  }
}
