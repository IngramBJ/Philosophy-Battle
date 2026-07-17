package com.philosophy.engine;


import com.philosophy.model.*;



public class RoundManager {


    private final GameRoom room;
    private final TimerService timerService;



    private boolean roundStarted;


public RoundManager(
        GameRoom room
){

    this.room = room;

    this.roundStarted=false;

    this.timerService =
            new TimerService();

}





    /**
     * 开始当前回合
     */
    public void startRound(){


    if(room.getStatus()
            != GameStatus.RUNNING){


        throw new IllegalStateException(
                "游戏未开始"
        );

    }



    resetActions();



    roundStarted=true;


}

    public void startRound(
        long seconds
){

    if(room.getStatus()
            != GameStatus.RUNNING){


        throw new IllegalStateException(
                "游戏未开始"
        );

    }

    resetActions();

    roundStarted=true;



    timerService.startTimer(
            () -> {

                finishRound();

            },
            seconds
    );


}





    /**
     * 玩家提交行动
     */
    public void submitAction(
            Player player,
            Action action
    ){


        if(!roundStarted){


            throw new IllegalStateException(
                    "当前没有进行中的回合"
            );


        }



        player.setCurrentAction(action);


    }





    /**
     * 结束回合并结算
     */
    public RoundResult finishRound(){

    if(!roundStarted){

        throw new IllegalStateException(
                "当前没有进行中的回合"
        );

    }


    roundStarted = false;



    RoundResult result =
            room.nextRound();



    /*
     * 如果游戏还在进行
     * 自动开启下一回合
     */
    if(room.getStatus()
            == GameStatus.RUNNING){


        startRound();


    }



    return result;


}





    public boolean isRoundStarted(){

        return roundStarted;

    }

    private void resetActions(){


    for(Player player:
            room.getGameState()
                    .getAlivePlayers()){


        player.clearAction();

    }

}


}