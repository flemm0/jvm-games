package org.jvmgames.scala.tictactoe

import cats.effect.unsafe.implicits.global

import org.jvmgames.core.Game
import org.jvmgames.scala.tictactoe.Game.playGame

object TicTacToe4S extends Game:
  override val name: String = "Tic Tac Toe (Scala)"
  override val description: String = "A simple Tic Tac Toe game implemented in Scala."
  override def run(): Unit = playGame().unsafeRunSync()
