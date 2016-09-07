package de.hsbo.geo.simsamples.eventqueues.servercustomer;

import de.hsbo.geo.simsamples.eventqueues.EntityQueue;

/**
 * Queue holding {@link Server} entities.  
 * 
 * @author Benno Schmidt
 */
public class ServerQueue extends EntityQueue
{
	public ServerQueue(String name) {
		super(name);
	}

	/**
	 * enqueues a variable number of {@link Server} objects.
	 * 
	 * @param servers Server objects
	 */
	public void enqueue(Server... servers) {
		for (Server serv : servers) q.add(0, serv);
	}

	/**
	 * dequeues a {@link Server} object. Note that this will be the server 
	 * object that has been enqueued first ("first in, first out" mechanism).
	 * The result will be <i>null</i> for empty queues.
	 * 
	 * @return Server object which has been removed from the queue 
	 */
	public Server dequeue() {
		Object obj = super.dequeue();
		return (obj instanceof Server) ? (Server) obj : null;
	}

	/**
	 * dequeues a given {@link Server} object. Note that this server object
	 * might be at an arbitrary position inside the queue. The result object
	 * is identical to the input object <tt>server</tt>, i.e. the method will  
	 * not return <i>null</i>, if the given object is not present.
	 * 
	 * @param server Server object to be dequeued 
	 * @return identical to input server object
	 */
	public Server dequeue(Server server) {
		q.remove(server);
		return server;
	}
}
