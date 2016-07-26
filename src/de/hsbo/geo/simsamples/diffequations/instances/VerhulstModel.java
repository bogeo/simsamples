package de.hsbo.geo.simsamples.diffequations.instances;

import de.hsbo.geo.simsamples.common.Parameter;
import de.hsbo.geo.simsamples.diffequations.DEModel;

/**
 * Logistic growth according Verhulst's model 
 * <tt>x(t+1) = r * x(t) + r/k * x(t)^2</tt>.
 * 
 * @author Benno Schmidt
 */
public class VerhulstModel extends DEModel 
{
	@Override
	public void declarations() 
	{
		defineLevels("x");
		defineParameter(new Parameter("r", 1.76)); 
		defineParameter(new Parameter("k", 1000.0)); 
	}
	
	@Override
	public void step(int ti) throws Exception 
	{
		double rate = 
			P("r") * L(ti, "x") - (P("r") / P("k")) * L(ti, "x") * L(ti, "x");
		
		this.integrate(level("x"), ti, rate);
	}
}
