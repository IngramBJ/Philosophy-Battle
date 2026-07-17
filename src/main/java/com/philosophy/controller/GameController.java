package com.philosophy.controller;


import com.philosophy.engine.GameRoom;
import com.philosophy.engine.GameRoomManager;


import org.springframework.web.bind.annotation.*;


import java.util.Map;



@RestController
@RequestMapping("/room")
public class GameController {


    private final GameRoomManager roomManager;



    public GameController(
            GameRoomManager roomManager
    ){

        this.roomManager = roomManager;

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