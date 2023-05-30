public class Polynomial {
	double[] coefficients;
	public Polynomial() {
		coefficients = new double[0];
	}
	public Polynomial(double[] coeffs) {
		this.coefficients = coeffs;
	}
	public Polynomial add(Polynomial obj) {
		double[] coeffs;
		int len_a = this.coefficients.length;
		int len_b = obj.coefficients.length;
		if(len_a>len_b)
			coeffs = new double[len_a];
		else
			coeffs = new double[len_b];
		int i = 0;
		while (i < len_a){
			coeffs[i] += this.coefficients[i];
			i++;
		}
		int j = 0;
		while (j < len_b){
			coeffs[j] += obj.coefficients[j];
			j++;
		}
		Polynomial res = new Polynomial(coeffs);
		return res;
	}
	public double evaluate(double x) {
		double result = 0.0;
		for(int i=0; i<this.coefficients.length; i++)
			result += this.coefficients[i] * Math.pow(x, i);
		return result;
	}
	public boolean hasRoot(double root) {
		return evaluate(root) == 0.0;
	}
	
}
