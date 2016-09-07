package de.hsbo.geo.simsamples.eventqueues.servercustomer;

import de.hsbo.geo.simsamples.eventqueues.Entity;

/**
 * Abstract base class for server entities as processed by the {@link 
 * SimpleServerCustomerSimulator}. These special entities are characterized by
 * having a duration that gives the serving time.  
 * 
 * @author Benno Schmidt
 */
abstract public class Server extends Entity
{
	public Server(String name) {
		super(name);
	}

	/**
	 * gets the duration needed to serve a request. 
	 * 
	 * @return Time value
	 */
	abstract public double getDuration();
}
