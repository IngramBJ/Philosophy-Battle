package com.philosophy.service;


import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;



@Service
public class GameBroadcastService {


    private final SimpMessagingTemplate template;



    public GameBroadcastService(
            SimpMessagingTemplate template
    ){

        this.template =
                template;

    }




    public void broadcast(
            String roomId,
            Object message
    ){


        template.convertAndSend(

                "/topic/room/"+roomId,

                message

        );


    }


}