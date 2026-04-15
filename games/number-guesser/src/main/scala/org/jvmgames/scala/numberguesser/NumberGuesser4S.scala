package org.jvmgames.scala.numberguesser

import cats.effect.unsafe.implicits.global

import org.jvmgames.core.Game
import org.jvmgames.scala.numberguesser.Game.playGame


object NumberGuesser4S extends Game {
  override val name: String = "Number Guesser (Scala)"
  override val description: String = "A simple number guessing game implemented in Scala."
  override def run(): Unit = playGame().unsafeRunSync()
}
