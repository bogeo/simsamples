package de.hsbo.geo.simsamples.agents.instances;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hsbo.geo.simsamples.agents.Agent;
import de.hsbo.geo.simsamples.agents.MultiAgentSystem;
import de.hsbo.geo.simsamples.cellularautomata.Cell;
import de.hsbo.geo.simsamples.cellularautomata.CellularSpace;
import de.hsbo.geo.simsamples.cellularautomata.NeighborhoodIndex;
import de.hsbo.geo.simsamples.cellularautomata.RectangularCellLocation;
import de.hsbo.geo.simsamples.cellularautomata.RectangularSpace;
import de.hsbo.geo.simsamples.common.RandomValueGenerator;

/**
 * Evolutionary algorithm that searches for the optimal value inside a 
 * rectangular cellular space. The simple algorithm works as follows: With 
 * a probability of 0.75, the agent will move to a neighbor cell (Moore 
 * neighborhood {@link NeighborhoodIndex#NEIGH_8()}). With a probability of
 * <tt>r/N</tt>, where <tt>r</tt> = rank with respect to the result value, 
 * <tt>N</tt> = number of agents, the agent will be moved to a completely 
 * different location.
 * TODO: The implementation could be more generic by operating on 
 * {@link CellularSpace}s.
 * 
 * @author Benno Schmidt
 */
public class EvolutionaryOptimumFinder extends MultiAgentSystem 
{
	private RectangularSpace sp;
	private boolean findMax = true; 
	private Object best;
	
	
	public EvolutionaryOptimumFinder(RectangularSpace sp) {
		this.sp = sp;
		this.best = null;
	}

	@Override
	public void step(int ti) throws Exception 
	{
		// Evolutionary algorithm:
		List<AgentValPair> rankedAgents = this.rankAgents();
		this.mutateAgents(rankedAgents);
		
		for (Agent agent : this.agents) {
			Object val = this.getCellValue(agent);
			this.checkIfBestValue(val);
		}

		if (this.consoleDump) {
			System.out.println("Best value (until now) = " + this.best); 
			this.agents = this.provideAgentList(rankedAgents);
		}
	}
	
	private Object getCellValue(Agent agent) throws Exception {
		if (agent instanceof CellAgent) {
			CellAgent someone = (CellAgent) agent;
			return someone.getCell().getInitialValue();
		}
		// else:
		// TODO: Not yet implemented; log warning
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private void checkIfBestValue(Object val) {
		if (val instanceof Comparable) {
			Comparable<Object> x = (Comparable<Object>) val;
			if (best == null) {
				best = x;
				return;
			}
			if (this.findMax) {
				if (x.compareTo(best) > 0) best = val;
			}
			else {
				if (x.compareTo(best) < 0) best = val;
			}
			return;
		}
		// else:
		// TODO: Log error
		return;
	}
	
	private List<AgentValPair> rankAgents() throws Exception {
		List<AgentValPair> pl = new ArrayList<AgentValPair>();
		for (Agent agent : this.agents) {
			Object val = this.getCellValue(agent);
			pl.add(new AgentValPair(agent, val));
		}
		Collections.sort(pl);
		if (this.findMax) {
			Collections.reverse(pl); 
		}
		return pl;
	}
	
	private class AgentValPair implements Comparable<Object> {
		protected Agent agent;
		protected Object val;
		
		protected AgentValPair(Agent agent, Object val) {
			super();
			this.agent = agent;
			this.val = val;
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public int compareTo(Object t) {
			if (t instanceof AgentValPair)
				return ((Comparable) this.val).compareTo(((AgentValPair) t).val);
			else
				return 0; // TODO: Log warning
		} 
	}

	private List<Agent> provideAgentList(List<AgentValPair> rankedAgents) 
	{
		List<Agent> newList = new ArrayList<Agent>(); 
		for (AgentValPair p : rankedAgents) {
			newList.add(p.agent);
		}
		return newList;
	}

	private void mutateAgents(List<AgentValPair> rankedAgents) throws Exception 
	{
		boolean letAgentDie;
		
		for (int r = 0; r < rankedAgents.size(); r++) {
			Agent agent = rankedAgents.get(r).agent;
			Cell c = ((CellAgent) agent).getCell();
			RectangularCellLocation loc = (RectangularCellLocation) c.getLocation();
			int iNew = loc.i, jNew = loc.j;

			// Move agent to a completely different location with a probability
			// of r/N, where r = rank, N = rankedAgents.size():
			float p = 1.f - 0.5f * ((float)r) / ((float)rankedAgents.size());
			letAgentDie = Math.random() > p;
			if (letAgentDie) {
				if (this.consoleDump) 
					System.out.println("Agent " + agent + " dies...");
				RectangularCellLocation locNew = RandomValueGenerator
					.randomLocation(sp);
				iNew = locNew.i; jNew = locNew.j;
			}
			else {
				// Mutate location:
				if (Math.random() > 0.5) 
					iNew = (iNew + 1) % sp.numberOfRows(); 
				if (Math.random() > 0.5) 
					iNew = (iNew - 1) % sp.numberOfRows();
				if (Math.random() > 0.5) 
					jNew = (jNew + 1) % sp.numberOfColumns();
				if (Math.random() > 0.5) 
					jNew = (jNew - 1) % sp.numberOfColumns();
				if (iNew < 0) iNew += sp.numberOfRows() - 1;
				if (jNew < 0) jNew += sp.numberOfColumns() - 1;
				// Here, with a probability of 12/16 = 0.75, the agent will 
				// move to a neighbor cell (Moore neighborhood NEIGH_8).
			}
			
			Cell cellNew = sp.getCell(iNew, jNew);
			((CellAgent) agent).setCell(cellNew);
		}
	}
	
	/**
	 * enables searching for minimum value. By default, the algorithm will 
	 * search for a maximum value. 
	 */
	public void setMinMode() {
		this.findMax = false;
	}
	
	/**
	 * returns the best value found yet.
	 * 
	 * @return Best cell value
	 */
	public Object getBestValue() {
		return this.best;
	}
}
