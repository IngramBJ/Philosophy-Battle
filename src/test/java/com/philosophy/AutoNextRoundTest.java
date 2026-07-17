package com.philosophy;


import com.philosophy.engine.*;
import com.philosophy.model.*;


import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;



public class AutoNextRoundTest {


    @Test
    public void nextRoundStartsAutomatically(){


        GameRoom room =
                new GameRoom("001");



        room.addPlayer(
                new Player(1,"A")
        );


        room.addPlayer(
                new Player(2,"B")
        );



        room.startGame();



        RoundManager manager =
                room.getRoundManager();



        assertTrue(
                manager.isRoundStarted()
        );



        manager.finishRound();



        assertTrue(
                manager.isRoundStarted()
        );


    }

}