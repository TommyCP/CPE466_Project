package neuralNetwork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class BinaryNetwork {
	
	private HashMap<Integer, ArrayList<Neuron>> network;
	private int size;
	
	/*
	 * Constructs a new instance of the network class.
	 * 
	 * The integer array input contains the number of layers to include at each level.
	 */
	public BinaryNetwork (int[] layers) {
		int size = layers.length;
		
		// Assert the user has input some layers and the last one is of size one.
		assert (size>0);
		assert (layers[size-1] == 1);
		
		// Adds layers of specified size
		for (int i = 0; i<layers.length; i++) {
			assert layers[i] > 0;
			ArrayList<Neuron> layer = new ArrayList<Neuron>();
			
			// sets inputsize for each neuron in this layer to either the number of neurons in the previous layer
			// or 1, if it is the first layer.
			int inputSize = (i>0) ? layers[i-1] : 1;
			for (int j=0; j<layers[i]; j++) {
				layer.add(new Neuron(inputSize));
			}
			
			// Adds the layer to the network
			network.put(i, layer);
		}
	}
	
	public ArrayList<Neuron> getLayer(int num) {
		return network.get(num);
	}
	
	
	// This function trains the neural network with the given data.
	// trainingSet must contain the training data
	// results must contain the expected classes
	public void train (double[][] trainingSet, boolean[] results) {
		
		// first assert the trainingSet contains data, results/trainingset are same length, and the input size is correct
		assert (trainingSet.length>0 && trainingSet.length == results.length);
		assert (trainingSet[0].length == network.get(0).size());
		
		for (int i = 0; i < trainingSet.length; i++) {
			boolean res = getResult(trainingSet[i]);
			int error=0;
			
			// false positive
			if (res != results[i] && res) {
				error = -2;
			} else { // false negative
				error = 2;
			}
			
			// If the network is wrong, teach it.
			if (error!=0) teachNetwork(error);
		}	
	}
	
	// predict takes in a training set and produces a boolean array of classifications.
	// the input must have the same number of columns as there are neurons in the input layer.
	public boolean[] predict(double[][] testingSet) {
		assert (testingSet.length>0);
		assert (testingSet[0].length == network.get(0).size());
		
		boolean[] res = new boolean[testingSet.length];
		
		for (int i=0; i<testingSet.length; i++) {
			res[i] = getResult(testingSet[i]);
		}
		
		return res;
	}
	
	// return the number of layers in the network
	public int size() {
		return size;
	}
	
	// goes through an iteration of the network, producing a boolean classification
	private boolean getResult(double[] _input) {
		double[] input = _input.clone();
		
		double[] out = null;
		
		for (int i=0; i<size; i++) {
			out = new double[network.get(i).size()];
			
			// set output of this layer to be the sigmoid score of each neuron
			for (int j=0; j<out.length; j++) {
				// sets each index of output to be the value produced using the 'input' array
				// 'input' contains the initial input at start, gets set to be the previous output on each iteration
				out[j] = network.get(i).get(i).SigmoidScore(input); 
			}
			
			input=out;
		}
		
		return out[0] > .5 ? true : false;
	}
	
	
	// Tells each of the items in the network to adjust its weights based on error produced
	private void teachNetwork(int error) {
		for (Entry<Integer, ArrayList<Neuron>> e : network.entrySet()) {
			for (Neuron n : e.getValue()) {
				n.teach(error);
			}
		}
	}
	
}
