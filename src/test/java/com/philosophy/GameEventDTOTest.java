package com.philosophy;


import com.philosophy.dto.GameEventDTO;

import org.junit.jupiter.api.Test;

import java.util.Map;


import static org.junit.jupiter.api.Assertions.*;



public class GameEventDTOTest {



    @Test
    public void createEvent(){


        GameEventDTO event =
                new GameEventDTO(

                        "GAME_START",

                        "room1",

                        Map.of(
                                "round",
                                1
                        )

                );



        assertEquals(
                "GAME_START",
                event.eventType
        );



        assertEquals(
                "room1",
                event.roomId
        );



        assertEquals(
                1,
                event.data.get("round")
        );


    }


}