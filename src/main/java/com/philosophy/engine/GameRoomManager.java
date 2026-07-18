package com.philosophy.engine;


import com.philosophy.service.GameBroadcastService;


import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;



public class GameRoomManager {


    private final Map<String, GameRoom> rooms;


    private final GameBroadcastService broadcastService;



    /**
     * 测试环境构造
     */






    /**
     * Spring环境构造
     */
    public GameRoomManager(
            GameBroadcastService broadcastService
    ){

        this.rooms =
                new ConcurrentHashMap<>();


        this.broadcastService =
                broadcastService;

    }







    /**
     * 创建房间
     */
    public GameRoom createRoom(){


        String roomId =
                UUID.randomUUID()
                        .toString()
                        .substring(0,8);



        GameRoom room =
                new GameRoom(
                        roomId,
                        broadcastService
                );



        rooms.put(
                roomId,
                room
        );



        return room;

    }







    /**
     * 获取房间
     */
    public GameRoom getRoom(
            String roomId
    ){

        return rooms.get(roomId);

    }







    public void removeRoom(
            String roomId
    ){

        rooms.remove(roomId);

    }







    public int roomCount(){

        return rooms.size();

    }







    public boolean hasRoom(
            String roomId
    ){

        return rooms.containsKey(roomId);

    }


}