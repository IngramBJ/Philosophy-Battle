package com.philosophy;


import com.philosophy.engine.*;
import com.philosophy.engine.resolver.DefenseResolver;
import com.philosophy.model.*;


import org.junit.jupiter.api.Test;


import java.util.List;


import static org.junit.jupiter.api.Assertions.*;



public class DefenseResolverTest {



    @Test
    public void doorShouldBeRecorded(){


        GameState game =
                new GameState();



        Player b =
                new Player(2,"B");


        game.addPlayer(b);



        b.setCurrentAction(
                new Action(
                        ActionType.DOOR,
                        List.of()
                )
        );



        RoundContext context =
                new RoundContext(game);



        DefenseResolver resolver =
                new DefenseResolver();



        DefenseState state =
                resolver.resolve(
                        context,
                        new RoundResult()
                );



        assertTrue(
                state.hasDefense(b)
        );


        assertEquals(
                ActionType.DOOR,
                state.getDefense(b)
        );

    }

}