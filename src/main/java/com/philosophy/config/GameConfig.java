package com.philosophy.config;


import com.philosophy.engine.GameRoomManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class GameConfig {



    @Bean
    public GameRoomManager gameRoomManager(){


        return new GameRoomManager();


    }


}