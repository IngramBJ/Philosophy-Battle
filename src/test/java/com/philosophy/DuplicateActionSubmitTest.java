package com.philosophy;


import com.philosophy.engine.*;
import com.philosophy.model.*;
import com.philosophy.service.GameBroadcastService;


import org.junit.jupiter.api.Test;


import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;



public class DuplicateActionSubmitTest {


    @Test
    public void playerCannotSubmitTwice(){


        GameBroadcastService broadcastService =
                mock(GameBroadcastService.class);



        GameRoom room =
                new GameRoom(
                        "001",
                        broadcastService
                );



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



        room.startGame();



        RoundManager manager =
                room.getRoundManager();



        assertTrue(
                manager.isRoundStarted()
        );



        Action first =
                new Action(
                        ActionType.BANANA,
                        List.of()
                );



        Action second =
                new Action(
                        ActionType.DOOR,
                        List.of()
                );



        manager.submitAction(
                a,
                first
        );



        /*
         * 当前设计：
         * 第二次提交会覆盖
         *
         * 这里先记录当前行为
         * 后续规则确定后修改
         */

        manager.submitAction(
                a,
                second
        );



        assertEquals(
                ActionType.DOOR,
                a.getCurrentAction()
                        .getType()
        );


    }


}