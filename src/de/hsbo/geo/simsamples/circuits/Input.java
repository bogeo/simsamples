package de.hsbo.geo.simsamples.circuits;

/**
 * Input signal interface.
 * 
 * @author Benno Schmidt
 */
public interface Input 
{
	/**
	 * provides the signal value (e.g., a voltage or current for an electric
	 * circuit) for the given simulation-time.
	 * 
	 * @param time Time given in seconds
	 * @return Signal value (e.g. given in Volt resp. Ampere)
	 */
	public double value(double time); 	
}
 