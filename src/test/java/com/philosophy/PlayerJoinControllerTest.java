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



public class PlayerJoinControllerTest {



    @Test
    public void playerCanJoinRoom(){



        GameRoomManager manager =
                new GameRoomManager();



        GameRoom room =
                manager.createRoom();




        GameBroadcastService service =
                new GameBroadcastService(
                        null
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



        GameRoomManager manager =
                new GameRoomManager();



        GameBroadcastService service =
                new GameBroadcastService(
                        null
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