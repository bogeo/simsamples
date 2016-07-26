package de.hsbo.geo.simsamples.circuits.electric;

import de.hsbo.geo.simsamples.circuits.CircuitNode;
import de.hsbo.geo.simsamples.circuits.Device;
import de.hsbo.geo.simsamples.circuits.Input;

/**
 * Voltage source. Optionally, the source element might be voltage-controlled.
 * TODO: Voltage-controlled sources 
 *
 * @author Benno Schmidt
 */
public class VoltageSource implements Device 
{
    private CircuitNode node = null;
    private short type = VoltageSource.CONSTANT;

	public static final short CONSTANT = 1;
	private double valConst = 0.;

	public static final short VOLTAGE_CONTROLLED = 2;

	public static final short SIGNAL_INPUT = 3;
    private Input inputSignal = null;
    
    
    /**
     * Constructor for a source with constant voltage. Note that the minus-pole
     * always is assumed as {@link CircuitNode#GND}.
     * 
     * @param nodePlus Electric node (the minus-pole is always GND)
     * @param valVolt Constant voltage value given in Volt
     */
    public VoltageSource(CircuitNode nodePlus, double valVolt) {
        this.node = nodePlus;
		this.valConst = valVolt;
		this.type = CONSTANT;
    }
    
	/**
	 * Constructor for a source that gets its voltage-value from an 
	 * <tt>IInput</tt> object. Note that the minus-pole always is assumed as 
	 * {@link CircuitNode#GND}.
	 *  
	 * @param node Node (the minus-pole is always GND)
	 * @param input Input-object
	 */
	public VoltageSource(CircuitNode node, Input input) {
		this.node = node;
		this.inputSignal = input;
		this.type = SIGNAL_INPUT;
	}
	
	public short getType() {
		return this.type;	
	}
	
    public void setNodePlus(CircuitNode node) {
    	this.node = node;  
    }

    public CircuitNode[] getConnectionNodes() {
    	CircuitNode[] ret = new CircuitNode[2];
    	ret[0] = this.node;
    	ret[1] = CircuitNode.GND;
    	return ret;
    }
    
    public double voltage(double pTime) {
    	switch (this.type) {
    		case CONSTANT: return this.valConst;
    		case VOLTAGE_CONTROLLED: return 0.; // TODO
    		case SIGNAL_INPUT: return this.inputSignal.value(pTime); 	
    	}
    	return 0.;
    }
    
    public Input getInputSignal() {
    	return this.inputSignal;
    }
}
 