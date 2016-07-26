package de.hsbo.geo.simsamples.agents.instances;

import de.hsbo.geo.simsamples.agents.Agent;
import de.hsbo.geo.simsamples.cellularautomata.Cell;
import de.hsbo.geo.simsamples.cellularautomata.CellLocation;

/**
 * Implementation for an agent referring to a location inside a cellular space. 
 * 
 * @author Benno Schmidt
 */
public class CellAgent implements Agent 
{
	private Cell cell;

	
	public CellAgent(Cell cell) {
		this.cell = cell;
	}

	public CellLocation getLocation() {
		return this.cell.getLocation();
	}

	public Cell getCell() {
		return this.cell;
	}
	
	public void setCell(Cell cell) {
		this.cell = cell;
	}
	
	public String toString() {
		StringBuffer s = new StringBuffer();
		
		s.append("(");
		s.append(this.getClass().getSimpleName());
		s.append(": ");
		s.append(this.cell);
		s.append(")");
		
		return s.toString();
	}
}
