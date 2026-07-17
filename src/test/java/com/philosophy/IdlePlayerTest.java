package com.philosophy;


import com.philosophy.engine.*;
import com.philosophy.model.*;


import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;



public class IdlePlayerTest {



    @Test
    public void idlePlayerHasNoAction(){


        GameRoom room =
                new GameRoom("001");



        Player a =
                new Player(1,"A");


        Player b =
                new Player(2,"B");



        room.addPlayer(a);

        room.addPlayer(b);



        room.startGame();



        /*
         * 模拟上一回合残留
         */
        a.setCurrentAction(
                new Action(
                        ActionType.BANANA,
                        java.util.List.of(2)
                )
        );



        RoundManager manager =
                new RoundManager(room);



        manager.startRound();



        assertNull(
                a.getCurrentAction()
        );


    }


}