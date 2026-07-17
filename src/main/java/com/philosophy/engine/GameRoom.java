package com.philosophy.engine;


import com.philosophy.model.*;

import java.util.ArrayList;
import java.util.List;



public class GameRoom {


    private final String roomId;



    private final GameState gameState;



    private GameStatus status;



    private int round;



    private final BattleEngine engine;



    public GameRoom(
            String roomId
    ){


        this.roomId = roomId;


        this.gameState =
                new GameState();


        this.status =
                GameStatus.WAITING;


        this.round = 0;


        this.engine =
                new BattleEngine();

    }




    public void addPlayer(
            Player player
    ){


        if(status != GameStatus.WAITING){

            throw new IllegalStateException(
                    "游戏已经开始"
            );

        }


        gameState.addPlayer(player);

    }





    public void startGame(){


        if(gameState.getPlayers().size()<2){

            throw new IllegalStateException(
                    "至少需要两个玩家"
            );

        }


        status =
                GameStatus.RUNNING;


        round = 1;


    }





    public RoundResult nextRound(){


        if(status != GameStatus.RUNNING){

            throw new IllegalStateException(
                    "游戏未开始"
            );

        }



        RoundResult result =
                engine.resolveRound(
                        gameState
                );



        round++;



        checkWinner();



        return result;

    }





    private void checkWinner(){


        List<Player> alive =
                gameState.getAlivePlayers();



        if(alive.size()<=1){


            status =
                    GameStatus.FINISHED;


        }


    }





    public GameState getGameState(){

        return gameState;

    }



    public GameStatus getStatus(){

        return status;

    }



    public int getRound(){

        return round;

    }


}