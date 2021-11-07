package com.achandla.distributedchess.engine;

import com.achandla.distributedchess.board.Chessboard;
import com.achandla.distributedchess.board.Color;
import com.achandla.distributedchess.board.Move;
import com.achandla.distributedchess.board.Piece;
import com.achandla.distributedchess.evaluator.Evaluator;
import com.achandla.distributedchess.move.MoveGenerator;
import com.achandla.distributedchess.move.MoveMaker;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MoveEngine {

  private static final int HALF_MOVE_DEPTH = 3;

  private static class MoveValue {
    Move move;
    int value;

    MoveValue(Move move, int value) {
      this.move = move;
      this.value = value;
    }

    @Override
    public String toString() {
      return String.format("%s %d\n", move, value);
    }
  }

  public static Move generateBestMove(Piece[][] pieces, Color color) {
    List<Move> moves = MoveGenerator.generateMoves(pieces, color);
    List<MoveValue> moveValues = moves.parallelStream()
        .map(move -> {
          Piece[][] copy = Chessboard.copyChessboard(pieces);
          return new MoveValue(move, getMoveValue(copy, Color.invert(color), move, HALF_MOVE_DEPTH));
        }).collect(Collectors.toList());
    System.out.println(moveValues);
    return moveValues.stream()
        .max(Comparator.comparingInt(moveVal -> moveVal.value))
        .map(moveVal -> moveVal.move)
        .orElse(null);
  }

  private static int getMoveValue(Piece[][] pieces, Color color, Move move, int depth) {
    MoveMaker.makeMove(pieces, move);
    if (depth == 0) {
      return Evaluator.getValue(pieces, color);
    }
    //Recursive step
    List<Move> moves = MoveGenerator.generateMoves(pieces, color);
    if (moves.size() == 0) {
      return Integer.MIN_VALUE;
    }
    Color nextColor = Color.invert(color);
    int maxValue = -1;
    for (Move nextMove : moves) {
      Piece[][] copy = Chessboard.copyChessboard(pieces);
      int val = getMoveValue(copy, nextColor, nextMove, depth - 1);
      maxValue = Integer.max(maxValue, val);
    }
    return maxValue;
  }
}
