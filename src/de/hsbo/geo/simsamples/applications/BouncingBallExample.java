package de.hsbo.geo.simsamples.applications;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import de.hsbo.geo.simsamples.diffequations.DEModel;
import de.hsbo.geo.simsamples.diffequations.DESimulation;
import de.hsbo.geo.simsamples.diffequations.Level;
import de.hsbo.geo.simsamples.diffequations.instances.BouncingBallModel;

/**
 * Simulation of a bouncing ball. The physical model is specified in {@link 
 * BouncingBallModel}. If you like to see the interactive 3D world showing
 * this bouncing ball, make sure that 1. the specified output file path exists,
 * 2. your computer is connected to the Internet, and 3. your Web-browser 
 * supports HTML5/WebGL.
 * 
 * @author Benno Schmidt
 */
public class BouncingBallExample 
{
	static double 
		endTime = 15., // simulation end time in secs
		deltaT = 0.05; // simulator time step width
		
	static public void main(String[] args) throws Exception 
	{
		// The physical model to be simulated:
		DEModel m = new BouncingBallModel();
		
		// Set up simulator:
		DESimulation a = new DESimulation(m);
		a.setStartTime(0.);
		a.setEndTime(endTime);
		a.setDeltaT(deltaT);
		
		// Run simulation:
		a.execute();
		
		// Generate a 3D scene for this bouncing ball:
		generateX3DomScene("./data/bouncingball.html", m.level("x"));
	}
	
	static private BufferedWriter w;
	
	static private void w(String s) throws IOException { 
		w.write(s + "\n"); 
	}
	
	static private void generateX3DomScene(String filename, Level x) 
		throws Exception 
	{
		w = new BufferedWriter(new FileWriter(filename));
		
		w("<html>");
		w("<head>");
		w("  <title>3D scene: Bouncing ball</title>");
		w("  <script type='text/javascript' " + 
				"src='http://www.x3dom.org/download/x3dom.js'></script>");
		w("</head>");
		w("<body>");
		w("  <h1>Animate Objects with X3DOM!</h1>");
		w("  <x3d width='500px' height='400px'>");
		w("    <Scene>");
		double camDist = 2. * x.maxNumericValue();
		w("      <Viewpoint position='0 0 " + camDist + "'></Viewpoint>");
		w("      <Transform DEF='ball'>");
		w("        <Shape>");
		w("          <Appearance>");
		w("            <Material diffuseColor='1 0 0'/>");
		w("          </Appearance>");
		w("          <Sphere />");
		w("        </Shape>");
		w("      </Transform>");
		w("      <Transform translation='0 -1.05 0'>");
		w("        <Shape>");
		w("          <Appearance>");
		w("            <Material diffuseColor='0 1 0'/>");
		w("          </Appearance>");
		w("          <Box size='10 0.1 10'/>");
		w("        </Shape>");
		w("      </Transform>");
		w("      <TimeSensor DEF='time' cycleInterval='" 
				+ endTime + "' loop='true'/>");
		w("		 <PositionInterpolator DEF='move' key='");
		for (int ti = 0; ti < x.size(); ti++) {
			w(((double) ti / (double) x.size()) + " ");
		}
		w("' keyValue='");
		for (int ti = 0; ti < x.size(); ti++) {
			w("0 " + ((Double) x.getValue(ti)) + " 0  ");
		}
		w("		   '/>");
		w("      <Route fromNode='time' fromField='fraction_changed' " + 
				"toNode='move' toField='set_fraction'></Route>");
		w("      <Route fromNode='move' fromField='value_changed' " + 
				"toNode='ball' toField='translation'></Route>");
		w("    </Scene>");
		w("  </x3d>");
		w("</body>");
		w("</html>");
				
		w.close(); 
		
		System.out.println("Wrote file \"" + filename + "\".");
	}
}
