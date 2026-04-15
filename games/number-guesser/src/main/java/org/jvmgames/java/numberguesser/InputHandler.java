package org.jvmgames.java.numberguesser;

import java.util.Scanner;
import java.util.Random;
import org.jvmgames.core.Game;
import org.jvmgames.java.numberguesser.GameDifficulty;


class InputHandler {

  public GameDifficulty getDifficulty(int choice) {
    return switch (choice) {
      case 1 -> GameDifficulty.EASY;
      case 2 -> GameDifficulty.MEDIUM;
      case 3 -> GameDifficulty.HARD;
      default -> null;
    };
  }

  public GameDifficulty promptDifficulty(Scanner scanner) {
    while (true) {
      System.out.println("""
        Please select a difficulty level:
        1. Easy (1-10, 5 guesses)
        2. Medium (1-20, 4 guesses)
        3. Hard (1-50, 3 guesses)
        Enter the number corresponding to your choice:
      """);
      int choice = scanner.nextInt();
      GameDifficulty difficulty = getDifficulty(choice);
      if (difficulty != null) {
        return difficulty;
      }
      System.out.println("Invalid choice. Please enter 1, 2, or 3.");
    }
  }

  public int handleGuess(Scanner scanner, GuessingGame game) {
    System.out.println("Enter your guess:");
    String guess = scanner.next();
    try {
      int num = Integer.parseInt(guess);
      if (num < 1 || num > game.getBound()) {
        System.out.println("Please enter a number between 1 and " + game.getBound() + ".");
        return handleGuess(scanner, game);
      }
      return num;
    } catch (NumberFormatException e) {
      System.out.println("Invalid input. Please enter a valid integer.");
      return handleGuess(scanner, game);
    }
  }

  public boolean promptPlayAgain(Scanner scanner) {
    System.out.println("Do you want to play again? (y/n)");
    String response = scanner.next().trim().toLowerCase();
    while (!response.equals("y") && !response.equals("n")) {
      System.out.println("Invalid input. Please enter 'y' for yes or 'n' for no.");
      response = scanner.next().trim().toLowerCase();
    }
    return response.equals("y");
  }
}
