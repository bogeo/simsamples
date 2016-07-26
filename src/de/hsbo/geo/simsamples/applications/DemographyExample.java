package de.hsbo.geo.simsamples.applications;

import de.hsbo.geo.simsamples.diffequations.DESimulation;
import de.hsbo.geo.simsamples.diffequations.instances.DemographicModel;

/**
 * Sample application that executes a model showing demographic development
 * (e.g. population pyramids) controlled by birth and mortality rates. The 
 * underlying model is defined in {@link DemographicModel}. This example
 * illustrates how applications could be built using the Java language.
 * 
 * @author Benno Schmidt
 */
public class DemographyExample 
{
	private DemographicModel m;
	
	public static void main(String[] args) throws Exception 
	{
		new DemographyExample().run();
	}
	
	private void run() throws Exception 
	{ 
		this.m = new DemographicModel();
		DESimulation a = new DESimulation(m);
		a.setConsoleDump(false); // Set it to 'true' if you like

		// For a start, take thousand 20-years old individuals:
		for (int age = 0; age <= m.maxAge; age++) { 
			m.level(m.pop(age)).setInitialValue(0.);
		}
		m.level(m.pop(20)).setInitialValue(1000.);
		
		// Birth rates:
		for (int age = 0; age <= m.maxAge; age++) {
			double p = 0.;
			if (age > 18 && age < 40) p = 0.1;
			m.parameter(m.birth(age)).setValue(p);
		}

		// Mortality rates:
		for (int age = 0; age <= m.maxAge; age++) {
			double p = 0.03;
			if (age > 80) p = 0.2;
			if (age > 119) p = 1.; // well...
			m.parameter(m.mort(age)).setValue(p);
		}
		
		// Simulate N years:
		int N = 100;
		a.execute(N);
		
		// Build sums:
		this.sumUpYears(N);
	}
	
	private void sumUpYears(int numberOfSteps) throws Exception 
	{
		System.out.println(
			"\nYear\tChilds\tAdults\tSeniors\tTotal population");
	
		for (int ti = 0; ti <= numberOfSteps; ti++) {
			double sumUnder18 = 0., sumAdults = 0., sumOldies = 0.;
			for (int age = 0; age <= 18; age++) {
				sumUnder18 += m.L(ti, m.pop(age));
			}
			for (int age = 19; age <= 68; age++) {
				sumAdults += m.L(ti, m.pop(age));
			}
			for (int age = 69; age <= m.maxAge; age++) {
				sumOldies += m.L(ti, m.pop(age));
			}
			System.out.println(ti + 
				"\t" + (int) sumUnder18 + 
				"\t" + (int) sumAdults + 
				"\t" + (int) sumOldies + 
				"\t" + (int) (sumUnder18 + sumAdults + sumOldies));
		}
	}
}
