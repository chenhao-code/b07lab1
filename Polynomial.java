
import java.util.Arrays;
import java.io.File;
import java.io.IOException;
import java.io.*;
public class Polynomial {
	double[] coefficients;
	int[] exponents;

	public Polynomial() {
		coefficients = new double[0];
		exponents = new int[0];
	}

	public Polynomial(double[] coefficients, int[] exponents) {
		this.coefficients = coefficients;
		this.exponents = exponents;
	}


	public int getMax(int[] array1, int[] array2) {
		int max1 = 0;
		int max2 = 0;
		//get the max number in array1
		for (int i = 0; i < array1.length; i++) {
			if (array1[i] > max1)
				max1 = array1[i];
		}
		//get the max number in array2
		for (int i = 0; i < array2.length; i++) {
			if (array2[i] > max2)
				max2 = array2[i];
		}
		int max = Math.max(max1, max2);
		return max;
	}
	public int getMax_mul(int[] array1, int[] array2){
		int counter = 0;
		for (int i = 0; i < array1.length; i++){
			for(int j = 0; j < array2.length; j++){
				int adder = array1[i] + array2[j];
				if(adder > counter)
					counter=adder;
			}
		}

		return counter;

	}

	public Polynomial mergePolynomials(Polynomial obj) {
		//check if the length of exponents is empty in two polynomials, then return the other one
		Polynomial mergedPoly = null;
		if (this.exponents.length == 0)
			mergedPoly = new Polynomial(obj.coefficients, obj.exponents);
		if (obj.exponents.length == 0)
			mergedPoly = new Polynomial(this.coefficients, this.exponents);
		return mergedPoly;
	}

	public Polynomial removeZeroTerms(double[] coe) {
		int counter = 0;
		for (int i = 0; i < coe.length; i++) {
			if (coe[i] != 0) {
				counter++;
			}
		}
		//create two new arrays to store exp and coe
		double[] newCoefficients = new double[counter];
		int[] newExponents = new int[counter];
		int pos = 0;

		for (int i = 0; i < coe.length; i++) {
			if (coe[i] != 0) {
				newCoefficients[pos] = coe[i];
				newExponents[pos] = i;
				pos++;
			}
		}

		return new Polynomial(newCoefficients, newExponents);
	}

	public Polynomial add(Polynomial obj) {
		mergePolynomials(obj);
		//call getMax method to get the max exponents in two polynomials
		int max_exp = getMax(obj.exponents, this.exponents);
		//set a new array to have max number of coe
		//ex. -2x+6+5x^3 && 5+6x^4 => [0,0,0,0,0]
		double[] summed_coe = new double[max_exp + 1];

		//goal: summed_coe -----> [6,-2,0,5,0]
		for (int i = 0; i < this.coefficients.length; i++)
			summed_coe[this.exponents[i]] += this.coefficients[i];

		//goal: summed_coe -----> [11,-2,0,5,6] => add coefficients of two polynomials together
		for (int i = 0; i < obj.coefficients.length; i++)
			summed_coe[obj.exponents[i]] += obj.coefficients[i];
		return removeZeroTerms(summed_coe);
	}


	public Polynomial multiply(Polynomial obj) {
		//check if the length of exponents is empty in two polynomials, then return the other one
		mergePolynomials(obj);
		//call getMax method to get the max exponents in two polynomials
		int max_exp = getMax_mul(obj.exponents, this.exponents);
		//set a new array to have max number of coe
		//ex. 6-2x+5x^3 && 5+6x^4 ------> [6,-2,0,5] && [5,0,0,0,6] ----> [0,0,0,0,0,0]
		double[] mul_coe = new double[max_exp+1];
		//goal: mul_coe -----> [6,-2,0,5,0,0,0]
		int[] mul_exp = new int[max_exp+1];
		for(int i = 0; i < this.exponents.length; i++){
			for(int j = 0; j < obj.exponents.length; j++){
				int index = this.exponents[i] + obj.exponents[j];
				mul_exp[index] = this.exponents[i] + obj.exponents[j];
				mul_coe[index] += this.coefficients[i] * obj.coefficients[j];
			}
		}

		int num = 0;
		for(int i = 0; i < mul_coe.length; i++){
			if(mul_coe[i] != 0)
				num++;
		
		}
		//System.out.println(num);
		double[] new_coe = new double[num];
		int[] new_exp = new int[num];
		int pos = 0;
		for(int i = 0; i < mul_coe.length; i++){
			if((mul_coe[i] != 0 || mul_exp[i] != 0)){
				new_coe[pos] = mul_coe[i];
				new_exp[pos] = mul_exp[i];
				pos++;
			}
		}
		return new Polynomial(new_coe,new_exp);


	}

	public double evaluate(double x) {
		double result = 0.0;
		for (int i = 0; i < this.coefficients.length; i++)
			result += this.coefficients[i] * Math.pow(x, this.exponents[i]);
		return result;
	}

	public boolean hasRoot(double root) {
		return evaluate(root) == 0.0;
	}

	public Polynomial(File file) throws IOException {
		BufferedReader input = new BufferedReader(new FileReader(file));
		String line = input.readLine();
		input.close();

		// line => "5-3x^2+7x^8"
		// arr -> ["5", "-3x^2", "7x^8"]
		// replace - with +- then using split
		line = line.replaceAll("\\s", "");
		String[] arr = line.split("(?=[+-])");

		this.exponents = new int[arr.length];
		this.coefficients = new double[arr.length];

		for (int i = 0; i < arr.length; i++) {
			String term = arr[i];

			// check if the term starts with "+" or "-"
			boolean isNegative = term.startsWith("-");

			// remove the "+" or "-" sign from the term
			term = term.substring(1);

			// split each term using the "x^" character sequence as the delimiter
			// "-3x^2" => ["-3", "2"]
			String[] ceoExp = term.split("x\\^");

			// The try block is wrapped around these parsing statements because parsing operations
			// like Double.parseDouble and Integer.parseInt can potentially throw a NumberFormatException
			// if the string being parsed does not represent a valid numeric value.
			try {
				this.coefficients[i] = ceoExp[0].isEmpty() ? 1.0 : Double.parseDouble(ceoExp[0]) * (isNegative ? -1 : 1);
				this.exponents[i] = (ceoExp.length == 1 || ceoExp[1].isEmpty()) ? 1 : Integer.parseInt(ceoExp[1]);
			} catch (NumberFormatException e) {
				// Handle the exception
				System.err.println("Invalid coefficient or exponent: " + e.getMessage());
				// Set default values or take appropriate action
				this.coefficients[i] = 0.0;
				this.exponents[i] = 0;			}
		}
	}


	public void saveToFile(String filename) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		StringBuilder build = new StringBuilder();

		// Add the leading coefficient if it is non-zero
		if (coefficients.length > 0 && coefficients[0] != 0) {
			build.append(coefficients[0]);
		}

		// Build the rest of the polynomial representation
		for (int i = 1; i < coefficients.length; i++) {
			double coefficient = coefficients[i];
			int exponent = exponents[i];

			if (coefficient != 0) {
				if (coefficient > 0 && build.length() > 0) {
					build.append("+"); // Add "+" sign for positive coefficients
				}

				if (coefficient != 1) {
					build.append(coefficient);
				}

				if (exponent != 0) {
					build.append("x^").append(exponent);
				}
			}
		}

		// Write the polynomial representation to the file
		writer.write(build.toString());
		writer.close();
	}
	public String toStringFromFile(String filename) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		StringBuilder stringBuilder = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
		}
		reader.close();
		return stringBuilder.toString();
	}


}