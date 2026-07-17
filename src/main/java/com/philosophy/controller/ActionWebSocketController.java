package com.philosophy.controller;


import com.philosophy.dto.ActionMessage;
import com.philosophy.engine.GameRoom;
import com.philosophy.engine.GameRoomManager;
import com.philosophy.model.*;
import com.philosophy.service.GameBroadcastService;
import com.philosophy.service.PlayerSessionService;


import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;



import java.util.Map;



@Controller
public class ActionWebSocketController {



    private final GameRoomManager roomManager;


    private final PlayerSessionService sessionService;


    private final GameBroadcastService broadcastService;




    public ActionWebSocketController(

            GameRoomManager roomManager,

            PlayerSessionService sessionService,

            GameBroadcastService broadcastService

    ){

        this.roomManager =
                roomManager;


        this.sessionService =
                sessionService;


        this.broadcastService =
                broadcastService;

    }







    /**
     * WebSocket提交行动
     *
     * 客户端:
     *
     * /app/room/{roomId}/action
     *
     */
    @MessageMapping("/room/{roomId}/action")
    public void submitAction(

            @DestinationVariable String roomId,

            ActionMessage message

    ){



        GameRoom room =
                roomManager.getRoom(roomId);



        if(room==null){

            return;

        }





        /*
         * 玩家在线检查
         */
        if(!sessionService.isOnline(
                String.valueOf(message.playerId)
        )){


            broadcastService.broadcastEvent(

                    roomId,

                    "ACTION_ERROR",

                    Map.of(
                            "message",
                            "玩家未连接"
                    )

            );


            return;

        }







        /*
         * 房间归属检查
         */
        if(!sessionService.inRoom(

                String.valueOf(message.playerId),

                roomId

        )){


            broadcastService.broadcastEvent(

                    roomId,

                    "ACTION_ERROR",

                    Map.of(
                            "message",
                            "玩家不属于该房间"
                    )

            );


            return;

        }







        Player player =
                null;



        for(Player p:
                room.getGameState()
                        .getPlayers()){


            if(p.getId()
                    ==
                    message.playerId){


                player=p;

                break;

            }

        }




        if(player==null){

            return;

        }






        try{


            Action action =
                    new Action(

                            ActionType.valueOf(
                                    message.type
                            ),

                            message.targets

                    );



            room.getRoundManager()
                    .submitAction(

                            player,

                            action

                    );





            broadcastService.broadcastEvent(

                    roomId,

                    "ACTION_SUBMIT",

                    Map.of(

                            "playerId",

                            player.getId(),


                            "action",

                            message.type

                    )

            );



        }catch(Exception e){



            broadcastService.broadcastEvent(

                    roomId,

                    "ACTION_ERROR",

                    Map.of(

                            "message",

                            e.getMessage()

                    )

            );


        }



    }



}