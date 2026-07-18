package com.philosophy;


import com.philosophy.controller.RoomController;
import com.philosophy.engine.GameRoom;
import com.philosophy.engine.GameRoomManager;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;



public class RoomControllerTest {



    @Test
    public void createRoomSuccess(){


        GameRoomManager manager =
                new GameRoomManager();



        RoomController controller =
                new RoomController(manager);



        GameRoom room =
                manager.createRoom();



        assertNotNull(room);



        assertNotNull(
                room.getRoomId()
        );


    }






    @Test
    public void queryStateSuccess(){


        GameRoomManager manager =
                new GameRoomManager();



        RoomController controller =
                new RoomController(manager);



        GameRoom room =
                manager.createRoom();



        Object state =
                controller.state(
                        room.getRoomId()
                );



        assertNotNull(state);


    }



}