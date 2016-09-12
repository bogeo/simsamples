package de.hsbo.geo.simsamples.markov.instances;

import de.hsbo.geo.simsamples.common.RandomValueGenerator;
import de.hsbo.geo.simsamples.diffequations.Level;
import de.hsbo.geo.simsamples.markov.MarkovProcess;

/**
 * Base class for Galton-Watson model implementations. This class 
 * provides a simple standard implementation for the method {@link 
 * GaltonWatsonProcess#numberOfDescendants()} which determines the number 
 * of descendants of a single individual (geometric distribution as default). 
 * Note that you might want to provide your own implementation by overriding 
 * this method in a derived class.
 * 
 * @author Benno Schmidt
 */
public class GaltonWatsonProcess extends MarkovProcess 
{
	private long pop = 1;
	private Level lev = new Level("pop");
	
	
	/**
	 * sets the initial population value. By default, an initial population of
	 * 1 is assumed.
	 * 
	 * @param pop Integer value > 0
	 */
	public void setInitialPopulation(long pop) {
		if (pop >= 1) {
			this.pop = pop; 
		}
	}

	@Override
	public void execute(int numberOfSteps) throws Exception 
	{
		this.beforeExecute();

		lev.setInitialValue(pop);
		
		for (int ti = 1; ti <= numberOfSteps; ti++) {
			long sum = 0;
			if (consoleDump) 
				System.out.print("ti = " + ti + ":");
			for (int i = 0; i < pop; i++) {
				long z = this.numberOfDescendants(); 
				sum += z;
				if (consoleDump) 
					System.out.print((i > 0 ? " + " : " ") + z);
			}
			if (consoleDump) 
				System.out.println(((pop > 0) ? " = " : " ") + sum);
			pop = sum;
			lev.setValue(ti, pop);
		}
		
		this.afterExecute();
	}

	/**
	 * gets the resulting time series as {@link Level}-object.
	 * 
	 * @return Simulation result
	 */
	public Level getResult() {
		return lev;
	}

	/**
	 * gets the final population value. 
	 * 
	 * @return Integer value >= 0
	 */
	public long getFinalPopulation() {
		return pop;
	}

	public void dumpResult() throws Exception 
	{
		System.out.println("ti\tpopulation");
		for (int ti = 0; ti < lev.size(); ti++) 
			System.out.println(ti + "\t" + lev.getValue(ti)); 
	}

	/**
	 * determines the number of descendants of a single individuum. This method
	 * might be overridden. The default implementation uses the geometric 
	 * distribution <tt>p(k) = 1 / 2**(k+1)</tt> where <tt>p(k)</tt> denotes 
	 * the probability an individuum has <tt>k</tt> descendants, i.e. <tt>p(0)
	 * = 1/2, p(1) = 1/4, p(2) = 1/8</tt> etc. 
	 * 
	 * @return Individual number of descendants
	 */
	protected long numberOfDescendants() {
		double pd = RandomValueGenerator.number(0., 1.);
		double p = 0.5; 
		long res = 0;
		while (!(p < pd)) {
			p /= 2.;
			res++;
		}
		return res;
	}
}
