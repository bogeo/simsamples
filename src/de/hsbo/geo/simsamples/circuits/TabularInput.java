package de.hsbo.geo.simsamples.circuits;


/**
 * Tabular input function.
 * 
 * @author Benno Schmidt
 */
public class TabularInput implements Input 
{
	private double[] time;
	private double[] valTab;

	
	/**
	 * Constructor
	 */
	public TabularInput(double[] time, double[] valTab) throws Exception 
	{
		if (
			time == null || valTab == null || 
			time.length <= 0 || time.length != valTab.length) 
		{
			throw new Exception(
				"Illegal constructor call to class TabularInput!");
		}
		this.time = time;
		this.valTab = valTab;	
	}
	
	/**
	 * provides the signal-value (e.g. a voltage, current etc.) for the given 
	 * simulation-time.
	 * 
	 * @param time Time given in seconds
	 * @return Signal value 
	 */
	public double value(double time) 
	{
		if (
			this.time == null || this.time.length <= 0 || 
			this.valTab == null || this.valTab.length <= 0)
		{
			return 0.;
		}
		if (this.time.length == 1) {
			return (time < this.time[0]) ? 0. : this.valTab[0];
		}
		
		int i = 0;
		while (i < this.time.length && this.time[i] < time) {
			i++;
		}
		if (i >= this.time.length) {
			return this.valTab[i - 1];
		}
		// else: this.time[i] >= time:
		double 
			t1 = (i > 0) ? this.time[i - 1] : this.time[0],
			val1 = (i > 0) ? this.valTab[i - 1] : this.valTab[0],
			t2 =  this.time[i],
			val2 = this.valTab[i];
			
		if (t1 != t2) {
			return (val2 - val1) * (time  - t1) / (t2 - t1) + val1;
		}
		else {	
			return (val1 + val2) / 2.; 
		}
	}
}
