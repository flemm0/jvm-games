package org.jvmgames.scala.tictactoe

import cats.effect.IO

import org.jvmgames.scala.tictactoe.Domain._
import org.jvmgames.scala.tictactoe.GameLogic._
import org.jvmgames.scala.tictactoe.ConsoleHandler._

object Game:

  def gameLoop(board: Board, currentPlayer: Player): IO[Unit] =
    isGameOver(board) match
      case true =>
        displayBoard(board) *>
        congratulateWinner(board)
      case false =>
        displayBoard(board) *>
        // TODO: Have computer make a move if currentPlayer is the computer's mark
        promptForMove(currentPlayer, board).flatMap { pos =>
          val updatedBoard = placeMark(currentPlayer.mark, pos, board)
          gameLoop(updatedBoard, nextPlayer(currentPlayer))
        }

  def playGame(): IO[Unit] =
    for {
      chosenMark <- chooseMark
      _ <- printWelcomeMessage(chosenMark)
      _ <- gameLoop(Board.fromCharList(List.fill(9)(" ")), Player(PlayerKind.Human, chosenMark))
      playAgain <- promptPlayAgain
      _ <- if (playAgain) playGame() else IO.unit
    } yield ()
