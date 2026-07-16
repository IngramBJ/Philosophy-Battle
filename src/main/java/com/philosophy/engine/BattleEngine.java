package com.philosophy.engine;


import com.philosophy.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class BattleEngine {


    private final ActionValidator validator;


    public BattleEngine(){

        validator = new ActionValidator();

    }



    /**
     * 一个回合的完整结算入口
     */
    public RoundResult resolveRound(
            GameState game
    ){

        RoundResult result =
                new RoundResult();



        /*
         * 第一阶段：
         * 检查行动是否合法
         */
        validateActions(
                game,
                result
        );



        /*
         * 第二阶段：
         * 扣除技能消耗
         */
        consumePoints(
                game,
                result
        );



        /*
         * 保存本轮行动快照
         *
         * 防止后续玩家死亡后
         * 影响行动读取
         */
        Map<Player, Action> actions =
                new HashMap<>();


        for(Player player:
                game.getAlivePlayers()){


            if(player.getCurrentAction()!=null){

    actions.put(
        player,
        player.getCurrentAction()
    );

}

        }



        /*
         * 第三阶段：
         * 哲学行动
         */
        resolvePhilosophy(
                actions,
                result
        );



        /*
         * 第四阶段：
         * 防御与攻击
         */
        resolveAttack(
                actions,
                result
        );



        /*
         * 第五阶段：
         * 清理行动
         */
        clearActions(game);



        return result;

    }





    /**
     * 检查行动是否合法
     */
    private void validateActions(
        GameState game,
        RoundResult result
){


    for(Player player:
            game.getAlivePlayers()){


        Action action =
                player.getCurrentAction();



        if(!validator.validate(player, action)){


            result.addLog(
                    player.getName()
                    +" 行动非法，本回合空过"
            );


            player.setCurrentAction(null);

        }

    }

}





    /**
     * 扣除技能消耗
     */
    private void consumePoints(
            GameState game,
            RoundResult result
    ){


        for(Player player:
                game.getAlivePlayers()){


            if(player.getCurrentAction()==null){

    continue;

}


ActionType type =
        player.getCurrentAction()
        .getType();



            int cost =
                    getCost(type);



            if(cost > 0){


                player.spendPoints(cost);


            }

        }


    }





    private int getCost(
            ActionType type
    ){

        return switch(type){

            case MUJI -> 2;

            case BILLY -> 3;

            case BANANA -> 4;

            case VANSAMA -> 5;

            case WHITE_MOUSE -> 7;

            case DEMON_KING -> 3;

            case DOOR -> 1;

            default -> 0;

        };

    }





    /**
     * 哲学行动
     */
    private void resolvePhilosophy(
            Map<Player,Action> actions,
            RoundResult result
    ){


        for(Map.Entry<Player,Action> entry:
                actions.entrySet()){


            Player player =
                    entry.getKey();



            Action action =
                    entry.getValue();



            if(action.getType()
                    == ActionType.PHILOSOPHY){


                player.addPoints(1);



                result.addLog(
                        player.getName()
                        +" 哲学 +1"
                );

            }


        }


    }





    /**
     * 攻击处理
     *
     * 当前版本：
     * 木吉
     * 比利
     * 香蕉
     */
    private void resolveAttack(
            Map<Player,Action> actions,
            RoundResult result
    ){


        for(Map.Entry<Player,Action> entry:
                actions.entrySet()){


            Player attacker =
                    entry.getKey();


            Action action =
                    entry.getValue();



            // 如果攻击者已经死亡
            // 他的攻击取消

            if(!attacker.isAlive()){

                continue;

            }



            switch(action.getType()){


                case MUJI -> {

                    attack(
                            attacker,
                            action,
                            actions,
                            ActionType.DEF_MUJI,
                            result
                    );

                }


                case BILLY -> {

                    attack(
                            attacker,
                            action,
                            actions,
                            ActionType.DEF_BILLY,
                            result
                    );

                }


                case BANANA -> {

                    attack(
                            attacker,
                            action,
                            actions,
                            ActionType.DEF_BANANA,
                            result
                    );

                }


                default -> {

                }

            }


        }


    }





    /**
     * 单目标攻击
     */
    private void attack(
            Player attacker,
            Action action,
            Map<Player,Action> actions,
            ActionType defenseType,
            RoundResult result
    ){


        if(action.getTargets().isEmpty()){

            return;

        }



        int targetId =
                action.getTargets()
                        .get(0);



        Player target =
                findPlayer(
                        actions,
                        targetId
                );



        if(target==null ||
                !target.isAlive()){


            return;

        }



        Action targetAction =
                actions.get(target);



        /*
         * 普通防御
         */
        if(targetAction.getType()
                == defenseType){


            result.addLog(
                    target.getName()
                    +" 防御成功"
            );


            return;

        }




        /*
         * door
         */
        if(targetAction.getType()
                == ActionType.DOOR){


            if(action.getType()
                    == ActionType.BANANA){


                target.die();


                result.addLog(
                        target.getName()
                        +" 被香蕉击杀"
                );


            }else{


                attacker.die();


                result.addLog(
                        attacker.getName()
                        +" 被door反杀"
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
            Map<Player,Action> actions,
            int id
    ){


        for(Player player:
                actions.keySet()){


            if(player.getId()==id){

                return player;

            }

        }


        return null;

    }





    private void clearActions(
            GameState game
    ){

        for(Player player:
                game.getPlayers()){


            player.clearAction();

        }

    }


}