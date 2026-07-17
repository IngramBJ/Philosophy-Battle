package com.philosophy;


import com.philosophy.dto.GameStateDTO;
import com.philosophy.engine.GameRoom;


import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;



@Service
public class GameBroadcastServiceTest {



    private final SimpMessagingTemplate template;



    /**
     * roomId -> 玩家session
     */
    private final Map<String, Set<String>> sessions;





    public GameBroadcastServiceTest(
            SimpMessagingTemplate template
    ){

        this.template =
                template;


        this.sessions =
                new ConcurrentHashMap<>();

    }







    /**
     * websocket广播
     */
    public void broadcast(
            String roomId,
            Object message
    ){


        if(template == null){

            return;

        }



        template.convertAndSend(

                "/topic/room/"+roomId,

                message

        );


    }







    /**
     * 广播游戏状态
     */
    public void broadcastState(
            GameRoom room
    ){


        GameStateDTO state =
                new GameStateDTO(

                        room.getRoomId(),

                        room.getStatus(),

                        room.getRound(),

                        room.getGameState()
                                .getPlayers()

                );



        broadcast(

                room.getRoomId(),

                state

        );


    }









    /**
     * 注册玩家session
     */
    public void registerPlayer(

            String roomId,

            int playerId,

            String sessionId

    ){


        sessions.computeIfAbsent(

                roomId,

                k -> ConcurrentHashMap.newKeySet()

        )
        .add(

                sessionId

        );


    }








    /**
     * 删除玩家session
     */
    public void removePlayer(

            String roomId,

            int playerId

    ){


        Set<String> set =
                sessions.get(roomId);



        if(set == null){

            return;

        }



        /*
         * 当前设计：
         * 一个玩家对应一个session
         */
        set.clear();


        if(set.isEmpty()){

            sessions.remove(roomId);

        }


    }








    /**
     * 在线数量
     */
    public int onlineCount(
            String roomId
    ){


        Set<String> set =
                sessions.get(roomId);



        if(set == null){

            return 0;

        }



        return set.size();


    }



}