package de.hsbo.geo.simsamples.markov.instances;

import java.util.ArrayList;

import de.hsbo.geo.simsamples.common.RandomValueGenerator;
import de.hsbo.geo.simsamples.diffequations.Level;
import de.hsbo.geo.simsamples.markov.MarkovProcess;

/**
 * Markov chain implementation. This class realizes a homogeneous, 1st order
 * Markov chain for an ordered list of states <tt>s[i]</tt>. The probabilities
 * of movement from a state <tt>i</tt> to a state <tt>j</tt> are specified by 
 * the element <tt>T[i][j]<tt> of the transition matrix. (Note that the sum 
 * of the elements of each matrix row <tt>T[0..stateSet.size()][j]<tt> must be
 * 1.) The stochastic process can be executed either for a single individual or
 * for a number of N individuals. 
 * 
 * @author Benno Schmidt
 */
public class SimpleMarkovChain extends MarkovProcess 
{
	private ArrayList<Object> stateSet;
	private Level lev = null; // used if 1 individual is given
	private Level[] levs = null; // used for > 1 indviduals
	private Object state;
	private double[][] trans;
	

	/**
	 * Constructor to be used, if the process will be executed for a single
	 * individual. 
	 * 
	 * @param stateSet Ordered list of states <tt>s[i]</tt>
	 * @param initialState Initial state
	 * @param transitionMatrix transition matrix <tt>T</tt>
	 * @throws Exception 
	 */
	public SimpleMarkovChain(
		ArrayList<Object> stateSet, Object initialState, 
		double[][] transitionMatrix) 
		throws Exception 
	{
		super();
	
		this.stateSet = stateSet;
		if (! stateSet.contains(initialState)) {
			throw new Exception(
				"Invalid invalid state specification: " + initialState);
		}
		state = initialState;

		lev = new Level("s"); 
		lev.setInitialValue(initialState);
		levs = null;
		
		trans = transitionMatrix; 
		checkTransitionMatrix();
	}

	/**
	 * Constructor to be used, if the process will be executed for more 
	 * than one individual. The <tt>i</tt>-th element of the array 
	 * <tt>numbersOfIndividuals</tt> gives the number of individuals that 
	 * are in state <tt>s[i]</tt> at simulation start time. 
	 * 
	 * @param stateSet Ordered list of states <tt>s[i]</tt>
	 * @param numbersOfIndividuals Numbers of individuals in initial states
	 * @param transitionMatrix transition matrix <tt>T</tt>
	 * @throws Exception 
	 */
	public SimpleMarkovChain(
		ArrayList<Object> stateSet, int[] numbersOfIndividuals, 
		double[][] transitionMatrix) 
		throws Exception 
	{
		super();
	
		this.stateSet = stateSet;

		lev = null;
		int N = 0;
		for (int i = 0; i < numbersOfIndividuals.length; i++)
			N += numbersOfIndividuals[i];
		levs = new Level[N];
		int k = 0;
		for (int i = 0; i < numbersOfIndividuals.length; i++) {
			for (int j = 0; j < numbersOfIndividuals[i]; j++) {
				levs[k] = new Level("s" + k);
				levs[k].setInitialValue(stateSet.get(i));
				k++;
			}
		}

		trans = transitionMatrix; 
		checkTransitionMatrix();
	}

	private void checkTransitionMatrix() throws Exception 
	{
		if (trans == null) {
			throw new Exception("Missing transition matrix!");
		}
		if (trans.length != stateSet.size()) {
			throw new Exception(
				"Invalid transition matrix size!");
		}
		for (int i = 0; i < trans.length; i++) {
			if (trans.length != trans[i].length) {
				throw new Exception(
					"Transition matrix must be symmetric)!");
			}
			double sum = 0.;
			for (int j = 0; j < trans[i].length; j++) {
				sum += trans[i][j];
			}
			double eps = 0.00001;
			if (sum < 1. && sum > 1. - eps) {
				// This might occur for numerical reasons. 
				trans[i][trans[i].length - 1] += eps; 
				sum += eps;
			}
			if (sum < 1. || sum > 1. + eps) {
				throw new Exception(
					"Row sums must be 1 for transition matrix!" +
					" Sum for row " + i + " is " + sum + "!");
			}
		}
	}

	@Override
	public void execute(int numberOfSteps) throws Exception 
	{
		this.beforeExecute();

		this.numberOfSteps = numberOfSteps;
		
		if (lev != null) {
			if (consoleDump) 
				System.out.println("ti = 0: " + lev.getValue(0));	
			for (int ti = 1; ti <= numberOfSteps; ti++) {
				state = this.transition(state); 
				lev.setValue(ti, state);
				if (consoleDump) 
					System.out.println("ti = " + ti + ": " + lev.getValue(ti));
			}
		}
		else {
			if (consoleDump) {
				System.out.println("Note: For simulation of > 1 individuals " + 
					"console dump provides no output.");
			}
			for (int ti = 1; ti <= numberOfSteps; ti++) {
				for (int k = 0; k < levs.length; k++) {
					state = levs[k].getValue(ti - 1);
					state = this.transition(state); 
					levs[k].setValue(ti, state);
				}
			}			
		}

		this.afterExecute();
	}
	
	/**
	 * gets the resulting time series. For one individual, a {@link Level}
	 * object will be returned. If more than one individual has been processed,
	 * an {@link ArrayList} of {@link Level} objects will be returned.  
	 * 
	 * @return Simulation result
	 */
	public Object getResult() {
		if (lev != null) return lev;
		// else:
		return levs;
	}

	public void dumpResult() throws Exception {
		dumpResult("ti", 0, 1);
	}
	
	public void dumpResult(String tTitle, int tStart, int deltaT) 
		throws Exception
	{
		if (lev != null) {
			System.out.println(tTitle + "\tstate");
			for (int ti = 0; ti < lev.size(); ti++) {
				System.out.println(ti + "\t" + lev.getValue(ti)); 
			}
		}
		else {
			System.out.print(tTitle);
			for (int i = 0; i < stateSet.size(); i++) 
				System.out.print("\t" + stateSet.get(i)); 
			System.out.println(); 
			int t = tStart; 
			for (int ti = 0; ti < levs[0].size(); ti++) {
				int[] sum = new int[stateSet.size()];
				for (int k = 0; k < levs.length; k++) {
					Object val = levs[k].getValue(ti);
					int i = stateSet.indexOf(val);
					sum[i]++;
				}
				System.out.print(t);
				t += deltaT;
				for (int i = 0; i < stateSet.size(); i++) 
					System.out.print("\t" + sum[i]); 
				System.out.println();
			}
		}
	}
	
	private Object transition(Object statePre) {
		int i = stateSet.indexOf(statePre);

		double p = RandomValueGenerator.number(0., 1.);
		int j = 0;
		double ps = trans[i][0]; 
		while (p >= ps) {
			ps += trans[i][++j];
		}
		// Assert that row sum is 1, otherwise infinite loops might occur.
		return stateSet.get(j); // statePost
	}
}
