package me.phgs.application.util;

import java.util.concurrent.ThreadLocalRandom;

public class Random {
        public static int getRandomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
