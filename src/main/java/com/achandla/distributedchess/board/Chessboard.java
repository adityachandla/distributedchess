package com.achandla.distributedchess.board;

import java.util.Arrays;

public class Chessboard {

  private Piece[][] pieces;

  public Chessboard() {
    pieces = new Piece[8][8];
    //Top left is 0,0 and white
    populatePawns(Color.BLACK);
    populatePawns(Color.WHITE);
    populatePieces(Color.BLACK);
    populatePieces(Color.WHITE);
  }

  private void populatePawns(Color color) {
    int row = color == Color.WHITE ? 1 : 6;
    for(int col = 0; col < 8; col++) {
      pieces[row][col] = new Piece(PieceType.PAWN, color);
    }
  }

  private void populatePieces(Color color) {
    int row = color == Color.WHITE ? 0 : 7;
    pieces[row][0] = new Piece(PieceType.ROOK, color);
    pieces[row][7] = new Piece(PieceType.ROOK, color);

    pieces[row][1] = new Piece(PieceType.KNIGHT, color);
    pieces[row][6] = new Piece(PieceType.KNIGHT, color);

    pieces[row][2] = new Piece(PieceType.BISHOP, color);
    pieces[row][5] = new Piece(PieceType.BISHOP, color);

    if(color == Color.WHITE) {
      pieces[row][3] = new Piece(PieceType.QUEEN, color);
      pieces[row][4] = new Piece(PieceType.KING, color);
    }else {
      pieces[row][4] = new Piece(PieceType.QUEEN, color);
      pieces[row][3] = new Piece(PieceType.KING, color);
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for(int idx = 0; idx < pieces.length; idx++) {
      sb.append(Arrays.toString(pieces[idx]));
      sb.append("\n");
    }
    return sb.toString();
  }

  public Piece[][] getPieces() {
    return this.pieces;
  }
}
