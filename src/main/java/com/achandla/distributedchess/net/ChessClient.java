package com.achandla.distributedchess.net;

import com.achandla.distributedchess.board.Chessboard;
import com.achandla.distributedchess.board.Color;
import com.achandla.distributedchess.board.Move;
import com.achandla.distributedchess.board.Piece;
import com.achandla.distributedchess.engine.MoveEngine;
import com.achandla.distributedchess.move.MoveGenerator;
import com.achandla.distributedchess.move.MoveMaker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChessClient {

  private static final Color myColor = Color.BLACK;

  private final BufferedReader reader;
  private final BufferedWriter writer;
  private final Socket socket;
  private final Piece[][] pieces;
  private int totalNodes;
  private int nodeNumber;
  private int depth;
  private final MoveEngine moveEngine;

  public ChessClient(String host, int port) throws IOException {
    this.socket = new Socket(host, port);
    this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    this.pieces = Chessboard.initChessboard();
    initializeNodes();
    initializeDepth();
    moveEngine = new MoveEngine(depth, myColor);
  }

  private void initializeNodes() {
    String[] nodeInfo = ClientUtil.readLine(reader).split(" ");
    this.totalNodes = Integer.parseInt(nodeInfo[0]);
    this.nodeNumber = Integer.parseInt(nodeInfo[1]);
  }

  private void initializeDepth() {
    this.depth = Integer.parseInt(ClientUtil.readLine(reader));
  }

  public void respond() {
    String message = ClientUtil.readLine(reader);
    if (message.startsWith("m")) {
      System.out.println("Received request to make move " + message);
      makeMove(message);
    } else if (message.equalsIgnoreCase("calculate")) {
      System.out.println("Received request to evaluate");
      generateBestMove();
      System.out.println("Completed evaluation request");
    }
  }

  private void makeMove(String message) {
    Move move = ServerUtil.convertStringToMove(message);
    MoveMaker.makeMove(pieces, move);
    ClientUtil.write(writer, "ack");
  }

  private void generateBestMove() {
    List<Move> possibleMoves = MoveGenerator.generateMoves(pieces, myColor);
    List<Move> movesToEvaluate = partitionMoves(possibleMoves);
    if (movesToEvaluate.isEmpty()) {
      ClientUtil.write(writer, "nil");
      return;
    }
    MoveEngine.MoveValue bestMove = moveEngine.evaluateBestMove(pieces, movesToEvaluate)
        .orElseThrow();
    String moveString = ServerUtil.convertMoveToString(bestMove.move());
    String valueString = Integer.toString(bestMove.value());
    String result = String.format("%s %s", moveString, valueString);
    ClientUtil.write(writer, result);
    System.out.println("Sent " + result);
  }

  private List<Move> partitionMoves(List<Move> possibleMoves) {
    if (totalNodes == 1) {
      return possibleMoves;
    }
    List<Move> movesToEvaluate = new ArrayList<>();
    int idx = 0;
    for (Move move : possibleMoves) {
      if (idx == nodeNumber) {
        movesToEvaluate.add(move);
      }
      idx = (idx + 1) % totalNodes;
    }
    return movesToEvaluate;
  }
}
