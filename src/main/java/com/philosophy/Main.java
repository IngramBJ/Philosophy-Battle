package com.philosophy;


import com.philosophy.model.*;
import com.philosophy.engine.*;

import java.util.List;


public class Main {


    public static void main(String[] args) {


        GameState game = new GameState();


        Player a =
                new Player(1,"A");


        Player b =
                new Player(2,"B");



        game.addPlayer(a);

        game.addPlayer(b);

        b.addPoints(1);



        // 给A 4点哲学
        a.addPoints(4);


        // A 香蕉攻击B
        a.setCurrentAction(
                new Action(
                        ActionType.BANANA,
                        List.of(2)
                )
        );


        // B door
        b.setCurrentAction(
                new Action(
                        ActionType.DOOR,
                        List.of()
                )
        );



        BattleEngine engine =
                new BattleEngine();



        RoundResult result =
                engine.resolveRound(game);



        for(String log:
                result.getLogs()){


            System.out.println(log);

        }



        System.out.println(
                "A alive="
                + a.isAlive()
        );


        System.out.println(
                "B alive="
                + b.isAlive()
        );


        System.out.println(
                "A points="
                + a.getPhilosophyPoints()
        );


        System.out.println(
                "B points="
                + b.getPhilosophyPoints()
        );


    }

}