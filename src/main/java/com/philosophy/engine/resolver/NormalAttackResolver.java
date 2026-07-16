package com.philosophy.engine.resolver;


import com.philosophy.engine.*;
import com.philosophy.model.*;

import java.util.Map;



public class NormalAttackResolver {



    public void resolve(
            RoundContext context,
            DefenseState defenseState,
            RoundResult result
    ){


        for(Player attacker:
                context.getActions().keySet()){


            Action action =
                    context.getAction(attacker);



            if(!attacker.isAlive()){

                continue;

            }



            switch(action.getType()){


                case MUJI -> {

                    attack(
                            attacker,
                            action,
                            context,
                            defenseState,
                            ActionType.DEF_MUJI,
                            result
                    );

                }



                case BILLY -> {

                    attack(
                            attacker,
                            action,
                            context,
                            defenseState,
                            ActionType.DEF_BILLY,
                            result
                    );

                }



                case BANANA -> {

                    attack(
                            attacker,
                            action,
                            context,
                            defenseState,
                            ActionType.DEF_BANANA,
                            result
                    );

                }



                default -> {

                }

            }


        }


    }





    private void attack(
            Player attacker,
            Action action,
            RoundContext context,
            DefenseState defenseState,
            ActionType normalDefense,
            RoundResult result
    ){


        if(action.getTargets().isEmpty()){

            return;

        }



        Player target =
                findPlayer(
                        context,
                        action.getTargets().get(0)
                );



        if(target==null ||
                !target.isAlive()){

            return;

        }



        /*
         * 普通防御
         */
        if(defenseState.getDefense(target)
                == normalDefense){


            result.addLog(
                    target.getName()
                    +" 防御成功"
            );


            return;

        }



        /*
         * door
         */
        if(defenseState.getDefense(target)
                == ActionType.DOOR){


            if(action.getType()
                    == ActionType.BANANA){


                target.die();


                result.addLog(
                        target.getName()
                        +" 被香蕉击杀"
                );


            }
            else{


                attacker.die();


                result.addLog(
                        attacker.getName()
                        +" 被door反弹死亡"
                );

            }


            return;

        }




        /*
         * 无防御
         */
        target.die();


        result.addLog(
                attacker.getName()
                +" 击杀 "
                +target.getName()
        );


    }





    private Player findPlayer(
            RoundContext context,
            int id
    ){


        for(Player player:
                context.getActions().keySet()){


            if(player.getId()==id){

                return player;

            }

        }


        return null;

    }


}