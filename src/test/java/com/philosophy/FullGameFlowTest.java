package com.philosophy;


import org.junit.jupiter.api.Test;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;


import org.springframework.web.client.RestTemplate;


import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;


import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;


import java.lang.reflect.Type;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.*;


import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest(
        webEnvironment =
        SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class FullGameFlowTest {



    @LocalServerPort
    private int port;



    private final RestTemplate rest =
            new RestTemplate();





    @Test
    public void fullBattleFlowTest()
            throws Exception {



        /*
         * 1 创建房间
         */

        Map room =
                rest.postForObject(

                "http://localhost:"
                + port
                + "/room/create",

                null,

                Map.class

        );


        String roomId =
                (String) room.get("roomId");



        assertNotNull(roomId);



        /*
         * 2 玩家加入
         */


        join(
                roomId,
                1,
                "玩家A"
        );


        join(
                roomId,
                2,
                "玩家B"
        );





        /*
         * 3 WebSocket连接
         */


        WebSocketStompClient client =
                new WebSocketStompClient(
                        new StandardWebSocketClient()
                );


        client.setMessageConverter(
                new MappingJackson2MessageConverter()
        );




        BlockingQueue<String> events =
                new LinkedBlockingQueue<>();




        StompSession session1 =
                connect(
                        client,
                        roomId,
                        1,
                        events
                );



        Thread.sleep(500);



        StompSession session2 =
                connect(
                        client,
                        roomId,
                        2,
                        events
                );



        Thread.sleep(1000);



        assertTrue(
                session1.isConnected()
        );


        assertTrue(
                session2.isConnected()
        );





        /*
         * 4 开始游戏
         */


        Map result =
                rest.postForObject(

                "http://localhost:"
                + port
                + "/room/"
                + roomId
                + "/start",

                null,

                Map.class

        );


        System.out.println(
                "START RESULT:"
                + result
        );



        Thread.sleep(2000);





        /*
         * 5 检查GAME_START事件
         */


        boolean gameStart=false;



        for(String event:events){


            System.out.println(
                    "CHECK EVENT:"
                    + event
            );



            if(event.contains(
                    "GAME_START"
            )){


                gameStart=true;


            }


        }



        assertTrue(gameStart);



        session1.disconnect();

        session2.disconnect();


    }







    private void join(
            String roomId,
            int playerId,
            String name
    ){


        Map<String,Object> body =
                Map.of(

                        "playerId",
                        playerId,


                        "name",
                        name

                );



        Map result =
                rest.postForObject(

                "http://localhost:"
                + port
                + "/room/"
                + roomId
                + "/join",

                body,

                Map.class

        );



        assertEquals(
                true,
                result.get("success")
        );


    }










    private StompSession connect(
            WebSocketStompClient client,
            String roomId,
            int playerId,
            BlockingQueue<String> queue

    )
            throws Exception {



        String url =
                "ws://localhost:"
                + port
                + "/ws?"
                + "roomId="
                + roomId
                + "&playerId="
                + playerId;





        StompSession session =
                client.connectAsync(

                        url,

                        new StompSessionHandlerAdapter(){}

                )
                .get(
                        5,
                        TimeUnit.SECONDS
                );




        session.subscribe(

                "/topic/room/"+roomId,

                new StompFrameHandler(){



                    @Override
                    public Type getPayloadType(
                            StompHeaders headers
                    ){

                        /*
                         * 接收原始JSON
                         */
                        return byte[].class;

                    }




                    @Override
                    public void handleFrame(

                            StompHeaders headers,

                            Object payload

                    ){



                        byte[] bytes =
                                (byte[])payload;



                        String json =
                                new String(
                                        bytes,
                                        StandardCharsets.UTF_8
                                );



                        System.out.println(
                                "================="
                        );


                        System.out.println(
                                "WS JSON:"
                                + json
                        );


                        System.out.println(
                                "HEADER:"
                                + headers
                        );


                        System.out.println(
                                "================="
                        );



                        queue.add(json);


                    }


                }

        );



        /*
         * 等待SUBSCRIBE完成
         */
        Thread.sleep(500);



        return session;


    }


}