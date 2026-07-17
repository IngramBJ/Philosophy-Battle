package com.philosophy.engine;


import java.util.concurrent.*;



public class TimerService {


    private final ScheduledExecutorService scheduler;



    public TimerService(){


        scheduler =
                Executors.newScheduledThreadPool(2);


    }





    /**
     * 创建倒计时任务
     */
    public ScheduledFuture<?> startTimer(
            Runnable task,
            long seconds
    ){


        return scheduler.schedule(
                task,
                seconds,
                TimeUnit.SECONDS
        );


    }





    public void shutdown(){


        scheduler.shutdown();

    }


}