package com.philosophy;


import com.philosophy.controller.PlayerController;
import com.philosophy.dto.JoinRoomRequest;
import com.philosophy.engine.GameRoom;
import com.philosophy.engine.GameRoomManager;
import com.philosophy.service.GameBroadcastService;


import org.junit.jupiter.api.Test;


import org.springframework.messaging.simp.SimpMessagingTemplate;


import java.util.Map;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;



public class PlayerJoinControllerTest {



    @Test
    public void playerCanJoinRoom(){


GameBroadcastService broadcastService =
            mock(
                GameBroadcastService.class
            );

        GameRoomManager manager =
                new GameRoomManager(broadcastService);



        GameRoom room =
                manager.createRoom();




        GameBroadcastService service =
                new GameBroadcastService(
                        null, null
                );




        PlayerController controller =
                new PlayerController(

                        manager,

                        service

                );





        JoinRoomRequest request =
                new JoinRoomRequest();



        request.setPlayerId(1);

        request.setName("Alice");






        Map<String,Object> result =
                controller.joinRoom(

                        room.getRoomId(),

                        request

                );






        assertEquals(

                true,

                result.get("success")

        );



        assertEquals(

                1,

                room.getGameState()
                        .getPlayers()
                        .size()

        );


    }








    @Test
    public void cannotJoinMissingRoom(){

GameBroadcastService broadcastService =
            mock(
                GameBroadcastService.class
            );

        GameRoomManager manager =
                new GameRoomManager(broadcastService);



        GameBroadcastService service =
                new GameBroadcastService(
                        null, null
                );



        PlayerController controller =
                new PlayerController(

                        manager,

                        service

                );



        JoinRoomRequest request =
                new JoinRoomRequest();



        request.setPlayerId(1);

        request.setName("Alice");




        Map<String,Object> result =
                controller.joinRoom(

                        "xxx",

                        request

                );



        assertEquals(

                false,

                result.get("success")

        );


    }



}