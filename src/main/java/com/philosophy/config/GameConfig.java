package com.philosophy.config;


import com.philosophy.engine.GameRoomManager;
import com.philosophy.service.GameBroadcastService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class GameConfig {


    @Bean
    public GameRoomManager gameRoomManager(
            GameBroadcastService broadcastService
    ){


        return new GameRoomManager(
                broadcastService
        );


    }


}