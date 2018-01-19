package de.hsbo.geo.simsamples.applications;

import de.hsbo.geo.simsamples.common.RandomValueGenerator;

/**
 * Monte-Carlo simulation to calculate PI 
 * 
 * @author Benno Schmidt
 */
public class MonteCarloCalculationOfPi 
{
	public long N = 10000000; // Number of experiments
	
	public static void main(String[] args) throws Exception 
	{
		double res = new MonteCarloCalculationOfPi().run();
		System.out.println("Approx.:\t" + res);
		System.out.println("Exact:\t" + Math.PI);
		System.out.println("Delta:\t" + (res - Math.PI));		
	}
	
	private double run() 
	{
		double x , y;
		long ct = 0;
		
		for (long i = 0; i < N; i++) {
			x = RandomValueGenerator.number(0., 1.);
			y = RandomValueGenerator.number(0., 1.);
			if (x * x + y * y < 1.) ct++;
		}
		
		return 4. * (double) ct/N;
	}
}

