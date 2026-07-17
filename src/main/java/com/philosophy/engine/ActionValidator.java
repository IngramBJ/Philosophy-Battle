package com.philosophy.engine;


import com.philosophy.model.*;



public class ActionValidator {


    public boolean validate(
            Player player,
            Action action
    ){


        /*
         * 没有行动
         */
        if(action == null){

            return false;

        }



        /*
         * 检查点数
         */
        int cost =
                ActionCost.getCost(
                        action.getType()
                );



        return player.getPhilosophyPoints()
                >= cost;


    }


}