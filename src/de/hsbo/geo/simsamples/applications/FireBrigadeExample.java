package de.hsbo.geo.simsamples.applications;

import de.hsbo.geo.simsamples.common.OutputFormat;
import de.hsbo.geo.simsamples.eventqueues.EventEntry;
import de.hsbo.geo.simsamples.eventqueues.EventQueue;
import de.hsbo.geo.simsamples.eventqueues.servercustomer.Customer;
import de.hsbo.geo.simsamples.eventqueues.servercustomer.CustomerQueue;
import de.hsbo.geo.simsamples.eventqueues.servercustomer.Event;
import de.hsbo.geo.simsamples.eventqueues.servercustomer.Server;
import de.hsbo.geo.simsamples.eventqueues.servercustomer.ServerQueue;
import de.hsbo.geo.simsamples.eventqueues.servercustomer.SimpleServerCustomerSimulator;

/**
 * Event-queue-based simulation of three fire-fighting vehicles which are 
 * called to extinguish dynamically occurring fires.
 * 
 * @author Benno Schmidt
 */
public class FireBrigadeExample 
{
	public static void main(String[] args) throws Exception 
	{
		new FireBrigadeExample().run();
	}

	private void run() throws Exception 
	{ 		
		FireCar // States: FREE or BUSY
			car1 = new FireCar("car1"), 
			car2 = new FireCar("car2"),
			car3 = new FireCar("car3");

		BurningHouse[] houses = new BurningHouse[8];
		// States: WAITING=burning, IN_SERVICE=operation, SATISFIED=calm
		for (int i = 0; i < houses.length; i++)
			houses[i] = new BurningHouse("house" + (i+1));
		
		ServerQueue 
			busy = new ServerQueue("X"), 
			free = new ServerQueue(".");
		CustomerQueue 
			nonBurning = new CustomerQueue("."), 
			burning = new CustomerQueue("?");

		free.enqueue(car1, car2, car3);
		for (int i = 0; i < houses.length; i++)
			nonBurning.enqueue(houses[i]);

		SimpleServerCustomerSimulator sim = 
			new SimpleServerCustomerSimulator(free, busy, burning, nonBurning);

		// Now let all houses burn at random times:
		double timeInterval = 48./*hours*/;
		EventQueue q = new EventQueue();
		for (int i = 0; i < houses.length; i++) {
			double t = timeInterval * Math.random();
			q.enqueue(new EventEntry(t, Event.NEEDS_SERVICE, houses[i])); 
		}

		// Output options:
		sim.setConsoleDump(false);
		OutputFormat.time("###0.000");
		
		// Simulate M hours:		
		sim.execute(timeInterval, q);
	}
	
	protected class FireCar extends Server {
		protected FireCar(String name) {
			super(name);
		}
		public double getDuration() {
			return 2.; // extinction takes 2 hours
		}
	}

	protected class BurningHouse extends Customer {
		protected BurningHouse(String name) { 
			super(name); 
		}
	}
}
