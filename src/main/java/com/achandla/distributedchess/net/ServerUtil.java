package com.achandla.distributedchess.net;

import com.achandla.distributedchess.board.Move;
import com.achandla.distributedchess.board.Position;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ServerUtil {

  public static void sendMessage(List<ChessServer.Client> clients, String message) {
    clients.stream()
        .map(ChessServer.Client::writer)
        .forEach(writer -> writeMessage(writer, message));
  }

  private static void writeMessage(BufferedWriter writer, String message) {
    try{
      writer.write(message + "\n");
      writer.flush();
    }catch (IOException exception) {
      exception.printStackTrace();
      throw new RuntimeException("Unable to write to client's writer");
    }
  }

  public static List<String> readMessage(List<ChessServer.Client> clients) {
    return clients.stream()
        .map(ChessServer.Client::reader)
        .map(ServerUtil::readLine)
        .collect(Collectors.toList());
  }

  private static String readLine(BufferedReader br) {
    try {
      return br.readLine();
    }catch (IOException exception) {
      exception.printStackTrace();
      throw new RuntimeException("Unable to read from client's reader");
    }
  }

  public static Move convertStringToMove(String move) {
    Position start = new Position(charToInt(move.charAt(0)), charToInt(move.charAt(1)));
    Position end = new Position(charToInt(move.charAt(2)), charToInt(move.charAt(3)));
    return new Move(start, end);
  }

  private static int charToInt(char c) {
    return c - '0';
  }

  public static String convertMoveToString(Move move) {
    char[] moveString = new char[5];
    moveString[0] = 'm';
    moveString[1] = Integer.toString(move.start.row).charAt(0);
    moveString[2] = Integer.toString(move.start.col).charAt(0);
    moveString[3] = Integer.toString(move.end.row).charAt(0);
    moveString[4] = Integer.toString(move.end.col).charAt(0);
    return new String(moveString);
  }
}
