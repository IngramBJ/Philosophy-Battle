package com.philosophy.dto;


import java.util.List;



public class ActionMessage {


    public int playerId;


    public String type;


    public List<Integer> targets;



    public ActionMessage(){

    }



    public ActionMessage(
            int playerId,
            String type,
            List<Integer> targets
    ){

        this.playerId =
                playerId;


        this.type =
                type;


        this.targets =
                targets;

    }


}