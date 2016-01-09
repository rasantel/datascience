package org.santel.testnn.math;

import java.util.Random;

public class MathUtils {
    private static final Random RANDOM = new Random();

    public static float nextRandomFloat(float from, float to) {
        return from + (to - from) * RANDOM.nextFloat();
    }
}
