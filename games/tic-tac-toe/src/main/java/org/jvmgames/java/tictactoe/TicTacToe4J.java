package org.jvmgames.java.tictactoe;

import org.jvmgames.core.Game;

public class TicTacToe4J implements Game {

    @Override
    public String name() {
        return "Tic Tac Toe (Java)";
    }

    @Override
    public String description() {
        return "A simple Tic Tac Toe game implemented in Java!";
    }

    @Override
    public void run() {
        boolean continuePlaying = true;
        while (continuePlaying) {
            TicTacToeGameRunner.playOneGame();
            continuePlaying = ConsoleHandler.promptPlayAgain();
        }
        System.out.println("Thanks for playing!");
    }
}
