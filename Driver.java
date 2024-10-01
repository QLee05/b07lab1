import java.io.File;
import java.io.FileNotFoundException;

public class Driver {
    public static void main(String [] args) throws FileNotFoundException{
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double [] c1 = {6,5};
        int [] e1 = {0, 1};
        Polynomial p1 = new Polynomial(c1, e1);
        double [] c2 = {-2, -5};
        int [] e2 = {5, 1};
        Polynomial p2 = new Polynomial(c2, e2);
        Polynomial s = p1.add(p2);
        System.out.println("s(1) = " + s.evaluate(1));
        System.out.println("s's length:" + s.coefficients.length);
        if(s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");
        double [] c3 = {1, -4};
        int [] e3 = {2, 0};
        Polynomial p3 = new Polynomial(c3, e3);
        if (p3.hasRoot(-3)){
            System.out.println("-3 is a root of p3");
        }
        else{
            System.out.println("-3 is not a root of p3");
        }

        Polynomial m = s.multiply(p3);
        for (int i = 0; i < 4; i++){
            System.out.println("m's c, e " + m.coefficients[i] + " " + m.exponents[i]);
        }


        Polynomial filep = new Polynomial(new File("C:\\Users\\quinc\\b07lab1\\polynomial.txt"));
        Polynomial Q = filep.add(m);
        Polynomial Qtimess = Q.multiply(s);
        for (int i =0; i < Qtimess.coefficients.length; i++){
            System.out.println("please c, e " + Qtimess.coefficients[i] + " " + Qtimess.exponents[i]);
        }

        Qtimess.saveToFile("C:\\Users\\quinc\\b07lab1\\polynomial.txt");
    }