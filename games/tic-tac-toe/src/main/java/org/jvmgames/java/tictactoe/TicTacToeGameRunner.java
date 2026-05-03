package org.jvmgames.java.tictactoe;

class TicTacToeGameRunner {
  public static void playOneGame() {
    Mark playerMark = ConsoleHandler.promptForMark();
    ConsoleHandler.printWelcomeMessage(playerMark);
    TicTacToeGame game = new TicTacToeGame(playerMark);
    while (!game.isOver()) {
      int move = ConsoleHandler.promptForMove(
        game.getOpenPositions(),
        game.getCurrentPlayer(),
        game.getHumanPlayer());
      System.out.println("You chose: " + move);
      game.addMove(move, game.getCurrentPlayer());
      game.printBoard();
      game.switchPlayer();
    }
    if (game.getWinner() != Mark.EMPTY) {
      System.out.println("Player " + game.getWinner() + " wins!");
    } else {
      System.out.println("It's a draw!");
    }
  }
}
