package com.philosophy.controller;


import com.philosophy.dto.GameStateDTO;
import com.philosophy.dto.JoinRoomRequest;
import com.philosophy.engine.GameRoom;
import com.philosophy.engine.GameRoomManager;
import com.philosophy.model.Player;

import org.springframework.web.bind.annotation.*;

import java.util.Map;



@RestController
@RequestMapping("/room")
public class RoomController {



    private final GameRoomManager roomManager;



    public RoomController(
            GameRoomManager roomManager
    ){

        this.roomManager = roomManager;

    }





    /**
     * 创建房间
     */
    @PostMapping("/create")
    public Map<String,String> createRoom(){


        GameRoom room =
                roomManager.createRoom();



        return Map.of(
                "roomId",
                room.getRoomId()
        );


    }






    /**
     * 查询房间数量
     */
    @GetMapping("/count")
    public Map<String,Integer> count(){


        return Map.of(
                "count",
                roomManager.roomCount()
        );


    }




    /**
     * 查询游戏状态
     */
    @GetMapping("/{roomId}")
    public Object state(

            @PathVariable String roomId

    ){


        GameRoom room =
                roomManager.getRoom(roomId);



        if(room==null){


            return Map.of(

                    "success",
                    false,

                    "message",
                    "房间不存在"

            );

        }



        return new GameStateDTO(

                room.getRoomId(),

                room.getStatus(),

                room.getRound(),

                room.getGameState()
                        .getPlayers()

        );


    }

    



}