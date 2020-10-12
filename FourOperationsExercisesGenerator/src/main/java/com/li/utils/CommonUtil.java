package com.li.utils;

import java.util.Random;

public class CommonUtil {

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            int randomNum = getRandomNum(1, 4);
            System.out.println(randomNum);
        }
    }

    /**
     * 随机生成[min,max]内的数字
     *
     * @param min 下界
     * @param max 上界
     * @return 随机生成数
     */
    public static Integer getRandomNum(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }
}
