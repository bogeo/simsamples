package de.hsbo.geo.simsamples.diffequations.instances;

import de.hsbo.geo.simsamples.common.Parameter;
import de.hsbo.geo.simsamples.diffequations.DEModel;

/**
 * Simple exponential growth model <tt>x(t+1) = x(t) + r * x(t)</tt>.
 * 
 * @author Benno Schmidt
 */
public class ExponentialGrowthModel extends DEModel 
{
	@Override
	public void declarations() 
	{
		defineLevels("x"); // Population value
		defineParameter(new Parameter("r", 0.1)); // Growth parameter
	}
	
	@Override
	public void step(int ti) throws Exception 
	{
		double rate = P("r") * L(ti, "x");
		this.integrate(level("x"), ti, rate);
	}
}
