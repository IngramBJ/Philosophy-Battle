package com.philosophy;


import com.philosophy.engine.*;
import com.philosophy.service.GameBroadcastService;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;



public class GameRoomManagerTest {



    @Test
    public void createRoom(){
GameBroadcastService broadcastService =
            mock(
                GameBroadcastService.class
            );

        GameRoomManager manager =
                new GameRoomManager(broadcastService);


        GameRoom room =
                manager.createRoom();



        assertNotNull(room);



        assertEquals(
                1,
                manager.roomCount()
        );


    }



    @Test
    public void findRoom(){

GameBroadcastService broadcastService =
            mock(
                GameBroadcastService.class
            );

        GameRoomManager manager =
                new GameRoomManager(broadcastService);



        GameRoom room =
                manager.createRoom();



        assertEquals(
                room,
                manager.getRoom(
                        room.getRoomId()
                )
        );


    }


}