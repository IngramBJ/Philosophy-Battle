package com.philosophy.controller;


import com.philosophy.dto.JoinRoomRequest;
import com.philosophy.engine.*;
import com.philosophy.model.Player;


import org.springframework.web.bind.annotation.*;

import java.util.Map;



@RestController
@RequestMapping("/room")
public class PlayerController {



    private final GameRoomManager roomManager;



    public PlayerController(
            GameRoomManager roomManager
    ){

        this.roomManager = roomManager;

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



return Map.of(

        "success",
        true,

        "playerId",
        player.getId()

);


    }


}