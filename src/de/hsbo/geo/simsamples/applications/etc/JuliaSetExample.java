package de.hsbo.geo.simsamples.applications.etc;

/**
 * Simple calculation of the Julia set for the complex number <tt>C</tt>.
 * 
 * @author Benno Schmidt
 */
public class JuliaSetExample {

	Complex C = new Complex(-1.0, 0.0);
	// Range: 
	Complex 
		x1 = new Complex(-2.2, -1.65),
		x2 = new Complex(2.2, 1.65);
	// Image size:
	int width = 75, height = 20;
	
	public class Complex {
		protected double re, im;
		public Complex(double re, double im) { 
			this.re = re; this.im = im; 
		}
		public Complex add(Complex x) {
			return new Complex(this.re + x.re, this.im + x.im);
		}
		public Complex mult(Complex x) {
			return new Complex(this.re * x.re - this.im * x.im, this.re * x.im + this.im * x.re);
		}
		public double abs() {
			return Math.sqrt(this.re * this.re + this.im * this.im);
		}
		public String toString() {
			StringBuffer buf = new StringBuffer();
			buf.append(this.re);
			if (this.im >= 0.0) buf.append("+");
			buf.append(this.im).append("i");
			return buf.toString();
		}
	}
	
	public boolean check(Complex x) {
		for (int k = 0; k < 10; k++)
			x = x.mult(x).add(C);
		return x.abs() > 10.0 ? false : true;
	}
	
	public static void main(String[] args) {
		new JuliaSetExample().run();
	}
	
	public void run() {
		System.out.println("Generating Julia set for C = " + C + "...");
		for (int i = 0; i <= height - 1; i++) {
			for (int j = 0; j <= width - 1; j++) {
				Complex x = new Complex(
						(((double) j) / (width - 1)) * (x2.re - x1.re) + x1.re,
						(((double) i) / (height - 1)) * (x1.im - x2.im) + x2.im); 
				System.out.print(check(x) ? "X" : ".");
			}
			System.out.println();
		}
	}
}
