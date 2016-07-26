package de.hsbo.geo.simsamples.diffequations.instances;

import de.hsbo.geo.simsamples.common.Parameter;
import de.hsbo.geo.simsamples.diffequations.DEModel;
import de.hsbo.geo.simsamples.diffequations.Level;

/**
 * Simple demographic model. Note that this model does not use the numerical
 * integration method {@link DEModel#integrate(Level, int, double)}. 
 * Nonetheless is useful to implement the model as {@link DEModel}, since 
 * this provides level and parameter definition and access functionality.
 * 
 * @author Benno Schmidt
 */
public class DemographicModel extends DEModel 
{
	public int maxAge = 120;
	
	
	@Override
	public void declarations() 
	{
		for (int age = 0; age <= maxAge; age++) {
			defineLevels(pop(age)); // Population value			
			defineParameter(new Parameter(birth(age))); // Birth rates
			defineParameter(new Parameter(mort(age))); // Mortality rates
		}
	}
	
	@Override
	public void step(int ti) throws Exception 
	{
		for (int age = 1; age <= maxAge; age++) {
			Level lev = level(pop(age));
			double M = P(mort(age - 1)) * L(ti, pop(age - 1));
			double popNew = L(ti, pop(age - 1)) - M;
			lev.setValue(ti + 1, popNew > 0 ? popNew : 0.);
		}

		double B = 0.;
		for (int age = 1; age <= maxAge; age++) {
			B += P(birth(age)) * L(ti, pop(age));
		}
		level(pop(0)).setValue(ti + 1, B);
	}
	
	public String pop(int age) { return "pop" + age; }
	public String birth(int age) { return "brt" + age; }
	public String mort(int age) { return "mrt" + age; }
}
