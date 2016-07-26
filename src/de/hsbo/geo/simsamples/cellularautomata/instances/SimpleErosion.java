package de.hsbo.geo.simsamples.cellularautomata.instances;

import java.util.List;

import de.hsbo.geo.simsamples.cellularautomata.Cell;
import de.hsbo.geo.simsamples.cellularautomata.ContinuousStateSet;
import de.hsbo.geo.simsamples.cellularautomata.NeighborhoodIndex;
import de.hsbo.geo.simsamples.cellularautomata.RectangularSpace;
import de.hsbo.geo.simsamples.cellularautomata.TransitionFunction;

/**
 * Very rudimentary erosion model operating on cellular elevation grids. It 
 * might be interesting to do experiment with this.
 * 
 * @author Benno Schmidt
 */
public class SimpleErosion extends TransitionFunction 
{
	double p = 0.2;
	
	@Override
	public void defineStates() {
		this.states = new ContinuousStateSet(); 
	}

	@Override
	public void beforeStep(int ti) throws Exception 
	{
        RectangularSpace sp = 
        	(RectangularSpace) this.getAutomaton().getCellularSpace();

        for (Cell c : sp.getCells()) {
    		c.setValue(ti + 1, c.getValue(ti));        	
        }
	}
	
	@Override
	public void step(Cell c, int ti) throws Exception 
	{
		Double z = (Double)(c.getValue(ti));

		// Determine neighbor with lowest elevation:
		List<Cell> neighs = c.getNeighbors(NeighborhoodIndex.NEIGH_8());
		Double zMin = z;
		Cell cMin = c;
		for (Cell n : neighs) {
			Double zN = (Double)(n.getValue(ti));
			if (zN < zMin) {
				zMin = zN; cMin = c;
			}
		}
		
		// Then just move some mass from the current cell to this neighbor:
		double exchange = p * (z - zMin);
		cMin.setValue(ti + 1, zMin + exchange);
		c.setValue(ti + 1, z - exchange);
	}
}
