package de.hsbo.geo.simsamples.eventqueues;

import de.hsbo.geo.simsamples.eventqueues.servercustomer.SimpleServerCustomerSimulator;

/**
 * Base class for entities that are processed by {@link EventQueueSimulator}s. 
 * E.g., this might be customer and server entities as used by the {@link 
 * SimpleServerCustomerSimulator}.
 * 
 * @author Benno Schmidt
 */
public class Entity 
{
	private String name;
	
	public Entity(String name) { 
		this.name = name;
	}

	public String toString() { 
		return name; 
	}
}
