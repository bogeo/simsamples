package de.hsbo.geo.simsamples.applications;

import de.hsbo.geo.simsamples.diffequations.DEModel;
import de.hsbo.geo.simsamples.diffequations.DESimulation;
import de.hsbo.geo.simsamples.diffequations.instances.ExponentialGrowthModel;
import de.hsbo.geo.simsamples.diffequations.instances.VerhulstModel;

/**
 * Sample application that executes Verhulst's famous model showing logistic 
 * growth as well as chaotic behavior. The underlying model 
 * <tt>x(t+1) = r * x(t) - r/k * x(t)^2</tt> is defined in the class 
 * {@link ExponentialGrowthModel}.
 * 
 * @author Benno Schmidt
 */
public class VerhulstExample 
{
	public static void main(String[] args) throws Exception 
	{
		DEModel m = new VerhulstModel();
		DESimulation a = new DESimulation(m);

		m.parameter("k").setValue(1000.); 
		m.level("x").setInitialValue(10.); // Initial value for x(t)

		System.out.println("Run #1 (logistic growth):");
		m.parameter("r").setValue(0.4);
		a.execute(40);

		System.out.println("Run #2 (overshoot):");
		m.parameter("r").setValue(1.85);
		a.execute(40);

		System.out.println("Run #3 (periodic):");
		m.level("x").setInitialValue(0.1); 
		m.parameter("r").setValue(2.5);
		a.execute(40);
	
		System.out.println("Run #4 (chaotic):");
		m.parameter("r").setValue(2.9);
		a.execute(40);
	}
}
