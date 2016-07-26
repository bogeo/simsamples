package de.hsbo.geo.simsamples.cellularautomata;

/**
 * Base class for transition function implementations as part of automaton 
 * definitions.
 * 
 * @author Benno Schmidt
 */
abstract public class TransitionFunction 
{
	protected StateSet states;
	protected CellularAutomaton a;

	
	public TransitionFunction() {
		this.defineStates();
	}

	/**
	 * defines the state set for this transition function. This method has to 
	 * be implemented by all {@link TransitionFunction}-realizations.
	 */
	abstract public void defineStates();

	/**
	 * defines the state set for this transition function. 
	 */
	public StateSet getStateSet() { 
		return this.states; 
	}

	/**
	 * gets the Cellular Automaton instance that is linked to this transition 
	 * function.
	 */
	public CellularAutomaton getAutomaton() {
		return a;
	}

	/**
	 * links a Cellular Automaton instance to this transition function. 
	 * 
	 * @param a
	 */
	public void setAutomaton(CellularAutomaton a) {
		this.a = a;
	}

	/**
	 * performs the transition for the given time step for all {@link 
	 * Cell}s of the Cellular Automaton. This method can be overridden 
	 * by concrete transition function implementations. By default, this 
	 * method has no effect. Note that additionally the method {@link 
	 * TransitionFunction#states} will be executed for each cell.
	 * 
	 * @param ti Time stamp index
	 * @throws Exception
	 */
	public void step(int ti) throws Exception {
		// do nothing
	}

	/**
	 * performs the transition for the given time step for a {@link Cell} of 
	 * the Cellular Automaton. This method can be overridden by concrete 
	 * transition function implementations. By default, this method copies 
	 * the value for time step <tt>ti</tt> to the value for <tt>ti + 1</tt>. 
	 * Note that this method will be called for all cells of the automaton. 
	 * Additionally the method {@link TransitionFunction#step(int)} will be 
	 * executed for all cells (without effect by default).
	 * 
	 * @param c Cell object
	 * @param ti Time stamp index
	 * @throws Exception
	 */
	public void step(Cell c, int ti) throws Exception {
		c.setValue(ti + 1, c.getValue(ti));
	}

	/**
	 * defines actions that will take place before the <tt>step</tt> 
	 * methods {@link TransitionFunction#step(Cell, int)} and {@link 
	 * TransitionFunction#step(int)} are executed. This method can be 
	 * overridden by concrete transition function implementations. By 
	 * default, this method has no effect. 
	 */
	public void beforeStep(int ti) throws Exception {
		// do nothing
	}

	public String toString() 
	{ 
		StringBuffer s = new StringBuffer();
		
		s.append("(");
		s.append(this.getClass().getSimpleName());
		s.append(":)");

		return s.toString();
	}
}
