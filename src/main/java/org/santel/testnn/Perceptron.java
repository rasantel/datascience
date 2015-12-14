package org.santel.testnn;

import java.util.Arrays;
import java.util.Collection;

public class Perceptron extends Neuron {
	private static final float E_CONSTANT = 2.71828f;

    private final float[] weights;
    private final int index;
    private final int numberOfInputs;
    private float offset;
    private float outputWeight;
    private final int trainingIterations;
    private final Output outputNeuron;

	public Perceptron(int index, int numberOfInputs, int trainingIterations, Output outputNeuron) {
        this.index = index;
        this.numberOfInputs = numberOfInputs;
        this.weights = new float[numberOfInputs];
        Arrays.fill(weights, 0f);
        this.trainingIterations = trainingIterations;
        this.outputNeuron = outputNeuron;
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
            outputNeuron.fuzz(); // random change
            offset = fuzz(offset);
            newError = currentError(samples);

//            System.out.print("Previous weights " + Arrays.toString(previousWeights) + " offset " + previousOffset + " outputNeuron weight " + previousOutputWeight + " error " + previousError
//                    + " new weights " + Arrays.toString(weights) + " offset " + offset + " outputNeuron weight " + outputWeight + " error " + newError
//                    + " error diff " + (newError - previousError));
            if (newError >= previousError) {
                System.arraycopy(previousWeights, 0, weights, 0, weights.length);
                offset = previousOffset;
//                System.out.println("; keeping previous weights");
            } else {
                previousError = newError;
                lastUpdateStep = step;
//                System.out.println("; using new weights");
            }
        }
        System.out.println("Perceptron " + index + " final weights " + Arrays.toString(weights) + " offset " + offset
                + " outputNeuron weight " + outputWeight + " error " + previousError
                + " found in step " + lastUpdateStep + "/" + trainingIterations);
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
	 * @return The outputNeuron value for the given inputs using
	 */
	public float process(float[] inputValues) {
		float weightedSum = offset + arrayProduct(inputValues, weights);
		float power = (float) Math.pow(E_CONSTANT, weightedSum);
		return outputNeuron.process(index, 1f / (1f + power));
	}

}
