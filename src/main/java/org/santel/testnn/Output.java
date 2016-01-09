package org.santel.testnn;

public class Output extends Neuron {
    private final float[] inputs;
    private final float[] weights;
    private float offset;
    private float outputWeight;
    private final float[] previousInputWeights;
    private float previousOffset;
    private float previousOutputWeight;

    public Output(int numberOfInputs) {
        this.inputs = new float[numberOfInputs];
        this.weights = new float[numberOfInputs];
        this.offset = 0f;
        this.outputWeight = 1.0f;
        this.previousInputWeights = new float[numberOfInputs];
    }

    public void fuzz() {
        System.arraycopy(weights, 0, previousInputWeights, 0, weights.length);
        previousOffset = offset;
        previousOutputWeight = outputWeight;

        fuzz(weights); // random change
        offset = fuzz(offset);
        outputWeight = fuzz(outputWeight);
    }

    public void undoFuzz() {
        System.arraycopy(previousInputWeights, 0, weights, 0, weights.length);
        offset = previousOffset;
        outputWeight = previousOutputWeight;
    }

    public float process(int index, float value) {
        inputs[index] = value;
        return process();
    }

    private float process() {
        float weightedSum = arrayProduct(inputs, weights);
        return offset + outputWeight * weightedSum;
    }

}
