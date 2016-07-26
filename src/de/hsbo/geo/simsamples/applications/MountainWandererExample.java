package de.hsbo.geo.simsamples.applications;

import java.util.ArrayList;
import java.util.List;

import de.hsbo.geo.simsamples.agents.Agent;
import de.hsbo.geo.simsamples.agents.MultiAgentSystem;
import de.hsbo.geo.simsamples.agents.instances.CellAgent;
import de.hsbo.geo.simsamples.agents.instances.EvolutionaryOptimumFinder;
import de.hsbo.geo.simsamples.cellularautomata.ElevationModelToolBox;
import de.hsbo.geo.simsamples.cellularautomata.RectangularSpace;
import de.hsbo.geo.simsamples.common.RandomValueGenerator;

/**
 * Example of a population {@link Agent}s searching for the highest point
 * in a cellular elevation grid.
 * 
 * @author Benno Schmidt
 */
public class MountainWandererExample 
{
	int numberOfAgents = 5;
	
	
	public static void main(String[] args) throws Exception {
		new MountainWandererExample().run();
	}
	
	public void run() throws Exception
	{
		// Read digital elevation model from file and create corresponding
		// rectangular cell space:
		RectangularSpace sp = new ElevationModelToolBox()
			.createCellSpace("./data/sampleDEM.asc");

		// Create agents at random locations:
		List<Agent> agents = new ArrayList<Agent>();
		for (int i = 0; i < numberOfAgents; i++) {
			agents.add(new CellAgent(RandomValueGenerator.randomCell(sp)));
		}

		// Set up multi-agent system:
		MultiAgentSystem m = new EvolutionaryOptimumFinder(sp);
		m.setAgents(agents);

		// Execute 100 time steps and provide console output:
		m.enableConsoleDump();
		m.execute(100);

		// Finally, show the best value known to the population:
		System.out.println("Best: " + ((EvolutionaryOptimumFinder) m).getBestValue());
	}	
}
