package de.hsbo.geo.simsamples.circuits.electric;

import de.hsbo.geo.simsamples.circuits.Circuit;
import de.hsbo.geo.simsamples.circuits.CircuitNode;
import de.hsbo.geo.simsamples.circuits.CircuitSimulator;
import de.hsbo.geo.simsamples.circuits.Device;
import de.hsbo.geo.simsamples.circuits.Input;
import de.hsbo.geo.simsamples.diffequations.IntegrationMethod;

/**
 * Very simple electric simulator. This implementation allows to simulate the
 * transient behavior of small linear circuits. Time steps are assumed to be
 * constant.
 * TODO: Limitation in number of elements/devices?
 * 
 * @author Benno Schmidt
 */
public class SimpleElectricSimulator extends CircuitSimulator 
{
	// Network to be simulated:
	private Circuit circuit = null;
	
	// Time step width:
	private double deltaT;
	
	// Simulator control parameters:
	private static final double sEps = .001;
	private static final double sRi = .001 /*Ohm*/;
	private static int sNumberIter = 0; 
	private static final int sMinNumberIter = 2, sMaxNumberIter = 8;
	 
	// Computation data structures: 
	private int mN = 0; // Number of nodes
	private double[][] mG, mGD, mGQ;
	private double[] mV, mVh;
	private double[] mI0, mI1, mI2;
	   
	private int mCountC, mCountL, mCountSt;
	private int mC_k[][], mL_k[][];
	private double mC[];
	private double mL[][];
	private int mUS_k[];
	private Input mUS[];
       
	
	/**
	 * Constructor. 
	 * When calling the {@link SimpleElectricSimulator#execute(int)} method, 
	 * the time interval <tt>0 .. numberOfSteps * deltaT</tt> will be 
	 * simulated.  
	 * 
	 * @param circuit Circuit instance to be simulated
	 * @param deltaT Time step width given in sec
	 */
	public SimpleElectricSimulator(Circuit circuit, double deltaT) 
	{
		super();

		this.circuit = circuit;
		if (circuit == null) {
			System.out.println("No circuit to be simulated...");
			return;
		}
		this.deltaT = deltaT;
	}

	/**
	 * gets information about the numerical integration method. For the current
	 * simulator implementation, the Backward Euler method is used. 
	 *  
	 * @return IntegrationMethod.BACKWARD_EULER
	 */
	public IntegrationMethod getIntegrationMethod() {
		return IntegrationMethod.BACKWARD_EULER;
	}

 	@Override
 	public void execute(int numberOfSteps) throws Exception 
 	{
 		this.numberOfSteps = numberOfSteps;
 		this.beforeExecute();
 		
		this.setUpMatrices(deltaT);
		this.performTransientAnalysis(deltaT, numberOfSteps);
		
		this.afterExecute();
 	}

	private void setUpMatrices(double deltaT) 
	{
		// Get number of nodes of the network to be simulated (without GND):
		mN = circuit.numberOfNodes(); 
		
		// Set up equation system (G + GD + GQ) * V = I0 + I1 + I2 
		// for node-voltage analysis:
		mG = new double[mN + 1][mN + 1];
		mGD = new double[mN + 1][mN + 1];
		mGQ = new double[mN + 1][mN + 1];
		mV = new double[mN + 1];
		mI0 = new double[mN + 1];
		mI1 = new double[mN + 1];
		mI2 = new double[mN + 1];
		mVh = new double[mN + 1];
		
		mCountC = 0;
		mCountL = 0;
		mCountSt = 0;
		    
		for (Device elem : this.circuit.getDevices()) 
		{
			if (elem instanceof Resistor) {
				CircuitNode[] k = elem.getConnectionNodes();
				double G = 1. / ((Resistor) elem).getOhmValue();
				mG[k[0].index][k[0].index] += G;
				mG[k[1].index][k[1].index] += G;
				mG[k[0].index][k[1].index] -= G;
				mG[k[1].index][k[0].index] -= G;
				continue;
			}
			    	    
			if (elem instanceof Capacitor) {
				CircuitNode[] k = elem.getConnectionNodes();
				double G = ((Capacitor) elem).getFaradValue() / deltaT;
				mG[k[0].index][k[0].index] += G;
				mG[k[1].index][k[1].index] += G;
				mG[k[0].index][k[1].index] -= G;
				mG[k[1].index][k[0].index] -= G;
				mCountC++;
				continue;
			}
			    	    
			if (elem instanceof Inductor) {
				CircuitNode[] k = elem.getConnectionNodes();
				double G = deltaT / ((Inductor) elem).getHenryValue();
				mG[k[0].index][k[0].index] += G;
				mG[k[1].index][k[1].index] += G;
				mG[k[0].index][k[1].index] -= G;
				mG[k[1].index][k[0].index] -= G;
				mCountL++;
				continue;
			}
    	    
			// TODO: Maybe this would be the right place to handle non-linear 
			// elements such as diodes... -> Hard job!
			    	    
			// TODO: Is the following code placed correctly?
			if (elem instanceof VoltageSource) {
				VoltageSource v = (VoltageSource) elem;
				CircuitNode[] k = elem.getConnectionNodes();
				double G = 1. / sRi;
				mG[k[0].index][k[0].index] += G;
				mI0[k[0].index] = v.voltage(0./*dummy param*/) / sRi;
				if (v.getType() == VoltageSource.SIGNAL_INPUT) {
					mCountSt++;
				}
				continue;
			}   	    
			
			// TODO: Current-controlled current-sources and voltage-controlled
			// voltage-sources (non-linear!) maybe could be handled here?
		}
		
		mC_k = new int[mCountC][2];
		mC = new double[mCountC];
		mL_k = new int[mCountL][2];
		mL = new double[mCountL][2];
		mUS_k = new int[mCountSt];
		mUS = new Input[mCountSt];
		
		int jC = 0, jL = 0, jSt = 0;
		for (Device elem : circuit.getDevices()) 
		{
			if (elem instanceof Capacitor) {
				mC_k[jC][0] = elem.getConnectionNodes()[0].index;
				mC_k[jC][1] = elem.getConnectionNodes()[1].index;
				double C = ((Capacitor) elem).getFaradValue();
				mC[jC] = C;
				jC++;
				continue;
			}
			
			if (elem instanceof Inductor) {
				mL_k[jC][0] = elem.getConnectionNodes()[0].index;
				mL_k[jC][1] = elem.getConnectionNodes()[1].index;
				double L = ((Inductor) elem).getHenryValue();
				mL[jL][0] = L;
				mL[jL][1] = 0.;
				jL++;
				continue;
			}
			
			if (elem instanceof VoltageSource) {
				mUS_k[jSt] = elem.getConnectionNodes()[0].index;
				mUS[jSt] = ((VoltageSource) elem).getInputSignal();
				jSt++;
				continue;
			}
		}
	}
    
	private void performTransientAnalysis(double deltaT, int numberOfSteps) 
		throws Exception
	{
		for (int k = 0; k <= mN; k++) {
			mV[k] = 0.f;
		}
		for (int k = 0; k < mCountL; k++) {
			mL[k][1] = 0.f;			
		}
		
		int ti = 0;
		for (double t = 0; t <= deltaT * (double) numberOfSteps; t += deltaT) 
		{
			this.doUpdateMatrix(t, deltaT); 
			this.doCalcPotentials();
			this.recordData(ti, t);
			ti++;
		}
	}

	private void doUpdateMatrix(double t, double h)
	{
		for (int i = 0; i <= mN; i++) { 
		    mI1[i] = 0.;
		}
		for (int i = 0; i < mCountC; i++) {
			double G = mC[i] / h;
		    mI1[mC_k[i][0]] +=  G * (mV[mC_k[i][0]] - mV[mC_k[i][1]]);
		    mI1[mC_k[i][1]] += -G * (mV[mC_k[i][0]] - mV[mC_k[i][1]]);
		}
		for (int i = 0; i < mCountL; i++) {
		    double G = h / mL[i][0]; 
		    double IOld = mL[i][1];
		    mL[i][1] = G * (mV[mL_k[i][0]] - mV[mL_k[i][1]]) + IOld;
		    mI1[mL_k[i][0]] += -mL[i][1];
		    mI1[mL_k[i][1]] +=  mL[i][1];
		}
		for (int i = 0; i < mCountSt; i++) {
			double val = mUS[i].value(t);
			mI1[mUS_k[i]] += val / sRi;
		}
	} 

	private void doCalcPotentials() throws Exception
	{
		for (int i = 1; i <= mN; i++) {
			if ((mG[i][i] + mGD[i][i] + mGQ[i][i]) == 0.) {
				String msg = 
					"Simulation has been terminated, " + 
					"since equations can not be solved numerically...";
				System.out.println(msg);
				throw new Exception(msg); 
			}
		}
		
		int z = 0;
		double max = 0.; // since z is 0, the while-loop will be entered:
		while ((max > sEps && z < sMaxNumberIter) || z < sMinNumberIter) {
			z++;
		max = 0.;
		
		for (int i = 1; i <= mN; i++) {
		double sum = 0.;
		for (int j = 1; j <= mN; j++) {
			if (i != j)
				sum += (mG[i][j] + mGD[i][j] + mGQ[i][j]) * mV[j];
			} 
			double x = Math.abs(sum 
				+ (mG[i][i] + mGD[i][i] + mGQ[i][i]) * mV[i] 
				- mI0[i] - mI1[i] - mI2[i]);
			if (x > max) {
				max = x;
			}
			mVh[i] = (mI0[i] + mI1[i] + mI2[i] - sum) 
				/ (mG[i][i] + mGD[i][i] + mGQ[i][i]);
			}
			for (int i = 1; i <= mN; i++) {
				mV[i] = mVh[i];
			}
		}
		sNumberIter = sNumberIter + z; 
	}
    
	private void recordData(int ti, double t) throws Exception {
		if (rec == null) return;
		
		rec.appendData(ti, t);
		for (int i = 1; i <= mN; i++) { 
			int x = rec.getIndex(new CircuitNode(i));
			if (x >= 0) {
				rec.appendData(new CircuitNode(i), ti, mV[i]);       			
			}
		}
	}
}
