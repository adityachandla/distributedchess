package com.achandla.distributedchess.move;

import com.achandla.distributedchess.board.Color;
import com.achandla.distributedchess.board.Move;
import com.achandla.distributedchess.board.Piece;
import com.achandla.distributedchess.board.PieceType;
import com.achandla.distributedchess.board.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckUtilTest {

  @Test
  public void verifyPawnChecks() {
    Piece[][] board = new Piece[8][8];
    board[0][0] = new Piece(PieceType.KING, Color.WHITE);
    board[2][1] = new Piece(PieceType.PAWN, Color.BLACK);
    Move move = new Move(new Position(0,0), new Position(1, 0));
    assertFalse(CheckUtil.isKingSafeAfterMove(board, move));

    board = new Piece[8][8];
    board[0][2] = new Piece(PieceType.KING, Color.WHITE);
    board[2][1] = new Piece(PieceType.PAWN, Color.BLACK);
    move = new Move(new Position(0,2), new Position(1, 2));
    assertFalse(CheckUtil.isKingSafeAfterMove(board, move));

    board = new Piece[8][8];
    board[7][0] = new Piece(PieceType.KING, Color.BLACK);
    board[5][1] = new Piece(PieceType.PAWN, Color.WHITE);
    move = new Move(new Position(7,0), new Position(6, 0));
    assertFalse(CheckUtil.isKingSafeAfterMove(board, move));

    board = new Piece[8][8];
    board[7][2] = new Piece(PieceType.KING, Color.BLACK);
    board[5][1] = new Piece(PieceType.PAWN, Color.WHITE);
    move = new Move(new Position(7,2), new Position(6, 2));
    assertFalse(CheckUtil.isKingSafeAfterMove(board, move));
  }

}