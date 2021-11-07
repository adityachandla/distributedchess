package com.achandla.distributedchess.move;

import com.achandla.distributedchess.board.Chessboard;
import com.achandla.distributedchess.board.Color;
import com.achandla.distributedchess.board.Move;
import com.achandla.distributedchess.board.Piece;
import com.achandla.distributedchess.board.PieceType;
import com.achandla.distributedchess.board.Position;

import java.util.Arrays;


public class CheckUtil {

  public static boolean isKingSafeAfterMove(Piece[][] pieces, Move move) {
    Position start = move.start;
    Color color = pieces[start.row][start.col].getColor();

    Piece[][] piecesCopy = Chessboard.copyChessboard(pieces);
    MoveMaker.makeMove(piecesCopy, move);

    return isKingSafe(piecesCopy, color);
  }

  private static boolean isKingSafe(Piece[][] pieces, Color color) {
    Position kingPosition = findKing(pieces, color);
    return safeStraight(pieces, kingPosition) &&
        safeDiagonal(pieces, kingPosition) &&
        safeFromKnight(pieces, kingPosition);
  }

  public static Position findKing(Piece[][] pieces, Color color) {
    for(int i = 0; i < 8; i++) {
      for(int j = 0; j < 8; j++) {
        if(isKing(pieces[i][j], color)) {
          return new Position(i,j);
        }
      }
    }
    throw new IllegalStateException("No king found :(");
  }

  private static boolean isKing(Piece piece, Color color) {
    if (piece == null) {
      return false;
    }
    return piece.getType() == PieceType.KING && piece.getColor() == color;
  }

  private static boolean safeStraight(Piece[][] pieces, Position start) {
    return checkSafetyStraight(pieces, start, pos -> pos.straight(1)) &&
        checkSafetyStraight(pieces, start, pos -> pos.straight(-1)) &&
        checkSafetyStraight(pieces, start, pos -> pos.lateral(1)) &&
        checkSafetyStraight(pieces, start, pos -> pos.lateral(-1));
  }

  private static boolean safeDiagonal(Piece[][] pieces, Position start) {
    return checkSafetyDiagonal(pieces, start, pos -> pos.diagonal(1,1) ) &&
        checkSafetyDiagonal(pieces, start, pos -> pos.diagonal(1,-1)) &&
        checkSafetyDiagonal(pieces, start, pos -> pos.diagonal(-1,1)) &&
        checkSafetyDiagonal(pieces, start, pos -> pos.diagonal(-1,-1));
  }

  private static boolean safeFromKnight(Piece[][] pieces, Position start) {
    Color color = pieces[start.row][start.col].getColor();
    return isKnightSafe(pieces, start.diagonal(1,2), color);
  }

  private static boolean isKnightSafe(Piece[][] pieces, Position position, Color color) {
    if(pieces[position.row][position.col] == null) {
      return true;
    }
    if(pieces[position.row][position.col].getType() != PieceType.KNIGHT) {
      return true;
    }
    return pieces[position.row][position.col].getColor() != color;
  }

  private static boolean checkSafetyDiagonal(Piece[][] pieces, Position start, PositionChanger positionChanger) {
    Color color = pieces[start.row][start.col].getColor();
    for(Position init = positionChanger.change(start); Position.isValid(init); init = positionChanger.change(init)) {
      if(pieces[init.row][init.col] == null) {
        continue;
      }
      if(pieces[init.row][init.col].getColor() == color) {
        return true;
      }else {
        //Rogue piece
        PieceType piece = pieces[init.row][init.col].getType();
        int rowDiff = start.row - init.row;
        int colDiff = start.col - init.col;
        return !isDangerousDiagonal(piece, rowDiff, colDiff, color);
      }
    }
    return true;
  }

  private static boolean checkSafetyStraight(Piece[][] pieces, Position start, PositionChanger positionChanger) {
    Color color = pieces[start.row][start.col].getColor();
    for(Position init = positionChanger.change(start); Position.isValid(init); init = positionChanger.change(init)) {
      if(pieces[init.row][init.col] == null) {
        continue;
      }
      if(pieces[init.row][init.col].getColor() == color) {
        return true;
      }else {
        //Rogue piece
        PieceType piece = pieces[init.row][init.col].getType();
        int diff = Math.abs(start.row - init.row) + Math.abs(start.col - init.col);
        return !isDangerousStraight(piece, diff);
      }
    }
    return true;
  }

  private static boolean isDangerousStraight(PieceType pieceType, int diff) {
    if(pieceType == PieceType.QUEEN || pieceType == PieceType.ROOK) {
      return true;
    }
    return diff == 1 && pieceType == PieceType.KING;
  }

  private static boolean isDangerousDiagonal(PieceType pieceType, int rowDiff, int colDiff, Color kingColor) {
    if(pieceType == PieceType.QUEEN || pieceType == PieceType.BISHOP) {
      return true;
    }
    if(Math.abs(rowDiff) == 1 && Math.abs(colDiff) == 1 && pieceType == PieceType.KING) {
      return true;
    }
    Color enemyColor = kingColor == Color.WHITE ? Color.BLACK : Color.WHITE;
    int pawnForward = enemyColor == Color.WHITE ? 1 : -1;
    if(rowDiff == pawnForward && colDiff == 1) {
      return pieceType == PieceType.PAWN;
    }
    return false;
  }
}
