package org.jvmgames.scala.numberguesser

import cats.effect.IO
import cats.effect.std.Console
import cats.syntax.all._

import org.jvmgames.scala.numberguesser.Domain.{GameState, Game, GameDifficulty}
import org.jvmgames.scala.numberguesser.GameLogic._
import org.jvmgames.scala.numberguesser.InputHandler._


object Game:

  def gameLoop(game: Game, gameDifficulty: GameDifficulty): IO[Boolean] =
    isGameOver(game) match
      case GameState.Won =>
        congratulate(game, gameDifficulty) *> promptPlayAgain
      case GameState.Lost =>
        sympathize(game) *> promptPlayAgain
      case GameState.InProgress =>
        promptGuess(game, gameDifficulty).flatMap { guess =>
          val updatedGame = handleGuess(guess, game)
          hint(guess, updatedGame) *>
          displayRemainingGuesses(updatedGame) *>
            gameLoop(updatedGame, gameDifficulty)
        }

  def playGame(playAgain: Boolean = true): IO[Unit] =
    promptGameDifficulty.flatMap { difficulty =>
      val game = initializeGame(difficulty)
      welcomeMessage(difficulty) *>
        gameLoop(game, difficulty).flatMap { playAgain => playAgain match
          case true => playGame()
          case false => IO.unit
        }
    }

