package org.jvmgames.scala.numberguesser

import scala.util.Random

import org.jvmgames.scala.numberguesser.Domain.{Game, GameDifficulty, GameState}


object GameLogic:

  def gameDifficultyFromIntSelection(input: String): Option[GameDifficulty] =
    input match
      case "1" => Some(GameDifficulty.Easy)
      case "2" => Some(GameDifficulty.Medium)
      case "3" => Some(GameDifficulty.Hard)
      case _   => None

  def initializeGame(difficulty: GameDifficulty): Game =
    val secretNumber = Random.nextInt(difficulty.bound) + 1
    Game(secretNumber, difficulty.maxGuesses, None)

  def isValidGuess(guess: Int, difficulty: GameDifficulty): Boolean =
    guess >= 1 && guess <= difficulty.bound

  def handleGuess(guess: Int, game: Game): Game =
    game.copy(currentGuess = Some(guess), remainingGuesses = game.remainingGuesses - 1)

  def isGameOver(game: Game): GameState =
    if game.currentGuess.getOrElse(-1) == game.secretNumber then GameState.Won
    else if game.remainingGuesses <= 0 then GameState.Lost
    else GameState.InProgress
