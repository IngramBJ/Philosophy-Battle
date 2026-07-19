package com.philosophy;


import com.philosophy.engine.*;
import com.philosophy.model.*;
import com.philosophy.service.GameBroadcastService;


import org.junit.jupiter.api.Test;


import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;



public class BattleEngineResolverTest {


    private GameRoom createRoom(){

        return new GameRoom(
                "test",
                mock(GameBroadcastService.class)
        );

    }





    @Test
    public void philosophyShouldAddPoint(){


        GameRoom room=createRoom();


        Player a =
                new Player(1,"A");


        Player b =
                new Player(2,"B");


        room.addPlayer(a);
        room.addPlayer(b);



        room.startGame();


        a.setCurrentAction(
                new Action(
                        ActionType.PHILOSOPHY,
                        List.of()
                )
        );


        b.setCurrentAction(
                new Action(
                        ActionType.PHILOSOPHY,
                        List.of()
                )
        );



        room.getRoundManager()
                .finishRound();



        assertEquals(
                1,
                a.getPhilosophyPoints()
        );


        assertEquals(
                1,
                b.getPhilosophyPoints()
        );

    }








    @Test
    public void bananaShouldKillWithoutDefense(){


        GameRoom room=createRoom();



        Player a =
                new Player(1,"A");

        a.addPoints(10);



        Player b =
                new Player(2,"B");



        room.addPlayer(a);
        room.addPlayer(b);



        room.startGame();



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



        room.getRoundManager()
                .finishRound();



        assertFalse(
                b.isAlive()
        );


    }










    @Test
    public void doorShouldReflectBanana(){


        GameRoom room=createRoom();



        Player a =
                new Player(1,"A");

        a.addPoints(10);



        Player b =
                new Player(2,"B");
        b.addPoints(10);



        room.addPlayer(a);
        room.addPlayer(b);



        room.startGame();



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



        room.getRoundManager()
                .finishRound();




        assertTrue(
                a.isAlive()
        );


        assertFalse(
                b.isAlive()
        );

    }











    @Test
    public void defenseShouldBlockBanana(){


        GameRoom room=createRoom();



        Player a =
                new Player(1,"A");

        a.addPoints(10);



        Player b =
                new Player(2,"B");



        room.addPlayer(a);
        room.addPlayer(b);



        room.startGame();



        a.setCurrentAction(
                new Action(
                        ActionType.BANANA,
                        List.of(2)
                )
        );



        b.setCurrentAction(
                new Action(
                        ActionType.DEF_BANANA,
                        List.of()
                )
        );



        room.getRoundManager()
                .finishRound();



        assertTrue(
                b.isAlive()
        );


    }












    @Test
    public void vansamaShouldBeBlockedByDoor(){


        GameRoom room=createRoom();



        Player a =
                new Player(1,"A");

        a.addPoints(10);



        Player b =
                new Player(2,"B");

        b.addPoints(10);



        room.addPlayer(a);
        room.addPlayer(b);



        room.startGame();



        a.setCurrentAction(
                new Action(
                        ActionType.VANSAMA,
                        List.of(2)
                )
        );



        b.setCurrentAction(
                new Action(
                        ActionType.DOOR,
                        List.of()
                )
        );



        room.getRoundManager()
                .finishRound();



        assertTrue(
                b.isAlive()
        );


    }












    @Test
    public void demonKingShouldDieWhenBlocked(){


        GameRoom room=createRoom();



        Player a =
                new Player(1,"A");

        a.addPoints(10);



        Player b =
                new Player(2,"B");

        b.addPoints(10);



        room.addPlayer(a);
        room.addPlayer(b);



        room.startGame();



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



        room.getRoundManager()
                .finishRound();



        assertFalse(
                a.isAlive()
        );


        assertTrue(
                b.isAlive()
        );


    }









    @Test
    public void whiteMouseShouldKillAllOthers(){


        GameRoom room=createRoom();



        Player a =
                new Player(1,"A");

        a.addPoints(10);



        Player b =
                new Player(2,"B");


        Player c =
                new Player(3,"C");



        room.addPlayer(a);
        room.addPlayer(b);
        room.addPlayer(c);



        room.startGame();




        a.setCurrentAction(
                new Action(
                        ActionType.WHITE_MOUSE,
                        List.of()
                )
        );


        b.setCurrentAction(
                new Action(
                        ActionType.PHILOSOPHY,
                        List.of()
                )
        );


        c.setCurrentAction(
                new Action(
                        ActionType.PHILOSOPHY,
                        List.of()
                )
        );



        room.getRoundManager()
                .finishRound();



        assertTrue(
                a.isAlive()
        );


        assertFalse(
                b.isAlive()
        );


        assertFalse(
                c.isAlive()
        );


    }

        @Test
    public void AttackerStronger(){


        GameRoom room=createRoom();



        Player a =
                new Player(1,"A");

        a.addPoints(10);



        Player b =
                new Player(2,"B");
            
        b.addPoints(10);



        Player c =
                new Player(3,"C");
        c.addPoints(10);




        room.addPlayer(a);
        room.addPlayer(b);
        room.addPlayer(c);



        room.startGame();




        a.setCurrentAction(
                new Action(
                        ActionType.WHITE_MOUSE,
                        List.of()
                )
        );


        b.setCurrentAction(
                new Action(
                        ActionType.DEMON_KING,
                        List.of(1,3)
                )
        );


        c.setCurrentAction(
                new Action(
                        ActionType.VANSAMA,
                        List.of(1,2)
                )
        );



        room.getRoundManager()
                .finishRound();



        assertFalse(
                a.isAlive()
        );


        assertTrue(
                b.isAlive()
        );


        assertFalse(
                c.isAlive()
        );


    }


}