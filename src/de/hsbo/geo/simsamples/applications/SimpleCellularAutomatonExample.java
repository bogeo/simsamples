package de.hsbo.geo.simsamples.applications;

import java.util.List;

import de.hsbo.geo.simsamples.cellularautomata.Cell;
import de.hsbo.geo.simsamples.cellularautomata.RectangularAutomaton;
import de.hsbo.geo.simsamples.cellularautomata.TransitionFunction;
import de.hsbo.geo.simsamples.cellularautomata.instances.SimpleEpidemy;

/**
 * Very simple example of a Cellular Automaton. The automaton's transition 
 * function is defined by the class {@link SimpleEpidemy}.
 * 
 * @author Benno Schmidt
 */
public class SimpleCellularAutomatonExample 
{
	public static void main(String[] args) throws Exception 
	{
		new SimpleCellularAutomatonExample().run();
	}
	
	public void run() throws Exception
	{
		TransitionFunction delta = new SimpleEpidemy();

		// Create rectangular automaton consisting of 5 rows and 50 columns...
		RectangularAutomaton a = new RectangularAutomaton(5, 50, delta);
		System.out.println(a); 
		// and populate cells with " " and "." values randomly: 
		a.initializeRandomly(" ", ".");
		
		// Execute 10 time steps and provide console output:
		a.enableConsoleDump();
		a.execute(10);

		// Finally, show the cell's development:
		this.dumpTimeSeries(a.getCellularSpace().getCells());
	}
	
	private void dumpTimeSeries(List<Cell> cells) throws Exception 
	{
		System.out.println("\nCell values over time:");
		for (Cell c : cells) {
			for (int ti = 0; ti < 10; ti++) {
				System.out.print(c.getValue(ti) + "");
			}
			System.out.println();
		}
	}
}
