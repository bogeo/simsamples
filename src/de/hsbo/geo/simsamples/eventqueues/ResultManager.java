package de.hsbo.geo.simsamples.eventqueues;

import java.util.ArrayList;
import java.util.Collections;

import de.hsbo.geo.simsamples.common.OutputFormat;

/**
 * Management of simulation results for event-based models. Basically, for
 * {@link Entity} objects the time-stamps of occurring [@link Event}s and
 * the entities' state values are stored in a dynamic matrix-like data 
 * structure.
 * 
 * @author Benno Schmidt
 */
public class ResultManager 
{
	private ArrayList<TimeEntry> res;
	private ArrayList<String> enames;

	
	public ResultManager() 
	{
		res = new ArrayList<TimeEntry>();
		res.add(new TimeEntry(0.));
	
		enames = new ArrayList<String>();
	}

	/**
	 * records an {@link Entity}'s state for a given time-stamp tt>t</tt>.    
	 * 
	 * @param ent Entity
	 * @param t Simulation time 
	 * @param symbol Entity state (symbolic representation)
	 * @return Number of recorded simulation steps (<tt>ti</tt>)
	 * @throws Exception 
	 */
	public int record(Entity ent, double t, String symbol) throws Exception 
	{
		if (ent == null) throw new Exception("Missing entity object!");

		TimeEntry last = res.get(0);
		Double tLast = last.t;
		int pos = 0;
		while (t < tLast) {
			if (pos >= res.size()) {
				break;
			}
			pos++;
			last = res.get(pos);
			tLast = last.t;
		}

		if (t > tLast) {  
			TimeEntry newEntry = new TimeEntry(t); 
			res.add(pos, newEntry);
			newEntry.entries.add(new EntityStatePair(ent.toString(), symbol));
			this.register(ent.toString());
		} 
		else {
			if (t == tLast) {
				EntityStatePair es = 
					this.getByEntity(last.entries, ent.toString());
				if (es == null) { 
					last.entries.add(
						new EntityStatePair(ent.toString(), symbol));
					this.register(ent.toString());
				} else { // overwrite stored value
					es.state = symbol; // usually this will not happen
				}
			}
			else { // t < tLast
				throw new Exception("Reached unreachable code...");
			}
		}

		return res.size();
	}

	/**
	 * gets the number of recorded simulation steps (<tt>ti</tt>).
	 */
	public int numberOfSteps() {
		return res.size();
	}

	private void register(String ename) {
		if (! enames.contains(ename)) {
			enames.add(ename);
			Collections.sort(enames);
		}
	}

	private EntityStatePair getByEntity(
		ArrayList<EntityStatePair> entries, String ename) 
	{
		int i = 0;
		while (i < entries.size()) {
			EntityStatePair es = entries.get(i);
			if (es.ename.equals(ename)) {
				return es;
			}
			i++;
		}
		return null;
	}

	/**
	 * shows the recorded data as simple Gantt chart in the console.
	 */
	public void dump() 
	{
		if (res.size() <= 0) 
			return;
		int N = enames.size();
		String[] states = new String[N], statesOld = new String[N]; 
		for (int i = 0; i < N; i++) {
			statesOld[i] = "(nil)"; // should not happen
		}

		// Title row:
		StringBuffer s = new StringBuffer();
		s.append("\nti\tt"); 
		for (int i = 0; i < N; i++) 
			s.append("\t" + enames.get(i));
		System.out.println(s.toString());		
		
		// Data rows:
		int ti = 0;
		for (int k = res.size() - 1; k >= 0; k--) {
			Double t = res.get(k).t;
			for (int i = 0; i < N; i++) {
				EntityStatePair es = 
					this.getByEntity(res.get(k).entries, enames.get(i));
				if (es != null) {
					states[i] = es.state;
					statesOld[i] = states[i]; 
				}
				else {
					states[i] = statesOld[i];
				}
			}
			dump(ti, t, states);
			ti++;
		}
	}

	private void dump(int ti, double t, String[] states) 
	{
		StringBuffer s = new StringBuffer();

		s.append(ti); 
		s.append("\t"); 
		s.append("" + OutputFormat.time(t)); 
		if (states != null) {
			for (int i = 0; i < states.length; i++) {
				s.append("\t");
				s.append(states[i]);
			}
		}
		String str = s.toString();
		System.out.println(str);		
	}

	// Internal dynamic data structure to manage recorded results:
	
	private class TimeEntry {
		public Double t;
		public ArrayList<EntityStatePair> entries;
		public TimeEntry(Double t) {
			this.t = t;
			this.entries = new ArrayList<EntityStatePair>();
		}	
	}
	
	private class EntityStatePair {
		public String ename; // entity name
		public String state; // state symbol
		public EntityStatePair(String ename, String state) {
			this.ename = ename;
			this.state = state;
		}
	}
}
