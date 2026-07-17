package com.philosophy.controller;


import com.philosophy.dto.JoinRoomRequest;
import com.philosophy.engine.*;
import com.philosophy.model.Player;
import com.philosophy.service.GameBroadcastService;


import org.springframework.web.bind.annotation.*;

import java.util.Map;



@RestController
@RequestMapping("/room")
public class PlayerController {



    private final GameRoomManager roomManager;


    private final GameBroadcastService broadcastService;




    public PlayerController(
            GameRoomManager roomManager,
            GameBroadcastService broadcastService
    ){

        this.roomManager = roomManager;

        this.broadcastService =
                broadcastService;

    }






    @PostMapping("/{roomId}/join")
    public Map<String,Object> joinRoom(

            @PathVariable String roomId,

            @RequestBody JoinRoomRequest request

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






        Player player =
                new Player(

                        request.getPlayerId(),

                        request.getName()

                );





        boolean success =
                room.addPlayer(player);





        if(!success){


            return Map.of(

                    "success",
                    false,

                    "message",
                    "无法加入房间"

            );

        }







        /*
         * 广播玩家加入事件
         */
        broadcastService.broadcastEvent(

        roomId,

        "PLAYER_JOIN",

        Map.of(

                "playerId",
                player.getId(),

                "name",
                player.getName()

        )

);







        return Map.of(

                "success",

                true,


                "playerId",

                player.getId()

        );


    }



}