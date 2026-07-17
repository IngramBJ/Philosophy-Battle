package com.philosophy;


import com.philosophy.service.GameBroadcastService;

import org.junit.jupiter.api.Test;



import java.util.Map;



import static org.junit.jupiter.api.Assertions.*;



public class GameBroadcastEventTest {



    @Test
    public void broadcastEventWithoutTemplate(){


        GameBroadcastService service =
                new GameBroadcastService(null);



        assertDoesNotThrow(
                () ->
                service.broadcastEvent(

                        "room1",

                        "PLAYER_JOIN",

                        Map.of(
                                "playerId",
                                1
                        )

                )
        );


    }



}