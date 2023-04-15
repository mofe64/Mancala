package com.groupthree.mancala;

import com.groupthree.mancala.gameplay.Board;
import com.groupthree.mancala.models.Admin;
import com.groupthree.mancala.models.Player;
import com.groupthree.mancala.repository.UserRepository;

public class CmdTest {
    public static void main(String[] args) {
        Board board = new Board();
        board.displayBoard();
        board.moveStones(6);
        board.displayBoard();
        System.out.println(board.isPlayerOneTurn());

//        Player player = new Player("testUsername11",
//                "testFirstname1",
//                "testLastname1",
//                "testProfileImage1");
//        Player player1 = new Player("11", "2", "3", "5");
//        Player player2 = new Player("22", "2", "2", "2");
//        player.addToFavorites(player1.getPublicProfile());
//        player.addToFavorites(player2.getPublicProfile());
//        player1.addToFavorites(player.getPublicProfile());
//        Admin admin = new Admin("testAdmin11", "testAdmin", "testAdmin", "testAdmin");
//        Admin admin1 = new Admin("testAdmin12", "testAdmin1", "testAdmin1", "testAdmin1");
//        UserRepository x = UserRepository.getInstance();
//
//        x.savePlayer(player);
//        x.savePlayer(player1);
//        x.savePlayer(player2);
//        x.saveAdmin(admin);
//        x.saveAdmin(admin1);
//        var res = x.writeToFile();
//        System.out.println(res);


//        var players = x.getPlayers();
//        var admins  = x.getAdmins();
//        System.out.println(admins.get(0));
//        System.out.println(players.get(0));


    }
}
