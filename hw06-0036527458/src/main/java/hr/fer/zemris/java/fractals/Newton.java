package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class Newton {
	
	private static ComplexRootedPolynomial rootedPolynomial;
	private static ComplexPolynomial polynomial;
	
	private static double getIm(String userInput) {
		String imaginary = "";
		for(char c : userInput.toCharArray())
			if(c != 'i')
				imaginary += c;
		return Double.parseDouble(imaginary);
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int number = 0;
		
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		List<Complex> list = new ArrayList<>();
		Complex constant = null;
		boolean first = true;
		
		while(true) {
			number++;
			System.out.printf("Root %d> ", number);
			
			String[] userInput = sc.nextLine().split(" ");
			
			if(userInput[0].equals("done"))
				break;
			
			double re = 0;
			double im = 0;
			
			if(userInput.length == 1) {
				boolean hasI = false;
				
				for(char c : userInput[0].toCharArray())
					if(c == 'i') {
						hasI = true;
						break;
					}
				
				if(!hasI)
					re = Double.parseDouble(userInput[0]);
				else {
					if(userInput[0].charAt(0) != '-')
						if(userInput[0].toCharArray().length == 1)
							im = 1.0;
						else
							im = getIm(userInput[0]);
					else
						if(userInput[0].toCharArray().length == 2)
							im = -1.0;
						else
							im = -getIm(userInput[0]);
				}
			} else if(userInput.length == 3 && userInput[1].equals("+")) {
				re = Double.parseDouble(userInput[0]);
				im = getIm(userInput[2]);
			} else if(userInput.length == 3 && userInput[1].equals("-")) {
				re = Double.parseDouble(userInput[0]);
				im = -getIm(userInput[2]);
			} else {
				System.out.println("Wrong input!");
				continue;
			}
			
			Complex complex = new Complex(re, im);
			
			if(first) {
				constant = new Complex(1, 0);
				first = false;
			}
			
			list.add(complex);
		}
		
		sc.close();
		
		Complex[] complexArray = new Complex[list.size()];
		for(int i = 0; i < list.size(); i++)
			complexArray[i] = list.get(i);
		
		rootedPolynomial = new ComplexRootedPolynomial(constant, complexArray);
		polynomial = rootedPolynomial.toComplexPolynom();
		
		System.out.println("Image of fractal will appear shortly. Thank you.");
		
		FractalViewer.show(new MojProducer());
	}
	
	public static class MojProducer implements IFractalProducer {

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo,
				IFractalResultObserver observer, AtomicBoolean cancel) {
			
			System.out.println("Zapocinjem izracun...");

			int m = 16*16*16;
			int offset = 0;
			short[] data = new short[width * height];
			
			// for(y in ymin to ymax)
			for(int y = 0; y < height; y++) {
				if(cancel.get())
					break;
				
				// for(x in xmin to xmax)
				for(int x = 0; x < width; x++) {
					// c = map_to_complex_plain(x, y, xmin, xmax, ymin, ymax, remin, remax, immin, immax);
					double cre = x / (width-1.0) * (reMax - reMin) + reMin;
					double cim = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;
					double module = 0;
					Complex c = new Complex(cre, cim);
					Complex zn = c;
					int iters = 0;
					double convergenceTreshold = 0.001;
					double rootTreshold = 0.002;
					do {
						Complex znold = zn;
						Complex numerator = polynomial.apply(zn);
						Complex denominator = polynomial.derive().apply(zn);
						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = znold.sub(zn).module();
						iters++;
					} while(iters < m && module > convergenceTreshold);
					short index = (short) rootedPolynomial.indexOfClosestRootFor(zn, rootTreshold);
					data[offset++] = (short) (index + 1);
				}
			}
			
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
			
		}
		
	}
}
