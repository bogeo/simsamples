package de.hsbo.geo.simsamples.common;

import java.text.DecimalFormat;

/**
 * Global output format definitions.
 * 
 * @author Benno Schmidt
 */
public class OutputFormat 
{
	private static DecimalFormat time = new DecimalFormat("###0.000");
	public static void time(String format) {
		OutputFormat.time.applyPattern(format);
	}
	public static String time(double t) {
		return time.format(t); 
	}
	
	private static DecimalFormat floatingPoint = new DecimalFormat("###0.000");
	public static void floatingPoint(String format) {
		OutputFormat.floatingPoint.applyPattern(format);
	}
	public static String floatingPoint(double t) {
		return floatingPoint.format(t); 
	}	
}
