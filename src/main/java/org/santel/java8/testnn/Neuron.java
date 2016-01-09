package org.santel.java8.testnn;

import org.santel.java8.testnn.math.MathUtils;

import java.util.Random;

public abstract class Neuron {
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

    protected static void fuzz(float[] values) {
        for (int i = 0; i < values.length; ++i) {
            values[i] = fuzz(values[i]);
        }
    }

    /** Multiply by random factor between -3.0f and 3.0f and add random offset between -1 and +1 */
    protected static float fuzz(float value) {
        return value * MathUtils.nextRandomFloat(-3f, 3f) + MathUtils.nextRandomFloat(-1f, 1f);
    }

}
