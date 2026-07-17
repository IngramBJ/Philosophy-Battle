package com.philosophy.model;


public class ActionCost {


    public static int getCost(
            ActionType type
    ){

        return switch(type){


            case PHILOSOPHY -> 0;


            case MUJI -> 2;


            case BILLY -> 3;


            case BANANA -> 4;


            case VANSAMA -> 5;


            case WHITE_MOUSE -> 7;


            case DEMON_KING -> 3;


            case DOOR -> 1;


            case DEF_MUJI,
                 DEF_BILLY,
                 DEF_BANANA -> 0;

        };

    }


}