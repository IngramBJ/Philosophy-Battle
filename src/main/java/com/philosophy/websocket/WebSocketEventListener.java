package com.philosophy.websocket;


import com.philosophy.service.GameBroadcastService;
import com.philosophy.service.PlayerSessionService;


import org.springframework.context.event.EventListener;

import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

import org.springframework.stereotype.Component;

import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;



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


        StompHeaderAccessor accessor =
                StompHeaderAccessor.wrap(event.getMessage());



        String roomId =
                (String)accessor
                        .getSessionAttributes()
                        .get("roomId");



        String playerId =
                (String)accessor
                        .getSessionAttributes()
                        .get("playerId");



        if(roomId==null || playerId==null){

            return;

        }



        int id =
                Integer.parseInt(playerId);



        sessionService.connect(
                playerId,
                roomId
        );



        broadcastService.registerPlayer(
                roomId,
                id,
                accessor.getSessionId()
        );


    }









    @EventListener
    public void disconnect(
            SessionDisconnectEvent event
    ){


        StompHeaderAccessor accessor =
                StompHeaderAccessor.wrap(event.getMessage());



        String roomId =
                (String)accessor
                        .getSessionAttributes()
                        .get("roomId");



        String playerId =
                (String)accessor
                        .getSessionAttributes()
                        .get("playerId");



        if(roomId==null || playerId==null){

            return;

        }



        int id =
                Integer.parseInt(playerId);



        sessionService.disconnect(
                playerId
        );



        broadcastService.removePlayer(
                roomId,
                id
        );


    }



}