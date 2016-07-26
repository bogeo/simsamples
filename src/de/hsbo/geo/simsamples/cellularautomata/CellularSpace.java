package de.hsbo.geo.simsamples.cellularautomata;

import java.util.List;

/**
 * Cellular space definition as part of an {@link CellularAutomaton} definition.
 * 
 * @author Benno Schmidt
 */
abstract public class CellularSpace 
{
	private StateSet stateSet;	
	
	
	/**
	 * gets the set of cell states as part of the automaton definition.
	 * 
	 * @return Set of state objects
	 */
	public StateSet getStateSet() {
		return stateSet;
	}

	/**
	 * assigns a set of cell states to the corresponding 
	 * {@link CellularAutomaton}.
	 * 
	 * @param states Set of state objects
	 */
	public void setStateSet(StateSet states) {
		this.stateSet = states;
	}

	/**
	 * gets all {@link Cell} objects of this cellular space.
	 * 
	 * @return List of cell objects
	 */
	abstract public List<Cell> getCells();

	/**
	 * generates console output of the cellular space and the cell state for a 
	 * given time step. Note that the output will not be acceptable, if the
	 * string representations of the cell states do not have the same length. 
	 * 
	 * @param ti time step index
	 * @throws Exception
	 */
	abstract public void dump(int ti) throws Exception;

	public String toString() 
	{
		StringBuffer s = new StringBuffer();
		
		s.append("(");
		s.append(this.getClass().getSimpleName());
		s.append(": )");

		return s.toString();
	}
}
