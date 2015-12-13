package org.santel.testnn;

public class Sample {
	private final float[] inputs;
	private final float output;
	
	public Sample(float[] inputs, float output) {
		this.inputs = inputs;
		this.output = output;
	}

	public float[] getInputs() {
		return inputs;
	}

	public float getOutput() {
		return output;
	}
}
