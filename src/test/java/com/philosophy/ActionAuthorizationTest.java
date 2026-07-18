package com.philosophy;


import com.philosophy.controller.ActionController;
import com.philosophy.engine.GameRoom;
import com.philosophy.engine.GameRoomManager;
import com.philosophy.model.Player;
import com.philosophy.service.GameBroadcastService;
import com.philosophy.service.PlayerSessionService;


import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



public class ActionAuthorizationTest {



    @Test
    public void offlinePlayerCannotSubmitAction(){

        GameBroadcastService broadcastService =
            mock(
                GameBroadcastService.class
            );



        GameRoomManager manager =
                new GameRoomManager(broadcastService);



        GameRoom room =
                manager.createRoom();




        Player playerA =
                new Player(
                        1,
                        "A"
                );


        Player playerB =
                new Player(
                        2,
                        "B"
                );



        room.addPlayer(
                playerA
        );


        room.addPlayer(
                playerB
        );



        room.startGame();




        PlayerSessionService sessionService =
                new PlayerSessionService();




        ActionController controller =
                new ActionController(

                        manager,

                        sessionService

                );




        ActionController.ActionRequest request =
                new ActionController.ActionRequest();



        request.playerId=1;


        request.type="ATTACK";


        request.targets =
                List.of();





        Map<String,Object> result =
                controller.submitAction(

                        room.getRoomId(),

                        request

                );




        assertEquals(

                false,

                result.get("success")

        );



        assertEquals(

                "玩家未连接",

                result.get("message")

        );


    }







    @Test
    public void playerCannotActInOtherRoom(){

GameBroadcastService broadcastService =
            mock(
                GameBroadcastService.class
            );

        GameRoomManager manager =
                new GameRoomManager(broadcastService);



        GameRoom room1 =
                manager.createRoom();



        GameRoom room2 =
                manager.createRoom();




        Player playerA =
                new Player(
                        1,
                        "A"
                );


        Player playerB =
                new Player(
                        2,
                        "B"
                );




        room1.addPlayer(
                playerA
        );


        room1.addPlayer(
                playerB
        );



        room1.startGame();




        Player playerC =
                new Player(
                        3,
                        "C"
                );


        Player playerD =
                new Player(
                        4,
                        "D"
                );



        room2.addPlayer(
                playerC
        );


        room2.addPlayer(
                playerD
        );



        room2.startGame();






        PlayerSessionService sessionService =
                new PlayerSessionService();




        /*
         * 玩家1实际属于room1
         */
        sessionService.connect(

                "1",

                room1.getRoomId()

        );





        ActionController controller =
                new ActionController(

                        manager,

                        sessionService

                );





        ActionController.ActionRequest request =
                new ActionController.ActionRequest();




        request.playerId=1;


        request.type="ATTACK";


        request.targets =
                List.of();






        Map<String,Object> result =
                controller.submitAction(

                        room2.getRoomId(),

                        request

                );





        assertEquals(

                false,

                result.get("success")

        );



        assertEquals(

                "玩家不属于该房间",

                result.get("message")

        );


    }



}