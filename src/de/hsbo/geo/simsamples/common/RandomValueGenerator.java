package de.hsbo.geo.simsamples.common;

import java.util.Random;
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
	static private Random rand = new Random();
	
	
	/**
	 * returns a random integer number in the range from <tt>min..max</tt>. The
	 * result will be an integer value greater or equal than <tt>min</tt> and 
	 * less or equal than <tt>max</tt>. A uniform distribution is assumed, i.e.
	 * the probability of selecting a number is constant for all numbers. 
	 * 
	 * @return Random number 
	 */
	static public int number(int min, int max) {
		return (int)((max - min + 1) * Math.random() + min);
	}

	/**
	 * returns a random double number in the range from <tt>min..max</tt>. The
	 * result will be a floating-point value greater or equal than <tt>min</tt> 
	 * and less than <tt>max</tt>. A uniform distribution is assumed. 
	 * 
	 * @return Random number 
	 */
	static public double number(double min, double max) {
		return (max - min) * Math.random() + min;
	}

	/**
	 * returns a random double number in the range from <tt>min..max</tt>. A 
	 * Gaussian distribution (aka standard normal distribution) characterized
	 * by the parameters <tt>mean</tt> and <tt>sDeviation</tt> (scale aka
	 * standard deviation "sigma", square-root of the variance) is assumed. 
	 * The result will be a floating-point value greater or equal than the 
	 * lower cut-off value <tt>min</tt> and less or equal than the upper 
	 * cut-off value <tt>max</tt>. 
	 * 
	 * @param min Lower cut-off value
	 * @param max Upper cut-off limit
	 * @param mean Mean
	 * @param sDeviation Standard deviation
	 * @return Random number 
	 */
	static public double number(
		double min, double max, double mean, double sDeviation) 
	{
		boolean insideInterval = false;
		double val; 
		do {
			val = sDeviation * rand.nextGaussian() + mean;
			if (val >= min && val <= max) 
				insideInterval = true;
		} while (!insideInterval);
		return val;
	}

	/**
	 * chooses an element from a set of objects randomly. A uniform 
	 * distribution is assumed, i.e. the probability of selecting an object 
	 * is constant for all objects.
	 * 
	 * @return Random object
	 */
	static public Object chooseRandomly(Set<Object> objects) 
	{
		Object[] a = objects.toArray(); // TODO: not performant, but cacheable
		return a[number(0, a.length - 1)];
	}

	/**
	 * chooses an element from an arbitrary number of objects randomly. A 
	 * uniform distribution is assumed, i.e. the probability of selecting an 
	 * object is constant for all objects.
	 * 
	 * @return Random object
	 */
	static public Object chooseRandomly(Object... objects) 
	{
		return objects[number(0, objects.length - 1)];
	}
	
	/**
	 * generates a random location inside a rectangular cell space. A uniform
	 * distribution is assumed, i.e. the probability of selecting a location 
	 * is constant for all cell space elements.
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
	 * generates a random location inside a rectangular cell space. A uniform
	 * distribution is assumed, i.e. the probability of selecting a cell 
	 * is constant for all cells.
	 *  
	 * @param sp Rectangular cell space
	 * @return {@link CellImpl} object
	 * @throws Exception 
	 */
	static public Cell randomCell(RectangularSpace sp) throws Exception {
		return sp.getCell(RandomValueGenerator.randomLocation(sp));
	}
}
