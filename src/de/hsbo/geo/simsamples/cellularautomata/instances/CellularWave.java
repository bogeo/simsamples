package de.hsbo.geo.simsamples.cellularautomata.instances;

import java.util.List;

import de.hsbo.geo.simsamples.cellularautomata.Cell;
import de.hsbo.geo.simsamples.cellularautomata.ContinuousStateSet;
import de.hsbo.geo.simsamples.cellularautomata.NeighborhoodIndex;
import de.hsbo.geo.simsamples.cellularautomata.RectangularCellLocation;
import de.hsbo.geo.simsamples.cellularautomata.RectangularSpace;
import de.hsbo.geo.simsamples.cellularautomata.TransitionFunction;

/**
 * Experimental transition function modeling wave spreading based on a 
 * rectangular grid. For each cell, the automaton iterates the simple formula 
 * <tt>z(t + 1) = 1/2 * (zN(t) + zE(t) + zS(t) + zW(t) - 2 * z(t))</tt> where 
 * <tt>zN</tt> gives the value of the Northern neighbor cell, <tt>zE</tt> for 
 * the Eastern neighbor etc. For edge and corner situations, the formula is
 * slightly modified, e.g. <tt>z(t + 1) = 1/2 * (2 * zE(t) + zS(t) + zW(t) 
 * - 2 * z(t))</tt> for the Western edge and <tt>z(t + 1) = 1/2 * (2 * zN(t) 
 * + 2* zE(t) - 2 * z(t))</tt> for the SW corner.
 * 
 * @author Benno Schmidt
 */
public class CellularWave extends TransitionFunction {

	@Override
	public void defineStates() {
		this.states = new ContinuousStateSet(); 
	}

	@Override
	public void step(Cell c, int ti) throws Exception 
	{
		RectangularSpace sp = (RectangularSpace) c.getCellularSpace();
		RectangularCellLocation loc = (RectangularCellLocation) c.getLocation();
		
		Cell
			cN = sp.getNeighborCell(loc.i, loc.j, (short) 0),
			cE = sp.getNeighborCell(loc.i, loc.j, (short) 2),
			cS = sp.getNeighborCell(loc.i, loc.j, (short) 4),
			cW = sp.getNeighborCell(loc.i, loc.j, (short) 6);
		
		Double z = (Double) c.getValue(ti);

		List<Cell> neighs = c.getNeighbors(NeighborhoodIndex.NEIGH_4());
		double sum = 0.;
		int N = neighs.size(); 	
		for (Cell n : neighs) {
			double zN = (Double) n.getValue(ti);
			sum += zN;
		}

		// N should be 4, 3 or 2 now.
		
		if (N == 3) { // Vertical or horizontal edge
			double zD = 42.;
			if (cN == null) zD = (Double) cS.getValue(ti);
			if (cE == null) zD = (Double) cW.getValue(ti);
			if (cS == null) zD = (Double) cN.getValue(ti);
			if (cW == null) zD = (Double) cE.getValue(ti);
			sum += zD;
		}

		if (N == 2) { // Corner situation
			sum *= 2.;
		}
		
		double k = 0.45; // Damping factor
		c.setValue(ti + 1, k * 0.5 * (sum - 2. * z));
	}
}
