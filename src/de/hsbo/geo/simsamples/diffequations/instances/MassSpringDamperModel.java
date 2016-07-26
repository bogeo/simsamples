package de.hsbo.geo.simsamples.diffequations.instances;

import de.hsbo.geo.simsamples.diffequations.DEModel;

/**
 * Implementation of a simple mass spring damper system described by the 
 * differential equation <tt>M*x'' + D*x' + K*x = F(t)</tt> with 
 * <tt>x(t)</tt> giving the position at time <tt>t</tt>. The model describes a 
 * damped harmonic oscillator. Note that the differential equation will not be 
 * solved analytically here. Instead, a simulation model will be evaluated 
 * numerically.
 * 
 * @author Benno Schmidt
 */
public class MassSpringDamperModel extends DEModel 
{
	double D = 0.1, K = 1., M = 0.2;
		
	@Override
	public void declarations() 
	{
		defineLevels("x", "dx", "d2x");
		level("x").setInitialValue(0.); // initial position
		level("dx").setInitialValue(0.); // initial velocity
		level("d2x").setInitialValue(0.); // initial acceleration
	}
	
	@Override
	public void step(int ti) throws Exception 
	{
		double 
			x = (Double) level("x").getValue(ti),
			dx = (Double) level("dx").getValue(ti),
			d2x = (Double) level("d2x").getValue(ti);
		
		this.integrate(level("x"), ti, dx); 
		this.integrate(level("dx"), ti, d2x); 
		
		level("d2x").setValue(
			ti + 1, 
			1./M * (F(ti) - D * dx - K * x));
	}
	
	private double F(int ti) {
		if (ti >= 0 && ti <= 25) 
			return 1.;
		else 
			return 0.;
	}
}
