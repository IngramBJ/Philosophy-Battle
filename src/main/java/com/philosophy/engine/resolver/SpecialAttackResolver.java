package com.philosophy.engine.resolver;


import com.philosophy.engine.*;
import com.philosophy.model.*;



public class SpecialAttackResolver {


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


                case VANSAMA -> {

                    resolveVansama(
                            attacker,
                            action,
                            context,
                            defenseState,
                            result
                    );

                }



                case WHITE_MOUSE -> {

                    resolveWhiteMouse(
                            attacker,
                            context,
                            defenseState,
                            result
                    );

                }



                case DEMON_KING -> {

                    resolveDemonKing(
                            attacker,
                            action,
                            context,
                            defenseState,
                            result
                    );

                }



                default -> {

                }

            }

        }

    }





    /**
     * vansama
     */
    private void resolveVansama(
        Player attacker,
        Action action,
        RoundContext context,
        DefenseState defenseState,
        RoundResult result
){


    for(Integer targetId:
            action.getTargets()){


        Player target =
                findPlayer(
                        context,
                        targetId
                );


        if(target==null ||
                !target.isAlive()){

            continue;

        }



        /*
         * vansama只能被door防御
         */
        if(defenseState.getDefense(target)
                == ActionType.DOOR){


            result.addLog(
                    target.getName()
                    +" 使用door防御vansama"
            );


            continue;

        }



        target.die();



        result.addLog(
                attacker.getName()
                +" 使用vansama击杀 "
                +target.getName()
        );


    }


}





    /**
     * 白鼠
     */
    private void resolveWhiteMouse(
        Player attacker,
        RoundContext context,
        DefenseState defenseState,
        RoundResult result
){


    for(Player target:
            context.getAlivePlayers()){


        // 不攻击自己
        if(target == attacker){

            continue;

        }



        // 已死亡玩家跳过
        if(!target.isAlive()){

            continue;

        }



        /*
         * 白鼠只能door防御
         */
        if(defenseState.getDefense(target)
                == ActionType.DOOR){


            result.addLog(
                    target.getName()
                    +" 使用door防御白鼠"
            );


            continue;

        }



        target.die();


        result.addLog(
                attacker.getName()
                +" 使用白鼠击杀 "
                +target.getName()
        );


    }


}





    /**
     * 妖王
     */
    private void resolveDemonKing(
        Player attacker,
        Action action,
        RoundContext context,
        DefenseState defenseState,
        RoundResult result
){


    boolean defended = false;



    /*
     * 检查所有目标
     */
    for(Integer targetId:
            action.getTargets()){


        Player target =
                findPlayer(
                        context,
                        targetId
                );



        if(target==null ||
                !target.isAlive()){

            continue;

        }



        /*
         * 任何防御都算
         */
        if(defenseState.hasDefense(target)){


            defended = true;


            result.addLog(
                    target.getName()
                    +" 使用防御抵挡妖王"
            );

        }

    }



    /*
     * 有人防御
     * 妖王死亡
     */
    if(defended){


        attacker.die();


        result.addLog(
                attacker.getName()
                +" 因妖王被防御而死亡"
        );


        return;

    }



    /*
     * 没有人防御
     * 所有目标死亡
     */
    for(Integer targetId:
            action.getTargets()){


        Player target =
                findPlayer(
                        context,
                        targetId
                );


        if(target!=null &&
                target.isAlive()){


            target.die();


            result.addLog(
                    attacker.getName()
                    +" 使用妖王击杀 "
                    +target.getName()
            );

        }

    }


}



    private Player findPlayer(
        RoundContext context,
        int id
){


    for(Player player:
            context.getAlivePlayers()){


        if(player.getId()==id){

            return player;

        }

    }


    return null;

}


}