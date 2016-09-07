package de.hsbo.geo.simsamples.applications;

import de.hsbo.geo.simsamples.eventqueues.servercustomer.Customer;
import de.hsbo.geo.simsamples.eventqueues.servercustomer.CustomerQueue;
import de.hsbo.geo.simsamples.eventqueues.servercustomer.Server;
import de.hsbo.geo.simsamples.eventqueues.servercustomer.ServerQueue;
import de.hsbo.geo.simsamples.eventqueues.servercustomer.SimpleServerCustomerSimulator;

/**
 * Simple system consisting of a single serving unit and multiple clients 
 * processing discrete events. The concrete example simulates a bar situation
 * with three customers and a barmaid. A customer entity can take one of 
 * the following states: WAITING, IN_SERVICE, SATISFIED. For the serving 
 * units there are the states FREE and BUSY. Typical events are requesting a 
 * drink, finishing a service etc. The implementation uses the 
 * {@link SimpleServerCustomerSimulator} which operates on {@link Server} 
 * and {@link Customer} entities. 
 * 
 * @author Benno Schmidt
 */
public class PubExample 
{
	public static void main(String[] args) throws Exception 
	{
		new PubExample().run();
	}
	
	private void run() throws Exception 
	{ 		
		Drinker  
			peter = new Drinker("Peter", 15./*average drinking time*/), 
			paul = new Drinker("Paul", 20.),
			harry = new Drinker("Harry", 30.);
		
		Barmaid 
			elke = new Barmaid("Elke", 2./*average serving time*/); 
		
		CustomerQueue drinking = new CustomerQueue("."); // initially empty
		CustomerQueue thursty = new CustomerQueue("?");
		thursty.enqueue(peter, paul, harry);

		ServerQueue tapping = new ServerQueue("X"); // initially empty
		ServerQueue free = new ServerQueue(".");
		free.enqueue(elke);
		
		SimpleServerCustomerSimulator sim = new SimpleServerCustomerSimulator(
			free, // "free servers"
			tapping, // "busy servers"
			thursty, // "waiting customers"
			drinking); // "satisfied customers"
		sim.setConsoleDump(false); 

		// Simulate M minutes:
		double M = 120.;
		sim.execute(M);
	}
	
	protected class Drinker extends Customer {
		private double dt;
		
		protected Drinker(String name, double drinkingTime) { 
			super(name);
			this.dt = drinkingTime;
		}
		@Override
		public double getDuration() {
			return dt + (Math.random() - 0.5) * 2.; 
		}
	}

	protected class Barmaid extends Server {
		private double dt;

		protected Barmaid(String name, double serviceTime) { 
			super(name); 
			this.dt = serviceTime;
		}
		public double getDuration() {
			return dt + (Math.random() - 0.5) * 2.; 
		}
	}
}
