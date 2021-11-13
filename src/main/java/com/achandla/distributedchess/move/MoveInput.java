package com.achandla.distributedchess.move;

import com.achandla.distributedchess.board.Move;
import com.achandla.distributedchess.board.Position;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.Scanner;

public class MoveInput {

  private BufferedReader br;

  public MoveInput() {
    this.br = new BufferedReader(new InputStreamReader(System.in));
  }

  public Optional<Move> takeInput() {
    System.out.println("Enter start and end position");
    try {
      Position start = inputPosition();
      Position end = inputPosition();
      return Optional.of(new Move(start, end));
    }catch (IOException ioException) {
      ioException.printStackTrace();
      return Optional.empty();
    }
  }

  private Position inputPosition() throws IOException {
    String[] values = br.readLine().split(" ");
    int start = Integer.parseInt(values[0].trim());
    int end = Integer.parseInt(values[1].trim());
    return new Position(start, end);
  }
}
