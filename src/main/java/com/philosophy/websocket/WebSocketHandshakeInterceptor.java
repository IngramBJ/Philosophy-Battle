package com.philosophy.websocket;


import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;

import org.springframework.stereotype.Component;

import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;


import java.net.URI;
import java.util.Map;



@Component
public class WebSocketHandshakeInterceptor
        implements HandshakeInterceptor {



    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String,Object> attributes
    ){


        URI uri =
                request.getURI();



        String query =
                uri.getQuery();



        if(query!=null){


            String[] params =
                    query.split("&");



            for(String param:params){


                String[] pair =
                        param.split("=");



                if(pair.length!=2){

                    continue;

                }



                if(pair[0].equals("roomId")){


                    attributes.put(
                            "roomId",
                            pair[1]
                    );


                }



                if(pair[0].equals("playerId")){


                    attributes.put(
                            "playerId",
                            pair[1]
                    );


                }


            }


        }



        return true;

    }






    @Override
    public void afterHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Exception exception
    ){

    }


}