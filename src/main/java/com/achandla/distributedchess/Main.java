package com.achandla.distributedchess;

import com.achandla.distributedchess.board.Chessboard;
import com.achandla.distributedchess.board.Color;
import com.achandla.distributedchess.board.Move;
import com.achandla.distributedchess.board.Piece;
import com.achandla.distributedchess.engine.MoveEngine;
import com.achandla.distributedchess.move.MoveInput;
import com.achandla.distributedchess.move.MoveMaker;

import java.io.IOException;
import java.util.Arrays;


public class Main {

  public static void main(String[] args) throws IOException {
    Piece[][] pieces = Chessboard.initChessboard();
    MoveInput moveInput = new MoveInput();
    MoveEngine moveEngine = new MoveEngine(3, Color.BLACK);
    Color turn = Color.WHITE;
    while (true) {
      Move move = turn == Color.WHITE ? moveInput.takeInput()
          : moveEngine.generateBestMove(pieces).orElseThrow(IllegalStateException::new);
      if (move == null) {
        System.out.println("Game ended stalemate/checkmate");
        break;
      }
      if (turn == Color.BLACK) {
        System.out.println(move);
      }
      MoveMaker.makeMove(pieces, move);
      turn = turn.invert();
    }
  }

  private static void printBoard(Piece[][] pieces) {
    for(Piece[] row : pieces) {
      System.out.println(Arrays.toString(row));
    }
  }

}
