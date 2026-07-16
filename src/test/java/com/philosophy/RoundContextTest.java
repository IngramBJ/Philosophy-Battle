package com.philosophy;


import com.philosophy.engine.RoundContext;
import com.philosophy.model.*;

import org.junit.jupiter.api.Test;


import java.util.List;


import static org.junit.jupiter.api.Assertions.*;



public class RoundContextTest {



    @Test
    public void saveActions(){


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



        assertNotNull(
                context.getAction(a)
        );


    }


}