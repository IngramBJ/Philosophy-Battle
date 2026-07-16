package com.philosophy.model;


import java.util.List;



public class Action {


    // 行动类型
    private final ActionType type;


    // 攻击目标
    private final List<Integer> targets;



    public Action(
            ActionType type,
            List<Integer> targets
    ){

        this.type = type;

        this.targets = targets;

    }



    public ActionType getType(){

        return type;

    }



    public List<Integer> getTargets(){

        return targets;

    }

}