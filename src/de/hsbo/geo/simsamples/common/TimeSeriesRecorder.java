package de.hsbo.geo.simsamples.common;

import java.util.ArrayList;
import java.util.List;

import de.hsbo.geo.simsamples.circuits.CircuitNode;

/**
 * TODO JvaDoc auch in Methoden
 * 
 * @author Benno Schmidt
 */
public class TimeSeriesRecorder 
{
	private List<Object> registry = new ArrayList<Object>();
	private List<TimeSeries> waves = new ArrayList<TimeSeries>();
	private TimeSeries t = new TimeSeries();
	
	
	public void registerNodes(CircuitNode... nodes) {
		for (Object node : nodes) {
			this.registry.add(node);
			this.waves.add(new TimeSeries());
		}
	}

	public List<CircuitNode> getRegisteredElecNodes() {
		List<CircuitNode> res = new ArrayList<CircuitNode>();
		for (Object entry : this.registry) {
			if (entry instanceof CircuitNode)
				res.add((CircuitNode) entry);
		}
		return res;
	}
	
	public void appendData(CircuitNode node, int ti, double val) throws Exception 
	{
		int i = this.getIndex(node);
		if (i >= 0) {
			this.waves.get(i).setValue(ti, val);
		}
	}

	public int getIndex(CircuitNode node) {
		int i = 0;
		while (i < this.registry.size()) {
			Object entry = this.registry.get(i);
			if (entry instanceof CircuitNode) {
				if (((CircuitNode) entry).index == node.index) {
					return i;
				}
			}
			i++;			
		}
		return -1; // not found
	}
	
	public void appendData(int ti, double t) throws Exception {
		this.t.setValue(ti, new Double(t));
		
	}

	private int tiMax() {
		int max = 0;
		for (TimeSeries w : this.waves) {
			if (w.size() > max) max = w.size();
		}
		return max;
	}
	
	public void dumpWaves() throws Exception 
	{	
		for (int ti = 0; ti < this.tiMax(); ti++) 
		{
			StringBuffer s = new StringBuffer();
			s.append(ti);
			if (ti < t.size()) {
				s.append("\t" + this.round(t.getValue(ti)));
			} 
			else {
				s.append("\t-");				
			}
			
			for (int i = 0; i < this.registry.size(); i++) {
				TimeSeries wave = this.waves.get(i);
				if (ti < wave.size()) {
					s.append("\t" + this.round(wave.getValue(ti)));
				} 
				else {
					s.append("\t-");
				}
			}
			System.out.println(s.toString().replace('.', ','));
		}
	}
	
	private Object round(Object valObj) {
		if (valObj instanceof Double)
			return "" + Math.round(1.e12 * ((Double) valObj).doubleValue()) / 1.e12;
		else 
			return valObj;
	}
}
