package com.philosophy.engine;


import com.philosophy.model.*;
import com.philosophy.service.GameBroadcastService;


public class RoundManager {


    private final GameRoom room;

    private final TimerService timerService;

    private final GameBroadcastService broadcastService;


    private boolean roundStarted;



    public RoundManager(GameRoom room){

        this(
                room,
                null
        );

    }




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
                +System.identityHashCode(this)
        );

    }






    /**
     * 开始回合
     */
    public synchronized void startRound(){


        System.out.println(
                "START ROUND:"
                +System.identityHashCode(this)
        );



        if(room.getStatus()
                !=GameStatus.RUNNING){

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



        if(broadcastService!=null){

            broadcastService.broadcastState(room);

        }


    }







    public void startRound(long seconds){


        startRound();


        timerService.startTimer(

                () -> {

                    if(roundStarted){

                        finishRound();

                    }

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



if(allPlayersSubmitted()){


    finishRound();


}
else{


    if(broadcastService != null){

        broadcastService.broadcastEvent(

                room.getRoomId(),

                "ACTION_SUBMIT",

                java.util.Map.of(

                    "playerId",
                    player.getId(),

                    "action",
                    action.getType().name()

                )

        );

    }

}






        if(allPlayersSubmitted()){


            System.out.println(
                    "ALL PLAYERS SUBMITTED"
            );


            finishRound();


        }


    }









    /**
     * 结束回合
     */
public synchronized RoundResult finishRound(){


    System.out.println(
            "========== FINISH ROUND =========="
    );


    if(!roundStarted){

        return null;

    }



    /*
     防止重复提交触发两次
     */
    roundStarted=false;



    int finishedRound =
            room.getRound();



    RoundResult result =
            room.nextRound();



    System.out.println(
            "ROUND RESULT="
            +result
    );



    if(broadcastService != null){


        /*
         先发送结果
         */
        broadcastService.broadcastEvent(

                room.getRoomId(),

                "ROUND_RESULT",

                java.util.Map.of(

                        "round",
                        finishedRound,

                        "message",
                        "ROUND_FINISHED"

                )

        );



        /*
         再发送状态
         */
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

                    &&player.getCurrentAction()==null){


                return false;


            }

        }



        return true;


    }



}