package com.philosophy;


import org.junit.jupiter.api.Test;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;


import org.springframework.messaging.simp.stomp.*;

import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import org.springframework.web.socket.messaging.WebSocketStompClient;


import org.springframework.messaging.converter.MappingJackson2MessageConverter;


import java.lang.reflect.Type;

import java.util.concurrent.BlockingQueue;

import java.util.concurrent.LinkedBlockingQueue;

import java.util.concurrent.TimeUnit;



import static org.junit.jupiter.api.Assertions.*;




@SpringBootTest(
        webEnvironment =
        SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class WebSocketIntegrationTest {



    @LocalServerPort
    private int port;





    @Test
    public void websocketConnectAndSubscribeTest()
            throws Exception {



        WebSocketStompClient stompClient =
                new WebSocketStompClient(
                        new StandardWebSocketClient()
                );



        stompClient.setMessageConverter(
                new MappingJackson2MessageConverter()
        );




        String url =
                "ws://localhost:"
                + port
                + "/ws";




        StompSession session =
                stompClient
                        .connectAsync(
                                url,
                                new StompSessionHandlerAdapter(){}
                        )
                        .get(
                                5,
                                TimeUnit.SECONDS
                        );




        assertTrue(
                session.isConnected()
        );




        BlockingQueue<Object> queue =
                new LinkedBlockingQueue<>();




        session.subscribe(

                "/topic/room/test-room",

                new StompFrameHandler(){


                    @Override
                    public Type getPayloadType(
                            StompHeaders headers
                    ){

                        return Object.class;

                    }



                    @Override
                    public void handleFrame(
                            StompHeaders headers,
                            Object payload
                    ){

                        queue.add(payload);

                    }


                }

        );




        Thread.sleep(500);



        assertTrue(
                session.isConnected()
        );



        session.disconnect();


    }


}