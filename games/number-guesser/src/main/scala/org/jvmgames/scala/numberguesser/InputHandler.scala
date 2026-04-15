package org.jvmgames.scala.numberguesser

import cats.effect.IO
import cats.effect.std.Console
import cats.syntax.all._

import org.jvmgames.scala.numberguesser.Domain.{GameDifficulty, Game}
import org.jvmgames.scala.numberguesser.GameLogic.isValidGuess
import org.jvmgames.scala.numberguesser.GameLogic.gameDifficultyFromIntSelection


object InputHandler:

  def welcomeMessage(difficulty: GameDifficulty): IO[Unit] =
    val welcomeMessage = s"""
    |Welcome to the Number Guesser Game!
    |
    |I am thinking of a number between 1 and ${difficulty.bound}.
    |Can you guess it within ${difficulty.maxGuesses} attempts?
    """.stripMargin
    Console[IO].println(welcomeMessage)

  def promptGameDifficulty: IO[GameDifficulty] =
    val prompt = """
    |Please select a difficulty level:
    |1. Easy (1-10, 5 guesses)
    |2. Medium (1-20, 4 guesses)
    |3. Hard (1-50, 3 guesses)
    |Enter the number corresponding to your choice:
    """.stripMargin
    Console[IO].println(prompt) *>
      Console[IO].readLine.flatMap { input =>
        gameDifficultyFromIntSelection(input) match
          case Some(difficulty) => IO.pure(difficulty)
          case None =>
            Console[IO].println("Invalid input. Please enter 1, 2, or 3.") *>
              promptGameDifficulty
      }
  
  def promptGuess(game: Game, gameDifficulty: GameDifficulty): IO[Int] =
    Console[IO].println("Enter your guess:") *>
      Console[IO].readLine.flatMap { input =>
        input.toIntOption match
          case Some(guess) if isValidGuess(guess, gameDifficulty) => IO.pure(guess)
          case Some(guess) =>
            Console[IO].println(s"Invalid guess. Please enter a number between 1 and ${gameDifficulty.bound}.") *>
              promptGuess(game, gameDifficulty)
          case None =>
            Console[IO].println("Invalid input. Please enter a valid integer.") *>
              promptGuess(game, gameDifficulty)
      }

  def hint(guess: Int, game: Game): IO[Unit] =
    val guessDiff = guess - game.secretNumber
    if guessDiff < 0 then
      Console[IO].println("Too low! Try again.")
    else
      Console[IO].println("Too high! Try again.")
  
  def displayRemainingGuesses(game: Game): IO[Unit] =
    Console[IO].println(s"Remaining guesses: ${game.remainingGuesses}")
  
  def congratulate(game: Game, gameDifficulty: GameDifficulty): IO[Unit] =
    Console[IO].println(s"Congratulations! You've guessed the number ${game.secretNumber}" +
      s" in ${gameDifficulty.maxGuesses - game.remainingGuesses} attempts!" + "\n" +
      s"⭐️⭐️⭐️⭐️⭐️⭐️⭐️⭐️⭐️⭐️⭐️⭐️⭐️⭐️⭐️⭐️⭐️⭐️⭐️⭐️")
  
  def sympathize(game: Game): IO[Unit] =
    Console[IO].println(s"Game over! You've used all your guesses. The number was ${game.secretNumber}" +
      s"😂🫵🏼😂🫵🏼😂🫵🏼😂🫵🏼😂🫵🏼")

  def promptPlayAgain: IO[Boolean] =
    Console[IO].println("Do you want to play again? (y/n)") *>
      Console[IO].readLine.flatMap {
        case "y" | "Y" => IO.pure(true)
        case "n" | "N" => IO.pure(false)
        case _ =>
          Console[IO].println("Invalid input. Please enter 'y' or 'n'.") *>
            promptPlayAgain
      }
