import java.io.IOException;
import java.io.File;
public class Driver {
    public static void main(String[] args) throws IOException {
        double[] c1 = {2, 5, 3};
        int[] e1 = {0, 1, 2};
        Polynomial p1 = new Polynomial(c1, e1);

        double[] c2 = {1, 3};
        int[] e2 = {0, 1};
        Polynomial p2 = new Polynomial(c2, e2);

        System.out.println("testing add");
        Polynomial a1 = p1.add(p2);

        for (int i = 0; i < a1.coefficients.length; i++)
            System.out.print(a1.coefficients[i] + " ");
        System.out.println();

        for (int i = 0; i < a1.exponents.length; i++)
            System.out.print(a1.exponents[i] + " ");
        System.out.println();

        System.out.println("testing multiply");
        Polynomial m2 = p1.multiply(p2);

        for (int i = 0; i < m2.coefficients.length; i++)
            System.out.print(m2.coefficients[i] + " ");
        System.out.println();

        for (int i = 0; i < m2.exponents.length; i++)
            System.out.print(m2.exponents[i] + " ");
        System.out.println();


        System.out.println("testing evaluate");
        Polynomial s = p1.add(p2);

        System.out.println("s(0.1) = " + s.evaluate(0.1));

        if(s.hasRoot(1))

            System.out.println("1 is a root of s");

        else

            System.out.println("1 is not a root of s");
            p1.saveToFile("output.txt");

            File file = new File("output.txt");


        Polynomial polynomial = new Polynomial();
        String polynomialString = polynomial.toStringFromFile("output.txt");
        System.out.println(polynomialString);


    }
}