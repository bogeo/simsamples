package de.hsbo.geo.simsamples.cellularautomata;

import java.util.Set;

import de.hsbo.geo.simsamples.common.RandomValueGenerator;

/**
 * Implementation of a universal Cellular Automaton operating on rectangular 
 * cellular spaces.
 * 
 * @author Benno Schmidt
 */
public class RectangularAutomaton extends CellularAutomaton 
{
	private int nx, ny;
	

	/**
	 * Constructor
	 * 
	 * @param nx Number of rows of the cellular grid
	 * @param ny Number of columns of the cellular grid
	 * @param states Set of cell state objects
	 * @param delta Transition function
	 * 
	 * @see CellularAutomaton
	 */
	public RectangularAutomaton(
		int nx, 
		int ny, 
		StateSet states, 
		TransitionFunction delta) 
	{
		this(nx, ny, delta);
		this.setStateSet(states);  
	}

	/**
	 * Constructor. Note that the set of cell states will be got from the
	 * given transition function <tt>delta</tt>.
	 * 
	 * @param nx Number of rows of the cellular grid
	 * @param ny Number of columns of the cellular grid
	 * @param delta Transition function
	 * 
	 * @see CellularAutomaton
	 */
	public RectangularAutomaton(
		int nx, 
		int ny, 
		TransitionFunction delta) 
	{
		this.cells = new RectangularSpace(nx, ny);
		this.nx = nx;
		this.ny = ny;
		this.setDelta(delta); 
	}

	@Override
	public void execute(int numberOfSteps) throws Exception 
	{
		// TODO: Check if main parts of the code could be moved to the base class.

		this.numberOfSteps = numberOfSteps;
		this.beforeExecute();
		
		if (! this.initialized) {
			this.initialize();
		}

		// Then step through time:
		for (int ti = 0; ti <= numberOfSteps; ti++) {
			this.step();

			if (this.consoleDump) {
				System.out.println("ti = " + ti + ":"); 
				((RectangularSpace) this.getCellularSpace()).dump(this.ti); 
			}
			
			this.ti++;
		}
		
		this.afterExecute();
	}

	// Note: The implementations of the following overridden methods have a 
	// better performance than those offered by the base class.

	@Override
	public void step() throws Exception 
	{
		this.delta.beforeStep(this.ti); 

		Cell[][] arr = ((RectangularSpace) this.cells).getCellArray();
		for (int i = 0; i < this.nx; i++) {
			for (int j = 0; j < this.ny; j++) {
				this.delta.step(arr[i][j], this.ti); 
				// Step for a cell, could be implemented as empty function!
			}
		}
		this.delta.step(this.ti); 
		// Step for automaton, could be implemented as empty function!
	}

	@Override
	public void initializeRandomly() throws Exception 
	{
		Cell[][] arr = ((RectangularSpace) this.cells).getCellArray();
		for (int i = 0; i < this.nx; i++) {
			for (int j = 0; j < this.ny; j++) {
				if (this.stateSet instanceof DiscreteStateSet) {
					Set<Object> states = 
						((DiscreteStateSet) this.stateSet).getAsSet();
					Object val = RandomValueGenerator.chooseRandomly(states);
					arr[i][j].setInitialValue(val); 
				}
				// TODO continuousstatese
			}
		}
		this.initialized = true;
	}

	@Override
	public void initializeRandomly(Object... vals) throws Exception 
	{
		if (vals.length == 1) {
			this.initializeWith(vals[0]);
			return;
		}
		Cell[][] arr = ((RectangularSpace) this.cells).getCellArray();
		for (int i = 0; i < this.nx; i++) {
			for (int j = 0; j < this.ny; j++) {
				Object val = RandomValueGenerator.chooseRandomly(vals);
				arr[i][j].setInitialValue(val); 
			}
		}
		this.initialized = true;
	}

	@Override
	public void initializeWith(Object val) throws Exception 
	{
		Cell[][] arr = ((RectangularSpace) this.cells).getCellArray();
		for (int i = 0; i < this.nx; i++) {
			for (int j = 0; j < this.ny; j++) {
				arr[i][j].setInitialValue(val); 
			}
		}
		this.initialized = true;
	}
}
