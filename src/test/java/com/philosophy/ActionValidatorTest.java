package com.philosophy;


import com.philosophy.engine.*;
import com.philosophy.model.*;


import org.junit.jupiter.api.Test;


import java.util.List;


import static org.junit.jupiter.api.Assertions.*;



public class ActionValidatorTest {


    @Test
    public void bananaNeedFourPoints(){


        Player a =
                new Player(1,"A");



        /*
         * A没有哲学点
         */
        a.setCurrentAction(
                new Action(
                        ActionType.BANANA,
                        List.of(2)
                )
        );



        boolean result =
                new ActionValidator()
                        .validate(
                                a,
                                a.getCurrentAction()
                        );



        assertFalse(result);


    }



    @Test
    public void bananaWithEnoughPoints(){


        Player a =
                new Player(1,"A");



        a.addPoints(4);



        a.setCurrentAction(
                new Action(
                        ActionType.BANANA,
                        List.of(2)
                )
        );



        boolean result =
                new ActionValidator()
                        .validate(
                                a,
                                a.getCurrentAction()
                        );



        assertTrue(result);


    }


}