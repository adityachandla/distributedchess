package com.achandla.distributedchess.evaluator;

import com.achandla.distributedchess.board.Color;
import com.achandla.distributedchess.board.Piece;

public class Evaluator {

  /**
   * Assign a value to position. Higher the better.
   *
   * @param pieces Board of pieces
   * @param color Color for which to evaluate value
   * @return Value of the position for color
   */
  public int getValue(Piece[][] pieces, Color color) {
    int value = 0;
    for(int i = 0; i < 8; i++) {
      for(int j = 0; j < 8; j++) {
        if(pieces[i][j] == null) {
          continue;
        }
        if(pieces[i][j].getColor() == color) {
          value += pieces[i][j].getValue();
        }else {
          value -= pieces[i][j].getValue();
        }
      }
    }
    return value;
  }
}
