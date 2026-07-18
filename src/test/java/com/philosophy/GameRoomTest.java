package com.philosophy;


import com.philosophy.engine.*;
import com.philosophy.model.*;
import com.philosophy.service.GameBroadcastService;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



public class GameRoomTest {



    @Test
    public void roomCanStart(){

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



        assertEquals(
                GameStatus.RUNNING,
                room.getStatus()
        );


        assertEquals(
                1,
                room.getRound()
        );


    }


}