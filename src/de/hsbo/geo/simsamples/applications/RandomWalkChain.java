package de.hsbo.geo.simsamples.applications;

import java.util.ArrayList;

import de.hsbo.geo.simsamples.markov.instances.SimpleMarkovChain;

/**
 * Sample application that shows how to realize a simple Markov chain. The
 * example simulates an agent's movement in (geographic) space. For each time
 * step, the movement direction depends only on the movement state of the
 * previous time step (i.e. "1st order Markov"). With a probability of 0.5
 * the movement direction does not change, with a probability of 0.25 it
 * changes by 45 degrees (e.g., from "North" to "NorthEast" or "NorthWest").
 * These probabilities are specified with help of a transition matrix (see 
 * source code).
 * 
 * @author Benno Schmidt 
 */
public class RandomWalkChain 
{
	public static void main(String[] args) throws Exception 
	{
		String initialState;
		String[] directions = new String[] {
			(initialState = "North"), "NorthEast", "East", "SouthEast", 
			"South", "SouthWest", "West", "NorthWest"};

		ArrayList<Object> stateSet = new ArrayList<Object>();
		for (String d : directions)
			stateSet.add(d);
		
		double[][] transitionMatrix = new double[][] {
			{.5 , .25,  0, .0 , .0 , .0 ,  .0 , .25},
			{.25, .5 , .25, .0 , .0 , .0 , .0 , .0 },
			{.0 , .25, .5 , .25, .0 , .0 , .0 , .0 },
			{.0 , .0 , .25, .5 , .25, .0 , .0 , .0 },
			{.0 , .0 , .0 , .25, .5 , .25, .0 , .0 },
			{.0 , .0 , .0 , .0 , .25, .5 , .25, .0 },
			{.0 , .0 , .0 , .0 , .0 , .25, .5 , .25},
			{.25, .0 , .0 , .0 , .0 , .0 , .25, .5 } 
		};
		
		SimpleMarkovChain a = new SimpleMarkovChain(
			stateSet, initialState, transitionMatrix);

		a.execute(100);
		
		a.dumpResult();
	}
}
