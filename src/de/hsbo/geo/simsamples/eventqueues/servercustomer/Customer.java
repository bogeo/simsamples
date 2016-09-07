package de.hsbo.geo.simsamples.eventqueues.servercustomer;

import de.hsbo.geo.simsamples.eventqueues.Entity;

/**
 * Abstract base class for customer entities as processed by the {@link 
 * SimpleServerCustomerSimulator}. These special entities are characterized by
 * having a duration that gives the time until the next customer request takes
 * place.
 * 
 * @author Benno Schmidt
 */
abstract public class Customer extends Entity
{
	public Customer(String name) {
		super(name);
	}

	/**
	 * gets the duration until the next request event will occur. You may 
	 * override this method in your individual implementations. By default,
	 * the duration value is set to a negative value (-1); this means that
	 * no follow-up event will be generated.
	 * 
	 * @return Time value
	 */
	public double getDuration() { 
		return -1.; 
	}
}
