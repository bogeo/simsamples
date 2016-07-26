package de.hsbo.geo.simsamples.circuits;

import de.hsbo.geo.simsamples.common.Simulator;
import de.hsbo.geo.simsamples.common.TimeSeriesRecorder;

/**
 * Base class for all transient circuit simulation implementations.
 * 
 * @author Benno Schmidt
 */
abstract public class CircuitSimulator extends Simulator 
{
	protected TimeSeriesRecorder rec;
	

	/**
	 * assigns an output recorder to this simulation.
	 * 
	 * @param rec Output recorder
	 */
	public void setOutputRecorder(TimeSeriesRecorder rec) {
		this.rec = rec;
	}
}
 