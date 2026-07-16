package com.philosophy.model;


import java.util.ArrayList;
import java.util.List;



public class GameState {


    private final List<Player> players;



    public GameState(){

        players = new ArrayList<>();

    }



    public void addPlayer(Player player){

        players.add(player);

    }



    public List<Player> getPlayers(){

        return players;

    }



    public List<Player> getAlivePlayers(){

        return players.stream()
                .filter(Player::isAlive)
                .toList();

    }



    public boolean isGameOver(){

        return getAlivePlayers().size() <= 1;

    }


}