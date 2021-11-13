package com.achandla.distributedchess.net;

import com.achandla.distributedchess.board.Color;
import com.achandla.distributedchess.board.Move;
import com.achandla.distributedchess.board.Piece;
import com.achandla.distributedchess.move.MoveInput;
import com.achandla.distributedchess.move.MoveMaker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class ChessServer {

  public record Client(
      Socket socket,
      BufferedReader reader,
      BufferedWriter writer
  ) {
  }

  /**
   * Connects to clients and starts the game
   *
   * @param numClients Number of clients to expect
   * @param depth      Depth of search on clients
   * @throws IOException Communication error
   */
  public static void startGame(int numClients, int depth) throws IOException {
    List<Client> clients = initializeClients(numClients, depth);
    System.out.printf("Connected to all %d clients\n", clients.size());
    Piece[][] pieces = new Piece[8][8];
    MoveInput moveInput = new MoveInput();
    Color turn = Color.WHITE;
    while (true) {
      Optional<Move> move = turn == Color.WHITE ? moveInput.takeInput() : calculateBestMove(clients);
      if (move.isEmpty()) {
        System.out.println("Game Ended");
        break;
      }
      sendMoveToClients(clients, move.get());
      MoveMaker.makeMove(pieces, move.get());
      turn = turn.invert();
    }
  }

  private static List<Client> initializeClients(int numClients, int depth) throws IOException {
    ServerSocket serverSocket = new ServerSocket(8080);
    List<Client> clients = new ArrayList<>(numClients);
    for (int i = 0; i < numClients; i++) {
      Socket socket = serverSocket.accept();
      BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
      clients.add(new Client(socket, reader, writer));

      writer.write(String.format("%d %d", numClients, i));
      writer.write(String.format("%d", depth));
      writer.flush();
    }
    return clients;
  }

  private static Optional<Move> calculateBestMove(List<Client> clients) {
    ServerUtil.sendMessage(clients, "calculate");
    return ServerUtil.readMessage(clients).stream()
        .filter(Predicate.not("nil"::equalsIgnoreCase))
        .max(Comparator.comparingInt(ChessServer::extractValue))
        .map(ChessServer::convertToMove);
  }

  private static int extractValue(String moveValue) {
    return Integer.parseInt(moveValue.split(" ")[1]);
  }

  /**
   * Expected value of type: m1122 33
   * this means we have a move from (1,1) to (2,2) with value 33
   *
   * @param value String value representation of move
   * @return Move Parsed move
   */
  private static Move convertToMove(String value) {
    String move = value.split(" ")[0];
    return ServerUtil.convertStringToMove(move);
  }

  /**
   * Move will be sent as text m3311 which means to move piece from
   * (3,3) to (1,1)
   * Expects client to send 'ack'
   *
   * @param clients Clients
   * @param move    Move to send to all clients
   */
  private static void sendMoveToClients(List<Client> clients, Move move) {
    String moveString = ServerUtil.convertMoveToString(move);
    ServerUtil.sendMessage(clients, moveString);
    boolean error = ServerUtil.readMessage(clients).stream()
        .anyMatch(Predicate.not(message -> message.equalsIgnoreCase("ack")));
    if (error) {
      throw new IllegalStateException("Client did not send acknowledgement");
    }
  }
}
