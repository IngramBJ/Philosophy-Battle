package com.philosophy.dto;


import com.philosophy.engine.GameRoom;
import com.philosophy.model.GameStatus;



public class GameRoomDTO {



    public String roomId;


    public GameStatus status;


    public int round;


    public GameStateDTO state;





    public GameRoomDTO(
            GameRoom room
    ){


        this.roomId =
                room.getRoomId();



        this.status =
                room.getStatus();



        this.round =
                room.getRound();



        this.state =
                new GameStateDTO(

                        room.getRoomId(),

                        room.getStatus(),

                        room.getRound(),

                        room.getGameState()
                                .getPlayers()

                );


    }



}