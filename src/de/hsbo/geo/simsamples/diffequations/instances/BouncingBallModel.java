package de.hsbo.geo.simsamples.diffequations.instances;

import de.hsbo.geo.simsamples.diffequations.DEModel;

/**
 * Implementation of a bouncing ball described by the differential equations
 * <tt>dv/dt = -g</tt> and <tt>dx/dt = v</tt>, where <tt>x(t)</tt> gives the 
 * position of the ball at time <tt>t</tt> and <tt>v(t)</tt> the velocity.
 * 
 * @author Benno Schmidt
 */
public class BouncingBallModel extends DEModel 
{
	double g = 9.81; // Gravitational acceleration
	double k = 0.8; // Restitution coefficient 
	
	
	@Override
	public void declarations() 
	{
		defineLevels("x", "v");
		
		// From a height of 10 m above ground, the ball will be thrown up with 
		// a velocity of 15 m/sec: 
		level("x").setInitialValue(10.); 
		level("v").setInitialValue(15.); 
	}
	
	@Override
	public void step(int ti) throws Exception 
	{
		double v = L(ti, "v"); // get velocity v(ti)

		this.integrate(level("x"), ti, v); 
		this.integrate(level("v"), ti, -g); 
	
		// The ball can not go below ground:
		if (L(ti + 1, "x") <= 0.) {
			level("x").setValue(ti + 1, 0.); // limit position
			level("v").setValue(ti + 1, -v * k); // change moving direction
		}
	}	
}
