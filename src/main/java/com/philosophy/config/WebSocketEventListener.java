package com.philosophy.config;


import com.philosophy.service.PlayerSessionService;
import com.philosophy.service.GameBroadcastService;


import org.springframework.context.event.EventListener;

import org.springframework.stereotype.Component;


import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;



import java.util.Map;



@Component
public class WebSocketEventListener {



    private final PlayerSessionService sessionService;


    private final GameBroadcastService broadcastService;





    public WebSocketEventListener(

            PlayerSessionService sessionService,

            GameBroadcastService broadcastService

    ){

        this.sessionService =
                sessionService;


        this.broadcastService =
                broadcastService;

    }








    @EventListener
    public void connect(
            SessionConnectEvent event
    ){



        var accessor =
                org.springframework.messaging.simp.stomp.StompHeaderAccessor
                        .wrap(
                                event.getMessage()
                        );



        Map<String,Object> attributes =
                accessor.getSessionAttributes();



        if(attributes==null){

            return;

        }



        String playerId =
                (String)attributes.get("playerId");


        String roomId =
                (String)attributes.get("roomId");



        if(playerId==null || roomId==null){

            return;

        }




        sessionService.connect(
                playerId,
                roomId
        );




        broadcastService.broadcastEvent(

                roomId,

                "PLAYER_ONLINE",

                Map.of(

                        "playerId",
                        playerId

                )

        );


    }









    @EventListener
    public void disconnect(
            SessionDisconnectEvent event
    ){


        var accessor =
                org.springframework.messaging.simp.stomp.StompHeaderAccessor
                        .wrap(
                                event.getMessage()
                        );



        Map<String,Object> attributes =
                accessor.getSessionAttributes();



        if(attributes==null){

            return;

        }




        String playerId =
                (String)attributes.get("playerId");


        String roomId =
                (String)attributes.get("roomId");




        if(playerId==null){

            return;

        }



        sessionService.disconnect(
                playerId
        );





        if(roomId!=null){

            broadcastService.broadcastEvent(

                    roomId,

                    "PLAYER_OFFLINE",

                    Map.of(

                            "playerId",

                            playerId

                    )

            );

        }


    }



}