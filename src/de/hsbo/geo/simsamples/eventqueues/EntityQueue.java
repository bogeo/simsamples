package de.hsbo.geo.simsamples.eventqueues;

/**
 * Base class for entity queues as processed by the {@link 
 * EventQueueSimulator}s.
 * 
 * @author Benno Schmidt
 */
abstract public class EntityQueue extends Queue 
{
	private String symbol;

	
	/**
	 * Constructor. As a parameter, a discrete state symbol has to be given. 
	 * This {@link String} (ideally of consisting of a single character} might
	 * be used in result visualizations. 
	 * 
	 * @param symbol State name
	 */
	public EntityQueue(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}
}
