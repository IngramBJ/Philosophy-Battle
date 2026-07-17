package com.philosophy;


import com.philosophy.engine.*;
import com.philosophy.model.GameStatus;
import com.philosophy.model.Player;


import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;



public class GameStartTest {


    @Test
    public void gameStartNeedsTwoPlayers(){


        GameRoom room =
                new GameRoom("001");



        room.addPlayer(
                new Player(1,"A")
        );



        assertThrows(
                IllegalStateException.class,
                room::startGame
        );


    }



    @Test
    public void gameCanStartWithTwoPlayers(){


        GameRoom room =
                new GameRoom("001");



        room.addPlayer(
                new Player(1,"A")
        );


        room.addPlayer(
                new Player(2,"B")
        );



        room.startGame();



        assertEquals(
                GameStatus.RUNNING,
                room.getStatus()
        );


    }


}