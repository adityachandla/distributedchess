package com.achandla.distributedchess.move;

import com.achandla.distributedchess.board.Color;
import com.achandla.distributedchess.board.Move;
import com.achandla.distributedchess.board.Piece;
import com.achandla.distributedchess.board.PieceType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class MoveGeneratorTest {

  @Test
  public void testPawnForward() {
    Piece[][] pieces = new Piece[8][8];
    pieces[0][0] = new Piece(PieceType.KING, Color.WHITE);
    pieces[1][0] = new Piece(PieceType.PAWN, Color.WHITE);
    List<Move> moves = MoveGenerator.generateMoves(pieces, Color.WHITE);
    assertEquals( 2 + 2, moves.size());
  }

  @Test
  public void testPawnCapture() {
    Piece[][] pieces = new Piece[8][8];
    pieces[0][0] = new Piece(PieceType.KING, Color.WHITE);
    pieces[1][0] = new Piece(PieceType.PAWN, Color.WHITE);
    pieces[2][1] = new Piece(PieceType.QUEEN, Color.BLACK);
    List<Move> moves = MoveGenerator.generateMoves(pieces, Color.WHITE);
    assertTrue(moves.stream()
        .anyMatch(move -> move.end.row == 2 && move.end.col == 1));
    assertEquals(moves.size(), 3);
  }

  @Test
  public void testPinnedPawn() {
    Piece[][] pieces = new Piece[8][8];
    pieces[0][0] = new Piece(PieceType.KING, Color.WHITE);
    pieces[1][1] = new Piece(PieceType.PAWN, Color.WHITE);
    pieces[3][3] = new Piece(PieceType.QUEEN, Color.BLACK);
    List<Move> moves = MoveGenerator.generateMoves(pieces, Color.WHITE);
    assertEquals(2, moves.size());
  }
}
