package com.philosophy.model;


/**
 * 玩家所有可选择行动
 */
public enum ActionType {

    // 积攒哲学点
    PHILOSOPHY,


    // 普通攻击
    MUJI,          // 木吉
    BILLY,         // 比利
    BANANA,        // 香蕉


    // 普通防御
    DEF_MUJI,
    DEF_BILLY,
    DEF_BANANA,


    // 特殊攻击
    VANSAMA,

    WHITE_MOUSE,

    DEMON_KING,


    // 特殊防御
    DOOR
}