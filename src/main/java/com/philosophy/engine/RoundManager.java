package com.philosophy.engine;


import com.philosophy.model.*;
import com.philosophy.service.GameBroadcastService;



public class RoundManager {


    private final GameRoom room;
    private final TimerService timerService;
    private final GameBroadcastService broadcastService;



    private boolean roundStarted;


/**
 * 原来的测试和本地调用使用
 */
public RoundManager(
        GameRoom room
){

    this.room = room;

    this.broadcastService = null;

    this.roundStarted=false;

    this.timerService =
            new TimerService();

}



/**
 * WebSocket版本
 */
public RoundManager(
        GameRoom room,
        GameBroadcastService broadcastService
){

    this.room = room;

    this.broadcastService =
            broadcastService;

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

    if(broadcastService != null){

    broadcastService.broadcastState(room);

}


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

    if(broadcastService != null){

    broadcastService.broadcastState(room);

}



    /*
     * 所有人提交完成
     * 自动进入结算
     */
    if(allPlayersSubmitted()){


        finishRound();


    }


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



    roundStarted=false;



    RoundResult result =
            room.nextRound();



    if(broadcastService != null){


    broadcastService.broadcastEvent(

            room.getRoomId(),

            "ROUND_RESULT",

            java.util.Map.of(

                    "logs",

                    result.getLogs()

            )

    );


    broadcastService.broadcastState(room);


}



    /*
     * 自动开启下一回合
     *
     * 如果游戏还在运行
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

private boolean allPlayersSubmitted(){

    for(Player player:
            room.getGameState()
                    .getPlayers()){


        if(player.isAlive()
                && player.getCurrentAction()==null){

            return false;

        }

    }


    return true;

}


}