package com.example.gatling.infrastructure.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomUtils {
    public static int randNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public static double randNumber(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static boolean randBoolean() {
        int value = ThreadLocalRandom.current().nextInt(0, 2);
        return value == 0;
    }

    public static String randColorCode() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();
        return String.format("#%06X", (0xFFFFFF & new Color(r, g, b).getRGB()));
    }

    public static String randAlphabet() {
        char ch = (char) ThreadLocalRandom.current().nextInt(65, 91);
        return Character.toString(ch);
    }
}
