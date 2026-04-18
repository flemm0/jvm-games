package org.jvmgames.scala.tictactoe

object Domain:

  enum Mark:
    case X, O
  
  case class Row(items: Tuple3[Option[Mark], Option[Mark], Option[Mark]]):
    def toList: List[Option[Mark]] = items
        .productIterator
        .toList
        .asInstanceOf[List[Option[Mark]]]

  object Row:
    def fromCharList(chars: List[String]): Row = chars match {
      case List(a, b, c) => Row(
        charToMark(a),
        charToMark(b),
        charToMark(c)
      )
      case _ => throw new IllegalArgumentException("Expected exactly 3 characters to create a Row")
    }
    private def charToMark(char: String): Option[Mark] = char match {
      case "X" => Some(Mark.X)
      case "O" => Some(Mark.O)
      case " " => None
      case _   => throw new IllegalArgumentException(s"Invalid character '$char'. Expected 'X', 'O', or ' '.")
    }

  case class Board(rows: Tuple3[Row, Row, Row]):
    def toList: List[Row] = rows
        .productIterator
        .toList
        .asInstanceOf[List[Row]]
    def toCharList: List[String] = toList
      .flatMap(_.toList.map(_.getOrElse(" ").toString))
  object Board:
    def fromCharList(chars: List[String]): Board = chars match {
      case List(a, b, c, d, e, f, g, h, i) => Board(
        Row.fromCharList(List(a, b, c)),
        Row.fromCharList(List(d, e, f)),
        Row.fromCharList(List(g, h, i))
      )
      case _ => throw new IllegalArgumentException("Expected exactly 9 characters to create a Board")
    }