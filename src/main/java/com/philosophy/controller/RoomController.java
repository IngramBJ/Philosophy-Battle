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
     * 玩家加入房间
     */
    @PostMapping("/{roomId}/join")
    public Map<String,Object> joinRoom(

            @PathVariable String roomId,

            @RequestBody JoinRoomRequest request

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



        Player player =
                new Player(

                        request.getPlayerId(),

                        request.getName()

                );



        boolean result =
                room.addPlayer(player);




        if(!result){


            return Map.of(

                    "success",
                    false,

                    "message",
                    "加入失败"

            );

        }



        return Map.of(

                "success",
                true

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









    /**
     * 开始游戏
     */
    @PostMapping("/{roomId}/start")
    public Map<String,Object> startGame(

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



        try{


            room.startGame();



            return Map.of(

                    "success",
                    true

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