package de.hsbo.geo.simsamples.applications;

import de.hsbo.geo.simsamples.cellularautomata.Cell;
import de.hsbo.geo.simsamples.cellularautomata.CellularAutomaton;
import de.hsbo.geo.simsamples.cellularautomata.RectangularAutomaton;
import de.hsbo.geo.simsamples.cellularautomata.instances.StruggleGame;

/**
 * Sample application which runs the {@link StruggleGame}. Since the initial
 * populations are distributed randomly, it is interesting to observe that the 
 * simulation result will vary for each run!  
 * 
 * @author Benno Schmidt
 */
public class StruggleExample 
{
	static private CellularAutomaton a; 
	static private final int numberOfSteps = 2000; 
	
	
	static public void main(String[] args) throws Exception 
	{
		a = new RectangularAutomaton(8, 8, new StruggleGame());
		a.initializeRandomly();

		// Print initial situation:
		a.getCellularSpace().dump(0);
		System.out.println();
		
		a.setConsoleDump(false); // Set this to 'true' if you want to see more.
		a.execute(numberOfSteps);

		// Print final situation:
		a.getCellularSpace().dump(numberOfSteps);

		dumpStatistics();
	}

	static private void dumpStatistics() throws Exception 
	{
		System.out.println("\nti\tRabbits\tFoxes\tGrass");

		for (int ti = 0; ti < numberOfSteps; ti++) 
		{ 
			int rabbits = 0, foxes = 0, grass = 0;
			for (Cell c : a.getCellularSpace().getCells()) {
				String val = (String) c.getValue(ti);
				
				if (val.equalsIgnoreCase("R")) rabbits++;
				if (val.equalsIgnoreCase("F")) foxes++;
				if (val.equalsIgnoreCase(".")) grass++;
			}
			if (ti % 10 == 0) { 
				System.out.println(ti + "\t" + 
					rabbits + "\t" + foxes + "\t" + grass);
			}
		}
	}
}
