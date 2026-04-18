package org.jvmgames.launcher

import cats.effect.{IO, IOApp, ExitCode}
import cats.syntax.all.*
import cats.effect.std.Console
import org.jvmgames.core.Game
import org.jvmgames.java.numberguesser.NumberGuesser4J
import org.jvmgames.scala.numberguesser.NumberGuesser4S
import org.jvmgames.scala.tictactoe.TicTacToe4S


object Launcher extends IOApp.Simple:

  val gameRegistry: List[Game] = List(
    new NumberGuesser4J(),
    NumberGuesser4S,
    TicTacToe4S
  )

  override def run: IO[Unit] =
    IO.println("Welcome to JVM Games!🎮") *>
    mainMenu

  def mainMenu: IO[Unit] =
    for
      _ <- IO.println("\n=== JVM Games ===")
      _ <- gameRegistry.zipWithIndex.traverse_ { case (game, idx) =>
        IO.println(s"  [${idx + 1}] ${game.name} — ${game.description}")
      }
      _ <- IO.println("  [0] Exit")
      _ <- IO.println("Select a game to play: ")
      choice <- Console[IO].readLine.map(_.trim.toInt)
      _ <- choice match
        case 0 =>
          IO.println("Goodbye!👋").as(ExitCode.Success)
        case n if n > 0 && n <= gameRegistry.length =>
          val selectedGame = gameRegistry(n - 1)
          IO.println(s"\nStarting ${selectedGame.name}...\n") *>
          IO.blocking(selectedGame.run()) *>
          mainMenu // Return to main menu after the game ends
        case _ =>
          IO.println("Invalid selection. Please try again.") *>
          mainMenu
    yield ()
