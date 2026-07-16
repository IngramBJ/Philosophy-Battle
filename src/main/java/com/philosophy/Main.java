package com.philosophy;


import com.philosophy.model.*;

import java.util.List;



public class Main {


    public static void main(String[] args) {


        GameState game = new GameState();



        Player a = new Player(1,"A");

        Player b = new Player(2,"B");



        game.addPlayer(a);

        game.addPlayer(b);



        a.addPoints(1);



        a.setCurrentAction(
                new Action(
                        ActionType.PHILOSOPHY,
                        List.of()
                )
        );



        System.out.println(
                a.getName()
                +
                " points="
                +
                a.getPhilosophyPoints()
        );


        System.out.println(
                "Alive players:"
                +
                game.getAlivePlayers().size()
        );

    }

}