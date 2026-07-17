package com.philosophy.controller;


import com.philosophy.engine.*;
import com.philosophy.model.*;


import org.springframework.web.bind.annotation.*;


import java.util.Map;



@RestController
@RequestMapping("/room")
public class ActionController {


    private final GameRoomManager roomManager;



    public ActionController(
            GameRoomManager roomManager
    ){

        this.roomManager =
                roomManager;

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



        Player player =
                null;



        for(Player p:
                room.getGameState()
                        .getPlayers()){


            if(p.getId()
                    ==request.playerId){


                player=p;

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