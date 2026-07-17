package com.philosophy;


import com.philosophy.engine.*;
import com.philosophy.model.*;

import org.junit.jupiter.api.Test;


import java.util.List;


import static org.junit.jupiter.api.Assertions.*;



public class ActionSubmitTest {



    @Test
    public void twoPlayersSubmitActionThenAutoFinishRound(){



        /*
         * 创建房间
         */
        GameRoom room =
                new GameRoom("room1");



        /*
         * 创建玩家
         */
        Player a =
                new Player(
                        1,
                        "A"
                );


        Player b =
                new Player(
                        2,
                        "B"
                );



        room.addPlayer(a);

        room.addPlayer(b);



        /*
         * 开始游戏
         */
        room.startGame();



        RoundManager manager =
                new RoundManager(room);



        /*
         * 开始回合
         */
        manager.startRound();



        assertTrue(
                manager.isRoundStarted()
        );



        /*
         * A提交行动
         */
        manager.submitAction(
                a,
                new Action(
                        ActionType.BANANA,
                        List.of(2)
                )
        );



        /*
         * 只有一个玩家提交
         * 不应该结束
         */
        assertTrue(
                manager.isRoundStarted()
        );



        /*
         * B提交行动
         */
        manager.submitAction(
                b,
                new Action(
                        ActionType.DOOR,
                        List.of()
                )
        );



        /*
         * 两人提交完成
         * 应该自动结算
         */
        assertTrue(
        manager.isRoundStarted()
);


    }


}