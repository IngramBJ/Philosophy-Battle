package com.philosophy;


import com.philosophy.engine.*;
import com.philosophy.model.*;
import com.philosophy.service.GameBroadcastService;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;



public class PlayerJoinTest {



    @Test
    public void duplicatePlayerCannotJoin(){

        GameBroadcastService broadcastService =
            mock(
                GameBroadcastService.class
            );


        GameRoom room =
                new GameRoom("001", broadcastService);



        Player a =
                new Player(1,"A");



        Player b =
                new Player(1,"B");



        assertTrue(
                room.addPlayer(a)
        );



        assertFalse(
                room.addPlayer(b)
        );


    }

}