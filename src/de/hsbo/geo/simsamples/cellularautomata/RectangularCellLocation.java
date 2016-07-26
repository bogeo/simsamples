package de.hsbo.geo.simsamples.cellularautomata;

/**
 * Cell location inside rectangular cellular spaces, e.g. a {@link 
 * RectangularSpace}.
 * 
 * @author Benno Schmidt
 */
public class RectangularCellLocation implements CellLocation 
{
	/**
	 * Constructor
	 * 
	 * @param i Row index
	 * @param j Column index
	 */
	public RectangularCellLocation(int i, int j) {
		this.i = i;
		this.j = j;
	}

	/**
	 * Row index
	 */
	public int i;

	/**
	 * Column index
	 */
	public int j;

	public String toString() 
	{	
		StringBuffer s = new StringBuffer();
		
		s.append("(");
		s.append(this.getClass().getSimpleName());
		s.append(": ");
		s.append(i);
		s.append(",");
		s.append(j);
		s.append(")");
	
		return s.toString();
	}
}
