package com.philosophy;


import com.philosophy.controller.RoomController;
import com.philosophy.engine.GameRoom;
import com.philosophy.engine.GameRoomManager;
import com.philosophy.dto.JoinRoomRequest;

import org.junit.jupiter.api.Test;


import java.util.Map;


import static org.junit.jupiter.api.Assertions.*;



public class RoomControllerTest {



    @Test
    public void joinRoomSuccess(){


        GameRoomManager manager =
                new GameRoomManager();


        RoomController controller =
                new RoomController(manager);



        GameRoom room =
                manager.createRoom();



        JoinRoomRequest request =
                new JoinRoomRequest();


        request.setPlayerId(1);

        request.setName("哲学家");



        Map<String,Object> result =
                controller.joinRoom(
                        room.getRoomId(),
                        request
                );



        assertTrue(
                (Boolean)result.get("success")
        );

        assertEquals(
                1,
                room.getGameState()
                        .getPlayers()
                        .size()
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