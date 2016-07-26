package de.hsbo.geo.simsamples.circuits;

import java.util.ArrayList;
import java.util.List;

/**
 * Circuit description. Instances of this class might hold electric circuit 
 * descriptions or descriptions suitable for other application domains such
 * as mechanics, hydraulic engineering etc. 
 * 
 * @author Benno Schmidt
 */
public class Circuit
{
    private ArrayList<Device> elems = null;
    private ArrayList<Integer> nodeNumbers;
   
    
    public Circuit() {
    	this.elems = new ArrayList<Device>();
    	this.nodeNumbers = new ArrayList<Integer>(); 
    	this.nodeNumbers.add(new Integer(0)); // initially GND-node only
    }
    
    /**
     * gets the number of nodes of the circuit (without GND-node).
     */
    public int numberOfNodes() {
    	return this.numberOfRegisteredNodes() - 1;
    }
    
    /**
     * adds an device element to the circuit model.
     */
    public void addDevice(Device elem) {
    	this.elems.add(elem);
    	CircuitNode[] nodes = elem.getConnectionNodes();
    	if (nodes == null)
    	    return;
    	for (int i = 0; i < nodes.length; i++) {
    	    this.registerNodeNumber(nodes[i].index);
    	}
    }
    
    /**
     * gets the device elements that are part of the circuit model.
     * 
     * @return Device element list
     */
    public List<Device> getDevices() {
    	return this.elems; 
    }
    
    /**
     * @deprecated
     * gets the i-th device that is part of the circuit. Note that the 
     * assertion <tt>0 &lt;= i &lt; <tt>this.numberOfDevices()</tt> always 
     * must hold.
     * 
     * @param i Index 
     * @see Circuit#numberOfDevices
     */
    public Device getDevice(int i) {
    	return (Device) this.elems.get(i); 
    }
    
    /**
     * @deprecated
     * gets the number of devices that are part of the circuit.
     */
    public int numberOfDevices() {
    	return this.elems.size();	
    }
    
    private void registerNodeNumber(int number) 
    {
    	if (this.nodeNumbers.size() <= number) {
    		// fill up:
    	    for (int i = this.nodeNumbers.size(); i <= number; i++)
    	    	this.nodeNumbers.add(new Integer(-1)); // "unused"-flag  
    	}
    	if (number > 0) {
    	    // If referenced, the i-th element will be set:
    		this.nodeNumbers.set((int) number, new Integer(number)); 
    	}
    }
    
    private int numberOfRegisteredNodes() {
    	this.purgeNodeList();
    	return this.nodeNumbers.size();
    } 
    
    private void purgeNodeList() {
    	// TODO Renumbering of node elements not implemented
    }
}
