package de.hsbo.geo.simsamples.applications;

import java.util.ArrayList;

import de.hsbo.geo.simsamples.markov.instances.SimpleMarkovChain;

/**
 * Simple Markov model giving the probabilities of movement between a city, 
 * its suburbs, and the surrounding country. The stochastic process will be 
 * executed for a number of N individuals. The example s taken from Collins,
 * L.: An Introduction to Markov Chain Analysis, Concepts and Techniques in 
 * Modern Geography No. 1, Ashford/London 1975.
 *  
 * @author Benno Schmidt
 */
public class MarkovExample 
{
	public static void main(String[] args) throws Exception 
	{
		String[] locations = new String[] {
			"London", "Suburbs", "Country"};
		int[] numberOfIndividuums = new int[] {
			50000, 30000, 20000
		}; // i.e., N = 100000 

		// Initially, 50000 individuals for state "London", 
		// 30000 for "Suburbs" etc.
		
		ArrayList<Object> stateSet = new ArrayList<Object>();
		for (String d : locations)
			stateSet.add(d);
		
		// The element T[i][j] of the transition matrix gives the probability
		// of movement of an individual from state i to state j:
		double[][] T = new double[][] {
			{.6, .3, .1},
			{.2, .5, .3},
			{.4, .1, .5}
		};
		
		SimpleMarkovChain a = new SimpleMarkovChain(
			stateSet, numberOfIndividuums, T);

		int numberOfSteps = 10;
		a.execute(numberOfSteps);
		
		a.dumpResult("Year", 2010, 10);
	}
}
