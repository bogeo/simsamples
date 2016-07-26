package de.hsbo.geo.simsamples.common;

/**
 * Parameter definition. A parameter consists of a name and an arbitrary value.
 * Note that parameter names should be unique!
 * 
 * @author Benno Schmidt
 */
public class Parameter 
{
	private String name;
	private Object val;
	

	/**
	 * Constructor for a parameter without value. Note that a value must be 
	 * assigned later.
	 * 
	 * @param name Parameter name
	 */
	public Parameter(String name) {
		this.name = name;
		this.val = null;
	}

	/**
	 * Constructor for a parameter which represents a floating-point number. 
	 * 
	 * @param name Parameter name
	 * @param val Parameter value
	 */
	public Parameter(String name, double val) {
		this.name = name;
		this.val = new Double(val);
	}

	/**
	 * Constructor for {@link Object}-valued parameters. Note that the 
	 * parameter name must be unique.
	 * 
	 * @param name Parameter name
	 * @param val Parameter value
	 */
	public Parameter(String name, Object val) {
		this.name = name;
		this.val = val;
	}

	/**
	 * gets the parameter name.
	 * 
	 * @return Name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * gets the parameter value. 
	 * 
	 * @return Parameter value
	 */
	public Object getValue() {
		return this.val;
	}
	
	/**
	 * gets the parameter value as floating-point number. 
	 * 
	 * @return Parameter value
	 * @throws Exception 
	 */
	public double getDoubleValue() throws Exception {
		if (!(this.val instanceof Double)) {
			throw new Exception(
				"Tried to access non-floating-point parameter \"" 
				+ this.name + "\"!");
		}
		return ((Double) this.val).doubleValue();
	}
	
	/**
	 * sets the parameter value. 
	 * 
	 * @param val Parameter value
	 */
	public void setValue(double val) {
		this.val = new Double(val);
	}

	/**
	 * sets the parameter value. 
	 * 
	 * @param val Parameter value
	 */
	public void setValue(Object val) {
		this.val = val;
	}
}
