package de.hsbo.geo.simsamples.applications.etc;

import de.hsbo.geo.simsamples.applications.etc.JuliaSetExample.Complex;

/**
 * Simple calculation of the Mandelbrot set for the complex start value 0.
 * 
 * @author Benno Schmidt
 */
public class MandelbrotSetExample {

	// Range: 
	Complex 
		C1 = new JuliaSetExample().new Complex(-2.0, -1.5),
		C2 = new JuliaSetExample().new Complex(1.0, 1.5);
	// Image size:
	int width = 75, height = 20;
	
	public boolean check(Complex C) {
		Complex x = new JuliaSetExample().new Complex(0.0, 0.0);
		for (int k = 0; k < 10; k++) 
			x = x.mult(x).add(C);
		return x.abs() > 10.0 ? false : true;
	}
	
	public static void main(String[] args) {
		new MandelbrotSetExample().run();
	}
	
	public void run() {		
		for (int i = 0; i <= height - 1; i++) {
			for (int j = 0; j <= width - 1; j++) {
				Complex C = new JuliaSetExample().new Complex(
						(((double) j) / (width - 1)) * (C2.re - C1.re) + C1.re,
						(((double) i) / (height - 1)) * (C1.im - C2.im) + C2.im); 
				System.out.print(check(C) ? "X" : ".");
			}
			System.out.println();
		}
	}
}
