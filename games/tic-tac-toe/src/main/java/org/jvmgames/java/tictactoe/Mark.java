package org.jvmgames.java.tictactoe;


public enum Mark {
  X, O, EMPTY;

  public String toString() {
    return switch (this) {
      case X -> "X";
      case O -> "O";
      case EMPTY -> " ";
    };
  }
}