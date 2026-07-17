package com.philosophy;


import com.philosophy.engine.*;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;



public class GameRoomManagerTest {



    @Test
    public void createRoom(){


        GameRoomManager manager =
                new GameRoomManager();



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


        GameRoomManager manager =
                new GameRoomManager();



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