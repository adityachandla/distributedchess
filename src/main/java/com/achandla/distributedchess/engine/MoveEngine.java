package com.achandla.distributedchess.engine;

import com.achandla.distributedchess.board.Chessboard;
import com.achandla.distributedchess.board.Color;
import com.achandla.distributedchess.board.Move;
import com.achandla.distributedchess.board.Piece;
import com.achandla.distributedchess.evaluator.Evaluator;
import com.achandla.distributedchess.move.MoveGenerator;
import com.achandla.distributedchess.move.MoveMaker;

import java.util.List;
import java.util.Optional;

public class MoveEngine {

  private final int halfMoveDepth;
  private final Color myColor;

  public MoveEngine(int halfMoveDepth, Color myColor) {
    this.halfMoveDepth = halfMoveDepth;
    this.myColor = myColor;
  }

  public Optional<Move> generateBestMove(Piece[][] pieces) {
    List<Move> possibleMoves = MoveGenerator.generateMoves(pieces, myColor);
    Optional<Move> bestMove = Optional.empty();
    int maxValue = Integer.MIN_VALUE;
    for(Move move: possibleMoves) {
      Piece[][] afterMove = Chessboard.copyChessboard(pieces);
      MoveMaker.makeMove(afterMove, move);
      int value = minimize(afterMove, halfMoveDepth, myColor.invert());
      if(value == Integer.MAX_VALUE) {
        return Optional.of(move);
      }
      if(value > maxValue) {
        maxValue = value;
        bestMove = Optional.of(move);
      }
    }
    return bestMove;
  }

  private int maximize(Piece[][] pieces, int depth, Color turn) {
    if(depth == 0) {
      return Evaluator.getValue(pieces, myColor);
    }
    List<Move> possibleMoves = MoveGenerator.generateMoves(pieces, turn);
    int val = Integer.MIN_VALUE;
    for(Move move : possibleMoves) {
      Piece[][] piecesAfterMove = Chessboard.copyChessboard(pieces);
      MoveMaker.makeMove(piecesAfterMove, move);
      val = Integer.max(val, minimize(piecesAfterMove, depth-1, turn.invert()));
    }
    return val;
  }

  private int minimize(Piece[][] pieces, int depth, Color turn) {
    if(depth == 0) {
      return Evaluator.getValue(pieces, myColor);
    }
    List<Move> possibleMoves = MoveGenerator.generateMoves(pieces, turn);
    int val = Integer.MAX_VALUE;
    for(Move move : possibleMoves) {
      Piece[][] piecesAfterMove = Chessboard.copyChessboard(pieces);
      MoveMaker.makeMove(piecesAfterMove, move);
      val = Integer.min(val, maximize(piecesAfterMove, depth-1, turn.invert()));
    }
    return val;
  }
}
