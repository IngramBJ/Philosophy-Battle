package com.philosophy.controller;


import com.philosophy.engine.*;
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


}