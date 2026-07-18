package com.philosophy;


import com.philosophy.engine.*;
import com.philosophy.model.*;
import com.philosophy.service.GameBroadcastService;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;



public class AutoNextRoundTest {


    @Test
    public void nextRoundStartsAutomatically(){

        GameBroadcastService broadcastService =
            mock(
                GameBroadcastService.class
            );


        GameRoom room =
                new GameRoom("001", broadcastService);



        room.addPlayer(
                new Player(1,"A")
        );


        room.addPlayer(
                new Player(2,"B")
        );



        room.startGame();



        RoundManager manager =
                room.getRoundManager();



        assertTrue(
                manager.isRoundStarted()
        );



        manager.finishRound();



        assertTrue(
                manager.isRoundStarted()
        );


    }

}