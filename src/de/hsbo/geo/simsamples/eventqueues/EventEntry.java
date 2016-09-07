package de.hsbo.geo.simsamples.eventqueues;

import de.hsbo.geo.simsamples.eventqueues.servercustomer.Event;

/**
 * {@link EventQueue} entry consisting of an event invoked by an {@link Entity}
 * at simulation-time <tt>t</tt>.
 * 
 * @author Benno Schmidt
 */
public class EventEntry 
{
	private double t;
	private Event eventType;
	private Entity ent; 
	private boolean delayed = false;
	
	
	/**
	 * Constructor.
	 * 
	 * @param t Simulation time
	 * @param eventType Event
	 * @param ent Invoking entity
	 */
	public EventEntry(double t, Event eventType, Entity ent) {
		this.t = t;
		this.eventType = eventType;
		this.ent = ent;
	}

	public double getTime() {
		return this.t;
	}

	public void setTime(double t) {
		this.t = t;
	}

	public Event getEventType() {
		return eventType;
	}

	/**
	 * gets the {@link Entity} invoking the event.
	 * 
	 * @return Entity object 
	 */
	public Entity getEntity() {
		return ent;
	}
	
	/**
	 * marks an event as delayed. <i>Delaying</i> means that the event will not
	 * be processed at the time when it originally has been generated ("arrival 
	 * time").Typically this occurs when no event-processing units are 
	 * available. Note that this method call has no further consequences, 
	 * opposite to {@link EventQueue#setDelayTime(double)}.
	 */
	public void delay() {
		this.delayed = true;
		// TODO Should the original event-time be preserved for later usage?
	}

	public String toString() {
		StringBuffer s = new StringBuffer();
		
		s.append("(");
		s.append(this.getClass().getSimpleName());
		s.append(": (t=");
		s.append(t);
		s.append(", ");
		s.append(eventType);
		s.append(", ");
		s.append(ent);
		if (this.delayed) s.append(", delayed");
		s.append("))");

		return s.toString();
	}
}
