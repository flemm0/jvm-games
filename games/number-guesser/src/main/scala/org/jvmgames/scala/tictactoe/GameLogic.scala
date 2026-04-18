package org.jvmgames.scala.tictactoe

import org.jvmgames.scala.tictactoe.Domain._

object GameLogic:

  def validBoardPosition(input: Int, board: Board): Either[String, Boolean] =
    if (input >= 1 && input <= 9) then
      Right(true)
    else 
      Left("Invalid input. Please enter a number between 1 and 9.")

  def isValidPlacement(pos: Int, board: Board): Either[String, Boolean] =
    if (board.toCharList(pos - 1) == " ") then
      Right(true)
    else
      Left("That position is already taken. Please choose another one.")
  
  def validateInput(input: Option[Int], board: Board): Either[String, Int] =
    input match
      case Some(pos) => 
        validBoardPosition(pos, board).flatMap { _ =>
          isValidPlacement(pos, board).map(_ => pos)
        }
      case None => Left("Invalid input. Please enter a number between 1 and 9.")

  def placeMark(mark: Mark, pos: Int, board: Board): Board =
    Board.fromCharList(board.toCharList.updated(pos - 1, mark.toString))

  def checkThree(triplet: List[Option[Mark]]): Map[Mark, Int] =
    triplet.flatten
      .groupBy(identity)
      .view
      .mapValues(_.size)
      .toMap

  def threeInARow(board: Board): Option[Mark] =
    val rowsAndCols = board.toList.map(_.toList) ++ board.toList.map(_.toList).transpose
    rowsAndCols.map(checkThree(_)).collect { 
      case m if m.exists(_._2 == 3) => m.find(_._2 == 3).get._1 
    }.headOption

  def threeInDiagonal(board: Board): Option[Mark] =
    val mat = board.toList.map(_.toList)
    val diagonals = List(
      mat.indices.map(i => mat(i)(i)).toList,
      mat.indices.map(i => mat(i)(mat.size - 1 - i)).toList
    )
    diagonals.map(checkThree(_)).collect { 
      case m if m.exists(_._2 == 3) => m.find(_._2 == 3).get._1 
    }.headOption

  def identifyWinner(board: Board): Option[Mark] =
    threeInDiagonal(board).orElse(threeInARow(board))

  def isGameOver(board: Board): Boolean =
    identifyWinner(board).isDefined || board.toCharList.forall(_ != " ")
