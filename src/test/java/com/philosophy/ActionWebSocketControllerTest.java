package com.philosophy;


import com.philosophy.controller.ActionWebSocketController;
import com.philosophy.dto.ActionMessage;
import com.philosophy.engine.GameRoomManager;
import com.philosophy.service.GameBroadcastService;
import com.philosophy.service.PlayerSessionService;


import org.junit.jupiter.api.Test;


import java.util.List;



import static org.junit.jupiter.api.Assertions.*;



public class ActionWebSocketControllerTest {



    @Test
    public void createActionMessageTest(){


        ActionMessage message =
                new ActionMessage(

                        1,

                        "ATTACK",

                        List.of(2)

                );



        assertEquals(
                1,
                message.playerId
        );


        assertEquals(
                "ATTACK",
                message.type
        );


    }



}