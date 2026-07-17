package com.philosophy;


import com.philosophy.engine.*;

import org.junit.jupiter.api.Test;


import java.util.concurrent.atomic.AtomicBoolean;


import static org.junit.jupiter.api.Assertions.*;



public class TimerServiceTest {



    @Test
    public void timerExecuteTask()
            throws Exception {



        TimerService timer =
                new TimerService();



        AtomicBoolean called =
                new AtomicBoolean(false);



        timer.startTimer(
                () -> called.set(true),
                1
        );



        Thread.sleep(1500);



        assertTrue(
                called.get()
        );



        timer.shutdown();


    }


}