package de.hsbo.geo.simsamples.applications;

import de.hsbo.geo.simsamples.markov.instances.GaltonWatsonProcess;

/**
 * Sample application that executes a Galton-Watson process (see
 * {@link GaltonWatsonProcess}.
 * 
 * @author Benno Schmidt
 */
public class GaltonWatsonExample 
{
	public static void main(String[] args) throws Exception 
	{
		GaltonWatsonProcess a = new GaltonWatsonProcess();

		a.setInitialPopulation(20);
		//a.setConsoleDump(true);
		a.execute(10);
		
		a.dumpResult();
		System.out.println("Final population: " + a.getFinalPopulation());
	}
}
