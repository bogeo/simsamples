package de.hsbo.geo.simsamples.circuits;

/**
 * Macro modeling element to group sub-circuits.
 * TODO: This functionality has not been implemented yet :-(
 * 
 * @author Benno Schmidt
 */
public class MacroDevice extends Circuit
{
	private String name;
	//private List<CircuitNode> innerNodes;
	//private List<CircuitNode> connectors;
	//private List<IDevice> devices;
	
	
    public MacroDevice(String name) {
    	this.name = name;
    }

	public String getName() {
		return name;
	}
    
    // TODO
}
 
 