package de.hsbo.geo.simsamples.cellularautomata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Elevation model processing tools (for experimental purposes).
 * 
 * @author Benno Schmidt
 */
public class ElevationModelToolBox 
{
	/**
	 * Automaton factory method. The values from an elevation grid file will be
	 * read and copied to the an automaton's rectangular space. 
	 * TODO: 1. For the cellular space no georeferencing mechanism has been 
	 * implemented yet. 2. For the ArcInfo ASCII grid, NODATA values will not
	 * be handled.
	 *  
	 * @param filename Name (incl. path) of file in ArcInfo ASCII grid format
	 * @param delta Transition function
	 * @return Automaton (with continuous state set)
	 * @throws Exception
	 */
	public RectangularAutomaton createAutomaton(
		String filename, TransitionFunction delta) 
		throws Exception 
	{
		ElevationModelToolBox.DEM dem = this.readArcInfoAsciiGrid(filename);
		RectangularAutomaton a = new RectangularAutomaton(
			dem.numberOfRows(), dem.numberOfColumns(), delta);
		this.copyElevations(dem, (RectangularSpace) a.getCellularSpace());
		a.initialized = true;
		
		return a;
	}

	/**
	 * Automaton factory method. The values from an elevation grid file will be
	 * read and copied to the a rectangular cell space. 
	 *  
	 * @param filename Name (incl. path) of file in ArcInfo ASCII grid format
	 * @return Rectangular cell space
	 * @throws Exception
	 */
	public RectangularSpace createCellSpace(String filename) 
		throws Exception 
	{
		ElevationModelToolBox.DEM dem = this.readArcInfoAsciiGrid(filename);
		RectangularSpace sp = new RectangularSpace(dem.nx, dem.ny);
		this.copyElevations(dem, sp);
		return sp;
	}

    /**
     * reads an elevation model from an ArcInfo ASCII grid file. 
     * 
	 * @param filename Name (incl. path) of file in ArcInfo ASCII grid format
     * @throws Exception 
     */
    public ElevationModelToolBox.DEM readArcInfoAsciiGrid(String filename) throws Exception
    {
        BufferedReader dat = new BufferedReader(new FileReader(filename));

        // Read header:
        int ny = (int) parseValue("ncols", dat.readLine());
        int nx = (int) parseValue("nrows", dat.readLine());
        double xllcorner = parseValue("xllcorner", dat.readLine());
        double yllcorner = parseValue("yllcorner", dat.readLine());
        double cellSize = parseValue("cellsize", dat.readLine());
        double NODATA_value = parseValue("NODATA_value", dat.readLine());

        // Construct elevation grid:
        DEM dem = new ElevationModelToolBox.DEM();
        dem.setSize(nx, ny);
        dem.setOrigin(xllcorner, yllcorner); 
        dem.setCellSize(cellSize); 
        dem.setOrigin(xllcorner + cellSize/2., yllcorner + cellSize/2.);
        
        // Read elevation values and populate target grid:
        double z = 0.0;
        String line = null;

        for (int i = nx - 1; i >= 0; i--)
        {
            line = dat.readLine();

            if (line != null) {
                StringTokenizer st = new StringTokenizer(line);
                for (int j = 0; j < ny; j++) {
                    try {
                        z = Double.parseDouble(st.nextToken());
                    }
                    catch (NumberFormatException nfe) {
                        z = 0.;
                    }
                    if (z != NODATA_value)
                        dem.setElevation(i, j, z);
                }
            }
        }
        
        dat.close();
        return dem;
    }
    
    static private double parseValue(String check, String line) 
    	throws Exception
    {
        StringTokenizer st = new StringTokenizer(line);
        String[] tokens = {"", ""};
        int i = 0;
        while (st.hasMoreTokens()) {
            tokens[i] = st.nextToken();
            if (tokens[i].length() > 0) i++;
            if (i > 1) break;
        }
        if (i == 2 && tokens[0].toLowerCase().equals(check.toLowerCase()))
            return Double.parseDouble(tokens[1]);
        else
            throw new Exception(
            	"Header-value \"" + check + "\" is missing in input file.");
    }

    /**
     * shows a rudimentary elevation grid visualization in the system console.
     * 
     * @param sp Cell space to be visualized
     * @param ti Time stamp
     * @throws Exception
     */
	static public void dump(RectangularSpace sp, int ti) 
		throws Exception 
	{
		System.out.println("Time ti = "+ ti + ":");
	
		double minZ = 42., maxZ = 42.;
		boolean minZInit = false, maxZInit = false;
		for (int i = 0; i < sp.numberOfRows(); i++) {
			for (int j = 0; j < sp.numberOfColumns(); j++) {
				double z = (Double) sp.getCell(i, j).getValue(ti);
				if (!minZInit) {
					minZ = z; minZInit = true;
				}
				else {
					if (z < minZ) minZ = z;
				}
				if (!maxZInit) {
					maxZ = z; maxZInit = true;
				}
				else {
					if (z > maxZ) maxZ = z;
				}
			}
		}
		System.out.println("elevation min. = "+ minZ + ", max = " + maxZ);
		
		double dZ = maxZ - minZ; 
		String[] symb = new String[]{" ", ".", ":", ";", "#"};
		for (int i = 0; i < sp.numberOfRows(); i++) {
			for (int j = 0; j < sp.numberOfColumns(); j++) {
				double z = (Double) sp.getCell(i, j).getValue(ti);
				
				int k = (int) (((double)symb.length) * (z - minZ) / dZ);
				if (k >= symb.length) {
					// occurs for z = maxZ
					k = symb.length - 1; 
				}
				System.out.print(symb[k]);
			}
			System.out.println();
		}
	}

	static private BufferedWriter w;
	
	static private void w(String s) throws IOException { 
		w.write(s + "\n"); 
	}
	
    /**
     * writes a cellular elevation space to a X3DOM scene. Inside the 3D scene,
     * a shape of X3D-type <tt>ElevationGrid</tt> will be used. 
     *
     * @param dem Cellular elevation space
     * @param ti Time stamp
	 * @param filename Output file name 
     * @throws Exception 
     */
    public void writeX3DomScene(
    	RectangularSpace dem, int ti, String filename) 
        throws Exception
	{
    	this.writeGenericX3DomScene(dem, ti, filename, false);
	}

    /**
     * writes a cellular elevation space to a X3DOM scene consisting of 
     * animated quads. Important note: For cellular spaces of big size your
     * system might not be able to display the scene!
     *
     * @param dem Cellular elevation space
	 * @param filename Output file name 
     * @throws Exception 
     */
    public void writeAnimatedX3DomQuadScene(
        	RectangularSpace dem, String filename) 
            throws Exception
	{
    	this.writeGenericX3DomScene(dem, -1, filename, true);
	}
    
    private void writeGenericX3DomScene(
    	RectangularSpace dem, int ti, String filename, boolean x3dQuads) 
    	throws Exception
    {
        // deltaX and deltaY will be set to 1. Thus a vertical scaling factor 
        // is introduced to obtain a suitable exaggeration: 
    	double dz = this.deltaElevation(dem);
    	double percentOfHorizExt = 10.; 
        double zf = percentOfHorizExt/100. * 
        	Math.max(dem.numberOfColumns(), dem.numberOfRows()) / dz; 
        // DEM center point:
        double 
        	px = dem.numberOfColumns() / 2.,   
        	py = dem.numberOfRows() / 2.,
        	pz = dz / 2.;
        
        w = new BufferedWriter(new FileWriter(filename));
		
		w("<html>");
		w("<head>");
		w("  <title>3D scene: Cellular elevation grid</title>");
		w("  <script type='text/javascript' " + 
			"src='http://www.x3dom.org/download/dev/x3dom-full.js'>" + 
			"</script>");
		w("</head>");
		w("<body>");
		w("  <h1>Cellular elevation grid</h1>");
		w("  This file has been generated automatically.");
		if (ti >= 0) {
			w("  <p>Time step = " + ti + "</p>");
		}
		w("  <x3d width='600px' height='500px'" + 
			" profile='Interactive' version='3.3'" + 
			" noNamespaceSchemaLocation=" + 
			"'http://www.web3d.org/specifications/x3d-3.3.xsd'>");
		w("    <Scene>");
        w("      <Viewpoint description='Top view' orientation='1 0 0 -1.57'" + 
        	" position='" + px + " " + (zf * 25. * pz) + " " + py + "'" +
        	" centerOfRotation='" + px + " " + (zf * pz) + " " + py + "'>" + 
        	"</Viewpoint>");
        
        if (!x3dQuads) {
        	// Generate shape of X3D type 'ElevationGrid':
            w("      <Shape>");
            w("        <Appearance><Material/></Appearance>");
	        w("        <ElevationGrid " +
	        	" solid='false'" +
	        	" xDimension='" + dem.numberOfColumns() + "'" +
	        	" xSpacing='1'" + 
	        	" zDimension='" + dem.numberOfRows()+ "'" + 
	        	" zSpacing='1'" +
	        	" height='");
	
	        // Elevation-values:
	        for (int i = dem.numberOfRows() - 1; i >= 0; i--) {
	            for (int j = 0; j < dem.numberOfColumns(); j++) {
	            	double z = (Double) dem.getCell(i, j).getValue(ti);
	                w("" + (zf * z) + " ");
	            }
	        }
	        w("'>");
	        w("        </ElevationGrid>");
	        w("      </Shape>");
        }
        else {
        	// Generate shapes of X3D type 'Box' for each cell:
	        for (int i = 0; i < dem.numberOfRows(); i++) {
	            for (int j = 0; j < dem.numberOfColumns(); j++) 
	            {
	            	String idTransf = "transf_" + i + "_" + j; 
	            	double z = (Double) dem.getCell(i, j).getInitialValue();
	                String moveTo = "" + i + " " + (zf * z) + " " + j;
	            	w("      <Transform DEF='" + idTransf + "'" + 
	            		" translation='" + moveTo + "'>");
	            	w("        <Shape>");
	            	if ((i + j) % 2 == 0) {
	            		w("          <Appearance>" +
	            			"<Material diffuseColor='1 1 0'/></Appearance>");
	            	} else {
	            		w("          <Appearance>" + 
	            			"<Material diffuseColor='0 1 0'/></Appearance>");
	            	}
			        w("          <Box size='1 1 1'/>");
			        w("        </Shape>");
	            	w("      </Transform>");
	            }
	        }
	        
	        w("      <TimeSensor DEF='time' cycleInterval='" 
	        		+ dem.getCell(0, 0).timeSeries.size() + "' loop='true'/>");
	        for (int i = dem.numberOfRows() - 1; i >= 0; i--) {
	            for (int j = 0; j < dem.numberOfColumns(); j++) 
	            {
	            	Cell c = dem.getCell(i, j);
	            	
	            	String idInterp = "move_" + i + "_" + j; 
	            	String idTransf = "transf_" + i + "_" + j; 
	            	w("		 <PositionInterpolator DEF='" + idInterp + "' " + 
	            		"key='");
					for (int t = 0; t < c.timeSeries.size(); t++) {
						w(((double) t / (double) c.timeSeries.size()) + " ");
					}
					w("' keyValue='");
					for (int t = 0; t < c.timeSeries.size(); t++) {
						double z = (Double) c.getValue(t);
						w("" + i + " " + (zf * z) + " " + j + " ");
					}
					w("		   '/>");
					w("      <Route" + 
						" fromNode='time' fromField='fraction_changed'" + 
						" toNode='" + idInterp + "'" + 
						" toField='set_fraction'></Route>");
					w("      <Route" + 
						" fromNode='" + idInterp + "'" +
						" fromField='value_changed'" + 
						" toNode='" + idTransf + "'" + 
						" toField='translation'></Route>");
	            }
	        }
        }
        
		w("    </Scene>");
		w("  </x3d>");
		w("</body>");
		w("</html>");
				
		w.close(); 
		System.out.println("Wrote file \"" + filename + "\".");
	}
    
    private double deltaElevation(RectangularSpace dem) throws Exception 
    {
    	double zMin = 42., zMax = 42.;
    	boolean initMin = false, initMax = false;
    
    	for (Cell c : dem.getCells()) {
    		Object val = c.getInitialValue();
    		if (val instanceof Double) {
    			double z = (Double) val;
    			if (!initMin) {
    				zMin = z; initMin = true;
    			} else {
    				if (z < zMin) zMin = z;
    			}
    			if (!initMax) {
    				zMax = z; initMax = true;
    			} else {
    				if (z > zMax) zMax = z;
    			}
    		}
    	}
		return zMax - zMin;
	}


	/**
	 * Geospatial digital elevation grid.
	 * 
	 * @author Benno Schmidt
	 */
	public class DEM
	{
	    private double[][] elev; // holds elevation values
	    private int nx; // number of rows
	    private int ny; // number of columns
	    private double cellSize; // grid cell size
	    private double originX; // x-coordinate of lower left grid corner
	    private double originY; // y-coordinate of lower left grid corner
	    
		private Double zMin, zMax;
		private boolean updateZMinMaxNecessary = true;

	    
	    public DEM() {
	    	nx = 0;
	    	ny = 0;
	    }
	    
	    public void setSize(int numberOfRows, int numberOfColumns) {
	    	elev = new double[numberOfRows][numberOfColumns];
	    	nx = numberOfRows;
	    	ny = numberOfColumns;
	    }

	    public int numberOfRows() {
	        return nx;
	    }

	    public int numberOfColumns() {
	        return ny;
	    }

	    /**
	     * sets an elevation value. Note that the assertions 
	     * <tt>0 <= i < this.numberOfRows()</tt> and
	     * <tt>0 <= j < this.numberOfColumns()</tt> always must hold.
	     * 
	     * @param i Row index of grid cell
	     * @param j Column index of grid cell
	     * @param z Elevation value
	     */
	    public void setElevation(int i, int j, double z) {
	    	elev[i][j] = z;
	    	updateZMinMaxNecessary = true;
	    }
	    
	    /**
	     * sets an elevation value. Note that the assertions 
	     * <tt>0 <= i < this.numberOfRows()</tt> and
	     * <tt>0 <= j < this.numberOfColumns()</tt> always must hold.
	     * 
	     * @param i Row index of grid cell
	     * @param j Column index of grid cell
	     * @return Elevation value
	     */
	    public double getElevation(int i, int j) {
	    	return elev[i][j];
	    }

	    public void setCellSize(double pCellSize) {
	    	cellSize = pCellSize;
	    }
	    
	    public double getCellSize() {
	    	return cellSize;
	    }
	    
	    public void setOrigin(double pOriginX, double pOriginY) {
	    	originX = pOriginX;
	    	originY = pOriginY;
	    }
	    
	    public double getOriginX() {
	    	return originX;
	    }

	    public double getOriginY() {
	    	return originY;
	    }
	   
	    public double getMinZ() {
	    	if (!updateZMinMaxNecessary) return zMin;
	    	
	    	zMin = elev[0][0];
	    	for (int i = 0; i < nx; i++) {
				for (int j = 0; j < ny; j++) {
					if (elev[i][j] < zMin)
						zMin = elev[i][j];
				}
	    	}

	    	updateZMinMaxNecessary = false;
	    	return zMin;
	    }

		public double getMaxZ() {
	    	if (!updateZMinMaxNecessary) return zMax;

	    	zMax = elev[0][0];
			for (int i = 0; i < nx; i++) {
				for (int j = 0; j < ny; j++) {
					if (elev[i][j] > zMax)
						zMax = elev[i][j];
				}
			}

			updateZMinMaxNecessary = false;
			return zMax;
		}
	}
	
	private void copyElevations(
		ElevationModelToolBox.DEM dem, RectangularSpace cellGrid) 
		throws Exception 
	{
		for (int i = 0; i < dem.nx; i++) {
			for (int j = 0; j < dem.ny; j++) {
				double val = dem.getElevation(i, j);
				cellGrid.getCell(i, j).setInitialValue(val);
			}
		}
	}
}
