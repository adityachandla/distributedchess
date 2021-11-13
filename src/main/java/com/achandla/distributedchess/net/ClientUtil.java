package com.achandla.distributedchess.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class ClientUtil {

  public static String readLine(BufferedReader br) {
    try{
      return br.readLine();
    }catch(IOException ioException) {
      ioException.printStackTrace();
      throw new RuntimeException("Unable to read line from server");
    }
  }

  public static void write(BufferedWriter bufferedWriter, String message) {
    try{
      bufferedWriter.write(message + "\n");
      bufferedWriter.flush();
    }catch (IOException exception) {
      exception.printStackTrace();
      throw new RuntimeException("Unable to write to server");
    }
  }
}
