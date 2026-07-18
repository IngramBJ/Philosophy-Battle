package com.philosophy.engine;


import com.philosophy.model.*;
import com.philosophy.service.GameBroadcastService;

import java.util.List;



public class GameRoom {


    private final String roomId;


    private final GameState gameState;


    private GameStatus status;


    private int round;


    private final BattleEngine engine;


    private final RoundManager roundManager;


    private final GameBroadcastService broadcastService;





    public GameRoom(
            String roomId,
            GameBroadcastService broadcastService
    ){

        this.roomId =
                roomId;


        this.broadcastService =
                broadcastService;



        this.gameState =
                new GameState();



        this.status =
                GameStatus.WAITING;



        this.round =
                0;



        this.engine =
                new BattleEngine();



        this.roundManager =
                new RoundManager(
                        this,
                        broadcastService
                );


    }









    /**
     * 玩家加入
     */
    public boolean addPlayer(
            Player player
    ){


        if(status != GameStatus.WAITING){

            return false;

        }



        for(Player p:
                gameState.getPlayers()){


            if(p.getId()==player.getId()){

                return false;

            }

        }



        gameState.addPlayer(player);



        return true;


    }









    /**
     * 开始游戏
     */
    public void startGame(){


    if(gameState.getPlayers().size()<2){

        throw new IllegalStateException(
                "至少需要两个玩家"
        );

    }


    status =
            GameStatus.RUNNING;


    round = 1;



    if(broadcastService != null){


        broadcastService.broadcastEvent(

                roomId,

                "GAME_START",

                java.util.Map.of(

                        "round",
                        round

                )

        );


    }



    roundManager.startRound();


}









    /**
     * 下一回合
     */
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









    public RoundManager getRoundManager(){


        return roundManager;


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









    public String getRoomId(){


        return roomId;


    }



}