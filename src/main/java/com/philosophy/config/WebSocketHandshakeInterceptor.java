package com.philosophy.config;


import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;

import org.springframework.web.socket.WebSocketHandler;

import org.springframework.web.socket.server.HandshakeInterceptor;


import java.util.Map;



public class WebSocketHandshakeInterceptor
        implements HandshakeInterceptor {




    @Override
    public boolean beforeHandshake(

            ServerHttpRequest request,

            ServerHttpResponse response,

            WebSocketHandler wsHandler,

            Map<String,Object> attributes

    ){



        String uri =
                request.getURI()
                        .toString();



        /*
         * 示例:
         *
         * /ws?playerId=1&roomId=test
         *
         */


        if(uri.contains("?")){


            String query =
                    uri.substring(
                            uri.indexOf("?")+1
                    );


            String[] params =
                    query.split("&");



            for(String param:
                    params){


                String[] pair =
                        param.split("=");



                if(pair.length!=2){

                    continue;

                }



                if(pair[0].equals("playerId")){


                    attributes.put(
                            "playerId",
                            pair[1]
                    );

                }



                if(pair[0].equals("roomId")){


                    attributes.put(
                            "roomId",
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