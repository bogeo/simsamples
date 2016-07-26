package de.hsbo.geo.simsamples.applications;

import de.hsbo.geo.simsamples.diffequations.DEModel;
import de.hsbo.geo.simsamples.diffequations.DESimulation;
import de.hsbo.geo.simsamples.diffequations.instances.LotkaVolterraModel;

/**
 * Simulation of a Lotka-Volterra system. The underlying model is specified 
 * in {@link LotkaVolterraModel}.
 * 
 * @author Benno Schmidt
 */
public class LotkaVolterraExample 
{
	public static void main(String[] args) throws Exception 
	{
		DEModel m = new LotkaVolterraModel();
		DESimulation a = new DESimulation(m);
		System.out.println(a);
		
		m.level("x").setInitialValue(50.);
		m.level("y").setInitialValue(15.);	
		
		m.parameter("a1").setValue((0.5)); 
		m.parameter("b1").setValue((0.05)); 
		m.parameter("a2").setValue((0.25)); 
		m.parameter("b2").setValue((0.005)); 

		a.execute(250);
	}
}
