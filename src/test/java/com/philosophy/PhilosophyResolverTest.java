package com.philosophy;


import com.philosophy.engine.*;
import com.philosophy.engine.resolver.PhilosophyResolver;
import com.philosophy.model.*;


import org.junit.jupiter.api.Test;


import java.util.List;


import static org.junit.jupiter.api.Assertions.*;



public class PhilosophyResolverTest {



    @Test
    public void philosophyIncreasePoint(){


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



        RoundContext context =
                new RoundContext(game);



        RoundResult result =
                new RoundResult();



        PhilosophyResolver resolver =
                new PhilosophyResolver();



        resolver.resolve(
                context,
                result
        );



        assertEquals(
                1,
                a.getPhilosophyPoints()
        );


    }

}