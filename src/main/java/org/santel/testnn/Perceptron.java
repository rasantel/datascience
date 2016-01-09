package org.santel.testnn;

import java.util.Arrays;
import java.util.Collection;

public class Perceptron extends Neuron {
	private static final float E_CONSTANT = 2.71828f;

    private final float[] weights;
    private final int id;
    private float offset;
    private final int trainingIterations;
    private final Output outputNeuron;

	public Perceptron(int id, int numberOfInputs, int trainingIterations, Output outputNeuron) {
        this.id = id;
        this.weights = new float[numberOfInputs];
        Arrays.fill(weights, 0f);
        this.trainingIterations = trainingIterations;
        this.outputNeuron = outputNeuron;
        this.offset = 0f;
	}
	
	public void train(Collection<Sample> samples) {
        float[] previousWeights = new float[weights.length];
        float previousOffset;
        int lastUpdateStep = -1;
        float previousError = currentError(samples);
        float newError;
        for (int step = 1; step <= trainingIterations; ++step) {
            System.arraycopy(weights, 0, previousWeights, 0, weights.length);
            previousOffset = offset;

            fuzz(weights); // random change
            offset = fuzz(offset);
            outputNeuron.fuzz(); // random change
            newError = currentError(samples);

//            System.out.print("Previous weights " + Arrays.toString(previousWeights) + " offset " + previousOffset + " error " + previousError
//                    + "; new weights " + Arrays.toString(weights) + " offset " + offset + " error " + newError
//                    + " error diff " + (newError - previousError));
            if (newError >= previousError) {
                System.arraycopy(previousWeights, 0, weights, 0, weights.length);
                offset = previousOffset;
                outputNeuron.undoFuzz();
//                System.out.println("; keeping previous weights");
            } else {
                previousError = newError;
                lastUpdateStep = step;
//                System.out.println("; using new weights");
            }
        }
        System.out.println("Perceptron " + id + " final weights " + Arrays.toString(weights) + " offset " + offset
                + " error " + previousError + " found in step " + lastUpdateStep + "/" + trainingIterations);
	}

    private float currentError(Collection<Sample> samples) {
        float error = 0f;
        for (Sample sample : samples) {
            float actualOutput = process(sample.getInputs());
            float squaredDifference = (actualOutput - sample.getActualOutput());
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
		return outputNeuron.process(id, 1f / (1f + power));
	}

}
