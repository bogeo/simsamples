package de.hsbo.geo.simsamples.circuits;

/**
 * Pulse input function. E.g., for an electric simulation a pulse could switch
 * between two voltage levels.
 * 
 * @author Benno Schmidt
 */
public class PulseInput implements Input 
{
	private double[] time;
	private double val1 = 0.;
	private double val2 = 0.;
	
	
	/**
	 * Constructor
	 * 
	 * @param time Array giving the switching times
	 * @param val1 First value
	 * @param val2 Second value
	 */
	public PulseInput(double[] time, double val1, double val2) {
		this.time = time;
		this.val1 = val1;
		this.val2 = val2;
	}

	/**
	 * provides the signal-value for the given simulation-time.
	 * 
	 * @param time Time given in seconds
	 * @return Signal value 
	 */
	public double value(double time) 
	{
		if (this.time == null || this.time.length <= 0)
			return this.val1;
		if (this.time.length == 1) 
			return (time < this.time[0]) ? this.val1 : this.val2; 
	    
		int i = 0;
	    while (i < this.time.length && this.time[i] < time) {
	    	i++;
	    }
		if (time < 1.e-8) return 0.; // due to numerical instability
	    return (i % 2 == 0) ? this.val1 : this.val2;	
	}
}
 