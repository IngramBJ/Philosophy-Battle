package com.philosophy;


import com.philosophy.service.GameBroadcastService;


import org.junit.jupiter.api.Test;


import org.springframework.messaging.simp.SimpMessagingTemplate;


import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.Mockito.*;




public class GameStateBroadcastTest {



    @Test
    public void broadcastStateShouldWork(){



        SimpMessagingTemplate template =
                mock(
                    SimpMessagingTemplate.class
                );



        GameBroadcastService service =
                new GameBroadcastService(
                        template, null
                );



        Object state =
                "game-state";



        service.broadcastState(
                "room1",
                state
        );



        verify(template)
                .convertAndSend(
                        "/topic/room/room1",
                        state
                );


    }





    @Test
    public void nullTemplateShouldNotCrash(){



        GameBroadcastService service =
                new GameBroadcastService(
                        null, null
                );



        assertDoesNotThrow(
                () -> {

                    service.broadcastState(
                            "room1",
                            "state"
                    );

                }
        );


    }



}