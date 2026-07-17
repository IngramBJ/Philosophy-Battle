package com.philosophy.engine;


import com.philosophy.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.philosophy.engine.resolver.PhilosophyResolver;
import com.philosophy.engine.resolver.DefenseResolver;
import com.philosophy.engine.resolver.NormalAttackResolver;
import com.philosophy.engine.resolver.SpecialAttackResolver;



public class BattleEngine {


    private final ActionValidator validator;
    private final PhilosophyResolver philosophyResolver;
    private final DefenseResolver defenseResolver;
    private final NormalAttackResolver normalAttackResolver;
    private final SpecialAttackResolver specialAttackResolver;


    public BattleEngine(){

        validator = new ActionValidator();
        philosophyResolver =
                    new PhilosophyResolver();
        defenseResolver =
                    new DefenseResolver();
        normalAttackResolver =
                    new NormalAttackResolver();
        specialAttackResolver =
                    new SpecialAttackResolver();
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

        /*
         * 第三阶段：
         * 哲学行动
         */
        RoundContext context =
        new RoundContext(game);


philosophyResolver.resolve(
        context,
        result
);

DefenseState defenseState =
        defenseResolver.resolve(
                context,
                result
        );

specialAttackResolver.resolve(
        context,
        defenseState,
        result
);

normalAttackResolver.resolve(
        context,
        defenseState,
        result
);



        /*
         * 第四阶段：
         * 防御与攻击
         */



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



        Action action =
                player.getCurrentAction();



        if(action == null){

            continue;

        }



        int cost =
                ActionCost.getCost(
                        action.getType()
                );



        if(cost > 0){


            player.spendPoints(cost);


        }


    }


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