package com.philosophy.dto;


import com.philosophy.model.*;

import java.util.List;
import java.util.stream.Collectors;



public class GameStateDTO {


    public String roomId;


    public GameStatus status;


    public int round;


    public int playerCount;


    public List<PlayerDTO> players;





    public GameStateDTO(
            String roomId,
            GameStatus status,
            int round,
            List<Player> players
    ){


        this.roomId = roomId;


        this.status = status;


        this.round = round;



        this.players =
                players.stream()
                .map(PlayerDTO::new)
                .collect(Collectors.toList());



        this.playerCount =
                this.players.size();


    }







    public static class PlayerDTO{


        public int id;


        public String name;


        public int philosophyPoints;


        public boolean alive;


        public boolean hasAction;




        public PlayerDTO(
                Player player
        ){


            this.id =
                    player.getId();



            this.name =
                    player.getName();



            this.philosophyPoints =
                    player.getPhilosophyPoints();



            this.alive =
                    player.isAlive();



            this.hasAction =
                    player.getCurrentAction()!=null;


        }

    }



}