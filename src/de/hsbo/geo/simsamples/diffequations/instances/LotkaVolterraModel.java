package de.hsbo.geo.simsamples.diffequations.instances;

import de.hsbo.geo.simsamples.common.Parameter;
import de.hsbo.geo.simsamples.diffequations.DEModel;
import de.hsbo.geo.simsamples.diffequations.IntegrationMethod;

/**
 * Implementation of the Lotka-Volterra model. Note that the model equations
 * will be integrated using the Runge-Kutta method.
 * 
 * @author Benno Schmidt
 */
public class LotkaVolterraModel extends DEModel 
{
	@Override
	public void declarations() {
		defineLevels("x", "y");
		defineParameter(new Parameter("a1", 0.5)); 
		defineParameter(new Parameter("b1", 0.05)); 
		defineParameter(new Parameter("a2", 0.25)); 
		defineParameter(new Parameter("b2", 0.005)); 

		try {
			this.getSimulation()
				.setIntegrationMethod(IntegrationMethod.RUNGE_KUTTA);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void step(int ti) throws Exception 
	{
		this.integrate(
			level("x"), ti, 
			P("a1") * L(ti, "x") - P("b1") * L(ti, "x") * L(ti, "y"));
		
		this.integrate(
			level("y"), ti, 
			P("b2") * L(ti, "x") * L(ti, "y") - P("a2") * L(ti, "y"));
	}
}
