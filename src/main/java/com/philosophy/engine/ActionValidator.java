package com.philosophy.engine;


import com.philosophy.model.*;



public class ActionValidator {


    public boolean validate(
            Player player,
            Action action
    ){


        if(action == null){

            return false;

        }


        return switch(action.getType()){


            case PHILOSOPHY ->
                    true;


            case MUJI ->
                    player.getPhilosophyPoints() >= 2;


            case BILLY ->
                    player.getPhilosophyPoints() >= 3;


            case BANANA ->
                    player.getPhilosophyPoints() >= 4;


            case VANSAMA ->
                    player.getPhilosophyPoints() >= 5;


            case WHITE_MOUSE ->
                    player.getPhilosophyPoints() >= 7;


            case DEMON_KING ->
                    player.getPhilosophyPoints() >= 3;


            case DOOR ->
                    player.getPhilosophyPoints() >= 1;


            default ->
                    true;

        };

    }

}