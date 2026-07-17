package com.philosophy;


import com.philosophy.engine.*;
import com.philosophy.model.*;


import org.junit.jupiter.api.Test;


import java.util.List;


import static org.junit.jupiter.api.Assertions.*;



public class ResourceTest {



    @Test
    public void bananaConsumePoints(){


        GameState game =
                new GameState();



        Player a =
                new Player(1,"A");


        Player b =
                new Player(2,"B");



        game.addPlayer(a);

        game.addPlayer(b);



        /*
         * A有4点
         */
        a.addPoints(4);



        a.setCurrentAction(
                new Action(
                        ActionType.BANANA,
                        List.of(2)
                )
        );



        b.setCurrentAction(
                new Action(
                        ActionType.PHILOSOPHY,
                        List.of()
                )
        );



        new BattleEngine()
                .resolveRound(game);



        assertEquals(
                0,
                a.getPhilosophyPoints()
        );


    }

@Test
public void invalidActionNoConsume(){


    GameState game =
            new GameState();



    Player a =
            new Player(1,"A");


    Player b =
            new Player(2,"B");



    game.addPlayer(a);

    game.addPlayer(b);



    /*
     * 只有1点
     */
    a.addPoints(1);



    a.setCurrentAction(
            new Action(
                    ActionType.BANANA,
                    List.of(2)
            )
    );



    new BattleEngine()
            .resolveRound(game);



    assertEquals(
            1,
            a.getPhilosophyPoints()
    );


}

@Test
public void philosophyGainPoint(){


    GameState game =
            new GameState();



    Player a =
            new Player(1,"A");


    game.addPlayer(a);



    a.setCurrentAction(
            new Action(
                    ActionType.PHILOSOPHY,
                    List.of()
            )
    );



    new BattleEngine()
            .resolveRound(game);



    assertEquals(
            1,
            a.getPhilosophyPoints()
    );


}
}