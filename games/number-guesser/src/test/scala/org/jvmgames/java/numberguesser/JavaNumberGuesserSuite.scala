package org.jvmgames.java.numberguesser

import java.util.Scanner

class JavaNumberGuesserSuite extends munit.FunSuite:

  test("GameDifficulty exposes bounds and max guesses"):
    assertEquals(GameDifficulty.EASY.getBound(), 10)
    assertEquals(GameDifficulty.EASY.getMaxGuesses(), 5)
    assertEquals(GameDifficulty.MEDIUM.getBound(), 20)
    assertEquals(GameDifficulty.MEDIUM.getMaxGuesses(), 4)
    assertEquals(GameDifficulty.HARD.getBound(), 50)
    assertEquals(GameDifficulty.HARD.getMaxGuesses(), 3)

  test("InputHandler maps difficulty menu choices"):
    val inputHandler = new InputHandler()

    assertEquals(inputHandler.getDifficulty(1), GameDifficulty.EASY)
    assertEquals(inputHandler.getDifficulty(2), GameDifficulty.MEDIUM)
    assertEquals(inputHandler.getDifficulty(3), GameDifficulty.HARD)
    assertEquals(inputHandler.getDifficulty(4), null)

  test("InputHandler retries invalid difficulty choices"):
    val inputHandler = new InputHandler()
    val scanner = Scanner("9\n2\n")

    assertEquals(inputHandler.promptDifficulty(scanner), GameDifficulty.MEDIUM)

  test("InputHandler retries invalid guesses until one is in range"):
    val inputHandler = new InputHandler()
    val scanner = Scanner("abc\n0\n11\n5\n")
    val game = new GuessingGame(GameDifficulty.EASY)

    assertEquals(inputHandler.handleGuess(scanner, game), 5)

  test("GuessingGame initializes from difficulty"):
    val game = new GuessingGame(GameDifficulty.HARD)

    assert(game.getSecretNumber() >= 1)
    assert(game.getSecretNumber() <= GameDifficulty.HARD.getBound())
    assertEquals(game.getBound(), GameDifficulty.HARD.getBound())
    assertEquals(game.getMaxGuesses(), GameDifficulty.HARD.getMaxGuesses())
    assertEquals(game.getGuessCount(), 0)
    assert(!game.isOver())
    assert(!game.isWon())

  test("GuessingGame returns directional results for wrong guesses"):
    val game = new GuessingGame(GameDifficulty.EASY)
    val secret = game.getSecretNumber()

    if secret > 1 then
      assertEquals(game.guess(secret - 1), GuessResult.TOO_LOW)
    else
      assertEquals(game.guess(secret + 1), GuessResult.TOO_HIGH)

  test("GuessingGame wins immediately on a correct guess"):
    val game = new GuessingGame(GameDifficulty.EASY)

    assertEquals(game.guess(game.getSecretNumber()), GuessResult.CORRECT)
    assert(game.isOver())
    assert(game.isWon())

  test("GuessingGame ends only after the maximum number of wrong guesses"):
    val game = new GuessingGame(GameDifficulty.MEDIUM)
    val wrongGuess = if game.getSecretNumber() == 1 then 2 else 1

    for _ <- 1 until game.getMaxGuesses() do
      assert(!game.isOver())
      assertNotEquals(game.guess(wrongGuess), GuessResult.CORRECT)

    assertEquals(game.getGuessCount(), game.getMaxGuesses() - 1)
    assert(!game.isOver())

    assertNotEquals(game.guess(wrongGuess), GuessResult.CORRECT)
    assertEquals(game.getGuessCount(), game.getMaxGuesses())
    assert(game.isOver())
    assert(!game.isWon())

  test("NumberGuesser4J exposes launcher metadata"):
    val game = new NumberGuesser4J()

    assertEquals(game.name(), "Number Guesser (Java)")
    assertEquals(game.description(), "Guess the number I'm thinking of!")
