package de.hsbo.geo.simsamples.applications;

import de.hsbo.geo.simsamples.cellularautomata.CellularAutomaton;
import de.hsbo.geo.simsamples.cellularautomata.ElevationModelToolBox;
import de.hsbo.geo.simsamples.cellularautomata.RectangularAutomaton;
import de.hsbo.geo.simsamples.cellularautomata.RectangularSpace;
import de.hsbo.geo.simsamples.cellularautomata.instances.CellularWave;

/**
 * Example of a Cellular Automaton which models a simple wave spreading 
 * process. The application generates a 3D scene showing animated quads. For  
 * a model description see {@link CellularWave}. 
 * 
 * @author Benno Schmidt
 */
public class SimpleWaveApplication 
{
	public static void main(String[] args) throws Exception {
		new SimpleWaveApplication().run();
	}
	
	public void run() throws Exception
	{
		// Create automaton consisting of 10x10 cells:
		CellularAutomaton a = 
			new RectangularAutomaton(10, 10, new CellularWave());
		a.initializeWith(new Double(0.));
		RectangularSpace sp = (RectangularSpace) a.getCellularSpace();
		sp.getCell(5, 5).setInitialValue(10.);
		
		// Execute 10 time steps and provide console output:
		a.enableConsoleDump();  
		a.execute(10);

		// Finally, show the spaces's development in 3D scene:
		ElevationModelToolBox tb = new ElevationModelToolBox();
		tb.writeAnimatedX3DomQuadScene(sp, "./data/cellularwave.html");
	}	
}
