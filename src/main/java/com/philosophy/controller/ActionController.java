package com.philosophy.controller;


import com.philosophy.engine.*;
import com.philosophy.model.*;
import com.philosophy.service.PlayerSessionService;
import com.philosophy.service.GameBroadcastService;


import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Map;



@RestController
@RequestMapping("/room")
public class ActionController {



    private final GameRoomManager roomManager;


    private final PlayerSessionService sessionService;


    private final GameBroadcastService broadcastService;




@Autowired
public ActionController(
        GameRoomManager roomManager,
        PlayerSessionService sessionService,
        com.philosophy.service.GameBroadcastService broadcastService
){

    this.roomManager =
            roomManager;


    this.sessionService =
            sessionService;


    this.broadcastService =
            broadcastService;

}

/**
 * 测试兼容
 */
public ActionController(
        GameRoomManager roomManager,
        PlayerSessionService sessionService
){

    this.roomManager = roomManager;

    this.sessionService = sessionService;

    this.broadcastService = null;

}






    @PostMapping("/{roomId}/action")
    public Map<String,Object> submitAction(


            @PathVariable String roomId,


            @RequestBody ActionRequest request


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







        /*
         * 玩家身份验证
         *
         * 1. 玩家是否在线
         * 2. 玩家是否属于当前房间
         */
        if(!sessionService.isOnline(

                String.valueOf(request.playerId)

        )){


            return Map.of(

                    "success",
                    false,

                    "message",
                    "玩家未连接"

            );


        }







        if(!sessionService.inRoom(

                String.valueOf(request.playerId),

                roomId

        )){


            return Map.of(

                    "success",
                    false,

                    "message",
                    "玩家不属于该房间"

            );


        }







        Player player =
                null;





        for(Player p :
                room.getGameState()
                        .getPlayers()){


            if(p.getId()
                    == request.playerId){


                player = p;


                break;

            }

        }






        if(player==null){


            return Map.of(

                    "success",

                    false,

                    "message",

                    "玩家不存在"

            );


        }








        try{


            Action action =
                    new Action(

                            ActionType.valueOf(

                                    request.type

                            ),

                            request.targets

                    );







            room.getRoundManager()
                    .submitAction(

                            player,

                            action

                    );







            /*
             * 广播玩家行动事件
             */
            if(broadcastService != null){

    broadcastService.broadcastEvent(

            roomId,

            "ACTION_SUBMIT",

            Map.of(

                    "playerId",

                    player.getId(),


                    "action",

                    request.type

            )

    );

}








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









    public static class ActionRequest{


        public int playerId;


        public String type;


        public java.util.List<Integer> targets;


    }



}