package de.hsbo.geo.simsamples.diffequations;

import de.hsbo.geo.simsamples.common.TimeSeries;

/**
 * Level definition. A level consists of a name and a series of values over
 * time. Note that level names should be unique!
 * 
 * @author Benno Schmidt
 */
public class Level extends TimeSeries
{
	private String name;
	private Object tmpVal; // required for impl. of Runge-Kutta method
	
	
	/**
	 * Constructor. The level name should be a unique identifier.
	 * 
	 * @param name Level name
	 */
	public Level(String name) {
		this.name = name;
	}

	protected String getName() {
		return this.name;
	}
	
	/**
	 * sets the initial level value. This method call is similar to 
	 * <tt>setValue(0, val)</tt>.
	 * 
	 * @param val Level value
	 */
	public void setInitialValue(Object val) {
		try {
			this.setValue(0, val);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected Object getTempValue() {
		return this.tmpVal;
	}

	protected void setTempValue(Object val) {
		this.tmpVal = val;
	}
}
