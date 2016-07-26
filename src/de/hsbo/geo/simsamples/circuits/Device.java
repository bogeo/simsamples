package de.hsbo.geo.simsamples.circuits;


/**
 * Interface for devices as modeling elements used inside the circuits.  
 * 
 * @author Benno Schmidt
 */
public interface Device 
{
    /**
     * gets the nodes the device is connected with.
     */
    public CircuitNode[] getConnectionNodes(); 
}
 