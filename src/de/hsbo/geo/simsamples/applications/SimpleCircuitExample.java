package de.hsbo.geo.simsamples.applications;

import de.hsbo.geo.simsamples.circuits.Circuit;
import de.hsbo.geo.simsamples.circuits.CircuitNode;
import de.hsbo.geo.simsamples.circuits.CircuitSimulator;
import de.hsbo.geo.simsamples.circuits.Input;
import de.hsbo.geo.simsamples.circuits.PulseInput;
import de.hsbo.geo.simsamples.circuits.electric.Capacitor;
import de.hsbo.geo.simsamples.circuits.electric.Resistor;
import de.hsbo.geo.simsamples.circuits.electric.SimpleElectricSimulator;
import de.hsbo.geo.simsamples.circuits.electric.VoltageSource;
import de.hsbo.geo.simsamples.common.TimeSeriesRecorder;

/**
 * Simple circuit example. For a short pulse signal as input, the output signal
 * of a RC-element (electrical resistance and capacitor in series) will be 
 * given in the console. 
 * 
 * @author Benno Schmidt
 */
public class SimpleCircuitExample
{
	public static void main(String args[]) throws Exception 
	{
		// Define network:
		Circuit c = new Circuit();
		CircuitNode 
			node0 = CircuitNode.GND,
			node1 = new CircuitNode(1),
			node2 = new CircuitNode(2);
		c.addDevice(new Resistor(node1, node2, 100./*Ohm*/)); 
		c.addDevice(new Capacitor(node2, node0, 1.e-9/*Farad*/));
		
		// Add input signal:
		Input in = new PulseInput(
			new double[]{10.e-8, 60.e-8/*sec*/}, 
			0./*Volt*/, 5./*Volt*/);
		c.addDevice(new VoltageSource(CircuitNode.get(1), in));

		// Set up simulator:
		double deltaT = 1.e-8;/*sec*/;
		CircuitSimulator sim = new SimpleElectricSimulator(c, deltaT);
		
		// Add output recorder:
		TimeSeriesRecorder rec = new TimeSeriesRecorder();
		rec.registerNodes(CircuitNode.get(1), CircuitNode.get(2));
		sim.setOutputRecorder(rec);
		
		// Run simulation (200 time steps):
		sim.execute(200);
		
		// Show result:
		rec.dumpWaves();
	}
}
