package de.hsbo.geo.simsamples.applications;

import de.hsbo.geo.simsamples.diffequations.DEModel;
import de.hsbo.geo.simsamples.diffequations.DESimulation;
import de.hsbo.geo.simsamples.diffequations.instances.MassSpringDamperModel;

/**
 * Simulation of a mass spring damper system. The physical model is specified 
 * in {@link MassSpringDamperModel}.
 * 
 * @author Benno Schmidt
 */
public class HarmonicOscillatorExample 
{
	public static void main(String[] args) throws Exception 
	{
		DEModel m = new MassSpringDamperModel();
		DESimulation a = new DESimulation(m);
		
		// Simulation interval and time step width:
		a.setStartTime(0.);
		a.setEndTime(10.);
		a.setDeltaT(0.04);
		
		a.enableConsoleDump();
		a.execute();
	}
}
