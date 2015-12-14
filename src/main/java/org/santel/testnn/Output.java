package org.santel.testnn;

import java.util.Arrays;

public class Output extends Neuron {
    private float[] signals;
    private float[] weights;
    private float offset;
    private float outputWeight;
    private float previousOutputWeight;

    public Output(int numberOfInputs) {
        this.signals = new float[numberOfInputs];
        Arrays.fill(this.signals, 0f);
        this.weights = new float[numberOfInputs];
        Arrays.fill(weights, 0f);
        this.offset = 0f;
        this.outputWeight = 1.0f;
    }

    public void fuzz() {
        //TODO: backup weights and offset
        fuzz(weights); // random change
        offset = fuzz(offset);
        outputWeight = fuzz(outputWeight);
    }

    public void undoFuzz() {
        // TODO: restore weights and offset
    }

    public float process(int index, float value) {
        signals[index] = value;
        return process();
    }

    private float process() {
        float weightedSum = arrayProduct(signals, weights);
        return offset + outputWeight * weightedSum;
    }

}
