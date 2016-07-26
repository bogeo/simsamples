package de.hsbo.geo.simsamples.applications;

import de.hsbo.geo.simsamples.cellularautomata.CellularAutomaton;
import de.hsbo.geo.simsamples.cellularautomata.RectangularAutomaton;
import de.hsbo.geo.simsamples.cellularautomata.instances.GameOfLife;

/**
 * "Game of Life" example. The automaton's transition function is implemented 
 * by the class {@link GameOfLife}.
 * 
 * @author Benno Schmidt
 */
public class GameOfLifeExample 
{
	public static void main(String[] args) throws Exception 
	{
		// Create automaton consisting of 10x50 cells:
		CellularAutomaton a = 
			new RectangularAutomaton(10, 50, new GameOfLife());
		a.initializeRandomly();
		
		// Execute 200 time steps and provide console output:
		a.enableConsoleDump();
		a.execute(200);
	}
}
