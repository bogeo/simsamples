package de.hsbo.geo.simsamples.circuits.electric;

import de.hsbo.geo.simsamples.circuits.CircuitNode;
import de.hsbo.geo.simsamples.circuits.Device;


/**
 * Inductor modeling element.
 * 
 * @author Benno Schmidt
 */
public class Inductor implements Device 
{
    private CircuitNode node1 = new CircuitNode(0);
    private CircuitNode node2 = new CircuitNode(0);
    private double val = 0.;
    
    
    /**
     * Constructor
     * 
     * @param node1 First electric node 
     * @param node2 Second electric node
     * @param valHenry Device dimension given in Henry
     */
    public Inductor(CircuitNode node1, CircuitNode node2, double valHenry) {
        this.node1 = node1;
        this.node2 = node2;   
        this.setHenryValue(valHenry);
    }
    
    public void setHenryValue(double val) {    	
    	this.val = val;
    }

    public double getHenryValue() {
    	return this.val;
    }
    
    public void setNode1(CircuitNode node) {
    	this.node1 = node;  
    }

    public void setNode2(CircuitNode node) {
    	this.node2 = node;  
    }

    public CircuitNode[] getConnectionNodes() {
    	CircuitNode[] ret = new CircuitNode[2];
    	ret[0] = this.node1;
    	ret[1] = this.node2;
    	return ret;
    }
}
 