package com.philosophy;


import com.philosophy.engine.*;
import com.philosophy.model.*;
import com.philosophy.service.GameBroadcastService;


import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;



public class MultiRoundFlowTest {


    @Test
    public void multipleRoundsShouldWork(){



        GameBroadcastService broadcastService =
                mock(GameBroadcastService.class);



        GameRoom room =
                new GameRoom(
                        "001",
                        broadcastService
                );



        Player a =
                new Player(
                        1,
                        "A"
                );


        Player b =
                new Player(
                        2,
                        "B"
                );



        room.addPlayer(a);

        room.addPlayer(b);



        room.startGame();



        RoundManager manager =
                room.getRoundManager();



        /*
         * round1
         */

        assertEquals(
                1,
                room.getRound()
        );



        manager.submitAction(
                a,
                new Action(
                        ActionType.BANANA,
                        java.util.List.of()
                )
        );



        manager.submitAction(
                b,
                new Action(
                        ActionType.DOOR,
                        java.util.List.of()
                )
        );



        assertTrue(
                manager.isRoundStarted()
        );



        /*
         * round2
         */

        assertEquals(
                2,
                room.getRound()
        );



        manager.submitAction(
                a,
                new Action(
                        ActionType.PHILOSOPHY,
                        java.util.List.of()
                )
        );


        manager.submitAction(
                b,
                new Action(
                        ActionType.PHILOSOPHY,
                        java.util.List.of()
                )
        );



        assertTrue(
                manager.isRoundStarted()
        );



        assertEquals(
                3,
                room.getRound()
        );


    }


}