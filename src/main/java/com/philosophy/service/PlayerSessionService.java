package com.philosophy.service;


import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;



/**
 * 玩家会话管理
 *
 * playerId
 *      |
 *      +---- roomId
 *      |
 *      +---- sessionId
 */
@Service
public class PlayerSessionService {


    /**
     * 玩家 -> 房间
     */
    private final Map<String,String> playerRooms;



    /**
     * 玩家 -> websocket session
     */
    private final Map<String,String> playerSessions;



    /**
     * 在线玩家
     */
    private final Map<String,Boolean> onlinePlayers;





    public PlayerSessionService(){


        playerRooms =
                new ConcurrentHashMap<>();


        playerSessions =
                new ConcurrentHashMap<>();


        onlinePlayers =
                new ConcurrentHashMap<>();

    }








    /**
     * 新版连接
     */
    public void connect(
            String playerId,
            String roomId,
            String sessionId
    ){


        playerRooms.put(
                playerId,
                roomId
        );


        /*
         * ConcurrentHashMap 不允许 null
         *
         * HTTP测试环境没有WebSocket session
         * 所以sessionId可能为空
         */
        if(sessionId != null){


            playerSessions.put(
                    playerId,
                    sessionId
            );


        }



        onlinePlayers.put(
                playerId,
                true
        );


    }







    /**
     * 兼容旧版本测试
     */
    public void connect(
            String playerId,
            String roomId
    ){


        connect(
                playerId,
                roomId,
                null
        );


    }








    /**
     * 玩家断开
     */
    public void disconnect(
            String playerId
    ){


        playerRooms.remove(
                playerId
        );


        playerSessions.remove(
                playerId
        );


        onlinePlayers.remove(
                playerId
        );


    }








    /**
     * 获取玩家房间
     */
    public String getRoomId(
            String playerId
    ){


        return playerRooms.get(
                playerId
        );


    }








    /**
     * 获取WebSocket Session
     */
    public String getSessionId(
            String playerId
    ){


        return playerSessions.get(
                playerId
        );


    }








    /**
     * 判断在线
     */
    public boolean isOnline(
            String playerId
    ){


        return onlinePlayers.getOrDefault(
                playerId,
                false
        );


    }








    /**
     * 在线人数
     */
    public int onlineCount(){


        return onlinePlayers.size();


    }








    /**
     * 是否属于房间
     */
    public boolean inRoom(
            String playerId,
            String roomId
    ){


        return roomId.equals(
                playerRooms.get(playerId)
        );


    }








    /**
     * 获取房间在线玩家
     */
    public Set<String> playersInRoom(
            String roomId
    ){


        Set<String> result =
                ConcurrentHashMap.newKeySet();



        playerRooms.forEach(
                (player,room)->{


                    if(roomId.equals(room)){


                        result.add(player);


                    }


                }
        );


        return result;


    }



}