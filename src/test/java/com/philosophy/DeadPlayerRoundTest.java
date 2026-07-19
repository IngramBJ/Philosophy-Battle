package com.philosophy;


import com.philosophy.engine.*;
import com.philosophy.model.*;
import com.philosophy.service.GameBroadcastService;


import org.junit.jupiter.api.Test;


import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;



public class DeadPlayerRoundTest {


    @Test
    public void deadPlayerShouldNotBlockRound(){


        GameBroadcastService broadcastService =
                mock(GameBroadcastService.class);



        GameRoom room =
                new GameRoom(
                        "001",
                        broadcastService
                );



        Player alive =
                new Player(
                        1,
                        "Alive"
                );


        Player dead =
                new Player(
                        2,
                        "Dead"
                );



        room.addPlayer(alive);

        room.addPlayer(dead);



        room.startGame();



        // 模拟玩家死亡
        dead.die();



        RoundManager manager =
                room.getRoundManager();



        manager.submitAction(
                alive,
                new Action(
                        ActionType.PHILOSOPHY,
                        List.of()
                )
        );



        /*
         * 死亡玩家不提交行动
         * 回合应该自动结束
         */

        assertFalse(
                manager.isRoundStarted()
        );


    }

}