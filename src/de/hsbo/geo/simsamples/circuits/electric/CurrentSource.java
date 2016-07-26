package de.hsbo.geo.simsamples.circuits.electric;

import de.hsbo.geo.simsamples.circuits.CircuitNode;
import de.hsbo.geo.simsamples.circuits.Device;

/**
 * Current source. 
 * 
 * @author Benno Schmidt
 */
public class CurrentSource implements Device 
{
    private CircuitNode nodePlus = new CircuitNode(0);
    private CircuitNode nodeMinus = new CircuitNode(0);
    private double val = 0.;

    
    /**
     * Constructor for a source with constant current.  
     * 
     * @param nodePlus First electric node (+)
     * @param nodeMinus Second electric node (-)
     * @param valAmpere Current value given in Ampere
     */
    public CurrentSource(
    	CircuitNode nodePlus, CircuitNode nodeMinus, double valAmpere) 
    {
        this.nodePlus = nodePlus;
        this.nodeMinus = nodeMinus;   
        this.val = valAmpere;
    }
    
    public void setAmpereValue(double val) {
    	this.val = val;
    }

    public double getAmpereValue() {
    	return val;
    }
    
    public void setNodePlus(CircuitNode node) {
    	this.nodePlus = node;  
    }

    public void setNodeMinus(CircuitNode node) {
    	this.nodeMinus = node;  
    }

    public CircuitNode[] getConnectionNodes() {
    	CircuitNode[] ret = new CircuitNode[2];
    	ret[0] = this.nodePlus;
    	ret[1] = this.nodeMinus;
    	return ret;
    }
    
    // TODO: current-controlled sources 
}
 
 