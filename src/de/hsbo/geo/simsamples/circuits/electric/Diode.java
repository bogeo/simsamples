package de.hsbo.geo.simsamples.circuits.electric;

import de.hsbo.geo.simsamples.circuits.CircuitNode;
import de.hsbo.geo.simsamples.circuits.Device;

/**
 * Diode modeling element.
 * TODO The {@link SimpleElectricSimulator} does not consider diodes.
 *
 * @author Benno Schmidt
 */
public class Diode implements Device 
{
    private CircuitNode nodePlus = new CircuitNode(0);
    private CircuitNode nodeMinus = new CircuitNode(0);
    
    
    public Diode(CircuitNode nodePlus, CircuitNode nodeMinus) {
        this.nodePlus = nodePlus;
        this.nodeMinus = nodeMinus;   
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
}
 