package de.hsbo.geo.simsamples.common;

/**
 * Null value representation. 
 *    
 * @author Benno Schmidt
 */
public class NullValue 
{
	static private NullValue inst = new NullValue();
	
	static public NullValue getInstance() {
		return inst;
	}
	
	public String toString() {
		return "nil";
	}
}
