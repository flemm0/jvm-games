package org.jvmgames.java.numberguesser;

import java.util.Scanner;
import java.util.Random;
import org.jvmgames.core.Game;
import org.jvmgames.java.numberguesser.GameDifficulty;
import org.jvmgames.java.numberguesser.GuessResult;
import org.jvmgames.java.numberguesser.InputHandler;


class GuessingGame {
  private int secretNumber;
  private int guesses;
  private int bound;
  private int maxGuesses;
  private boolean isGameOver;

  public GuessingGame(GameDifficulty difficulty) {
    this.bound = difficulty.getBound();
    this.maxGuesses = difficulty.getMaxGuesses();
    this.secretNumber = new Random().nextInt(bound) + 1;
    this.isGameOver = false;
    this.guesses = 0;
  }

  public GuessResult guess(int num) {
    guesses++;
    if (num == secretNumber) {
      isGameOver = true;
      return GuessResult.CORRECT;
    } else if (num < secretNumber) {
      return GuessResult.TOO_LOW;
    } else {
      return GuessResult.TOO_HIGH;
    }
  }

  public boolean isOver() { return isGameOver || guesses >= maxGuesses - 1; }
  public boolean isWon() { return isGameOver && guesses <= maxGuesses; }
  public int getBound() { return bound; }
  public int getSecretNumber() { return secretNumber; }
  public int getGuessCount() { return guesses; }
  public int getMaxGuesses() { return maxGuesses; }

}

public class NumberGuesser4J implements Game {

  private Scanner scanner;
  private InputHandler inputHandler;

  @Override
  public String name() { return "Number Guesser (Java)"; }

  @Override
  public String description() { return "Guess the number I'm thinking of!"; }

  public void init() {
    this.scanner = new Scanner(System.in);
    this.inputHandler = new InputHandler();
  }

  public boolean playOneGame() {
    init();
    GameDifficulty selectedDifficulty = inputHandler.promptDifficulty(scanner);
    GuessingGame game = new GuessingGame(selectedDifficulty);
    
    System.out.println("""
      Welcome to the Number Guesser Game!

      I am thinking of a number between 1 and %d. Can you guess it?
      """.formatted(game.getBound()));

    int currentGuess = inputHandler.handleGuess(scanner, game);
    while (!game.isOver()) {
      GuessResult result = game.guess(currentGuess);
      if (result == GuessResult.TOO_LOW) {
        System.out.println("Too low! Try again.");
      } else if (result == GuessResult.TOO_HIGH) {
        System.out.println("Too high! Try again.");
      } else {
        break;
      }
      System.out.println("Remaining guesses: " + (game.getMaxGuesses() - game.getGuessCount()));
      currentGuess = inputHandler.handleGuess(scanner, game);
    }
    if (game.isWon()) {
      System.out.println("Congratulations! You've guessed the number in " + game.getGuessCount() + " guesses.");
      System.out.println("⭐️⭐️⭐️⭐️⭐️⭐️⭐️⭐️⭐️⭐️⭐️⭐️⭐️⭐️⭐️⭐️⭐️⭐️⭐️⭐️");
    } else {
      System.out.println("Game over! You've used all your guesses. The number was " + game.getSecretNumber() + ".");
      System.out.println("😂🫵🏼");
    }
    boolean playAgain = inputHandler.promptPlayAgain(scanner);
    scanner.close();
    return playAgain;
  }

  @Override
  public void run() {
    boolean playAgain = true;
    while (playAgain) {
      playAgain = playOneGame();
    }
  }
} 
