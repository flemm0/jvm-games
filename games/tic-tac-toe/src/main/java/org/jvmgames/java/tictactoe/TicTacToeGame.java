package org.jvmgames.java.tictactoe;

import java.util.*;

class TicTacToeGame {
    private ArrayList<Mark> board;
    private Mark currentPlayer;
    private Mark humanPlayer;

    public TicTacToeGame(Mark firstPlayerMark) {
        this.board = new ArrayList<>(Collections.nCopies(9, Mark.EMPTY));
        this.currentPlayer = firstPlayerMark;
        this.humanPlayer = firstPlayerMark;
    }

    public void printBoard() {
        String boardStr = """
         %s | %s | %s
        ---+---+---
         %s | %s | %s
        ---+---+---
         %s | %s | %s
        """.formatted(this.board.toArray());
        System.out.println(boardStr);
    }

    public List<Integer> getOpenPositions() {
        List<Integer> openPositions = new ArrayList<>();
        for (int i = 0; i < board.size(); i++) {
            if (board.get(i) == Mark.EMPTY) {
                openPositions.add(i + 1);
            }
        }
        return openPositions;
    }

    public Mark getWinner() {
        List<List<Mark>> combos = new ArrayList<>();
        for (int r = 0; r < 3; r++) {
            combos.add(List.of(board.get(r * 3), board.get(r * 3 + 1), board.get(r * 3 + 2)));
        }
        for (int c = 0; c < 3; c++) {
            combos.add(List.of(board.get(c), board.get(c + 3), board.get(c + 6)));
        }
        combos.add(List.of(board.get(0), board.get(4), board.get(8)));
        combos.add(List.of(board.get(2), board.get(4), board.get(6)));
        for (List<Mark> combo : combos) {
            if (combo.get(0) != Mark.EMPTY && combo.get(0) == combo.get(1) &&
                combo.get(1) == combo.get(2)) {
                return combo.get(0);
            }
        }
        return Mark.EMPTY;
    }

    public void addMove(int position, Mark mark) { board.set(position - 1, mark); }

    public Mark getCurrentPlayer() { return currentPlayer; }

    public Mark getHumanPlayer() { return humanPlayer; }

    public void switchPlayer() {
        this.currentPlayer = this.currentPlayer == Mark.X ? Mark.O : Mark.X;
    }

    public boolean isOver() { return getWinner() != Mark.EMPTY || getOpenPositions().isEmpty(); }
}
