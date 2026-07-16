package com.philosophy;


import com.philosophy.engine.*;
import com.philosophy.engine.resolver.*;
import com.philosophy.model.*;


import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



public class NormalAttackResolverTest {


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



        RoundContext context =
                new RoundContext(game);



        DefenseState defense =
                new DefenseResolver()
                        .resolve(
                                context,
                                new RoundResult()
                        );



        new NormalAttackResolver()
                .resolve(
                        context,
                        defense,
                        new RoundResult()
                );



        assertFalse(
                b.isAlive()
        );


    }

}