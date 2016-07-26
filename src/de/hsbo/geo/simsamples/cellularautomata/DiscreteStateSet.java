package de.hsbo.geo.simsamples.cellularautomata;

import java.util.Set;
import java.util.TreeSet;

/**
 * Definition of the set of cell states as part of an automaton definition.
 * 
 * @author Benno Schmidt
 */
public class DiscreteStateSet implements StateSet
{
	private Set<Object> states;
	
	
	/**
	 * constructs a state set from an arbitrary number of state objects. 
	 * 
	 * @param states State objects
	 */
	public DiscreteStateSet(Object... states) {
		this.states = new TreeSet<Object>(); 
		for (Object s : states) {
			this.states.add(s);
		}
	}

	/**
	 * constructs a state set consisting of {@link Integer} objects with the
	 * number values ranging from <tt>from</tt> to <tt>to</tt>.
	 * 
	 * @param min
	 * @param max
	 */
	public DiscreteStateSet(int min, int max) {
		if (min > max) {
			int x = min; min = max; max = x;
		}
		this.states = new TreeSet<Object>(); 
		for (int i = min; i <= max; i++ ) {
			this.states.add(new Integer(i));
		}
	}

	public Set<Object> getAsSet() {
		return this.states;
	}
}
