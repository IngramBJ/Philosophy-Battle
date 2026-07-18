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

            System.out.println(
    "CREATE ROUND MANAGER:"
    + System.identityHashCode(this)
);

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

            System.out.println(
    "CREATE ROUND MANAGER:"
    + System.identityHashCode(this)
);

}





    /**
     * 开始当前回合
     */
    public void startRound(){

        System.out.println(
    "START ROUND:"
    +System.identityHashCode(this)
);


    if(room.getStatus()
            != GameStatus.RUNNING){


        throw new IllegalStateException(
                "游戏未开始"
        );

    }



    resetActions();



    roundStarted=true;

    System.out.println(
    "ROUND STARTED="
    +roundStarted
);

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
public synchronized void submitAction(
        Player player,
        Action action
){

    System.out.println(
    "SUBMIT ROUND:"
    +System.identityHashCode(this)
    +" STARTED="
    +roundStarted
);



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
public synchronized RoundResult finishRound(){


    System.out.println(
        "========== FINISH ROUND =========="
    );


    if(!roundStarted){

        throw new IllegalStateException(
                "当前没有进行中的回合"
        );

    }


    roundStarted=false;


    RoundResult result =
            room.nextRound();


    System.out.println(
        "ROUND RESULT="
        +result
    );


    if(result!=null){

        System.out.println(
            "LOGS="
            +result.getLogs()
        );

    }



if(broadcastService != null){


    System.out.println(
        "SEND ROUND RESULT"
    );


    broadcastService.broadcastEvent(

            room.getRoomId(),

            "ROUND_RESULT",

            java.util.Map.of(

                    "round",
                    room.getRound(),

                    "message",
"ROUND_FINISHED"

            )

    );


    broadcastService.broadcastState(room);


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

        System.out.println(
            "CHECK PLAYER:"
            +player.getId()
            +" ACTION="
            +player.getCurrentAction()
        );


        if(player.isAlive()
                && player.getCurrentAction()==null){

            return false;

        }

    }


    return true;

}


}