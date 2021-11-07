package com.achandla.distributedchess.board;

import java.util.Arrays;

public class Chessboard {

  public static Piece[][] initChessboard() {
    Piece[][] pieces = new Piece[8][8];
    //Top left is 0,0 and white
    populatePawns(pieces, Color.BLACK);
    populatePawns(pieces, Color.WHITE);
    populatePieces(pieces, Color.BLACK);
    populatePieces(pieces, Color.WHITE);
    return pieces;
  }

  private static void populatePawns(Piece[][] pieces, Color color) {
    int row = color == Color.WHITE ? 1 : 6;
    for (int col = 0; col < 8; col++) {
      pieces[row][col] = new Piece(PieceType.PAWN, color);
    }
  }

  private static void populatePieces(Piece[][] pieces, Color color) {
    int row = color == Color.WHITE ? 0 : 7;
    pieces[row][0] = new Piece(PieceType.ROOK, color);
    pieces[row][7] = new Piece(PieceType.ROOK, color);

    pieces[row][1] = new Piece(PieceType.KNIGHT, color);
    pieces[row][6] = new Piece(PieceType.KNIGHT, color);

    pieces[row][2] = new Piece(PieceType.BISHOP, color);
    pieces[row][5] = new Piece(PieceType.BISHOP, color);

    if (color == Color.WHITE) {
      pieces[row][3] = new Piece(PieceType.QUEEN, color);
      pieces[row][4] = new Piece(PieceType.KING, color);
    } else {
      pieces[row][4] = new Piece(PieceType.QUEEN, color);
      pieces[row][3] = new Piece(PieceType.KING, color);
    }
  }

  public static Piece[][] copyChessboard(Piece[][] source) {
    Piece[][] destination = new Piece[8][8];
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (source[i][j] != null) {
          destination[i][j] = new Piece(source[i][j].getType(), source[i][j].getColor());
        }
      }
    }
    return destination;
  }
}
