package de.hsbo.geo.simsamples.common;

/**
 * Base class for all simulators. Inside this framework, simulations are
 * regarded as <i>dynamic</i>, i.e. simulations over <i>time</i>.
 * 
 * @author Benno Schmidt
 */
abstract public class Simulator 
{
	protected boolean consoleDump = true; 
	private long sysTimeStart, sysTimeEnd; 
	protected int numberOfSteps;

	
	/**
	 * executes the simulation for the given number of time steps.
	 * 
	 * @param numberOfSteps Number of time steps
	 * @throws Exception
	 */
	abstract public void execute(int numberOfSteps) throws Exception;

	/**
	 * controls console output of simulation results.
	 * 
	 * @param mode <i>true</i> to enable output, else <i>false</i>
	 */
	public void setConsoleDump(boolean mode) {
		this.consoleDump = mode;
	}

	/**
	 * disables console output of simulation results.
	 */
	public void disableConsoleDump() {
		this.setConsoleDump(false);
	}

	/**
	 * enables console output of simulation results.
	 */
	public void enableConsoleDump() {
		this.setConsoleDump(true);
	}

	protected boolean getConsoleDump() {
		return this.consoleDump;
	}
	
	protected void beforeExecute() {
		this.sysTimeStart = System.currentTimeMillis();
	}

	protected void afterExecute() {
		this.sysTimeEnd = System.currentTimeMillis();

		System.out.println("Simulation finished successfully.");
		
		long sysTime = this.sysTimeEnd - this.sysTimeStart;
		String str = (sysTime >= 10000) 
			? sysTime/1000 + " secs" 
			: sysTime + " msecs";
		
		System.out.println(
			"Execution time: " + str + " (" + numberOfSteps + " time steps)");
	}
}
