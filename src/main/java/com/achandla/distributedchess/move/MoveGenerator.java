package com.achandla.distributedchess.move;

import com.achandla.distributedchess.board.Color;
import com.achandla.distributedchess.board.Move;
import com.achandla.distributedchess.board.Piece;
import com.achandla.distributedchess.board.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MoveGenerator {

  public static List<Move> generateMoves(Piece[][] pieces, Color color) {
    List<Position> possiblePositions = getPositions(pieces, color);
    List<Move> possibleMoves = new ArrayList<>(100);
    for(Position position : possiblePositions) {
      possibleMoves.addAll(getPossibleMovesForPosition(pieces, position));
    }
    return possibleMoves.stream()
        .filter(move -> CheckUtil.isKingSafeAfterMove(pieces, move))
        .collect(Collectors.toList());
  }

  private static List<Position> getPositions(Piece[][] pieces, Color color) {
    List<Position> positions = new ArrayList<>(16);
    for(int row = 0; row < 8; row++) {
      for(int col = 0; col < 8; col++) {
        if(pieces[row][col] != null && pieces[row][col].getColor() == color) {
          positions.add(new Position(row, col));
        }
      }
    }
    return positions;
  }

  public static List<Move> getPossibleMovesForPosition(Piece[][] pieces, Position position) {
    Piece piece = pieces[position.row][position.col];
    return switch (piece.getType()) {
      case PAWN -> getMovesForPawn(pieces, position);
      case QUEEN -> getMovesForQueen(pieces, position);
      case KING -> getMovesForKing(pieces, position);
      case BISHOP -> getMovesForBishop(pieces, position);
      case ROOK ->  getMovesForRook(pieces, position);
      case KNIGHT -> getMovesForKnight(pieces, position);
    };
  }

  public static List<Move> getMovesForKnight(Piece[][] pieces, Position position) {
    List<Position> possiblePositions = getPossiblePositionsForKnight(position);
    Color color = pieces[position.row][position.col].getColor();
    return possiblePositions.stream()
        .filter(pos -> pieces[pos.row][pos.col] == null || pieces[pos.row][pos.col].getColor() != color)
        .map(finalPosition -> new Move(position, finalPosition))
        .collect(Collectors.toList());
  }

  public static List<Position> getPossiblePositionsForKnight(Position initial) {
    List<Position> positions = new ArrayList<>();
    if(Position.isValid(initial.diagonal(2, 1))) {
      positions.add(initial.diagonal(2,1));
    }
    if(Position.isValid(initial.diagonal(1, 2))) {
      positions.add(initial.diagonal(1,2));
    }
    if(Position.isValid(initial.diagonal(-2, -1))) {
      positions.add(initial.diagonal(-2,-1));
    }
    if(Position.isValid(initial.diagonal(-1, -2))) {
      positions.add(initial.diagonal(-1,-2));
    }
    if(Position.isValid(initial.diagonal(2, -1))) {
      positions.add(initial.diagonal(2,-1));
    }
    if(Position.isValid(initial.diagonal(1, -2))) {
      positions.add(initial.diagonal(1,-2));
    }
    if(Position.isValid(initial.diagonal(-2, 1))) {
      positions.add(initial.diagonal(-2,1));
    }
    if(Position.isValid(initial.diagonal(-1, 2))) {
      positions.add(initial.diagonal(-1,2));
    }
    return positions;
  }

  public static List<Move> getMovesForRook(Piece[][] pieces, Position position) {
    return getStraightMoves(pieces, position);
  }

  public static List<Move> getMovesForBishop(Piece[][] pieces, Position position) {
    return getDiagonalMoves(pieces, position);
  }

  public static List<Move> getMovesForKing(Piece[][] pieces, Position position) {
    List<Position> validPositions = getValidPositionsForKing(position);
    Color color = pieces[position.row][position.col].getColor();
    return validPositions.stream()
        .filter(pos -> (pieces[pos.row][pos.col] == null) || (pieces[pos.row][pos.col].getColor() != color))
        .map(end -> new Move(position, end))
        .collect(Collectors.toList());
  }

  private static List<Position> getValidPositionsForKing(Position initial) {
    List<Position> positions = new ArrayList<>();
    if(Position.isValid(initial.straight(1))) {
      positions.add(initial.straight(1));
    }
    if(Position.isValid(initial.straight(-1))) {
      positions.add(initial.straight(-1));
    }
    if(Position.isValid(initial.lateral(1))) {
      positions.add(initial.lateral(1));
    }
    if(Position.isValid(initial.lateral(-1))) {
      positions.add(initial.lateral(-1));
    }
    if(Position.isValid(initial.diagonal(1,1))) {
      positions.add(initial.diagonal(1,1));
    }
    if(Position.isValid(initial.diagonal(-1,1))) {
      positions.add(initial.diagonal(-1,1));
    }
    if(Position.isValid(initial.diagonal(1,-1))) {
      positions.add(initial.diagonal(1,-1));
    }
    if(Position.isValid(initial.diagonal(-1,-1))) {
      positions.add(initial.diagonal(-1,-1));
    }
    return positions;
  }

  public static List<Move> getMovesForQueen(Piece[][] pieces, Position position) {
    List<Move> moves = new ArrayList<>(16);
    moves.addAll(getStraightMoves(pieces, position));
    moves.addAll(getDiagonalMoves(pieces, position));
    return moves;
  }

  public static List<Move> getMovesForPawn(Piece[][] pieces, Position position) {
    List<Move> moves = new ArrayList<>();
    Color color = pieces[position.row][position.col].getColor();
    int forward = color == Color.WHITE ? 1 : -1;
    int homeRow = color == Color.WHITE ? 1 : 6;
    if(Position.isValid(position.straight(forward)) &&
        pieces[position.row+forward][position.col] == null) {
      moves.add(new Move(position, position.straight(forward)));
    }
    if(position.row == homeRow && pieces[position.row+(2*forward)][position.col] == null) {
      moves.add(new Move(position, position.straight(2*forward)));
    }
    if(Position.isValid(position.diagonal(forward, 1)) &&
        pieces[position.row+forward][position.col+1] != null &&
        pieces[position.row+forward][position.col+1].getColor() != color) {
      moves.add(new Move(position, position.diagonal(forward, 1)));
    }
    if(Position.isValid(position.diagonal(forward, -1)) &&
        pieces[position.row+forward][position.col-1] != null &&
        pieces[position.row+forward][position.col-1].getColor() != color) {
      moves.add(new Move(position, position.diagonal(forward, -1)));
    }
    return moves;
  }

  public static List<Move> getDiagonalMoves(Piece[][] pieces, Position position) {
    List<Move> moves = new ArrayList<>();
    moves.addAll(generateMoves(pieces, position, pos -> pos.diagonal(1,1)));
    moves.addAll(generateMoves(pieces, position, pos -> pos.diagonal(1,-1)));
    moves.addAll(generateMoves(pieces, position, pos -> pos.diagonal(-1,1)));
    moves.addAll(generateMoves(pieces, position, pos -> pos.diagonal(-1,-1)));
    return moves;
  }

  public static List<Move> getStraightMoves(Piece[][] pieces, Position position) {
    List<Move> moves = new ArrayList<>();
    moves.addAll(generateMoves(pieces, position, pos -> pos.straight(1)));
    moves.addAll(generateMoves(pieces, position, pos -> pos.straight(-1)));
    moves.addAll(generateMoves(pieces, position, pos -> pos.lateral(1)));
    moves.addAll(generateMoves(pieces, position, pos -> pos.lateral(-1)));
    return moves;
  }

  private static List<Move> generateMoves(Piece[][] pieces, Position initial, PositionChanger positionChanger) {
    List<Move> moves = new ArrayList<>();
    Color color = pieces[initial.row][initial.col].getColor();
    for(var current = initial.copy();
        Position.isValid(positionChanger.change(current));
        current = positionChanger.change(current)
    ) {
      if(pieces[current.row][current.col] == null) {
        moves.add(new Move(initial, current.copy()));
      }else {
        if(pieces[current.row][current.col].getColor() != color) {
          moves.add(new Move(initial, current.copy()));
        }
        break;
      }
    }
    return moves;
  }
}
