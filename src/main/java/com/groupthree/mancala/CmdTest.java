package com.groupthree.mancala;

import com.groupthree.mancala.gameplay.Board;

public class CmdTest {
    public static void main(String[] args) {
        Board board = new Board();
        board.displayBoard();
        board.moveStones(6);
        board.displayBoard();
        System.out.println(board.getTurnPoints());


    }
}
