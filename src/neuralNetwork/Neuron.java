package neuralNetwork;

import java.util.Random;

public class Neuron {

	private static final double LC = 0.01;
	private int inputSize;
	private double[] weights;
	private double bias;
	
	
	public Neuron(int _inputSize) {
		inputSize = _inputSize;
		weights = new double[inputSize];
		Random rand = new Random();
		for (int i = 0; i<inputSize; i++) {
			weights[i] = rand.nextDouble()*2-1;
		}
		bias = rand.nextDouble()*2-1;
	}
	
	public double SigmoidScore(double[] input) {
		assert (input.length==inputSize);
		
		double z = 0;
		
		for (int i=0; i<inputSize; i++) {
			z += input[i]*weights[i];
		}
		
		return 1 / (1 + Math.exp(-(z + bias)));
	}
	
	public void teach (double[] input, int error) {
		assert (input.length == inputSize);
		
		for (int i=0; i<inputSize; i++) {
			weights[i] += error*input[i]*LC;
		}
		
		bias += error*LC;
	}
	
	public int size() {
		return inputSize;
	}
	
}
