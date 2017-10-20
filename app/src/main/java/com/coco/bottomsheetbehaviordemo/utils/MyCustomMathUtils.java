package com.coco.bottomsheetbehaviordemo.utils;

/**
 * Created by Administrator on 2017/10/20.
 */

public class MyCustomMathUtils {

    public static int constrain(int amount, int low, int high) {
        return amount < low ? low : (amount > high ? high : amount);
    }

    public static float constrain(float amount, float low, float high) {
        return amount < low ? low : (amount > high ? high : amount);
    }

}
