package de.hsbo.geo.simsamples.diffequations;

import java.util.ArrayList;
import java.util.List;

import de.hsbo.geo.simsamples.common.Parameter;

/**
 * Abstract base class for all simulation models that will be implemented
 * on the basis of the <tt>de.hsbo.bogeosim.diffequations</tt> package.
 *    
 * @author Benno Schmidt
 */
abstract public class DEModel 
{
	protected List<Level> levels = new ArrayList<Level>();
	protected List<Parameter> params = new ArrayList<Parameter>();
	
	private DESimulation sim;
	
	
	protected DESimulation getSimulation() {
		return this.sim;
	}
	
	protected void setSimulation(DESimulation sim) {
		this.sim = sim;
	}

	/**
	 * defines the levels that can be used inside the simulation model 
	 * by giving unique level identifiers. Note that level names are 
	 * context-sensitive.
	 * TODO: Avoid multiple entries for equal identifiers!
	 * 
	 * @param identifiers Arbitrary number of level names
	 */
	public void defineLevels(String... identifiers) {
		for (String s : identifiers) {
			this.levels.add(new Level(s));
		}
	}

	/**
	 * defines a level that can be used inside the simulation model.
	 * 
	 * @param level Level object
	 */
	public void defineLevel(Level level) {
		levels.add(level);
	} 

	protected List<Level> getLevels() {
		return levels;
	}

	/**
	 * gets a level object by name.
	 * 
	 * @param identifier Level name
	 * @return Level object or <i>null</i>, if the level has not been declared
	 */
	public Level level(String identifier) 
	{
		int i = 0;
		while (i < levels.size()) {
			Level lev = levels.get(i);
			if (lev.getName().equals(identifier)) {
				return lev;
			}
			i++;
		}			
		return null;
	}
	
	/**
	 * gets a level's floating-point value.
	 * 
	 * @param ti Time step number
	 * @param identifier Level name
	 * @return Value (will be 0. for non-{@link Double}-valued levels)
	 * @throws Exception
	 */
	public double L(int ti, String identifier) throws Exception 
	{
		Level l = this.level(identifier);
		if (l != null && l.getValue(ti) instanceof Double) {
			return ((Double) l.getValue(ti)).doubleValue();
		}
		return 0.;
	}

	/**
	 * defines the constant parameters that can be used inside the simulation 
	 * model by giving unique parameter identifiers. Note that parameter names
	 * are context-sensitive.
	 * TODO: Avoid multiple entries for equal identifiers!
	 * 
	 * @param identifiers Arbitrary number of parameter names
	 */
	public void defineParameters(String... identifiers) {
		for (String s : identifiers) {
			this.params.add(new Parameter(s));
		}
	}

	/**
	 * defines a parameter that can be used inside the simulation model.
	 * 
	 * @param param Parameter object
	 */
	public void defineParameter(Parameter param) {
		params.add(param);
	} 

	/**
	 * defines a {@link Double}-valued parameter that can be used inside the
	 * simulation model. Additionally, a default-value is given. 
	 * 
	 * @param identifier Parameter name
	 * @param val Default-value
	 */
	public void defineParameter(String identifier, double val) {
		params.add(new Parameter(identifier, val));
	} 

	/**
	 * defines a parameter that can be used inside the simulation model. 
	 * Additionally, a default-value is given. 
	 * 
	 * @param identifier Parameter name
	 * @param val Default-value
	 */
	public void defineParameter(String identifier, Object val) {
		params.add(new Parameter(identifier, val));
	} 

	/**
	 * gets a parameter object by name.
	 * 
	 * @param identifier Parameter name
	 * @return Parameter object or <i>null</i>, if parameter not declared
	 */
	public Parameter parameter(String identifier) {
		int i = 0;
		while (i < params.size()) {
			Parameter param = params.get(i);
			if (param.getName().equals(identifier)) {
				return param;
			}
			i++;
		}			
		return null;
	}

	/**
	 * gets a parameters's floating-point value.
	 * 
	 * @param identifier Parameter name
	 * @return Value (will be 0. for non-{@link Double}-valued parameters)
	 * @throws Exception
	 */
	public double P(String identifier) throws Exception 
	{
		Parameter p = this.parameter(identifier);
		if (p != null && p.getValue() instanceof Double) {
			return p.getDoubleValue();
		}
		return 0.;
	}

	/**
	 * Declarations of a model's {@link Level}s. This method must be 
	 * implemented by all model instances. Optionally, model {@link Parameter}s
	 * or other initializing Java code that is needed to execute a model, can 
	 * be added here.
	 */
	abstract public void declarations();

	/**
	 * Definition of a model's dynamic behavior. This method must be 
	 * implemented by all model instances. Usually, the differential 
	 * equations that describe the model's behavior <tt>x(ti + 1) = x(ti) 
	 * + dt * f(ti, ...)</tt> will be specified here.
	 * 
	 * @param ti Time stamp
	 * @throws Exception
	 */
	abstract public void step(int ti) throws Exception;

	/**
	 * General integration implementation. The level <tt>x</tt> will be 
	 * integrated by adding the rate <tt>f</tt>: <tt>x(ti + 1) = x(ti) 
	 * + dt * f(ti, ...)</tt>. The time step <tt>dt</tt> must have been set 
	 * before, see {@link DESimulation#setDeltaT(double)}. The integration
	 * method can be set by calling 
	 * {@link DESimulation#setIntegrationMethod(IntegrationMethod)}.
	 * 
	 * @param level Level to be integrated
	 * @param ti Time stamp
	 * @param val Rate
	 * @throws Exception
	 */
	public void integrate(Level level, int ti, double val) throws Exception 
	{
		if (level == null) 
			return;
		
		double levelOld = (Double) level.getValue(ti);
		double levelNew = levelOld;
		
		switch (this.getSimulation().getIntegrationMethod()) {
		case FORWARD_EULER:
			levelNew = levelOld + val * this.sim.getDeltaT();
			level.setValue(ti + 1, levelNew);
			break;
		case RUNGE_KUTTA:
			// First perform integration for dt/2, then integrate 
			// will be called for a 2nd time (RUNGE_KUTTA_TEMP, see 
			// DESimulation#execute), while the tempValue-field is used to 
			// store intermediate integration results.
			double levelMid = levelOld + val * 0.5 * this.sim.getDeltaT();
			level.setTempValue(levelMid);			
			break;
		case RUNGE_KUTTA_TEMP:
			double levelTemp = (Double) level.getTempValue();
			levelNew = levelTemp + val * this.sim.getDeltaT();
			level.setValue(ti + 1, levelNew);
			break;
		case BACKWARD_EULER:
			throw new Exception("Called unsupported integration method!");
		}
	}

	public String toString() 
	{
		StringBuffer s = new StringBuffer();
	
		s.append("(");
		s.append(this.getClass().getSimpleName());
		s.append(":");
		if (this.levels != null) {
			for (Level l : this.levels) {
				s.append(" \"" + l.getName() + "\"");
			}
		}
		s.append(")");
		
		return s.toString();
	}
}
