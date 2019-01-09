package org.starrier.dreamwar.util;

import java.util.Random;

/**
 * @author Starrier
 * @date 2018/12/9.
 */
public class RandomUtils {

    private static final int[] numArr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    /**
     * Get two random number
     * @param length number length
     *
     * */
    public static String randomNum(int length) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            builder.append(String.valueOf(numArr[random.nextInt(10)]));
        }
        return builder.toString();
    }

    public static int nextInt(int num) {
        Random random = new Random();
        return random.nextInt(num);
    }

    public static int nextInt(int start, int end) {
        Random random = new Random();
        int index = random.nextInt(end - start);
        return start + index;
    }


}
