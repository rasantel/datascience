package org.santel.testnn;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PerceptronTest {

    /** Function to learn: f(x) = 1 - x + x^2 */
    private static float functionToLearn1(float x) {
        return 1f - x + (float)Math.pow(x, 2);
    }

    /** Function to learn: f(x) = 1 - x + x^2 - x^3 + x^4 */
    private static float functionToLearn2(float x) {
        return 1f - x + (float)Math.pow(x, 2) - (float)Math.pow(x, 3) + (float)Math.pow(x, 4d);
    }

    private static Sample createSample(float x, Function<Float, Float> function) {
        return new Sample(new float[] { x }, function.apply(x));
    }

    @Test
    public void onePerceptronQuadraticFunction() {
        double[] sampleInputs = new double[] {1d, 2d, 3d, 4d, 5d};
        double[] testInputs = new double[] {0d, 6d, 7d, 8d, 9d, 10d};

        List<Sample> samples = Arrays.stream(sampleInputs)
                .mapToObj(d -> createSample((float)d, PerceptronTest::functionToLearn1))
                .collect(Collectors.toList());

        Output outputNeuron = new Output(1);
        Perceptron perceptron1 = new Perceptron(0, 1, 1000, outputNeuron);
        perceptron1.train(samples);

        System.out.println("**** Performance on sample inputs");
        Arrays.stream(sampleInputs)
                .forEach(input -> {
                    float expected = functionToLearn1((float) input);
                    float actual = perceptron1.process(new float[]{(float) input});
                    System.out.println("Input " + input + " expected " + expected + " perceptron " + actual + " diff " + (actual - expected));
                });

        System.out.println("**** Performance on test inputs");
        Arrays.stream(testInputs)
                .forEach(input -> {
                    float expected = functionToLearn1((float) input);
                    float actual = perceptron1.process(new float[]{(float) input});
                    System.out.println("Input " + input + " expected " + expected + " perceptron " + actual + " diff " + (actual - expected));
                });
    }

//    @Test
//    public void twoPerceptronsQuadraticFunction() {
//        List<Sample> samples = Arrays.stream(new double[] {1d, 2d, 3d, 4d, 5d})
//                .boxed()
//                .map(d -> createSample(d.floatValue(), PerceptronTest::functionToLearn1))
//                .collect(Collectors.toList());
//
//        Output outputNeuron = new Output(1);
//        Perceptron perceptron1 = new Perceptron(0, 1, 100000, outputNeuron);
//        perceptron1.train(samples);
//
//        System.out.println("**** Performance on sample inputs");
//        Arrays.stream(new double[] {1d, 2d, 3d, 4d, 5d})
//                .forEach(input -> {
//                    float expected = functionToLearn1((float) input);
//                    float actual = perceptron1.process(new float[]{(float) input});
//                    System.out.println("Input " + input + " expected " + expected + " perceptron " + actual + " diff " + (actual - expected));
//                });
//
//        System.out.println("**** Performance on other inputs");
//        Arrays.stream(new double[] {0d, 6d, 7d, 8d, 9d, 10d})
//                .forEach(input -> {
//                    float expected = functionToLearn1((float) input);
//                    float actual = perceptron1.process(new float[]{(float) input});
//                    System.out.println("Input " + input + " expected " + expected + " perceptron " + actual + " diff " + (actual - expected));
//                });
//    }
}
