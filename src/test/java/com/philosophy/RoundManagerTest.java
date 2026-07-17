package com.philosophy;


import com.philosophy.engine.*;
import com.philosophy.model.*;


import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;



public class RoundManagerTest {



    @Test
    public void playerCanSubmitAction(){


        GameRoom room =
                new GameRoom("001");



        Player a =
                new Player(1,"A");


        Player b =
                new Player(2,"B");



        room.addPlayer(a);

        room.addPlayer(b);



        room.startGame();



        RoundManager manager =
                new RoundManager(room);



        manager.startRound();



        manager.submitAction(
                a,
                new Action(
                        ActionType.PHILOSOPHY,
                        java.util.List.of()
                )
        );



        assertEquals(
                ActionType.PHILOSOPHY,
                a.getCurrentAction()
                .getType()
        );


    }


}