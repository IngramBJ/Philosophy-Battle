package com.philosophy.engine;


import com.philosophy.model.*;

import java.util.HashMap;
import java.util.Map;



public class RoundContext {


    private final GameState game;


    private final Map<Player,Action> actions;



    public RoundContext(
            GameState game
    ){

        this.game = game;


        this.actions =
                new HashMap<>();


        for(Player player:
                game.getAlivePlayers()){


            if(player.getCurrentAction()!=null){

                actions.put(
                        player,
                        player.getCurrentAction()
                );

            }

        }

    }



    public GameState getGame(){

        return game;

    }



    public Map<Player,Action> getActions(){

        return actions;

    }



    public Action getAction(
            Player player
    ){

        return actions.get(player);

    }

}