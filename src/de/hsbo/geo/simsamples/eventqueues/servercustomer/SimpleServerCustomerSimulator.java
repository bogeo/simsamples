package de.hsbo.geo.simsamples.eventqueues.servercustomer;

import de.hsbo.geo.simsamples.applications.PubExample;
import de.hsbo.geo.simsamples.eventqueues.Entity;
import de.hsbo.geo.simsamples.eventqueues.EventEntry;
import de.hsbo.geo.simsamples.eventqueues.EventQueue;
import de.hsbo.geo.simsamples.eventqueues.EventQueueSimulator;
import de.hsbo.geo.simsamples.eventqueues.ResultManager;

/**
 * Simple discrete-event simulator for systems consisting of a multiple serving 
 * and customer entities requesting service. See {@link PubExample} for an 
 * example. A customer entity can take one of the following states: WAITING, 
 * IN_SERVICE, SATISFIED. For the serving units there are the states FREE and
 * BUSY.
 * 
 * @author Benno Schmidt
 */
public class SimpleServerCustomerSimulator extends EventQueueSimulator
{
	private ServerQueue 
		freeServers, busyServers;
	private CustomerQueue 
		waitingCustomers, satisfiedCustomers, customersInService;
	
	private double 
		t, // clock
		tEnd; // used if tiMax < 0  
	private int 
		ti, // time step counter
		tiMax = -1; // > 0, if set
	private EventQueue 
		q; // holds the events to be handled
	private ResultManager 
		resRec; // records the simulation results 
	
	
	public SimpleServerCustomerSimulator(
		ServerQueue freeServers,
		ServerQueue busyServers, 
		CustomerQueue waitingCustomers,
		CustomerQueue satisfiedCustomers) 
	{
		// Queues for the server states FREE and BUSY:
		this.freeServers = freeServers; 
		this.busyServers = busyServers; 

		// Queues for the customer states WAITING, IN_SERVICE and SATISFIED:
		this.waitingCustomers = waitingCustomers;
		this.satisfiedCustomers = satisfiedCustomers;
		this.customersInService = new CustomerQueue("X"); 
	}

	/**
	 * executes the simulation for the time interval <tt>[0..tEnd]</tt>.
	 * 
	 * @param tEnd End time of simulation
	 * @throws Exception
	 */
	public void execute(double tEnd) throws Exception
	{
		this.execute(tEnd, null);
	}

	public void execute(double tEnd, EventQueue qAdd) throws Exception
	{
		tiMax = -1;
		this.tEnd = tEnd;
		this.execute(qAdd);
	}
	
	@Override
	public void execute(int numberOfSteps) throws Exception 
	{
		this.execute(numberOfSteps, null);
	}

	public void execute(int numberOfSteps, EventQueue qAdd) throws Exception
	{
		tiMax = numberOfSteps;
		this.execute(qAdd);
	}

	private void execute(EventQueue qAdd) throws Exception 
	{
		this.beforeExecute();
		this.initialize();

		if (qAdd != null) {
			while (! qAdd.isEmpty()) {
				EventEntry ee = qAdd.dequeue();
				this.q.enqueue(ee);
			}
		}
		
		boolean stillEventsToBeHandled = q.isEmpty() ? false : true;

		dumpComment("Starting simulation...");

		while (
			((tiMax >= 0 && ti <= tiMax) || (tiMax < 0 && t < tEnd)) 
			&& stillEventsToBeHandled) 
		{
			this.handleNextEvent(q);
			stillEventsToBeHandled = q.stillEventsToBeHandled();
		}

		dumpCommentP(
			"Simulation end (t = " + t + ", tEnd = " + tEnd + ").");
		dumpComment("\n" + q + "\n");
		dumpQueues(null);
		
		this.numberOfSteps = this.resRec.numberOfSteps();
		this.afterExecute();
		
		// Show result as simple Gantt chart:
		resRec.dump();
	}

	private void initialize() throws Exception 
	{
		t = 0;
		ti = 0;
		q = new EventQueue(); // holds the events to be handled
		resRec = new ResultManager(); 
		
		for (Object obj : freeServers.getAll())
			resRec.record(((Server) obj), 0, freeServers.getSymbol());
		for (Object obj : busyServers.getAll())
			resRec.record(((Server) obj), 0, busyServers.getSymbol());
		for (Object obj : waitingCustomers.getAll())
			resRec.record(((Customer) obj), 0, waitingCustomers.getSymbol());
		for (Object obj : satisfiedCustomers.getAll())
			resRec.record(((Customer) obj), 0, satisfiedCustomers.getSymbol());
		
		for (int i = waitingCustomers.getAll().size() - 1; i >= 0; i--) {
			Customer cust = (Customer) waitingCustomers.getAll().get(i);
			q.enqueue(new EventEntry(t, Event.TRY_REQUEST, cust)); 
			dumpCommentPr("Enqueued initial event...");
			ti = resRec.record(cust, t, waitingCustomers.getSymbol());
			//t += 0.001; // why not? 
		}
	}
	
	private void handleNextEvent(EventQueue q) throws Exception {
		handleNextEvent(q, 0);
	}

	private void handleNextEvent(EventQueue q, int pos) throws Exception 
	{
		dumpCommentP("handleNextEvent(pos=" + pos + "), " + q);
		
		// Determine event to be handled:
		EventEntry ee = q.dequeue(pos);
		if (ee == null) {
			throw new Exception("Unexpected null-pointer!");
		}
		dumpComment("To be handled: " + ee); 
		
		t = ee.getTime();

		dumpQueues("\nPRE:");

		switch (ee.getEventType()) {

		case TRY_REQUEST:
			if (freeServers.isEmpty()) {
				dumpCommentPr("Selected event entry will be delayed...");
				ee.delay(); // Try to handle the request later...
				q.enqueue(ee); 
				// ... and try to handle the next entry:
				handleNextEvent(q, pos + 1); // set stack size high enough!
			} 
			else { // ! freeServers.isEmpty()
				Customer cust = 
					waitingCustomers.dequeue((Customer) ee.getEntity());
				Server serv = freeServers.dequeue();
				dumpCommentPr(
					"Now \"" + serv + "\" will serve \"" + cust + "\"...");
				// State transitions : serv from FREE -> BUSY, 
				// cust from WAITING -> IN_SERVICE queue:
				busyServers.enqueue(serv); 
				customersInService.enqueue(cust); 
				ti = resRec.record(serv, t, busyServers.getSymbol());
				ti = resRec.record(cust, t, customersInService.getSymbol());
				// This service will take dt seconds...
				double dt = serv.getDuration();
				if (dt > 0.) { // see JavaDoc for Customer.getDuration()
					// ... at ti + dt the next events to be handled will occur:
					q.enqueue(
						new EventEntry(t + dt, Event.FINISHES_SERVICE, serv));
					q.enqueue(
						new EventEntry(t + dt, Event.FINISHES_SERVICE, cust));
				}
			}
			break;
			
		case FINISHES_SERVICE:
			Entity ent = ee.getEntity();
			if (ent instanceof Customer) {
				Customer cust = (Customer) ent;
				dumpCommentPr(
					"Finish service for customer \"" + cust + "\"..."); 
				customersInService.dequeue(cust); 
				satisfiedCustomers.enqueue(cust); 
				ti = resRec.record(cust, t, satisfiedCustomers.getSymbol());
				double dt = cust.getDuration();
				if (dt > 0.) { // see JavaDoc for Customer.getDuration()
					q.enqueue(
						new EventEntry(t + dt, Event.NEEDS_SERVICE, cust));
				}
			}
			if (ent instanceof Server) {
				Server serv = (Server) ent;
				dumpCommentPr("Server \"" + serv + "\" finishes service...");
				busyServers.dequeue(serv); 
				freeServers.enqueue(serv); 
				ti = resRec.record(serv, t, freeServers.getSymbol());
			}
			q.setDelayTime(ee.getTime()); // !
			break;
		
		case NEEDS_SERVICE:
			Customer cust = 
				satisfiedCustomers.dequeue((Customer) ee.getEntity());
			dumpCommentPr("Now \"" + cust + "\" requests service...");
			// State transition: cust from SATISFIED -> WAITING queue: 
			waitingCustomers.enqueue(cust);
			ti = resRec.record(cust, t, waitingCustomers.getSymbol());
			q.enqueue(
				new EventEntry(t + 0.01, Event.TRY_REQUEST, cust));
			break;
		}
	
		dumpQueues("\nPOST:");
	}
	
	private void dumpQueues(String prolog) 
	{
		if (!consoleDump) return;

		if (prolog != null) 
			System.out.println(prolog);
	
		System.out.println("waitingCustomers:" + waitingCustomers);
		System.out.println("freeServers:" + freeServers);
		System.out.println("customersInService:" + customersInService);
		System.out.println("satisfiedCustomers:" + satisfiedCustomers);
		System.out.println("busyServers:" + busyServers);
	}
	
	private void dumpComment(String str) {
		dumpComment(str, "");
	}

	private void dumpCommentP(String str) {
		dumpComment(str, "\n---\n");
	}

	private void dumpCommentPr(String str) {
		dumpComment(str, "\n>> ");
	}

	private void dumpComment(String str, String pre) {
		if (consoleDump) 
			System.out.println(pre + str);
	}
}
