package io.lite.framework.component;

import io.lite.framework.type.IdGenerator;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * 此生成器可以使用到 7108年 （还能用5000多年）
 * 理论上每天最多可以产生 86400000L * 999 * 99 个id
 */


@SuppressWarnings("all")
public class Blizzard implements IdGenerator {


    // 一天的毫秒数
    private static final long ONE_DAY_TIME = 86400000L;

    // 当前日期0点时刻 - 到格林威治时间day0零点时刻 的 毫秒差
    private long dayStartEpoch = -1L;


    // 一秒内产生的序列号
    private long sequence = 0L;

    //
    private long lastMillisencod = -1L;

    // 分片id
    private int workId;

    public static Blizzard create(int workerId) {

        // 最大99个work
        if (workerId > 99) {
            throw new IllegalArgumentException("workerId should be less than 99");
        }
        return new Blizzard(workerId);
    }


    private Blizzard(int workId) {
        this.workId = workId;
        resetEpoch();
    }


    public synchronized long nextId() {

        // 获取当前时间 millis
        long currentMillisecond = System.currentTimeMillis();


        // 如果最后一次millis 等于 当前时间的 millis
        if (this.lastMillisencod == currentMillisecond) {

            // 将 sequence 增加1
            this.sequence = this.sequence + 1;

            // 如果 sequence 增加后大于 999 （要超位了）
            if (this.sequence > 999) {

                // 将 sequence 恢复 为 0
                this.sequence = 0;
            }

            // 如果 sequence 为 0
            if (this.sequence == 0) {

                // 等待到下一个millis
                currentMillisecond = this.tilNextMillis(this.lastMillisencod);
            }
        } else {
            this.sequence = 0;
        }

        // currentMillisecond 小于 lastMillisencod，说明系统时钟回退了，这是一个异常现象，不上升异常，返回 -1
        if (currentMillisecond < this.lastMillisencod) {
            return -1;
        }

        this.lastMillisencod = currentMillisecond;

        // 如果 lastMillisencod - dayStartEpoch 大于 1day， 说明到下一天了，重新初始化 epoch 信息
        if (this.lastMillisencod - this.dayStartEpoch > ONE_DAY_TIME) {
            resetEpoch();
        }

        //
        return currentMillisecond * 1_00_000L  // 1_00_000L 为 3位sequence 占位 + 2位 worker占位
                + workId * 1_000L   // 1_000L 为 3位sequence 占位
                + this.sequence;

    }

    // 重新计算 dayStartEpoch
    private void resetEpoch() {
        this.dayStartEpoch = DateUtils.truncate(new Date(), Calendar.DATE).getTime();
    }


    // 等待到lastTimestamp这一毫秒
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }


}