package com.philosophy;


import com.philosophy.dto.GameRoomDTO;
import com.philosophy.engine.GameRoom;
import com.philosophy.model.Player;
import com.philosophy.service.GameBroadcastService;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;



public class GameRoomDTOTest {



    @Test
    public void gameRoomConvertToDTO(){

        GameBroadcastService broadcastService =
            mock(
                GameBroadcastService.class
            );



        GameRoom room =
                new GameRoom(
                        "room001",
                        broadcastService
                );



        room.addPlayer(
                new Player(
                        1,
                        "Alice"
                )
        );



        GameRoomDTO dto =
                new GameRoomDTO(room);



        assertEquals(
                "room001",
                dto.roomId
        );



        assertEquals(
                1,
                dto.state.playerCount
        );



        assertEquals(
                "Alice",
                dto.state.players
                        .get(0)
                        .name
        );



    }


}