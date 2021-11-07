package com.achandla.distributedchess.engine;

import com.achandla.distributedchess.board.Chessboard;
import com.achandla.distributedchess.board.Color;
import com.achandla.distributedchess.board.Move;
import com.achandla.distributedchess.board.Piece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveEngineTest {

  @Test
  public void testMoves() {
    Piece[][] pieces = Chessboard.initChessboard();
    Move bestMove = MoveEngine.generateBestMove(pieces, Color.WHITE);
    System.out.println(bestMove);
  }

}