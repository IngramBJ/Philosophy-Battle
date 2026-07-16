package com.philosophy;


import com.philosophy.engine.*;
import com.philosophy.model.*;


import org.junit.jupiter.api.Test;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;



public class BattleEngineTest {



    @Test
    public void bananaIgnoreDoor(){


        GameState game =
                new GameState();



        Player a =
                new Player(1,"A");


        Player b =
                new Player(2,"B");



        game.addPlayer(a);

        game.addPlayer(b);



        // A拥有4点使用香蕉
        a.addPoints(4);


        // B拥有1点使用door
        b.addPoints(1);



        a.setCurrentAction(
                new Action(
                        ActionType.BANANA,
                        List.of(2)
                )
        );



        b.setCurrentAction(
                new Action(
                        ActionType.DOOR,
                        List.of()
                )
        );



        BattleEngine engine =
                new BattleEngine();



        engine.resolveRound(game);



        assertTrue(a.isAlive());


        assertFalse(b.isAlive());

    }

}