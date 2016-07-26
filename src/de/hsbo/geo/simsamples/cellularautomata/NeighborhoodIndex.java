package de.hsbo.geo.simsamples.cellularautomata;

/**
 * Neighborhood index definition.
 * TODO: Implementation might be extended in the future.
 * 
 * @author Benno Schmidt
 */
public class NeighborhoodIndex 
{
	private short numberNeighbors = 4;
	private int distance = 1;
	private boolean cyclic = false; 
	

	/**
	 * Constructor
	 * 
	 * @param numberNeighbors Number of neighbors, e.g. 4 or 8 
	 * @param distance Neighborhood extent, e.g. 1
	 * @param cyclic <i>true</i> for cyclic cell indices, else <i>false</i>
	 */
	public NeighborhoodIndex(
		int numberNeighbors, int distance, boolean cyclic) 
	{
		this.numberNeighbors = (short) numberNeighbors;
		this.distance = distance;
		this.cyclic = cyclic;
	}

	/**
	 * defines a Moore neighborhood, i.e. 8 neighbor cells (for rectangular
	 * cell spaces).
	 * 
	 * @return Neighborhood index object
	 */
	public static NeighborhoodIndex MOORE() {
		return new NeighborhoodIndex(8, 1, false);
	}

	/**
	 * @see NeighborhoodIndex#MOORE()
	 */
	public static NeighborhoodIndex NEIGH_8() {
		return NeighborhoodIndex.MOORE();
	}

	/**
	 * defines a Von Neumann neighborhood, i.e. 4 neighbor cells (for 
	 * rectangular cell spaces.
	 * 
	 * @return Neighborhood index object
	 */
	public static NeighborhoodIndex VON_NEUMANN() {
		return new NeighborhoodIndex(4, 1, false);
	}

	/**
	 * @see NeighborhoodIndex#VON_NEUMANN()
	 */
	public static NeighborhoodIndex NEIGH_4() {
		return NeighborhoodIndex.VON_NEUMANN();
	}

	/**
	 * defines an extended Von Neumann neighborhood, i.e. 4 neighbor cells 
	 * and additionally the 4 closest neighbor cells with a distance of 2 in 
	 * horizontal and vertical direction (for rectangular cell spaces).
	 * 
	 * @return Neighborhood index object
	 */
	public static NeighborhoodIndex VON_NEUMANN_EXTENDED() {
		return new NeighborhoodIndex(4, 2, false);
	}

	public static NeighborhoodIndex HEXAGONAL() {
		return new NeighborhoodIndex(6, 1, false);
	}

	public short getNumberNeighbors() {
		return numberNeighbors;
	}

	public void setNumberNeighbors(short numberNeighbors) {
		this.numberNeighbors = numberNeighbors;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public boolean getCyclic() {
		return this.cyclic;
	}

	public void setCyclic(boolean cyclic) {
		this.cyclic = cyclic;
	}
		
	public String toString() 
	{
		StringBuffer s = new StringBuffer();
	
		s.append("(");
		s.append(this.getClass().getSimpleName());

		s.append(" " + this.numberNeighbors);
		s.append(" " + this.distance);
		s.append(" " + this.cyclic);
		s.append(")");
		
		return s.toString();
	}
}
