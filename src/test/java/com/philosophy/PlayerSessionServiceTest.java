package com.philosophy;


import com.philosophy.service.PlayerSessionService;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;



public class PlayerSessionServiceTest {



    @Test
    public void playerConnectAndDisconnectTest(){


        PlayerSessionService service =
                new PlayerSessionService();



        service.connect(
                "player1",
                "room001"
        );



        assertTrue(
                service.isOnline(
                        "player1"
                )
        );



        assertEquals(
                "room001",
                service.getRoomId(
                        "player1"
                )
        );



        assertTrue(
                service.inRoom(
                        "player1",
                        "room001"
                )
        );



        service.disconnect(
                "player1"
        );



        assertFalse(
                service.isOnline(
                        "player1"
                )
        );



        assertNull(
                service.getRoomId(
                        "player1"
                )
        );

    }





    @Test
    public void onlineCountTest(){


        PlayerSessionService service =
                new PlayerSessionService();



        service.connect(
                "A",
                "room1"
        );


        service.connect(
                "B",
                "room1"
        );



        assertEquals(
                2,
                service.onlineCount()
        );


        service.disconnect(
                "A"
        );


        assertEquals(
                1,
                service.onlineCount()
        );


    }


}