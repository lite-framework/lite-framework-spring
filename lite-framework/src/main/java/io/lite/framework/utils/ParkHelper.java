package io.lite.framework.utils;

import java.util.concurrent.locks.LockSupport;

public class ParkHelper {
    public static void parkSeconds(long second) {
        LockSupport.parkNanos(second * 1000 * 1000 * 1000);
    }
}
