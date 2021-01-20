package de.hsbo.geo.simsamples.applications;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import de.hsbo.geo.simsamples.markov.instances.SimpleMarkovChain;

/**
 * Simple Markov model describing a random movement between two absorbers.
 * The example is taken from the lecture "Spatiotemporal modelling and 
 * simulation" of the Master course Geoinformatics at Bochum University of
 * Applied Sciences (chapter 4, "Random modelling").
 *  
 * @author Benno Schmidt
 */
public class RandomMovementExample 
{
	double p = 0.4; // Probability to move right (for states "2" ... "7")
	double q = 1. - p; // Probability to move left (for states "2" ... "7")
	String[] states = new String[] {
			"1", "2", "3", "4", "5", "6", "7", "8"};
	String sInit = "5"; // initial state
	// The element M1[i][j] of the transition matrix gives the probability
	// that the object will move from state i to state j:
	double[][] M1 = new double[][] {
		{1, 0, 0, 0, 0, 0, 0, 0},
		{q, 0, p, 0, 0, 0, 0, 0},
		{0, q, 0, p, 0, 0, 0, 0},
		{0, 0, q, 0, p, 0, 0, 0},
		{0, 0, 0, q, 0, p, 0, 0},
		{0, 0, 0, 0, q, 0, p, 0},
		{0, 0, 0, 0, 0, q, 0, p},
		{0, 0, 0, 0, 0, 0, 0, 1}
	};
	
	public static void main(String[] args) throws Exception {
		new RandomMovementExample().run();
	}
	
	public void run() throws Exception {
		ArrayList<Object> stateSet = new ArrayList<Object>();
		for (String s : states)
			stateSet.add(s);
		
		SimpleMarkovChain a = new SimpleMarkovChain(
			stateSet, sInit, M1);

		int numberOfSteps = 20;
		a.execute(numberOfSteps);
		
		a.dumpResult("Time", 0, 10);

		showTransitionMatrices();
	}
	
	private void showTransitionMatrices() 
	{	
		// The elements Mk[i][j] of the transition matrix Mk give the probabilities
		// the object's moves from state i to state j during the next k time steps.
		double[][] 
		    M2 = mult(M1, M1), 
		    M3 = mult(M2, M1);
		System.out.println("M1:"); 
		printMatrix(M1);
		System.out.println("M2:");
		printMatrix(M2);
		System.out.println("M3:");
		printMatrix(M3);
		double[][]
		    M = mult(M1, M1);
		int N = 20;
     	for (int i = 3; i <= N; i++) {
			M = mult(M, M1); 
		}
		System.out.println("M" + N + ":");
		printMatrix(M);
	}
	    
	static public void printMatrix(double[][] M) {
	    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
	    ((DecimalFormat) numberFormat).applyPattern("###.##");

	    for (int i = 0; i < M.length; i++) {
			for (int j = 0; j < M[i].length; j++) {
			  String out = numberFormat.format(new Double(M[i][j]));
			  System.out.print("\t" + out);			 
			}
			System.out.println();
		}
	}
	
	static public double[][] mult(double[][] A, double[][] B) {
		double[][] C = new double[A.length][A[0].length];
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A[i].length; j++) {
				C[i][j] = 0;
				for (int k = 0; k < A.length; k++) {
					C[i][j] += A[i][k] * B[k][j];
				}
			}
		}
		return C;
	}
}
