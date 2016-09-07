package de.hsbo.geo.simsamples.eventqueues.servercustomer;

import de.hsbo.geo.simsamples.eventqueues.EntityQueue;

/**
 * Queue holding {@link Customer} entities.  
 * 
 * @author Benno Schmidt
 */
public class CustomerQueue extends EntityQueue
{
	public CustomerQueue(String name) {
		super(name);
	}

	/**
	 * enqueues a variable number of {@link Customer} objects.
	 * 
	 * @param customers Customer objects
	 */
	public void enqueue(Customer... customers) {
		for (Customer cust : customers) q.add(0, cust);
	}

	/**
	 * dequeues a {@link Customer} object. Note that this will be the customer
	 * object that has been enqueued first ("first in, first out" mechanism).
	 * The result will be <i>null</i> for empty queues.
	 * 
	 * @return Customer object which has been removed from the queue 
	 */
	public Customer dequeue() {
		Object obj = super.dequeue();
		return (obj instanceof Customer) ? (Customer) obj : null;
	}

	/**
	 * dequeues a given {@link Customer} object. Note that this customer object
	 * might be at an arbitrary position inside the queue. The result object
	 * is identical to the input object <tt>customer</tt>, i.e. the method will  
	 * not return <i>null</i>, if the given object is not present.
	 * 
	 * @param customer Customer object to be dequeued 
	 * @return identical to input server object
	 */
	public Customer dequeue(Customer customer) {
		q.remove(customer);
		return customer;
	}
}
