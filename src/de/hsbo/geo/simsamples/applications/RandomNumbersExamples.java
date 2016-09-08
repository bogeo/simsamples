package de.hsbo.geo.simsamples.applications;

import de.hsbo.geo.simsamples.common.RandomValueGenerator;

/**
 * Sample application that show how to generate random numbers as needed for 
 * various purposes inside simulation scenarios. 
 * 
 * @author Benno Schmidt
 */
public class RandomNumbersExamples 
{
	public static void main(String[] args) throws Exception 
	{
		new RandomNumbersExamples().run();
	}
	
	private void run() throws Exception 
	{ 
		this.throwDice(10);
		
		this.throwDiceAndCount(100);
		this.throwDiceAndCount(10000);
		
		this.generateNormalDistributedValues(20, 0., 100.);
	}
	
	public void throwDice(int N) {
		System.out.println("Throw dice (" + N + "x):");
		for (int i = 0; i < N; i++)
			System.out.println(RandomValueGenerator.number(1, 6));
	}

	public void throwDiceAndCount(int N) {
		System.out.println("Throw dice (" + N + "x) and count result values:");
		int[] sum = new int[6];
		for (int k = 0; k < 6; k++) 
			sum[k] = 0; // init.
		for (int i = 0; i < N; i++) { 
			int res = RandomValueGenerator.number(1, 6); // throw dice
			sum[res-1]++; // ... and count
		}
		// Show summary:
		for (int k = 0; k < 6; k++) 
			System.out.println("Result " + (k+1) + ": " + sum[k] + "x"
				+ " (p = " + ((double)sum[k]/(double)N) + ")");
	}
	
	public void generateNormalDistributedValues(int N, double min, double max) 
	{
		System.out.println(N + " random normal distributed values"
			+ " in the range " + min + ".." + max);
		double mean = (min + max) / 2.; 
		double sDeviation = (max - min) / 6.;
		double sum = 0., val;
		for (int i = 0; i < N; i++) {
			val = RandomValueGenerator.number(min, max, mean, sDeviation);
			System.out.println(val);
			sum += val;
		}
		System.out.println("Estimated mean: " + sum/N);
	}
}
