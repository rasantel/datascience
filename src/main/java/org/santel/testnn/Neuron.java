package org.santel.testnn;

import java.util.Random;

public class Neuron {
    private static final Random RANDOM = new Random();

    protected static float arrayProduct(float[] array1, float[] array2) {
        assert array1.length == array2.length;
        float product = 0f;
        final int numberOfValues = array1.length;
        for (int i = 0; i < numberOfValues; ++i) {
            product += array1[i] * array2[i];
        }
        return product;
    }

    protected static void fuzz(float[] weights) {
        for (int i = 0; i < weights.length; ++i) {
            weights[i] = fuzz(weights[i]);
        }
    }

    /** Multiply by random factor between -3.0f and 3.0f and add random offset between -1 and +1 */
    protected static float fuzz(float value) {
        return value * (.1f * (30 - RANDOM.nextInt(61)))
                + (.1f * (10 - RANDOM.nextInt(21)));
    }

}
