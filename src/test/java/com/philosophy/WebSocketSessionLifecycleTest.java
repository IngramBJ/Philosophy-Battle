package com.philosophy;


import com.philosophy.service.GameBroadcastService;
import com.philosophy.service.PlayerSessionService;
import com.philosophy.config.WebSocketEventListener;


import org.junit.jupiter.api.Test;

import org.mockito.Mockito;


import java.util.Map;


import static org.junit.jupiter.api.Assertions.*;




public class WebSocketSessionLifecycleTest {



    @Test
    void playerSessionConnectAndDisconnect(){



        PlayerSessionService sessionService =
                new PlayerSessionService();



        GameBroadcastService broadcastService =
                Mockito.mock(
                        GameBroadcastService.class
                );



        WebSocketEventListener listener =
                new WebSocketEventListener(

                        sessionService,

                        broadcastService

                );





        /*
         * 模拟连接
         *
         * 等价于WebSocket握手完成后的状态
         */
        sessionService.connect(

                "100",

                "room001"

        );





        assertTrue(

                sessionService.isOnline(
                        "100"
                )

        );



        assertEquals(

                "room001",

                sessionService.getRoomId(
                        "100"
                )

        );






        /*
         * 模拟断开
         */
        sessionService.disconnect(

                "100"

        );





        assertFalse(

                sessionService.isOnline(
                        "100"
                )

        );



        assertNull(

                sessionService.getRoomId(
                        "100"
                )

        );


    }






    @Test
    void broadcastServiceShouldReceiveOnlineEvent(){



        GameBroadcastService broadcastService =
                Mockito.mock(
                        GameBroadcastService.class
                );



        broadcastService.broadcastEvent(

                "room001",

                "PLAYER_ONLINE",

                Map.of(

                        "playerId",

                        "100"

                )

        );




        Mockito.verify(

                broadcastService

        )
        .broadcastEvent(

                "room001",

                "PLAYER_ONLINE",

                Map.of(

                        "playerId",

                        "100"

                )

        );


    }



}