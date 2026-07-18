package com.philosophy;


import com.philosophy.controller.RoomController;
import com.philosophy.engine.GameRoom;
import com.philosophy.engine.GameRoomManager;
import com.philosophy.service.GameBroadcastService;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;



public class RoomControllerTest {



    @Test
    public void createRoomSuccess(){

GameBroadcastService broadcastService =
            mock(
                GameBroadcastService.class
            );

        GameRoomManager manager =
                new GameRoomManager(broadcastService);



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

        GameBroadcastService broadcastService =
                mock(
                        GameBroadcastService.class
                );

        GameRoomManager manager =
                new GameRoomManager(broadcastService);



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