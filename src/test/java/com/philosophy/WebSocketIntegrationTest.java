package com.philosophy;


import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;


import org.springframework.http.ResponseEntity;

import org.springframework.messaging.simp.stomp.*;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;

import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;


import java.lang.reflect.Type;

import java.util.Map;
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



    @Autowired
    private TestRestTemplate restTemplate;





    @Test
    public void websocketPlayerJoinBroadcastTest()
            throws Exception {



        /*
         * 1.
         * 创建房间
         */
        ResponseEntity<Map> createResponse =
                restTemplate.postForEntity(

                        "http://localhost:"
                                + port
                                + "/room/create",

                        null,

                        Map.class

                );



        assertTrue(
                createResponse.getStatusCode()
                        .is2xxSuccessful()
        );



        String roomId =
                (String)createResponse
                        .getBody()
                        .get("roomId");



        assertNotNull(roomId);



        /*
         * 2.
         * 创建STOMP客户端
         */
        WebSocketStompClient stompClient =
                new WebSocketStompClient(

                        new StandardWebSocketClient()

                );



        stompClient.setMessageConverter(

                new MappingJackson2MessageConverter()

        );





        BlockingQueue<Object> queue =
                new LinkedBlockingQueue<>();






        /*
         * 3.
         * 玩家2连接
         *
         * 注意：
         * 参数进入HandshakeInterceptor
         */
        String wsUrl =

                "ws://localhost:"
                        + port
                        + "/ws?playerId=2&roomId="
                        + roomId;




        StompSession session =

                stompClient
                        .connectAsync(

                                wsUrl,

                                new StompSessionHandlerAdapter(){}

                        )
                        .get(

                                5,

                                TimeUnit.SECONDS

                        );



        assertTrue(
                session.isConnected()
        );





        /*
         * 4.
         * 订阅房间广播
         */
        session.subscribe(


                "/topic/room/"
                        + roomId,


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





        /*
         * 5.
         * 玩家1 HTTP加入房间
         */
        Map<String,Object> joinRequest =

                Map.of(

                        "playerId",

                        1,


                        "name",

                        "player1"

                );





        ResponseEntity<Map> joinResponse =

                restTemplate.postForEntity(

                        "http://localhost:"
                                + port
                                + "/room/"
                                + roomId
                                + "/join",


                        joinRequest,


                        Map.class

                );




        assertTrue(

                joinResponse.getStatusCode()
                        .is2xxSuccessful()

        );





        /*
         * 6.
         * 等待WebSocket广播
         */
        Object event =

                queue.poll(

                        5,

                        TimeUnit.SECONDS

                );




        assertNotNull(

                event,

                "没有收到PLAYER_JOIN广播"

        );




        /*
         * 7.
         * 断开连接
         */
        session.disconnect();



    }


}