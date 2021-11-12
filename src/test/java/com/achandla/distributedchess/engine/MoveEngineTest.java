package com.achandla.distributedchess.engine;

import com.achandla.distributedchess.board.Chessboard;
import com.achandla.distributedchess.board.Color;
import com.achandla.distributedchess.board.Move;
import com.achandla.distributedchess.board.Piece;
import com.achandla.distributedchess.board.PieceType;
import com.achandla.distributedchess.board.Position;
import com.achandla.distributedchess.move.MoveMaker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveEngineTest {

  @Test
  public void testPawnPromotion() {
    MoveEngine moveEngine = new MoveEngine(5, Color.BLACK);
    Piece[][] pieces = new Piece[8][8];
    pieces[0][0] = new Piece(PieceType.KING, Color.WHITE);
    pieces[3][7] = new Piece(PieceType.PAWN, Color.BLACK);
    pieces[7][7] = new Piece(PieceType.KING, Color.BLACK);
    Move move = moveEngine.generateBestMove(pieces).orElseThrow(IllegalStateException::new);
    assertEquals(move.start.row, 3);
    assertEquals(move.start.col, 7);
    assertEquals(move.end.row, 2);
    assertEquals(move.end.col, 7);
  }

  @Test
  public void testCheckmate() {
    MoveEngine moveEngine = new MoveEngine(5, Color.BLACK);
    Piece[][] pieces = new Piece[8][8];
    pieces[5][7] = new Piece(PieceType.KING, Color.WHITE);
    pieces[6][0] = new Piece(PieceType.QUEEN, Color.WHITE);

    pieces[0][3] = new Piece(PieceType.QUEEN, Color.BLACK);
    pieces[1][6] = new Piece(PieceType.QUEEN, Color.BLACK);
    pieces[7][7] = new Piece(PieceType.KING, Color.BLACK);
    Move move = moveEngine.generateBestMove(pieces).orElseThrow();
    System.out.println(move);
  }

}