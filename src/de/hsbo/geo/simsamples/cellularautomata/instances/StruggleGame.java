package de.hsbo.geo.simsamples.cellularautomata.instances;

import java.util.List;

import de.hsbo.geo.simsamples.cellularautomata.Cell;
import de.hsbo.geo.simsamples.cellularautomata.DiscreteStateSet;
import de.hsbo.geo.simsamples.cellularautomata.NeighborhoodIndex;
import de.hsbo.geo.simsamples.cellularautomata.RectangularSpace;
import de.hsbo.geo.simsamples.cellularautomata.TransitionFunction;
import de.hsbo.geo.simsamples.common.RandomValueGenerator;

/**
 * Implementation of the "Struggle" game developed by Eigen & Winkler. The game 
 * idea is as follows. A limited space is occupied by a 1. producer, 2. a 
 * predator population and 3. a prey population. Here the cell values 
 * "." = grass, "R" = rabbits and "F" = foxes are used. For every time step,
 * a cell will be chosen randomly. Now one of the following rules will apply:
 * 1. The cell is occupied with "grass": If there is no rabbit on one of the 
 * four neighbor cells, nothing happens. If there is at least one rabbit, a new
 * rabbit will be born. 2. The cell is occupied with a "rabbit": If there is 
 * fox in the neighborhood, the rabbit will be eaten and a new fox will be 
 * born. If there is no fox in the neighborhood, the rabbit will survive only 
 * if there is enough grass in the neighborhood. 3. The cell is occupied with a
 * "fox": The fox will survive if there is at least one rabbit (that will not 
 * be eaten) in the neighborhood, otherwise the fox will die and there will 
 * grow grass on the field. 
 * 
 * @author Benno Schmidt
 */
public class StruggleGame extends TransitionFunction 
{
	@Override
	public void defineStates() {
		this.states = new DiscreteStateSet(".", "R", "F"); 
	}

	@Override
	public void step(int ti) throws Exception 
	{
        RectangularSpace sp = 
            	(RectangularSpace) this.getAutomaton().getCellularSpace();

		int i = RandomValueGenerator.number(0, sp.numberOfRows() - 1);
		int j = RandomValueGenerator.number(0, sp.numberOfColumns() - 1);
		
        Cell c = sp.getCell(i, j);
        String val = (String) c.getValue(ti);
        
    	List<Cell> neighs = c.getNeighbors(NeighborhoodIndex.NEIGH_8());
    	boolean neiR = false, neiF = false, neiG = false;
    	for (Cell n : neighs) {
    		if (((String) n.getValue(ti)).equalsIgnoreCase("R")) neiR = true;
    		if (((String) n.getValue(ti)).equalsIgnoreCase("F")) neiF = true;
    		if (((String) n.getValue(ti)).equalsIgnoreCase("G")) neiG = true;
    	}
    	
        String newVal = val;
        if (val.equalsIgnoreCase(".")) {
        	if (neiR) newVal = "R";
        }
        if (val.equalsIgnoreCase("R")) {
        	if (neiF) newVal = "F";
        	else {
            	if (neiG) newVal = "R";
            	else newVal = ".";            		
        	}
        }
        if (val.equalsIgnoreCase("F")) {
            newVal = ".";
        	if (neiR) newVal = "F";
        }

		c.setValue(ti + 1, newVal);
 	}
}
