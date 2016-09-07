package de.hsbo.geo.simsamples.eventqueues;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract queue implementation ("first in, first out" structure).
 * 
 * @author Benno Schmidt
 */
abstract public class Queue 
{
	protected List<Object> q = new ArrayList<Object>();
	
	
	/**
	 * returns <i>true</i> for empty queues, else <i>false</i>.
	 */
	public boolean isEmpty() {
		return q.size() <= 0 ? true : false;
	}

	/**
	 * gets a list of all objects inside the queue. Note that the result list
	 * contains the original objects.
	 * 
	 * @return Object list
	 */
	public List<Object> getAll() {
		return q;
	}
	
	/**
	 * dequeues an object. Note that this will be the object that has been 
	 * enqueued first ("first in, first out" mechanism). The result will be 
	 * <i>null</i> for empty queues.
	 * 
	 * @return Object which has been removed from the queue 
	 */
	public Object dequeue() {
		if (q.size() <= 0) return null;
		
		Object last = q.get(q.size() - 1);
		q.remove(q.size() - 1);
		return last;
	}
	
	public String toString() {
		StringBuffer s = new StringBuffer();
		
		s.append("(");
		s.append(this.getClass().getSimpleName());
		s.append(": [");
		for (Object elem : this.q) {
			s.append("\n  ");
			s.append(elem);
		}
		s.append("])");

		return s.toString();
	}
}
