package org.santel.testnn;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

public class Perceptron {
	private static final float E_CONSTANT = 2.71828f;
    private static final Random RANDOM = new Random();

    private final float[] weights;
    private final int trainingIterations;
    private float offset;
    private float outputWeight;

	public Perceptron(int numberOfInputs, int trainingIterations) {
        this.trainingIterations = trainingIterations;
        this.weights = new float[numberOfInputs];
        Arrays.fill(weights, 0f);
        this.offset = 0f;
        this.outputWeight = 1.0f;
	}
	
	public void train(Collection<Sample> samples) {
        float[] previousWeights = new float[weights.length];
        float previousOffset;
        float previousOutputWeight;
        int lastUpdateStep = -1;
        float previousError = currentError(samples);
        float newError;
        for (int step = 1; step <= trainingIterations; ++step) {
            System.arraycopy(weights, 0, previousWeights, 0, weights.length);
            previousOffset = offset;
            previousOutputWeight = outputWeight;

            fuzz(weights); // random change
            offset = fuzz(offset);
            outputWeight = fuzz(previousOutputWeight);
            newError = currentError(samples);

//            System.out.print("Previous weights " + Arrays.toString(previousWeights) + " offset " + previousOffset + " output weight " + previousOutputWeight + " error " + previousError
//                    + " new weights " + Arrays.toString(weights) + " offset " + offset + " output weight " + outputWeight + " error " + newError
//                    + " error diff " + (newError - previousError));
            if (newError >= previousError) {
                System.arraycopy(previousWeights, 0, weights, 0, weights.length);
                offset = previousOffset;
                outputWeight = previousOutputWeight;
//                System.out.println("; keeping previous weights");
            } else {
                previousError = newError;
                lastUpdateStep = step;
//                System.out.println("; using new weights");
            }
        }
        System.out.println("Final weights " + Arrays.toString(weights) + " offset " + offset + " output weight " + outputWeight + " error " + previousError
                + " found in step " + lastUpdateStep + "/" + trainingIterations);
	}

    private static void fuzz(float[] weights) {
        for (int i = 0; i < weights.length; ++i) {
            weights[i] = fuzz(weights[i]);
        }
    }

    /** Multiply by random factor between -3.0f and 3.0f and add random offset between -1 and +1 */
    private static float fuzz(float value) {
        return value * (.1f * (30 - RANDOM.nextInt(61)))
                + (.1f * (10 - RANDOM.nextInt(21)));
    }

    private float currentError(Collection<Sample> samples) {
        float error = 0f;
        for (Sample sample : samples) {
            float actualOutput = process(sample.getInputs());
            float squaredDifference = (actualOutput - sample.getOutput());
            squaredDifference *= squaredDifference;
            error += squaredDifference;
        }
        return error;
    }

    /**
	 * Uses the sigmoid function 1 / (1 + e^WeightedInputSum)
	 * @return The output value for the given inputs using
	 */
	public float process(float[] inputValues) {
		float weightedSum = offset + arrayProduct(inputValues, weights);
		float power = (float) Math.pow(E_CONSTANT, weightedSum);
		return outputWeight * 1f / (1f + power);
	}

	private static float arrayProduct(float[] array1, float[] array2) {
		assert array1.length == array2.length;
		float product = 0f;
		final int numberOfValues = array1.length;
		for (int i = 0; i < numberOfValues; ++i) {
			product += array1[i] * array2[i];
		}
		return product;
	}
	
}
