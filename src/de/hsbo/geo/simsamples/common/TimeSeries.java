package de.hsbo.geo.simsamples.common;

import java.util.ArrayList;

/**
 * Series of object values over time.
 * 
 * @author Benno Schmidt
 */
public class TimeSeries 
{
	private ArrayList<Object> entries = new ArrayList<Object>(); 
	
	private Double min, max;
	private boolean updateMinMaxNecessary = true;
	
	
	/**
	 * adds a given value to the series.
	 * 
	 * @param val Value
	 */
	public void add(Object val) {
		entries.add(val);
		updateMinMaxNecessary = true;
	}
	
	/**
	 * gets the number of values stores in the series.
	 * 
	 * @return Number >= 0
	 */
	public int size() {
		return entries.size();
	}
	
	/**
	 * sets the value of the <tt>ti</tt>-th entry of the series. Missing 
	 * entries in the range <tt>this.size</tt> ... <tt>ti - 1</tt> will be 
	 * filled up with {@link NullValue}s.
	 * 
	 * @param ti Time stamp number
	 * @param val Value
	 * @throws Exception 
	 */
	public void setValue(int ti, Object val) throws Exception {
		if (ti < 0) {
			throw new Exception(
					"Invalid time stamp access: " + 
					"Index " + ti + " out of bounds 0.." +  (this.size() - 1));
		}
		int last = this.size() - 1;
		if (last < ti) {
			// Fill up entries:
			for (int i = last + 1; i <= ti; i++) {
				this.add(NullValue.getInstance());
			}
		}
		entries.set(ti,  val);
		updateMinMaxNecessary = true;
	}
	
	/**
	 * gets the <<tt>ti</tt>-th value stored in the series. If the entry is not 
	 * present, an {@link Exception} will be thrown.
	 * 
	 * @param ti Time stamp number
	 * @return Value 
	 */
	public Object getValue(int ti) throws Exception {
		if (ti < 0 || ti >= this.size()) {
			throw new Exception(
				"Invalid time stamp query: " + 
				"Index " + ti + " out of bounds 0.." +  (this.size() - 1));
		}
		return entries.get(ti);
	}
	
	/**
	 * returns this series' minimum numeric value. The result will be 
	 * <i>null</i>, if the series contains no numeric values.
	 *  
	 * @return Numeric value or <i>null</i> 
	 */
	public Double minNumericValue() 
	{
		if (updateMinMaxNecessary)
			updateMinMax();
		return this.min;
	}

	/**
	 * returns this series' maximum numeric value. The result will be 
	 * <i>null</i>, if the series contains no numeric values.
	 *  
	 * @return Numeric value or <i>null</i>
	 */
	public Double maxNumericValue() 
	{
		if (updateMinMaxNecessary)
			updateMinMax();
		return this.max;
	}
	
	private void updateMinMax() {
		// lazy evaluation
		boolean set = false;
		double min = 42., max = 42.; // dummies as init vals
		for (Object e : entries) {
			if (e instanceof java.lang.Number) {
				double val = Double.parseDouble(e.toString());
				if (!set) {
					set = true;
					min = val;
					max = val;
				}
				if (val < min) min = val;
				if (val > max) max = val;
			}			
		}
		this.min = set ? min : null;
		this.max = set ? max : null;
		updateMinMaxNecessary = false;	
	}
}
