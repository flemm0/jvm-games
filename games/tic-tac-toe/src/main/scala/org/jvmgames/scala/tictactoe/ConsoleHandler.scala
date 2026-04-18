package org.jvmgames.scala.tictactoe

import cats.effect.IO
import cats.effect.std.Console

import org.jvmgames.scala.tictactoe.Domain._
import org.jvmgames.scala.tictactoe.GameLogic.{validateInput, identifyWinner}

object ConsoleHandler:

  def printWelcomeMessage(chosenMark: Mark): IO[Unit] =
    val welcomeMessage: String =
      s"""
        |Welcome to Tic Tac Toe!
        |Player 1 will be ${chosenMark.toString} and Player 2 will be ${if (chosenMark == Mark.X) "O" else "X"}.
        |To make a move, enter a number between 1 and 9 corresponding to the board position:
        | 1 | 2 | 3
        |---+---+---
        | 4 | 5 | 6
        |---+---+---
        | 7 | 8 | 9
      """.stripMargin
    Console[IO].println(welcomeMessage)

  def chooseMark: IO[Mark] =
    Console[IO].println("Choose your mark (X/O):") *>
      Console[IO].readLine.map(_.toUpperCase).flatMap {
        case "X" => IO.pure(Mark.X)
        case "O" => IO.pure(Mark.O)
        case _   => Console[IO].println("Invalid input. Please enter 'X' or 'O'.") *> chooseMark
      }

  def promptForMove(player: Mark, board: Board): IO[Int] =
    Console[IO].println(s"Player $player, enter your move (1-9):") *>
      Console[IO].readLine.flatMap { input =>
        validateInput(input.toIntOption, board) match
          case Right(pos) => IO.pure(pos)
          case Left(error) =>
            Console[IO].println(error) *>
              promptForMove(player, board)
      }

  def displayBoard(board: Board): IO[Unit] =
    val boardStr = s"""
                  | %s | %s | %s
                  |---+---+---
                  | %s | %s | %s
                  |---+---+---
                  | %s | %s | %s
                  """.stripMargin
    Console[IO].println(boardStr.format(board.toCharList*))
  
  def congratulateWinner(board: Board): IO[Unit] =
    identifyWinner(board) match
      case Some(mark) => Console[IO].println(s"Congratulations Player ${mark.toString}! You win!")
      case None       => Console[IO].println("It's a draw!")
  
  def promptPlayAgain: IO[Boolean] =
    Console[IO].println("Do you want to play again? (y/n)") *>
      Console[IO].readLine.map(_.toLowerCase).flatMap {
        case "y" => IO.pure(true)
        case "n" => IO.pure(false)
        case _   => Console[IO].println("Invalid input. Please enter 'y' or 'n'.") *> promptPlayAgain
      }
      