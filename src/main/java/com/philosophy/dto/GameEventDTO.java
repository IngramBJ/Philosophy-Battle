package com.philosophy.dto;


import java.time.Instant;
import java.util.Map;



public class GameEventDTO {



    public String eventType;



    public String roomId;



    public long timestamp;



    public Map<String,Object> data;





    public GameEventDTO(
            String eventType,
            String roomId,
            Map<String,Object> data
    ){


        this.eventType =
                eventType;



        this.roomId =
                roomId;



        this.timestamp =
                Instant.now()
                        .toEpochMilli();



        this.data =
                data;


    }




}