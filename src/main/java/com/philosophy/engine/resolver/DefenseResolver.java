package com.philosophy.engine.resolver;


import com.philosophy.engine.*;
import com.philosophy.model.*;



public class DefenseResolver {



    public DefenseState resolve(
            RoundContext context,
            RoundResult result
    ){


        DefenseState state =
                new DefenseState();



        for(Player player:
                context.getActions().keySet()){


            Action action =
                    context.getAction(player);



            switch(action.getType()){


                case DEF_MUJI,
                     DEF_BILLY,
                     DEF_BANANA,
                     DOOR -> {


                    state.addDefense(
                            player,
                            action.getType()
                    );


                    result.addLog(
                            player.getName()
                            +" 进入防御状态"
                    );

                }


                default -> {

                }

            }


        }


        return state;

    }

}