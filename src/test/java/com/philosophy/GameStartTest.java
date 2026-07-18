package com.philosophy;


import com.philosophy.engine.*;
import com.philosophy.model.GameStatus;
import com.philosophy.model.Player;
import com.philosophy.service.GameBroadcastService;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;



public class GameStartTest {


    @Test
    public void gameStartNeedsTwoPlayers(){

        GameBroadcastService broadcastService =
            mock(
                GameBroadcastService.class
            );


        GameRoom room =
                new GameRoom("001", broadcastService);



        room.addPlayer(
                new Player(1,"A")
        );



        assertThrows(
                IllegalStateException.class,
                room::startGame
        );


    }



    @Test
    public void gameCanStartWithTwoPlayers(){

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


    }


}