package com.achandla.distributedchess.move;

import com.achandla.distributedchess.board.Color;
import com.achandla.distributedchess.board.Move;
import com.achandla.distributedchess.board.Piece;
import com.achandla.distributedchess.board.PieceType;
import com.achandla.distributedchess.board.Position;

public class MoveMaker {

  public static void makeMove(Piece[][] pieces, Move move) {
    Position start = move.start;
    Position end = move.end;
    Piece sourcePiece = pieces[start.row][start.col];
    if (isPromotion(sourcePiece, end)) {
      promoteToQueen(sourcePiece);
    }
    pieces[start.row][start.col] = null;
    pieces[end.row][end.col] = sourcePiece;
  }

  private static boolean isPromotion(Piece source, Position end) {
    if (source.getType() != PieceType.PAWN) {
      return false;
    }
    int endPosition = source.getColor() == Color.WHITE ? 7 : 0;
    return endPosition == end.row;
  }

  private static void promoteToQueen(Piece piece) {
    piece.setType(PieceType.QUEEN);
  }
}
