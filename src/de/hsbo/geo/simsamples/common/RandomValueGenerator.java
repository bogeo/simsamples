package de.hsbo.geo.simsamples.common;

import java.util.Set;

import de.hsbo.geo.simsamples.cellularautomata.Cell;
import de.hsbo.geo.simsamples.cellularautomata.CellImpl;
import de.hsbo.geo.simsamples.cellularautomata.RectangularCellLocation;
import de.hsbo.geo.simsamples.cellularautomata.RectangularSpace;

/**
 * Random value generation methods.
 * 
 * @author Benno Schmidt
 */
public class RandomValueGenerator 
{
	/**
	 * generates a random number in the range from <tt>min</tt> to 
	 * <tt>max</tt>. The result will be an integer value greater or equal 
	 * than <tt>min</tt> and less or equal than <tt>max</tt>.
	 * 
	 * @param min
	 * @param max
	 * @return Random number 
	 */
	static public int number(int min, int max) {
		return (int)((max - min + 1) * Math.random() + min);
	}
	
	/**
	 * chooses an element from a set of objects randomly.
	 * 
	 * @param objects
	 * @return Random object
	 */
	static public Object chooseRandomly(Set<Object> objects) 
	{
		Object[] a = objects.toArray(); // TODO: not performant, but cacheable
		return a[number(0, a.length - 1)];
	}

	/**
	 * chooses an element from an arbitrary number of objects randomly.
	 * 
	 * @param objects
	 * @return Random object
	 */
	static public Object chooseRandomly(Object... objects) 
	{
		return objects[number(0, objects.length - 1)];
	}
	
	/**
	 * generates a random location inside a rectangular cell space.
	 *  
	 * @param sp Rectangular cell space
	 * @return Location information
	 */
	static public RectangularCellLocation randomLocation(RectangularSpace sp) {
		return new RectangularCellLocation(
			(int)(Math.random() * (double) sp.numberOfRows()),
			(int)(Math.random() * (double) sp.numberOfRows()));
	}

	/**
	 * generates a random location inside a rectangular cell space.
	 *  
	 * @param sp Rectangular cell space
	 * @return {@link CellImpl} object
	 * @throws Exception 
	 */
	static public Cell randomCell(RectangularSpace sp) throws Exception {
		return sp.getCell(RandomValueGenerator.randomLocation(sp));
	}
	
	// TODO: Add Gaussian distribution 
}
