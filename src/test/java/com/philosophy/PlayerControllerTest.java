package com.philosophy;


import com.philosophy.controller.PlayerController;
import com.philosophy.dto.JoinRoomRequest;
import com.philosophy.engine.GameRoom;
import com.philosophy.engine.GameRoomManager;
import com.philosophy.service.GameBroadcastService;

import org.junit.jupiter.api.Test;


import java.util.Map;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;



public class PlayerControllerTest {



@Test
public void joinRoomSuccess(){


    GameRoomManager manager =
            new GameRoomManager();



    GameBroadcastService broadcastService =
            mock(GameBroadcastService.class);



    PlayerController controller =
            new PlayerController(
                    manager,
                    broadcastService
            );



    GameRoom room =
            manager.createRoom();



    JoinRoomRequest request =
            new JoinRoomRequest();


    request.setPlayerId(1);

    request.setName(
            "哲学家"
    );



    Map<String,Object> result =
            controller.joinRoom(
                    room.getRoomId(),
                    request
            );



    assertTrue(
            (Boolean)result.get("success")
    );


}


}