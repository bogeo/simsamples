package de.hsbo.geo.simsamples.diffequations;

import java.util.List;

import de.hsbo.geo.simsamples.common.OutputFormat;
import de.hsbo.geo.simsamples.common.Simulator;

/**
 * Simulator to run difference equation based models. Note that this 
 * simulator allows to process simple System-Dynamics-like descriptions. 
 * 
 * @author Benno Schmidt
 */
public class DESimulation extends Simulator
{
	private DEModel model;
	private double tStart = 0., tEnd = 10., deltaT = 1.;
	private IntegrationMethod methIntegr = IntegrationMethod.FORWARD_EULER;
	
	
	public DESimulation(DEModel model) {
		this.model = model;
		this.model.setSimulation(this);
		this.model.declarations();
	}

	public DEModel getModel() {
		return this.model;
	}

	/**
	 * sets the start time for the simulation. The time has to be given in 
	 * seconds. By default, start time is set to 0 sec.  
	 * 
	 * @param tStart Start time (given in seconds)
	 */
	public void setStartTime(double tStart) {
		this.tStart = tStart;
	}
	
	/**
	 * sets the end time for the simulation. The time has to be given in 
	 * seconds. By default, end time is set to 0 sec.  
	 * 
	 * @param tEnd End time (given in seconds)
	 */
	public void setEndTime(double tEnd) {
		this.tEnd = tEnd;
	}

	/**
	 * Gets the time step width for the simulation. 
	 * 
	 * @return deltaT Time step width (given in seconds)
	 */
	public double getDeltaT() {
		return this.deltaT;
	}

	/**
	 * sets the time step width for the simulation. The time has to be given in 
	 * seconds. By default, a time step width if 1 sec will be used. Note that 
	 * currently only constant time steps are supported. 
	 * TODO: Support variable time steps in the future
	 * 
	 * @param deltaT Time step width (given in seconds)
	 */
	public void setDeltaT(double deltaT) {
		this.deltaT = deltaT;
	}

	public void execute() throws Exception {
		int numberOfSteps = (int) Math.ceil(
			(this.tEnd - this.tStart) / this.getDeltaT());
		this.execute(numberOfSteps);
	}

	@Override
	public void execute(int numberOfSteps) throws Exception 
	{
		this.numberOfSteps = numberOfSteps;
		this.beforeExecute();
		
		this.dumpNames("ti", "t", this.model.getLevels());
		
		for (int ti = 0; ti < numberOfSteps; ti++) {
			this.model.step(ti);

			if (this.getIntegrationMethod() == IntegrationMethod.RUNGE_KUTTA) {
				// Swap tempValue and value(ti):
				for (Level l : this.model.getLevels()) {
					Object x = l.getValue(ti);
					l.setValue(ti, l.getTempValue());
					l.setTempValue(x);
				}
				// Perform second integration half-step (for impl. details, see
				// method DEModel#integrate):
				this.methIntegr = IntegrationMethod.RUNGE_KUTTA_TEMP;
				this.model.step(ti);
				// Copy tempValue to value(ti):
				for (Level l : this.model.getLevels()) {
					l.setValue(ti, l.getTempValue());
				}
				this.methIntegr = IntegrationMethod.RUNGE_KUTTA;
			}
			
			this.dumpValues(ti, ti * this.getDeltaT(), this.model.getLevels());
		}
		
		this.dumpValues(
			numberOfSteps, numberOfSteps * this.getDeltaT(), 
			this.model.getLevels());
		
		this.afterExecute();
	}

	public IntegrationMethod getIntegrationMethod() {
		return this.methIntegr;
	}

	public void setIntegrationMethod(IntegrationMethod method) 
		throws Exception 
	{
		if (method == IntegrationMethod.RUNGE_KUTTA_TEMP) {
			throw new Exception(
				"Integration method must be FORWARD_EULER or RUNGE_KUTTA!");
		}
		this.methIntegr = method;
	}

	private void dumpNames(String s1, String s2, List<Level> levels) 
	{
		if (!consoleDump) 
			return;

		StringBuffer s = new StringBuffer();

		s.append(s1); 
		s.append("\t");
		s.append(s2); 
		for (Level l : levels) {
			s.append("\t");
			s.append(l.getName());
		}

		System.out.println(s.toString());
	}

	private void dumpValues(int ti, double t, List<Level> levels) 
	{
		if (!consoleDump) 
			return;

		StringBuffer s = new StringBuffer();

		s.append(ti); 
		s.append("\t"); 
		s.append(OutputFormat.time(t)); 
		for (Level l : levels) {
			s.append("\t");
			try {
				double val = (Double) l.getValue(ti);
				s.append(OutputFormat.floatingPoint(val));
			} 
			catch (Exception e) {
				s.append("error");
			}
		}

		String str = s.toString();
		System.out.println(str);
	}

	public String toString() 
	{
		StringBuffer s = new StringBuffer();
	
		s.append("(");
		s.append(this.getClass().getSimpleName());
		s.append(":");
		if (this.model != null) {
			s.append(" " + this.model);
		}
		s.append(")");
		
		return s.toString();
	}
}
