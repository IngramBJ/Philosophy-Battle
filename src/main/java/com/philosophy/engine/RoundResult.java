package com.philosophy.engine;


import java.util.ArrayList;
import java.util.List;


public class RoundResult {


    private final List<String> logs;


    public RoundResult(){

        logs = new ArrayList<>();

    }



    public void addLog(String log){

        logs.add(log);

    }



    public List<String> getLogs(){

        return logs;

    }


}