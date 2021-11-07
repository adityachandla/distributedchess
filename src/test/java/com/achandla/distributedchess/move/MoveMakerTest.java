package com.achandla.distributedchess.move;

import com.achandla.distributedchess.board.Color;
import com.achandla.distributedchess.board.Move;
import com.achandla.distributedchess.board.Piece;
import com.achandla.distributedchess.board.PieceType;
import com.achandla.distributedchess.board.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveMakerTest {

  @Test
  public void verifyPromoteToQueenWhite() {
    Piece[][] pieces = new Piece[8][8];
    Piece pawn = new Piece(PieceType.PAWN, Color.WHITE);
    pieces[6][0] = pawn;
    Move move = new Move(new Position(6,0), new Position(7, 0));

    MoveMaker.makeMove(pieces, move);

    assertEquals(pieces[7][0].getType(), PieceType.QUEEN);
    assertEquals(pieces[7][0].getColor(), Color.WHITE);
    assertNull(pieces[6][0]);
  }

  @Test
  public void verifyPromoteToQueenBlack() {
    Piece[][] pieces = new Piece[8][8];
    Piece pawn = new Piece(PieceType.PAWN, Color.BLACK);
    pieces[1][0] = pawn;
    Move move = new Move(new Position(1,0), new Position(0, 0));

    MoveMaker.makeMove(pieces, move);

    assertEquals(pieces[0][0].getType(), PieceType.QUEEN);
    assertEquals(pieces[0][0].getColor(), Color.BLACK);
    assertNull(pieces[1][0]);
  }

  @Test
  public void verifyNormalMove() {
    Piece[][] pieces = new Piece[8][8];
    Piece pawn = new Piece(PieceType.PAWN, Color.WHITE);
    pieces[5][0] = pawn;
    Move move = new Move(new Position(5,0), new Position(6,0));

    MoveMaker.makeMove(pieces, move);

    assertEquals(pieces[6][0].getType(), PieceType.PAWN);
    assertEquals(pieces[6][0].getColor(), Color.WHITE);
    assertNull(pieces[5][0]);
  }

  @Test
  public void verifyPromotionOnlyForPawn() {
    Piece[][] pieces = new Piece[8][8];
    Piece pawn = new Piece(PieceType.ROOK, Color.WHITE);
    pieces[6][0] = pawn;
    Move move = new Move(new Position(6,0), new Position(7, 0));

    MoveMaker.makeMove(pieces, move);

    assertEquals(pieces[7][0].getType(), PieceType.ROOK);
    assertEquals(pieces[7][0].getColor(), Color.WHITE);
    assertNull(pieces[6][0]);
  }

}