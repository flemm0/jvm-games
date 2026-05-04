package org.jvmgames.scala.numberguesser

import org.jvmgames.scala.numberguesser.Domain.{Game, GameDifficulty, GameState}

class ScalaNumberGuesserSuite extends munit.FunSuite:

  test("gameDifficultyFromIntSelection maps menu choices to difficulties"):
    assertEquals(GameLogic.gameDifficultyFromIntSelection("1"), Some(GameDifficulty.Easy))
    assertEquals(GameLogic.gameDifficultyFromIntSelection("2"), Some(GameDifficulty.Medium))
    assertEquals(GameLogic.gameDifficultyFromIntSelection("3"), Some(GameDifficulty.Hard))
    assertEquals(GameLogic.gameDifficultyFromIntSelection("4"), None)
    assertEquals(GameLogic.gameDifficultyFromIntSelection("easy"), None)

  test("initializeGame creates a game inside the selected difficulty range"):
    List(GameDifficulty.Easy, GameDifficulty.Medium, GameDifficulty.Hard).foreach { difficulty =>
      val game = GameLogic.initializeGame(difficulty)

      assert(game.secretNumber >= 1)
      assert(game.secretNumber <= difficulty.bound)
      assertEquals(game.remainingGuesses, difficulty.maxGuesses)
      assertEquals(game.currentGuess, None)
    }

  test("isValidGuess accepts only guesses within the difficulty range"):
    assert(!GameLogic.isValidGuess(0, GameDifficulty.Easy))
    assert(GameLogic.isValidGuess(1, GameDifficulty.Easy))
    assert(GameLogic.isValidGuess(10, GameDifficulty.Easy))
    assert(!GameLogic.isValidGuess(11, GameDifficulty.Easy))

  test("handleGuess records the guess and consumes one attempt"):
    val game = Game(secretNumber = 7, remainingGuesses = 5, currentGuess = None)

    assertEquals(
      GameLogic.handleGuess(3, game),
      Game(secretNumber = 7, remainingGuesses = 4, currentGuess = Some(3))
    )

  test("isGameOver reports won, lost, and in-progress states"):
    assertEquals(
      GameLogic.isGameOver(Game(secretNumber = 7, remainingGuesses = 4, currentGuess = Some(7))),
      GameState.Won
    )
    assertEquals(
      GameLogic.isGameOver(Game(secretNumber = 7, remainingGuesses = 0, currentGuess = Some(3))),
      GameState.Lost
    )
    assertEquals(
      GameLogic.isGameOver(Game(secretNumber = 7, remainingGuesses = 4, currentGuess = Some(3))),
      GameState.InProgress
    )

  test("NumberGuesser4S exposes launcher metadata"):
    assertEquals(NumberGuesser4S.name, "Number Guesser (Scala)")
    assertEquals(NumberGuesser4S.description, "A simple number guessing game implemented in Scala.")
