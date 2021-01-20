package de.hsbo.geo.simsamples.applications;

import java.util.ArrayList;

import de.hsbo.geo.simsamples.markov.instances.SimpleMarkovChain;

/**
 * Markov model describing a very simple weather.
 * The example is taken from the lecture "Spatiotemporal modelling and 
 * simulation" of the Master course Geoinformatics at Bochum University of
 * Applied Sciences (chapter 4, "Random modelling").
 *  
 * @author Benno Schmidt
 */
public class SimpleWeatherExample 
{
	String sInit = "H"; // initial state

	public static void main(String[] args) throws Exception {
		new SimpleWeatherExample().run();
	}

	// The element M1[i][j] of the transition matrix gives the probability
	// that the object will move from state i to state j:
	double[][] M1 = new double[][] {
		{0.8, 0.2},
		{0.4, 0.6}
	};

	public void run() throws Exception {
		ArrayList<Object> stateSet = new ArrayList<Object>();
		stateSet.add("H");
		stateSet.add("T");		
				
		SimpleMarkovChain a = new SimpleMarkovChain(
			stateSet, "H", M1);

		int numberOfSteps = 20;
		a.execute(numberOfSteps);
		
		a.dumpResult("Time", 0, 10);
		
		showTranstionMatrices();
	}
	
	private void showTranstionMatrices() 
	{
		// The elements Mk[i][j] of the transition matrix Mk give the probabilities
		// the object's moves from state i to state j during the next k time steps.
		double[][] 
		    M2 = RandomMovementExample.mult(M1, M1), 
		    M3 = RandomMovementExample.mult(M2, M1);
		System.out.println("M1:"); 
		RandomMovementExample.printMatrix(M1);
		System.out.println("M2:");
		RandomMovementExample.printMatrix(M2);
		System.out.println("M3:");
		RandomMovementExample.printMatrix(M3);
		double[][]
		    M = RandomMovementExample.mult(M1, M1);
		int N = 8;
     	for (int i = 3; i <= N; i++) {
			M = RandomMovementExample.mult(M, M1); 
		}
		System.out.println("M" + N + ":");
		RandomMovementExample.printMatrix(M);
	}
}