package com.philosophy;


import com.philosophy.engine.*;
import com.philosophy.model.*;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;



public class PlayerJoinTest {



    @Test
    public void duplicatePlayerCannotJoin(){


        GameRoom room =
                new GameRoom("001");



        Player a =
                new Player(1,"A");



        Player b =
                new Player(1,"B");



        assertTrue(
                room.addPlayer(a)
        );



        assertFalse(
                room.addPlayer(b)
        );


    }

}