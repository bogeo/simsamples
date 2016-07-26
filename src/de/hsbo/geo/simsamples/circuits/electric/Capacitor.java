package de.hsbo.geo.simsamples.circuits.electric;

import de.hsbo.geo.simsamples.circuits.CircuitNode;
import de.hsbo.geo.simsamples.circuits.Device;


/**
 * Capacitor modeling element. 
 * 
 * @author Benno Schmidt
 */
public class Capacitor implements Device 
{
    private CircuitNode node1 = new CircuitNode(0);
    private CircuitNode node2 = new CircuitNode(0);
    private double val = 1.e-9;
    
    
    /**
     * Constructor
     * 
     * @param node1 First electric node 
     * @param node2 Second electric node
     * @param valFarad Device dimension given in Farad
     */
    public Capacitor(CircuitNode node1, CircuitNode node2, double valFarad) {
        this.node1 = node1;
        this.node2 = node2;   
        this.setFaradValue(valFarad);
    }
    
    public void setFaradValue(double val) {
    	this.val = val;
    }

    public double getFaradValue() {
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
 