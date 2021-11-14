package com.achandla.distributedchess.net;

import com.achandla.distributedchess.board.Move;
import com.achandla.distributedchess.board.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerUtilTest {

  @Test
  void testConversionFromString() {
    String moveString = "m1325";
    Move move = ServerUtil.convertStringToMove(moveString);
    assertEquals(move.start.row, 1);
    assertEquals(move.start.col, 3);
    assertEquals(move.end.row, 2);
    assertEquals(move.end.col, 5);
  }

  @Test
  void testConversionToString() {
    Move move = new Move(new Position(1,5), new Position(7, 2));
    String moveString = ServerUtil.convertMoveToString(move);
    assertEquals(moveString, "m1572");
  }

}