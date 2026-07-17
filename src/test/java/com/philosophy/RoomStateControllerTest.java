package com.philosophy;


import com.philosophy.controller.RoomStateController;
import com.philosophy.engine.GameRoom;
import com.philosophy.engine.GameRoomManager;
import com.philosophy.dto.GameStateDTO;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;



public class RoomStateControllerTest {



    @Test
    public void getStateShouldReturnDTO(){


        GameRoomManager manager =
                new GameRoomManager();



        GameRoom room =
                manager.createRoom();



        RoomStateController controller =
                new RoomStateController(manager);



        Object result =
                controller.state(
                        room.getRoomId()
                );



        assertTrue(
                result instanceof GameStateDTO
        );


        GameStateDTO dto =
                (GameStateDTO) result;



        assertEquals(

                room.getRoomId(),

                dto.roomId

        );


    }






    @Test
    public void stateShouldFailWhenRoomNotExist(){


        GameRoomManager manager =
                new GameRoomManager();



        RoomStateController controller =
                new RoomStateController(manager);



        Object result =
                controller.state(
                        "not-exist"
                );



        assertTrue(
                result instanceof java.util.Map
        );


    }


}