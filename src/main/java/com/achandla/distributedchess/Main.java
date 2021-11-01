package com.achandla.distributedchess;

import com.achandla.distributedchess.board.Chessboard;
import com.achandla.distributedchess.board.Color;
import com.achandla.distributedchess.board.Move;
import com.achandla.distributedchess.move.MoveGenerator;

import java.util.List;

public class Main {
  public static void main(String args[]) {
    Chessboard chessboard = new Chessboard();
    MoveGenerator generator = new MoveGenerator();
    List<Move> moves = generator.generateMoves(chessboard.getPieces(), Color.WHITE);
    System.out.println(moves.size());
    for(Move move : moves) {
      System.out.println(move);
    }
  }
}
