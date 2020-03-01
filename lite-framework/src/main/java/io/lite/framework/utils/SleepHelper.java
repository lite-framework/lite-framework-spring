package io.lite.framework.utils;

import org.apache.commons.lang3.RandomUtils;

public class SleepHelper {

    public static void randomSleep(int seconds) {
        int rand = RandomUtils.nextInt(0, seconds);
        ParkHelper.parkSeconds(rand);
    }


    public static void sleepSomeSeconds() {
        randomSleep(60);
    }


    public static void sleepSomeMinutes() {
        int rand = RandomUtils.nextInt(60, 60 * 10);
        randomSleep(rand);
    }

}
