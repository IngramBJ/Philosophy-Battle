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
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest(
        webEnvironment =
        SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class FullBattleRoundFlowTest {



    @LocalServerPort
    private int port;



    private final RestTemplate rest =
            new RestTemplate();





    @Test
    public void fullBattleRoundFlowTest()
            throws Exception {



        /*
         * 创建房间
         */

        Map room =
                rest.postForObject(

                        url("/room/create"),

                        null,

                        Map.class

                );


        String roomId =
                (String)room.get("roomId");


        assertNotNull(roomId);





        /*
         * 玩家加入
         */

        join(roomId,1,"A");

        join(roomId,2,"B");







        /*
         * websocket
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



        StompSession session2 =
                connect(
                        client,
                        roomId,
                        2,
                        events
                );



        Thread.sleep(1000);






        /*
         * 开始游戏
         */

        rest.postForObject(

                url(
                        "/room/"+roomId+"/start"
                ),

                null,

                Map.class

        );



        Thread.sleep(1000);






        /*
         * 玩家提交哲学行动
         */


        sendAction(
                session1,
                roomId,
                1,
                "PHILOSOPHY"
        );



        sendAction(
                session2,
                roomId,
                2,
                "PHILOSOPHY"
        );






        /*
         * 等待ROUND_RESULT
         */

        boolean roundResult=false;


        long timeout =
                System.currentTimeMillis()
                +10000;



        while(
                System.currentTimeMillis()
                <
                timeout
        ){


            String e =
                    events.poll(
                            500,
                            TimeUnit.MILLISECONDS
                    );



            if(e==null){

                continue;

            }



            System.out.println(
                    "EVENT:"
                    +e
            );



            if(
                    e.contains(
                            "ROUND_RESULT"
                    )
            ){

                roundResult=true;

                break;

            }


        }



        assertTrue(roundResult);



        session1.disconnect();

        session2.disconnect();


    }











    private void sendAction(
            StompSession session,
            String roomId,
            int playerId,
            String type
    ){



        Map<String,Object> body =
                Map.of(

                        "playerId",
                        playerId,


                        "type",
                        type,


                        "targets",
                        List.of()

                );



        session.send(

                "/app/room/"
                +roomId
                +"/action",

                body

        );


    }











    private void join(
            String roomId,
            int id,
            String name
    ){



        Map body =
                Map.of(

                        "playerId",
                        id,

                        "name",
                        name

                );



        Map result =
                rest.postForObject(

                        url(
                                "/room/"
                                +roomId
                                +"/join"
                        ),

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



        String ws =
                "ws://localhost:"
                +port
                +"/ws?"
                +"roomId="
                +roomId
                +"&playerId="
                +playerId;





        StompSession session =
                client.connectAsync(

                        ws,

                        new StompSessionHandlerAdapter(){


                            @Override
                            public void handleTransportError(
                                    StompSession session,
                                    Throwable exception
                            ){

                                exception.printStackTrace();

                            }



                            @Override
                            public void handleException(
                                    StompSession session,
                                    StompCommand command,
                                    StompHeaders headers,
                                    byte[] payload,
                                    Throwable exception
                            ){

                                exception.printStackTrace();

                            }


                        }

                )
                .get(
                        5,
                        TimeUnit.SECONDS
                );






        StompSession.Subscription subscription =
                session.subscribe(

                        "/topic/room/"+roomId,


                        new StompFrameHandler(){



                            @Override
                            public Type getPayloadType(
                                    StompHeaders headers
                            ){

                                return Map.class;

                            }





                            @Override
                            public void handleFrame(
                                    StompHeaders headers,
                                    Object payload
                            ){


                                System.out.println(
                                        "================="
                                );


                                System.out.println(
                                        "WS JSON:"
                                        +payload
                                );


                                System.out.println(
                                        "================="
                                );



                                Map event =
        (Map) payload;


System.out.println(
        "WS EVENT:"
        +event
);


queue.add(
        event.get("eventType").toString()
);


                            }


                        }


                );



        assertNotNull(subscription);



        return session;

    }









    private String url(
            String path
    ){

        return "http://localhost:"
                +port
                +path;

    }


}