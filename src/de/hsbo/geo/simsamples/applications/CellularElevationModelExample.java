package de.hsbo.geo.simsamples.applications;

import de.hsbo.geo.simsamples.cellularautomata.CellularAutomaton;
import de.hsbo.geo.simsamples.cellularautomata.ElevationModelToolBox;
import de.hsbo.geo.simsamples.cellularautomata.RectangularSpace;
import de.hsbo.geo.simsamples.cellularautomata.TransitionFunction;
import de.hsbo.geo.simsamples.cellularautomata.instances.SimpleErosion;

/**
 * Example of a Cellular Automaton where each cell value corresponds to an 
 * elevation value. Transition functions that are suitable to process elevation
 * data are implemented in {@link ElevationModelToolBox}.
 * 
 * @author Benno Schmidt
 */
public class CellularElevationModelExample 
{
	static private CellularAutomaton a; 

	public static void main(String[] args) throws Exception {
		new CellularElevationModelExample().run();
	}
	
	public void run() throws Exception
	{
		ElevationModelToolBox tb = new ElevationModelToolBox();
		
		TransitionFunction delta = new SimpleErosion();

		// Read digital elevation model from file and create corresponding
		// rectangular automaton:
		a = tb.createAutomaton("./data/sampleDEM.asc", delta); 

		// Execute 40 time steps:
		a.disableConsoleDump();
		a.execute(40);

		// Finally, show the spaces's development:
		RectangularSpace sp = (RectangularSpace) a.getCellularSpace();
		sp.dump(0); 
		tb.writeX3DomScene(sp, 0, "./data/sampleDEM_0.html");
		sp.dump(20); 
		tb.writeX3DomScene(sp, 20, "./data/sampleDEM_20.html");
		sp.dump(40); 
		tb.writeX3DomScene(sp, 40, "./data/sampleDEM_40.html");
	}	
}
