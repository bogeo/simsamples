package de.hsbo.geo.simsamples.cellularautomata.instances;

import java.util.List;

import de.hsbo.geo.simsamples.cellularautomata.Cell;
import de.hsbo.geo.simsamples.cellularautomata.DiscreteStateSet;
import de.hsbo.geo.simsamples.cellularautomata.NeighborhoodIndex;
import de.hsbo.geo.simsamples.cellularautomata.TransitionFunction;

/**
 * Implementation of a cellular automaton that realizes John Conway's popular 
 * "Game of Life".
 * 
 * @author Benno Schmidt
 */
public class GameOfLife extends TransitionFunction 
{
	@Override
	public void defineStates() {
		this.states = new DiscreteStateSet(".", "X"); 
	}

	@Override
	public void step(Cell c, int ti) throws Exception 
	{	
		// First, count living neighbors of c:
		List<Cell> neighs = c.getNeighbors(NeighborhoodIndex.NEIGH_8());
		int livingNeighs = 0;
		for (Cell n : neighs) {
			String valS = (String)(n.getValue(ti));
			if (valS.equals("X")) {
				livingNeighs++;
			}
		}
		
		// Then apply the game's rules:
		String valC = (String) c.getValue(ti);
		if (valC.equals(".") && livingNeighs == 3) {
			c.setValue(ti + 1, "X"); 			
			return;
		}
		if (valC.equals("X") && livingNeighs < 2) {
			c.setValue(ti + 1, "."); 			
			return;
		}
		if (valC.equals("X") && livingNeighs > 1 && livingNeighs < 4) {
			c.setValue(ti + 1, "X"); 			
			return;
		}
		// else:
		c.setValue(ti + 1, "."); 			
	}
}
