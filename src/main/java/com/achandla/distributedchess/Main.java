package com.achandla.distributedchess;

import com.achandla.distributedchess.net.ChessClient;
import com.achandla.distributedchess.net.ChessServer;

import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException {
    if(args.length == 0) {
      throw new IllegalArgumentException("Specify whether to run as 'client' or 'server'");
    }
    if(args[0].equalsIgnoreCase("server")) {
      int clients = Integer.parseInt(args[1]);
      int depth = Integer.parseInt(args[2]);
      ChessServer.startGame(clients, depth);
    }else if(args[0].equalsIgnoreCase("client")) {
      String hostAddress = args[1];
      ChessClient client = new ChessClient(hostAddress, 8080);
      while(true) {
        client.respond();
      }
    }
  }

}
