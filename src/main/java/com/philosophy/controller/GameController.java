package com.philosophy.controller;


import com.philosophy.engine.GameRoom;
import com.philosophy.engine.GameRoomManager;
import com.philosophy.service.GameBroadcastService;


import org.springframework.web.bind.annotation.*;


import java.util.Map;



@RestController
@RequestMapping("/room")
public class GameController {


    private final GameRoomManager roomManager;


    private final GameBroadcastService broadcastService;




    public GameController(
            GameRoomManager roomManager,
            GameBroadcastService broadcastService
    ){

        this.roomManager = roomManager;

        this.broadcastService =
                broadcastService;

    }






    /**
     * 开始游戏
     */
    @PostMapping("/{roomId}/start")
    public Map<String,Object> startGame(

            @PathVariable String roomId

    ){



        GameRoom room =
                roomManager.getRoom(roomId);





        if(room == null){


            return Map.of(

                    "success",

                    false,


                    "message",

                    "房间不存在"

            );

        }






        try{


            room.startGame();





            /*
             * 广播游戏开始事件
             */
            broadcastService.broadcastEvent(

        roomId,

        "GAME_START",

        Map.of(

                "round",

                room.getRound()

        )

);







            return Map.of(

                    "success",

                    true,


                    "round",

                    room.getRound()

            );



        }catch(Exception e){



            return Map.of(

                    "success",

                    false,


                    "message",

                    e.getMessage()

            );

        }



    }



}