package com.philosophy.engine.resolver;


import com.philosophy.engine.RoundContext;
import com.philosophy.engine.RoundResult;
import com.philosophy.model.ActionType;
import com.philosophy.model.Player;



public class PhilosophyResolver {


    public void resolve(
            RoundContext context,
            RoundResult result
    ){


        for(Player player:
                context.getActions().keySet()){


            if(context.getAction(player)
                    .getType()
                    == ActionType.PHILOSOPHY){


                player.addPoints(1);


                result.addLog(
                        player.getName()
                        +" 哲学 +1"
                );

            }

        }

    }

}