package de.hsbo.geo.simsamples.cellularautomata.instances;

import de.hsbo.geo.simsamples.cellularautomata.Cell;
import de.hsbo.geo.simsamples.cellularautomata.DiscreteStateSet;
import de.hsbo.geo.simsamples.cellularautomata.TransitionFunction;

/**
 * Simple transition function for a Cellular Automaton. This automaton 
 * uses four cell states " ", ".", ":", ";" and "#" representing increasing 
 * infection levels, where the initial value " " represents lowest and "#" 
 * the highest value. For every time step, with a probability given by
 * <tt>this.p</tt>, a cell's level will increase.
 * 
 * @author Benno Schmidt
 */
public class SimpleEpidemy extends TransitionFunction 
{
	double p = 0.6;
	
	
	@Override
	public void defineStates() {
		this.states = new DiscreteStateSet(" ", ".", ":", ";", "#");
	}

	@Override
	public void step(Cell c, int ti) throws Exception 
	{
		Object val = c.getValue(ti); 
		Object valNew = val;
		
		String s = (String) val;
		if (Math.random() > (1. - p)) {
			if (s.equals(" ")) valNew = ".";
			if (s.equals(".")) valNew = ":";
			if (s.equals(":")) valNew = ";";
			if (s.equals(";")) valNew = "#";
		}
		c.setValue(ti + 1, valNew); 		
	}
}
