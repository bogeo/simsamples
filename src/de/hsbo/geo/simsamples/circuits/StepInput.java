package de.hsbo.geo.simsamples.circuits;


/**
 * Step input function. E.g., for an electric simulation a pulse could step
 * from a given voltage level to another level.
 * 
 * @author Benno Schmidt
 */
public class StepInput implements Input 
{
	private double time = 0.;
	private double valBefore = 0.;
	private double valAfter = 0.;
	
	
	/**
	 * Constructor
	 * 
	 * @param time Time to perform step (given in seconds)
	 * @param valBefore Value before the step takes place (given in Volt)
	 * @param valAfter Value after the step takes place (given in Volt)
	 */
	public StepInput(double time, double valBefore, double valAfter) {
		this.time = time;
		this.valBefore = valBefore;
		this.valAfter = valAfter;
	}

	public StepInput(double valAfter) {
		this(0., 0., valAfter);
	}
	
	/**
	 * provides the signal-value for the given simulation-time.
	 * 
	 * @param time Time given in seconds
	 * @return Signal value 
	 */
	public double value(double time) {
		if (time < this.time)
			return valBefore;
		else
			return valAfter;		 	
	}
}
 