package com.philosophy.service;


import com.philosophy.engine.GameRoom;
import com.philosophy.dto.GameRoomDTO;
import com.philosophy.dto.GameEventDTO;


import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;



import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;



@Service
public class GameBroadcastService {



    private final SimpMessagingTemplate template;



    private final Map<String,Map<Integer,String>> sessions;

    private final ObjectMapper mapper;





    public GameBroadcastService(
            SimpMessagingTemplate template,
            ObjectMapper mapper
    ){

        this.template =
                template;


        this.sessions =
                new ConcurrentHashMap<>();

        this.mapper =
                mapper;

    }






    /**
     * 普通广播
     */
    public void broadcast(
        String roomId,
        Object message
){


    try{

        System.out.println(
                "WS SEND JSON="
                +mapper.writeValueAsString(message)
        );


    }catch(Exception e){

        e.printStackTrace();

    }



    template.convertAndSend(
            "/topic/room/"+roomId,
            message
    );

}







    /**
     * 广播GameRoom状态
     *
     * 给RoundManager使用
     */
public void broadcastState(
        GameRoom room
)
{


    if(room==null){

        return;

    }



    GameRoomDTO dto =
            new GameRoomDTO(room);



    broadcast(

            room.getRoomId(),

            dto

    );


}







    /**
     * 原来的状态广播接口
     */
    public void broadcastState(
            String roomId,
            Object state
    ){


        broadcast(
                roomId,
                state
        );


    }









    public void registerPlayer(
            String roomId,
            int playerId,
            String sessionId
    ){


        sessions
                .computeIfAbsent(
                        roomId,
                        k->new ConcurrentHashMap<>()
                )
                .put(
                        playerId,
                        sessionId
                );


    }








    public void removePlayer(
            String roomId,
            int playerId
    ){


        Map<Integer,String> map =
                sessions.get(roomId);



        if(map!=null){

            map.remove(playerId);

        }


    }







    public int onlineCount(
            String roomId
    ){


        Map<Integer,String> map =
                sessions.get(roomId);



        if(map==null){

            return 0;

        }


        return map.size();


    }







    public Set<Integer> onlinePlayers(
            String roomId
    ){


        Map<Integer,String> map =
                sessions.get(roomId);



        if(map==null){

            return Set.of();

        }


        return map.keySet();


    }

        /**
     * 广播游戏事件
     */
    public void broadcastEvent(
            String roomId,
            String eventType,
            Map<String,Object> data
    ){


        GameEventDTO event =
                new GameEventDTO(

                        eventType,

                        roomId,

                        data

                );



        broadcast(

                roomId,

                event

        );


    }


}