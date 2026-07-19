package com.philosophy;


import com.philosophy.controller.GameController;
import com.philosophy.engine.GameRoom;
import com.philosophy.engine.GameRoomManager;
import com.philosophy.model.Player;
import com.philosophy.service.GameBroadcastService;


import org.junit.jupiter.api.Test;


import java.util.Map;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;



public class GameStartControllerTest {



    @Test
    public void startGameSuccess(){


GameBroadcastService broadcastService =
            mock(
                GameBroadcastService.class
            );

        GameRoomManager manager =
                new GameRoomManager(broadcastService);



        GameRoom room =
                manager.createRoom();



        room.addPlayer(
                new Player(
                        1,
                        "A"
                )
        );


        room.addPlayer(
                new Player(
                        2,
                        "B"
                )
        );




        GameController controller =
                new GameController(

                        manager,

                        broadcastService

                );




        Map<String,Object> result =
                controller.startGame(

                        room.getRoomId()

                );




        assertEquals(

                true,

                result.get("success")

        );



        assertEquals(

                1,

                room.getRound()

        );


    }








    @Test
    public void startGameFailWhenRoomMissing(){

GameBroadcastService broadcastService =
            mock(
                GameBroadcastService.class
            );

        GameController controller =
                new GameController(

                        new GameRoomManager(broadcastService),

                        new GameBroadcastService(
                                null, null
                        )

                );



        Map<String,Object> result =
                controller.startGame(

                        "xxx"

                );



        assertEquals(

                false,

                result.get("success")

        );


    }








    @Test
    public void startGameFailWhenPlayersLessThanTwo(){
GameBroadcastService broadcastService =
            mock(
                GameBroadcastService.class
            );


        GameRoomManager manager =
                new GameRoomManager(broadcastService);



        GameRoom room =
                manager.createRoom();




        room.addPlayer(

                new Player(
                        1,
                        "A"
                )

        );




        GameController controller =
                new GameController(

                        manager,

                        broadcastService

                );




        Map<String,Object> result =
                controller.startGame(

                        room.getRoomId()

                );



        assertEquals(

                false,

                result.get("success")

        );


    }



}