package com.philosophy.engine;


import com.philosophy.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class RoundContext {


    private final GameState game;



    /**
     * 本轮所有存活玩家
     */
    private final List<Player> alivePlayers;



    /**
     * 本轮有效行动
     */
    private final Map<Player,Action> actions;



    public RoundContext(
            GameState game
    ){

        this.game = game;



        this.alivePlayers =
                game.getAlivePlayers();



        this.actions =
                new HashMap<>();



        for(Player player:
                alivePlayers){


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



    public List<Player> getAlivePlayers(){

        return alivePlayers;

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