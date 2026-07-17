package com.philosophy.service;


import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



/**
 * 玩家会话管理
 *
 * 当前阶段负责:
 *
 * playerId
 *      |
 *      |
 * roomId
 *
 *
 * 后续会接入 WebSocket Session
 */
@Service
public class PlayerSessionService {


    /**
     * 玩家 -> 房间
     */
    private final Map<String,String> playerRooms;



    /**
     * 在线玩家集合
     */
    private final Map<String,Boolean> onlinePlayers;





    public PlayerSessionService(){


        playerRooms =
                new ConcurrentHashMap<>();


        onlinePlayers =
                new ConcurrentHashMap<>();


    }





    /**
     * 玩家连接房间
     */
    public void connect(
            String playerId,
            String roomId
    ){


        playerRooms.put(
                playerId,
                roomId
        );


        onlinePlayers.put(
                playerId,
                true
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


        onlinePlayers.remove(
                playerId
        );


    }





    /**
     * 获取玩家所在房间
     */
    public String getRoomId(
            String playerId
    ){


        return playerRooms.get(
                playerId
        );


    }





    /**
     * 判断玩家是否在线
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
     * 当前在线人数
     */
    public int onlineCount(){


        return onlinePlayers.size();


    }





    /**
     * 判断玩家是否属于房间
     */
    public boolean inRoom(
            String playerId,
            String roomId
    ){


        return roomId.equals(
                playerRooms.get(playerId)
        );


    }


}