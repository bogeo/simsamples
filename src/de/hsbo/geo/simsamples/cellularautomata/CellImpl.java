package de.hsbo.geo.simsamples.cellularautomata;

import java.util.List;

/**
 * Cell definitions implementation.
 * 
 * @author Benno Schmidt
 */
public class CellImpl extends Cell 
{
	/**
	 * Constructor. As parameters, the definition of the {@link CellularSpace} 
	 * the cell is embedded in and information about the cell's location inside
	 * this space have to be given.
	 * 
	 * @param sp Cellular space definition
	 * @param loc Location information
	 */
	public CellImpl(CellularSpace sp, CellLocation loc) {
		this.sp = sp;
		this.loc = loc;
	}

	/**
	 * gets a {@link Cell}s neighbor cells with respect to the given 
	 * neighborhood definition. 
	 * TODO The current implementation is limited to neighborhoods consisting 
	 * of 4 or 8 neighbor cells. Moreover, cyclic neighborhoods have not been 
	 * implemented yet. However, if needed this all would be easy to implement.
	 * 
	 * @param neighDef Neighborhood definition
	 * @return List of cell objects
	 * @throws Exception
	 */
	@Override
	public List<Cell> getNeighbors(NeighborhoodIndex neighDef) throws Exception
	{
		if (this.loc instanceof RectangularCellLocation) {
			RectangularCellLocation rLoc = (RectangularCellLocation) this.loc;
			if (!(this.sp instanceof RectangularSpace)) {
				throw new Exception("Inconsistent space definition!");
			}
			RectangularSpace rSpace = (RectangularSpace) this.sp;
			int i = rLoc.i, j = rLoc.j;
			return rSpace.getNeighborCells(i, j, neighDef);
		}

		// TODO: Log warning
		System.out.println(
			"Warning: Could not query neighbors for space " + this.loc); 
		return null; // TODO		
	}
}
