package com.philosophy;


import com.philosophy.engine.*;
import com.philosophy.model.*;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



public class BattleEngineFlowTest {



    @Test
    public void philosophyActionShouldIncreasePoint(){



        GameState game =
                new GameState();



        Player player =
                new Player(
                        1,
                        "A"
                );



        player.setCurrentAction(

                new Action(
                        ActionType.PHILOSOPHY,
                        List.of()
                )

        );



        game.addPlayer(player);



        BattleEngine engine =
                new BattleEngine();



        RoundResult result =
                engine.resolveRound(game);



        assertEquals(
                1,
                player.getPhilosophyPoints()
        );



        assertTrue(
                result.getLogs()
                        .contains(
                            "A 哲学 +1"
                        )
        );


        assertNull(
                player.getCurrentAction()
        );

    }


}