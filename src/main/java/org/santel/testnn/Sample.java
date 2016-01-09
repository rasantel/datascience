package org.santel.testnn;

public class Sample {
	private final float[] inputs;
	private final float actualOutput;
	
	public Sample(float[] inputs, float actualOutput) {
		this.inputs = inputs;
		this.actualOutput = actualOutput;
	}

	public float[] getInputs() {
		return inputs;
	}

	public float getActualOutput() {
		return actualOutput;
	}
}
