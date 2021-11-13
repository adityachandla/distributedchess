package com.achandla.distributedchess.engine;

import com.achandla.distributedchess.board.Chessboard;
import com.achandla.distributedchess.board.Color;
import com.achandla.distributedchess.board.Move;
import com.achandla.distributedchess.board.Piece;
import com.achandla.distributedchess.board.Position;
import com.achandla.distributedchess.evaluator.Evaluator;
import com.achandla.distributedchess.move.MoveGenerator;
import com.achandla.distributedchess.move.MoveMaker;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public record MoveEngine(int halfMoveDepth, Color myColor) {

  private record MoveValue(
      Move move,
      int value
  ){}

  public Optional<Move> generateBestMove(Piece[][] pieces) {
    return MoveGenerator.generateMoves(pieces, myColor).parallelStream()
        .map(move -> getMoveValue(pieces, move))
        .max(Comparator.comparingInt(MoveValue::value))
        .map(MoveValue::move);
  }

  private MoveValue getMoveValue(Piece[][] pieces, Move move) {
    Piece[][] boardCopy = Chessboard.copyChessboard(pieces);
    MoveMaker.makeMove(boardCopy, move);
    int value = minimize(boardCopy, halfMoveDepth, myColor.invert());
    return new MoveValue(move, value);
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
