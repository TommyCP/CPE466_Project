package neuralNetwork;

import java.util.Random;

public class Neuron {

	private static final double LC = 0.01;
	private int inputSize;
	private double[] weights;
	private double bias;
	
	// saves recent input for training
	private double[] recent;
	
	// initializes a neuron with random weights and random bias of size _inputSize
	public Neuron(int _inputSize) {
		inputSize = _inputSize;
		weights = new double[inputSize];
		Random rand = new Random();
		for (int i = 0; i<inputSize; i++) {
			weights[i] = rand.nextDouble()*2-1;
		}
		bias = rand.nextDouble()*2-1;
	}
	
	// Produces a sigmoid score from input. Saves recent input for training.
	public double SigmoidScore(double[] input) {
		assert (input.length==inputSize);
		
		recent = input;
		
		double z = 0;
		
		for (int i=0; i<inputSize; i++) {
			z += input[i]*weights[i];
		}
		
		return 1 / (1 + Math.exp(-(z + bias)));
	}
	
	// Uses recent input, the error, and a learning constant to train the neuron.
	public void teach ( int error) {
		assert (recent.length == inputSize);
		
		for (int i=0; i<inputSize; i++) {
			weights[i] += error*recent[i]*LC;
		}
		
		bias += error*LC;
	}
	
	// returns the input size of this neuron
	public int size() {
		return inputSize;
	}
	
}
