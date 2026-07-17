package com.philosophy;


import com.philosophy.engine.*;
import com.philosophy.engine.resolver.*;

import com.philosophy.model.*;


import org.junit.jupiter.api.Test;


import java.util.List;


import static org.junit.jupiter.api.Assertions.*;



public class SpecialAttackResolverTest {



    @Test
    public void vansamaNeedSeparateDefense(){


        GameState game =
                new GameState();



        Player a =
                new Player(1,"A");


        Player b =
                new Player(2,"B");


        Player c =
                new Player(3,"C");



        game.addPlayer(a);

        game.addPlayer(b);

        game.addPlayer(c);



        /*
         * A vansama B,C
         */
        a.setCurrentAction(
                new Action(
                        ActionType.VANSAMA,
                        List.of(2,3)
                )
        );



        /*
         * B door
         */
        b.setCurrentAction(
                new Action(
                        ActionType.DOOR,
                        List.of()
                )
        );


        /*
         * C不防御
         */
        c.setCurrentAction(
                new Action(
                        ActionType.PHILOSOPHY,
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



        RoundResult result =
                new RoundResult();



        new SpecialAttackResolver()
                .resolve(
                        context,
                        defense,
                        result
                );



        assertTrue(
                a.isAlive()
        );


        assertTrue(
                b.isAlive()
        );


        assertFalse(
                c.isAlive()
        );

    }

    @Test
public void whiteMouseNeedDoorDefense(){


    GameState game =
            new GameState();



    Player a =
            new Player(1,"A");


    Player b =
            new Player(2,"B");


    Player c =
            new Player(3,"C");


    Player d =
            new Player(4,"D");



    game.addPlayer(a);
    game.addPlayer(b);
    game.addPlayer(c);
    game.addPlayer(d);



    a.setCurrentAction(
            new Action(
                    ActionType.WHITE_MOUSE,
                    List.of()
            )
    );



    b.setCurrentAction(
            new Action(
                    ActionType.DOOR,
                    List.of()
            )
    );


    c.setCurrentAction(
            new Action(
                    ActionType.DOOR,
                    List.of()
            )
    );


    d.setCurrentAction(
            new Action(
                    ActionType.PHILOSOPHY,
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



    new SpecialAttackResolver()
            .resolve(
                    context,
                    defense,
                    new RoundResult()
            );



    assertTrue(a.isAlive());

    assertTrue(b.isAlive());

    assertTrue(c.isAlive());

    assertFalse(d.isAlive());


}

@Test
public void whiteMouseCanAttackNoActionPlayer(){


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
                    ActionType.WHITE_MOUSE,
                    List.of()
            )
    );



    // B没有行动
    b.setCurrentAction(null);



    RoundContext context =
            new RoundContext(game);



    new SpecialAttackResolver()
            .resolve(
                    context,
                    new DefenseState(),
                    new RoundResult()
            );



    assertFalse(
            b.isAlive()
    );


}

@Test
public void demonKingKillWithoutDefense(){


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
                    ActionType.DEMON_KING,
                    List.of(2)
            )
    );



    b.setCurrentAction(
            new Action(
                    ActionType.PHILOSOPHY,
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



    new SpecialAttackResolver()
            .resolve(
                    context,
                    defense,
                    new RoundResult()
            );



    assertTrue(a.isAlive());

    assertFalse(b.isAlive());


}

@Test
public void demonKingDieWhenTargetDefends(){


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
                    ActionType.DEMON_KING,
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



    new SpecialAttackResolver()
            .resolve(
                    context,
                    defense,
                    new RoundResult()
            );



    assertFalse(a.isAlive());

    assertTrue(b.isAlive());


}

}
