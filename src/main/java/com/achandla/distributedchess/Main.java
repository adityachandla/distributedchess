package com.achandla.distributedchess;

public class Main {

  public static void main(String[] args) {
    if(args.length == 0) {
      throw new IllegalArgumentException("Specify whether to run as 'client' or 'server'");
    }
  }

}
