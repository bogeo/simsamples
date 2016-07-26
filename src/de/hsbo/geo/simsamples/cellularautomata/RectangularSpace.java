package de.hsbo.geo.simsamples.cellularautomata;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a rectangular cellular space.
 * 
 * @author Benno Schmidt
 */
public class RectangularSpace extends CellularSpace 
{
	private Cell[][] cells = null;
	private int nx = 0, ny = 0;
	

	/**
	 * Constructor
	 * 
	 * @param nx Number of rows of the cellular grid
	 * @param ny Number of columns of the cellular grid
	 */
	public RectangularSpace(int nx, int ny) {
		super();
		this.nx = nx;
		this.ny = ny;
		this.cells = new Cell[nx][ny];
		for (int i = 0; i < nx; i++) {
			for (int j = 0; j < ny; j++) {
				this.cells[i][j] = 
					new CellImpl(this, new RectangularCellLocation(i, j));
			}
		}
	}

	/**
	 * gets all {@link Cell} objects of this cellular space. Note that for this
	 * rectangular space the method {@link RectangularSpace#getCellArray()} is 
	 * more efficient! 
	 * 
	 * @return List of cell objects
	 */
	@Override
	public List<Cell> getCells() 
	{
		List<Cell> cells = new ArrayList<Cell>(); 
		for (int i = 0; i < this.nx; i++) {
			for (int j = 0; j < this.ny; j++) {
				Cell c = this.cells[i][j];
				if (c != null) {
					cells.add(c);
				}
			}
		}
		return cells;
	}

	/**
	 * provides direct access to all {@link Cell} objects of this rectangular 
	 * cell space. 
	 * 
	 * @return Array of cell objects
	 */
	public Cell[][] getCellArray() 
	{
		return this.cells;
	}

	/**
	 * returns the number of rows of the cellular grid.
	 */
	public int numberOfRows() {
		return this.nx;
	}
	
	/**
	 * returns the number of columns of the cellular grid.
	 */
	public int numberOfColumns() {
		return this.ny;
	}

	/**
	 * gets the {@link Cell} at a given position inside the rectangular 
	 * cellular space. Note that the assertion <tt>i >= 0 && i < this.nx() && 
	 * j > 0 && j < this.ny()</tt> must hold. Otherwise an exception will be 
	 * thrown.
	 * 
	 * @param i Row index
	 * @param j Column index
	 * @return Cell object
	 * @throws Exception
	 */
	public Cell getCell(int i, int j) throws Exception {
		if (i < 0 || i >= nx || j < 0 || j >= ny) {
			throw new Exception("Tried to access cell (" + i + ", " + j + 
				") out of array bounds (0.." + nx + ", 0.." + ny + ")!");
		}
		return this.cells[i][j];
	}

	/**
	 * gets the {@link Cell} at a given position inside the rectangular 
	 * cellular space. 
	 * 
	 * @param pos Row and column index as cell location object
	 * @return Cell object
	 * @throws Exception
	 */
	public Cell getCell(RectangularCellLocation pos) throws Exception {
		return this.getCell(pos.i, pos.j);
	}

	/**
	 * gets a {@link Cell}s neighbor cells with respect to the given 
	 * neighborhood definition. 
	 * TODO The current implementation is limited to neighborhoods consisting 
	 * of 4 or 8 neighbor cells. Moreover, cyclic neighborhoods have not been 
	 * implemented yet. However, if needed this all would be easy to implement.
	 * 
	 * @param i Row index
	 * @param j Column index
	 * @param neighDef Neighborhood definition
	 * @return List of cell objects
	 */
	public List<Cell> getNeighborCells(
		int i, int j, NeighborhoodIndex neighDef)
	{
		List<Cell> res = new ArrayList<Cell>(); 

		if (i > 0) {
			res.add(this.cells[i - 1][j]);
		}
		if (i < this.nx - 1) {
			res.add(this.cells[i + 1][j]);
		}
		if (j > 0) {
			res.add(this.cells[i][j - 1]);
		}
		if (j < this.ny - 1) {
			res.add(this.cells[i][j + 1]);
		}

		if (neighDef.getNumberNeighbors() == 8) 
		{
			if (i > 0 && j > 0) {
				res.add(this.cells[i - 1][j - 1]);
			}
			if (i > 0 && j < this.ny - 1) {
				res.add(this.cells[i - 1][j + 1]);
			}
			if (i < this.nx - 1 && j > 0) {
				res.add(this.cells[i + 1][j - 1]);
			}
			if (i < this.nx - 1 && j < this.ny - 1) {
				res.add(this.cells[i + 1][j + 1]);
			}			
		}
		
		if (res.size() <= 0)
			return null;
		return res;
	} 

	@Override
	public void dump(int ti) throws Exception 
	{
		if (this.getStateSet() instanceof DiscreteStateSet) 
		{
			if (this.cells == null) return; // TODO
			for (int i = 0; i < this.nx; i++) {
				for (int j = 0; j < this.ny; j++) {
					if (this.cells[i][j] == null) continue; // TODO
					System.out.print(this.cells[i][j].getValue(ti));						
				}
				System.out.println();
			}
			return;
		}
		
		if (this.getStateSet() instanceof ContinuousStateSet) 
		{
			ElevationModelToolBox.dump(this, ti);
		}
	}
}
