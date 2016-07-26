package de.hsbo.geo.simsamples.cellularautomata;

import java.util.List;

import de.hsbo.geo.simsamples.common.TimeSeries;

/**
 * Base class for cell definitions. Basically, a cell is embedded in a {@link 
 * CellularSpace} and consists of state values over time and information about
 * its location inside the given cellular space.
 * 
 * @author Benno Schmidt
 */
abstract public class Cell 
{
	protected TimeSeries timeSeries = new TimeSeries();
	protected CellularSpace sp = null; // will be set by implementing class
	protected CellLocation loc; // !

	
	/**
	 * gets this cell's neighbor cells with respect to the given neighborhood 
	 * definition. 
	 * 
	 * @param neighDef Neighborhood definition
	 * @return List of cell objects
	 * @throws Exception
	 */
	abstract public List<Cell> getNeighbors(NeighborhoodIndex neighDef) 
		throws Exception;

	/**
	 * gets the cell's initial value (at simulation start time, 
	 * time stamp <tt>ti = 0</tt>).
	 * 
	 * @return Value as state object
	 * @throws Exception
	 */
	public Object getInitialValue() throws Exception {
		return this.getValue(0);
	} 

	/**
	 * sets the cell's initial value (at simulation start time,  
	 * time stamp <tt>ti = 0</tt>).
	 * 
	 * @param val Value as state object
	 */
	public void setInitialValue(Object val) {
		try {
			this.setValue(0, val);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see TimeSeries#getValue
	 * 
	 * @param ti Time stamp number
	 * @return Value 
	 */
	public Object getValue(int ti) throws Exception {
		return this.timeSeries.getValue(ti);
	}

	/**
	 * @see TimeSeries#setValue
	 * 
	 * @param ti Time stamp number
	 * @param val Value
	 * @throws Exception 
	 */
	public void setValue(int ti, Object val) throws Exception {
		this.timeSeries.setValue(ti, val);
	}
	
	/**
	 * gets information about this cell's location inside the given cellular 
	 * space.
	 * 
	 * @return Location information
	 */
	public CellLocation getLocation() {
		return this.loc;
	}

	/**
	 * gets information about the space this cell s embedded in.
	 * 
	 * @return {@link CellularSpace} object
	 */
	public CellularSpace getCellularSpace() {
		return this.sp;
	}

	public String toString() 
	{
		StringBuffer s = new StringBuffer();
		
		s.append("(");
		s.append(this.getClass().getSimpleName());
		s.append(": ");
		s.append(this.loc);
		s.append(" (initval=");
		try {
			s.append(this.getInitialValue());
		} catch (Exception e) {
			s.append("err");
		}
		s.append("))");

		return s.toString();
	}
}
