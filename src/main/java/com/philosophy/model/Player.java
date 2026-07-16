package com.philosophy.model;


public class Player {


    private final int id;


    private final String name;


    // 哲学点数
    private int philosophyPoints;


    // 是否存活
    private boolean alive;


    // 本回合行动
    private Action currentAction;



    public Player(int id, String name) {

        this.id = id;
        this.name = name;

        // 初始0点
        this.philosophyPoints = 0;

        // 初始存活
        this.alive = true;
    }



    public int getId() {
        return id;
    }



    public String getName() {
        return name;
    }



    public int getPhilosophyPoints() {
        return philosophyPoints;
    }



    public void addPoints(int amount){

        philosophyPoints += amount;

    }



    public boolean spendPoints(int amount){

        if(philosophyPoints >= amount){

            philosophyPoints -= amount;

            return true;
        }

        return false;
    }



    public boolean isAlive(){

        return alive;

    }



    public void die(){

        alive = false;

    }



    public Action getCurrentAction(){

        return currentAction;

    }



    public void setCurrentAction(Action action){

        this.currentAction = action;

    }


}