package org.jvmgames.scala.numberguesser

object Domain:
  
  enum GameDifficulty(val bound: Int, val maxGuesses: Int):
    case Easy extends GameDifficulty(10, 5)
    case Medium extends GameDifficulty(20, 4)
    case Hard extends GameDifficulty(50, 3)

  case class Game(
    secretNumber: Int,
    remainingGuesses: Int,
    currentGuess: Option[Int]
  )

  enum GameState:
    case Lost, InProgress, Won
