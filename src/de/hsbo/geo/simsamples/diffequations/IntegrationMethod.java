package de.hsbo.geo.simsamples.diffequations;

/**
 * Identifiers for different numerical integration methods used inside the
 * <tt>de.hsbo.bogeosim.diffequations</tt> package. Note that the identifier
 * <tt>RUNGE_KUTTA_TEMP</tt> is used internally only.
 * 
 * @author Benno Schmidt
 */
public enum IntegrationMethod 
{
	FORWARD_EULER, 
	RUNGE_KUTTA, 
	RUNGE_KUTTA_TEMP, 
	BACKWARD_EULER
}
