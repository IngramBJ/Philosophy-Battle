package com.philosophy.engine;


import com.philosophy.service.GameBroadcastService;


import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;



public class GameRoomManager {


    private final Map<String, GameRoom> rooms;


    /**
     * WebSocket广播服务
     *
     * 测试环境可以为空
     */
    private final GameBroadcastService broadcastService;




    /**
     * 保留原构造
     *
     * 给测试使用
     */
    public GameRoomManager(){


        this.rooms =
                new ConcurrentHashMap<>();


        this.broadcastService =
                null;


    }





    /**
     * Spring使用
     *
     * 注入WebSocket广播
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



        GameRoom room;



        if(broadcastService == null){


            /*
             * 测试环境
             */
            room =
                    new GameRoom(roomId);


        }else{


            /*
             * 在线游戏环境
             */
            room =
                    new GameRoom(
                            roomId,
                            broadcastService
                    );


        }



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