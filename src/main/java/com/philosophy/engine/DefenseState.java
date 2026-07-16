package com.philosophy.engine;


import com.philosophy.model.ActionType;
import com.philosophy.model.Player;


import java.util.HashMap;
import java.util.Map;



public class DefenseState {


    private final Map<Player, ActionType> defenses;



    public DefenseState(){

        defenses = new HashMap<>();

    }



    public void addDefense(
            Player player,
            ActionType type
    ){

        defenses.put(
                player,
                type
        );

    }



    public boolean hasDefense(
            Player player
    ){

        return defenses.containsKey(player);

    }



    public ActionType getDefense(
            Player player
    ){

        return defenses.get(player);

    }


}