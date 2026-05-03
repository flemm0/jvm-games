package org.jvmgames.java.tictactoe;

import java.util.Scanner;
import java.util.List;
import java.util.Random;


public class ConsoleHandler {

  public static void printWelcomeMessage(Mark playerMark) {
    String welcomeMessage = """
            Welcome to Tic Tac Toe!
            Player 1 will be %s, and Player 2 will be %s.
            To make a move, enter a number between 1 and 9 corresponding to the board position:
             1 | 2 | 3
            ---+---+---
             4 | 5 | 6
            ---+---+---
             7 | 8 | 9
            """;
    System.out.println(
        welcomeMessage.formatted(playerMark, playerMark == Mark.X ? Mark.O : Mark.X)
    );
  }

  public static Mark promptForMark() {
    System.out.println("Choose your mark (X/O): ");
    Scanner scanner = new Scanner(System.in);
    Mark mark = Mark.EMPTY;
    while (mark == Mark.EMPTY) {
      String input = scanner.nextLine().trim().toUpperCase();
      if (input.equals("X")) {
        mark = Mark.X;
      } else if (input.equals("O")) {
        mark = Mark.O;
      } else {
        System.out.println("Invalid input. Please enter X or O.");
      }
    }
    scanner.close();
    return mark;
  }

  public static int promptForMove(List<Integer> openPositions,
                    Mark currentPlayer, Mark humanPlayer) {
    System.out.println("Player " + currentPlayer + ", enter your move (1-9):");
    if (currentPlayer == humanPlayer) {
      Scanner scanner = new Scanner(System.in);
      int move = -1;
      while (true) {
        String input = scanner.nextLine();
        try {
          move = Integer.parseInt(input);
          if (move >= 1 && move <= 9 && openPositions.contains(move)) {
            scanner.close();
            return move;
          } else if (move < 1 || move > 9) {
            System.out.println("Invalid input. Please enter a number between 1 and 9.");
          } else {
            System.out.println("That position is already taken. Please choose an open position.");
          }
        } catch (NumberFormatException e) {
          System.out.println("Invalid input. Please enter a number between 1 and 9.");
        }
      }
    } else {
      Random random = new Random();
      System.out.println("Computer is thinking...");
      try {
        Thread.sleep(2000); // sleep for 2 seconds to simulate thinking
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
      int move = openPositions.get(random.nextInt(openPositions.size()));
      return move;
    }
  }

  public static boolean promptPlayAgain() {
    System.out.println("Do you want to play again? (y/n)");
    Scanner scanner = new Scanner(System.in);
    String response = "";
    while (!List.of("y", "n").contains(response)) {
      String input = scanner.nextLine().trim().toLowerCase();
      if (!List.of("y", "n").contains(input)) {
        System.out.println("Invalid input. Please enter 'y' or 'n'.");
      } else {
        response = input;
      }
    }
    scanner.close();
    return response.equals("y");
  }
}
