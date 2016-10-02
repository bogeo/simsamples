package de.hsbo.geo.simsamples.applications;

import de.hsbo.geo.simsamples.diffequations.DEModel;
import de.hsbo.geo.simsamples.diffequations.DESimulation;
import de.hsbo.geo.simsamples.diffequations.instances.ExponentialGrowthModel;

/**
 * Sample application that executes a model showing exponential growth. The 
 * underlying model <tt>x(t+1) = (1 + r) * x(t)</tt> is defined in the class 
 * {@link ExponentialGrowthModel}.
 * 
 * @author Benno Schmidt
 */
public class ExponentialGrowthExample 
{
	public static void main(String[] args) throws Exception 
	{
		DEModel m = new ExponentialGrowthModel();
		DESimulation a = new DESimulation(m);
		a.enableConsoleDump();

		// Set growth parameter r:
		m.parameter("r").setValue(0.1);
		
		// Set initial value for x(t):
		m.level("x").setInitialValue(10.); 
		
		// Execute 40 time steps:
		a.execute(40);
	}
}
