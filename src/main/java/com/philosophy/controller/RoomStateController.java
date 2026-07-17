package com.philosophy.controller;


import com.philosophy.dto.GameStateDTO;
import com.philosophy.engine.GameRoom;
import com.philosophy.engine.GameRoomManager;


import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/room")
public class RoomStateController {


    private final GameRoomManager roomManager;



    public RoomStateController(
            GameRoomManager roomManager
    ){

        this.roomManager =
                roomManager;

    }






    /**
     * 获取房间当前状态
     */
    @GetMapping("/{roomId}/state")
    public Object state(

            @PathVariable String roomId

    ){


        GameRoom room =
                roomManager.getRoom(roomId);



        if(room==null){


            return java.util.Map.of(

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