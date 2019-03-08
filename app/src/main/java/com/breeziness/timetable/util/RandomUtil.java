package com.breeziness.timetable.util;

import java.util.Random;

public class RandomUtil {


    /**
     * 返回一个0到count的随机数
     *
     * @param count
     * @return
     */
    public static int getRandomInt(int count) {
        if (count > 0) {
            Random random = new Random();
            return random.nextInt(count);
        }
        return 0;
    }
}
