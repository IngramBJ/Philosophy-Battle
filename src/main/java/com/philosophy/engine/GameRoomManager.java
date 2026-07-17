package com.philosophy.engine;


import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;



public class GameRoomManager {


    private final Map<String, GameRoom> rooms;



    public GameRoomManager(){


        rooms =
                new ConcurrentHashMap<>();


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
                new GameRoom(roomId);



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





    /**
     * 删除房间
     */
    public void removeRoom(
            String roomId
    ){


        rooms.remove(roomId);


    }





    /**
     * 房间数量
     */
    public int roomCount(){


        return rooms.size();


    }

    public boolean hasRoom(
        String roomId
){

    return rooms.containsKey(roomId);

}


}