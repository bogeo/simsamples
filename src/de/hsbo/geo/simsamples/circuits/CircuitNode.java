package de.hsbo.geo.simsamples.circuits;

/**
 * Node inside a {@link Circuit}.
 * 
 * @author Benno Schmidt
 */
public class CircuitNode 
{       
    /** 
     * Ground node. (As known from the electric application domain.)
     */
    static final public CircuitNode GND = new CircuitNode(0); 

    public int index = 0; // 0 = GND!

    
    /** 
     * Constructor
     * 
     * @param number Node-index 
     */
    public CircuitNode(int number) {
    	this.index = number; 
    }
    
    /**
     * gets the node's index.
     * 
     * @return Index number
     */
    public int index() {
    	return this.index;	
    }
    
    /**
     * generates a node with the given node-index. Note: Two nodes are assumed 
     * as equal, if they have got the same index. I.e.: 
     * <tt>e1.index() == e2.index() &lt;=&gt; e1 == e2</tt>
     *   
     * @param number Node-index 
     * @return Node instance
     */
    static public CircuitNode get(int number) {
    	return new CircuitNode(number);
    }
}
 