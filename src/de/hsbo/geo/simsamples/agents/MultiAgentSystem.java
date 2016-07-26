package de.hsbo.geo.simsamples.agents;

import java.util.List;

import de.hsbo.geo.simsamples.common.Simulator;

/**
 * Base class for multi-agent based methods. Inside this framework, currently
 * the {@link MultiAgentSystem} model supports the implementation of systems 
 * which consist of one or more {@link Agent}s which operate on {@link 
 * de.hsbo.geo.simsamples.cellularautomata.CellularSpace}s.
 * TODO: Realize some interesting implementations
 *
 * @author Benno Schmidt
 */
abstract public class MultiAgentSystem extends Simulator
{
	protected List<Agent> agents = null;
	
	
	/**
	 * assigns a list of {@link Agent}s to this multi-agent system. 
	 * 
	 * @param agents Agents to be assigned
	 */
	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}

	/**
	 * executes the system for a single time step. 
	 *  
	 * @param ti Time stamp 
	 * @throws Exception
	 */
	abstract public void step(int ti) throws Exception;
	
	@Override
	public void execute(int numberOfSteps) throws Exception 
	{
		consoleDump();

		for (int ti = 1; ti < numberOfSteps; ti++) {
			this.step(ti);
			
			consoleDump();
		}
	}

	private void consoleDump() {
		if (this.getConsoleDump()) {
			for (Agent agent : this.agents) {
				System.out.println(agent);
			}
			System.out.println();
		}
	}
}
