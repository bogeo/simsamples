package de.hsbo.geo.simsamples.cellularautomata;

import java.util.List;
import java.util.Set;

import de.hsbo.geo.simsamples.common.RandomValueGenerator;
import de.hsbo.geo.simsamples.common.Simulator;

/**
 * Base class for Cellular Automaton implementations. Inside this framework,  
 * a Cellular Automaton is defines by a tuple (<tt>S</tt>, <tt>N</tt>, 
 * <tt>Q</tt>, <tt>delta</tt>), where <tt>S</tt> gives a {@link CellularSpace},
 * <tt>N</tt> defines a finite neighborhood, <tt>Q</tt> a set of cell 
 * states, and <tt>delta</tt> a transition function. Note that inside this 
 * framework <tt>N</tt> and <tt>Q</tt> will be defined inside concrete 
 * <tt>delta</tt>-implementations, i.e. {@link TransitionFunction}-objects.
 * 
 * @author Benno Schmidt
 */
abstract public class CellularAutomaton extends Simulator
{
	protected CellularSpace cells; 
	protected StateSet stateSet;
	protected boolean initialized = false;
	
	protected TransitionFunction delta;
	protected int ti = 0;
	
	
	/**
	 * gets the cellular space as part of the automaton definition.
	 * 
	 * @return Cellular space instance
	 */
	public CellularSpace getCellularSpace() {
		return cells;
	}

	/**
	 * assigns a cellular space to this automaton.
	 * 
	 * @param cells Cellular space instance
	 */
	public void setCells(CellularSpace cells) {
		this.cells = cells;
		if (this.cells != null) {
			// TODO: Log info 
			this.stateSet = this.cells.getStateSet();
		}
	}
		
	/**
	 * gets the set of cell states as part of the automaton definition.
	 * 
	 * @return Set of state objects
	 */
	public StateSet getStateSet() {
		return stateSet;
	}

	/**
	 * assigns a set of cell states to this automaton.
	 * 
	 * @param states Set of state objects
	 */
	public void setStateSet(StateSet states) {
		this.stateSet = states;
		if (this.cells != null) {
			// TODO: Log info
			this.cells.setStateSet(stateSet);
		}
	}

	protected void initialize() throws Exception 
	{
		this.initializeRandomly();
	}

	/**
	 * assigns initial values to all cells of the automaton randomly. All 
	 * values defined by the assigned state set will occur with the same 
	 * probability. 
	 * 
	 * @throws Exception
	 */
	public void initializeRandomly() throws Exception 
	{
		for (Cell c : this.cells.getCells()) {
			if (this.stateSet instanceof DiscreteStateSet) {
				Set<Object> states = ((DiscreteStateSet) this.stateSet).getAsSet();
				Object val = RandomValueGenerator.chooseRandomly(states);
				c.setInitialValue(val); 
			}
			// TODO Handle continuous state sets
		}
		this.initialized = true;
	}

	/**
	 * assigns initial values to all cells of the automaton randomly. The 
	 * values given in <tt>vals</tt> will occur with the same probability.
	 * Make sure, all given <tt>vals</tt> are elements of the automaton's
	 * set of cell states.
	 * 
	 * @param vals
	 * @throws Exception
	 */
	public void initializeRandomly(Object... vals) throws Exception 
	{
		if (vals.length == 1) {
			this.initializeWith(vals[0]);
			return;
		}
		for (Cell c : this.cells.getCells()) {
			Object val = RandomValueGenerator.chooseRandomly(vals);
			c.setInitialValue(val); 
		}
		this.initialized = true;
	}

	/**
	 * assigns the initial value <tt>val</tt> to all cells of the automaton.
	 * Make sure, <tt>val</tt> is element of the automaton's set of cell 
	 * states.
	 * 
	 * @param val
	 * @throws Exception
	 */
	public void initializeWith(Object val) throws Exception 
	{
		for (Cell c : this.cells.getCells()) {
			c.setInitialValue(val); 
		}
		this.initialized = true;
	}

	/**
	 * gets the transition function as part of the automaton definition.
	 * 
	 * @return Transition function instance
	 */
	public TransitionFunction getDelta() {
		return delta;
	}
	
	/**
	 * assigns a transition function to this automaton.
	 * 
	 * @param delta Transition function instance
	 */
	public void setDelta(TransitionFunction delta) {
		this.delta = delta;
		this.setStateSet(delta.getStateSet()); 	
		this.delta.setAutomaton(this);
	}
	
	/**
	 * executes the assigned transition function for a single time step. 
	 *  
	 * @throws Exception
	 */
	public void step() throws Exception 
	{
		this.delta.beforeStep(this.ti); 
		// Step for automaton, could be implemented as empty function!

		List<Cell> cs = this.cells.getCells();
		for (Cell c : cs) {	
			this.delta.step(c, this.ti); 
			// Step for a cell, could be implemented as empty function!
		}
		
		this.delta.step(this.ti); 
		// Step for automaton, could be implemented as empty function!
	}
	
	public String toString() 
	{
		StringBuffer s = new StringBuffer();
	
		s.append("(");
		s.append(this.getClass().getSimpleName());
		s.append(":");

		if (this.cells != null) {
			s.append(" " + this.cells);
		}
		if (this.stateSet != null) {
			s.append(" " + this.stateSet);
		}
		if (this.delta != null) {
			s.append(" " + this.delta);
		}
		s.append(")");
		
		return s.toString();
	}
}
