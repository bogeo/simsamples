package de.hsbo.geo.simsamples.eventqueues;

import de.hsbo.geo.simsamples.eventqueues.servercustomer.Customer;
import de.hsbo.geo.simsamples.eventqueues.servercustomer.Event;

/**
 * Base class for event queues as processed by the {@link 
 * EventQueueSimulator}s.
 * 
 * @author Benno Schmidt
 */
public class EventQueue extends Queue
{
	/**
	 * enqueues an arbitrary number of {@link EventEntry} objects.
	 * 
	 * @param ee Event entry object
	 */
	public void enqueue(EventEntry... ee) 
	{
		for (EventEntry entry : ee) {
			// Find index of insertion position:
			int pos = 0;
			if (q != null) {
				int i = q.size() - 1;
				pos = i + 1;
				
				while (i >= 0) {
					Object obj = q.get(i);
					if (obj instanceof EventEntry) {
						if (entry.getTime() <= ((EventEntry) obj).getTime()) 
							break;
					}
					pos = i;
					i--;
				}
			}
			// Insert item:
			q.add(pos, entry);
		}
	}

	/**
	 * @deprecated
	 * enqueues an {@link Event} triggered by a {@link Customer} or [@link
	 * Server} entity at simulation time <tt>t</tt>.
	 * 
	 * @param t Simulation time
	 * @param eventType Event
	 * @param ent Customer or Server object
	 */
	public void enqueue(double t, Event eventType, Entity ent) {
		EventEntry ee = new EventEntry(t, eventType, ent);
		this.enqueue(ee);
	}

	/**
	 * returns <i>false</i> for empty queues, else <i>true</i> what means that
	 * the queue still contains events that have to be handled.
	 */
	public boolean stillEventsToBeHandled() {
		return !(q.isEmpty());
	}

	/**
	 * dequeues an {@link EventEntry}. Note that this will be the event entry
	 * that has been enqueued first ("first in, first out" mechanism). The 
	 * result will be <i>null</i> for empty queues.
	 * 
	 * @return Event entry which has been removed from the queue 
	 */
	public EventEntry dequeue() {
		Object obj = super.dequeue();
		return (obj instanceof EventEntry) ? (EventEntry) obj : null;
	}

	/**
	 * dequeues the {@link EventEntry} object at a given queue position. The 
	 * method will return <i>null</i>, if the given object is not present.
	 * 
	 * @param pos Queue position
	 * @return dequeued event entry object
	 */
	public EventEntry dequeue(int pos) {
		if (q.size() - 1 - pos >= 0) {
			EventEntry ee = (EventEntry) q.get(q.size() - 1 - pos);
			q.remove(q.size() - 1 - pos);
			return ee;
		}
		else {
			return null;
		}
	}

	/**
	 * delays all queued events referring to a simulation time less than 
	 * <tt>t</tt>. See documentation for {@link EventEntry#delay()}. 
	 * 
	 * @param t Simulation time
	 */
	public void setDelayTime(double t) {
		for (Object item : this.getAll()) {
			if (item instanceof EventEntry) {
				EventEntry ee = (EventEntry) item;
				if (ee.getTime() < t) {
					ee.setTime(t);
				}
			}
		}
	}
}
