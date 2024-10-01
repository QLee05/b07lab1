import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Polynomial{
    public double [] coefficients;
    public int [] exponents;

    public Polynomial(){
        this.coefficients = null;
        this.exponents = null;
    }

    public Polynomial(double [] C, int [] exp){
        int length = C.length;
        this.coefficients = new double [length];
        this.exponents = new int [length];
        for (int j=0; j < length; j++){
            this.coefficients[j] = C[j];
            this.exponents[j] = exp[j];
        }
    }

    public Polynomial(File file){
        try{
            Scanner input = new Scanner(file);
            String P = input.nextLine();

            boolean first = P.charAt(0) == '-';
            if (first){
                P = P.substring(1);
            }
            String[] T = P.split("-");
            /*for (int i = 0; i < T.length; i++){
                System.out.println(T[i]);
            }*/
            if (first){
                T[0] = '-' + T[0];
            }

            //System.out.println(first);
            for (int i = 0; i < T.length; i++){
                if (i == 0){
                    continue;
                }

                T[i] = '-' + T[i];
            }

            /*for (int i = 0; i < T.length; i++){
                System.out.println(T[i]);
            }*/
            ArrayList<Double> C = new ArrayList<Double>();
            ArrayList<Integer> E = new ArrayList<Integer>();

            for (int i = 0; i < T.length; i++){
                String[] T2 = T[i].split("\\+");
                for(int j = 0; j < T2.length; j++){
                    String[] T3 = T2[j].split("x");
                    double coef = Double.parseDouble(T3[0]);
                    C.add(coef);
                    if (T3.length == 1){
                        E.add(0);
                        continue;
                    }
                    int exp = Integer.parseInt(T3[1]);
                    E.add(exp);
                }
            }


            while (C.contains(0.0)){
                int ind = C.indexOf(0.0);
                C.remove(ind);
                E.remove(ind);
            }

            double[] RC = new double[C.size()];
            for (int i=0; i < C.size();i++){
                RC[i] = C.get(i);
            }

            int[] RE = new int[E.size()];
            for (int i = 0; i < E.size();i++){
                RE[i] = E.get(i);
            }
            this.coefficients = RC;
            this.exponents = RE;
        }
        catch (FileNotFoundException E){
            E.printStackTrace();
        }
    }

    public Polynomial add(Polynomial P){
        if (this.coefficients == null && P.coefficients == null){
            return new Polynomial();
        }
        if (this.coefficients == null){
            return new Polynomial(P.coefficients, P.exponents);
        }
        else if (P.coefficients == null){
            return new Polynomial(this.coefficients, this.exponents);
        }

        int length1 = this.coefficients.length;
        int length2 = P.coefficients.length;

        ArrayList<Double> C = new ArrayList<Double>();
        ArrayList<Integer> E = new ArrayList<Integer>();

        for (int i = 0; i < length1; i++){
            C.add(this.coefficients[i]);
            E.add(this.exponents[i]);
        }

        for (int i = 0; i < length2; i++){
            int ind = E.indexOf(P.exponents[i]);
            if (ind == -1){
                C.add(P.coefficients[i]);
                E.add(P.exponents[i]);
            }
            else{
                double old = C.get(ind);
                C.set(ind, old + P.coefficients[i]);
            }
        }

        while (C.contains(0.0)){
            int ind = C.indexOf(0.0);
            C.remove(ind);
            E.remove(ind);
        }

        double[] RC = new double[C.size()];
        for (int i=0; i < C.size();i++){
            RC[i] = C.get(i);
        }

        int[] RE = new int[E.size()];
        for (int i = 0; i < E.size();i++){
            RE[i] = E.get(i);
        }

        return new Polynomial(RC, RE);
    }

    public double evaluate(double x){
        if (this.coefficients == null){
            return 0;
        }
        double total = 0.0;
        for (int i = 0; i < this.coefficients.length; i++){
            total += this.coefficients[i]*(Math.pow(x,this.exponents[i]));
        }
        return total;
    }

    public boolean hasRoot(double x){
        return this.evaluate(x) == 0;
    }

    public Polynomial multiply(Polynomial P){
        if (this.coefficients == null || P.coefficients == null){
            return new Polynomial();
        }

        int length1 = this.coefficients.length;
        int length2 = P.coefficients.length;


        Polynomial[] results = new Polynomial[length1];
        for (int i = 0; i < length1; i++){

            ArrayList<Double> C = new ArrayList<Double>();
            ArrayList<Integer> E = new ArrayList<Integer>();

            for (int j = 0; j < length2; j++){
                C.add(this.coefficients[i]*P.coefficients[j]);
                E.add(this.exponents[i] + P.exponents[j]);
            }

            while (C.contains(0.0)){
                int ind = C.indexOf(0.0);
                C.remove(ind);
                E.remove(ind);
            }

            double[] RC = new double[C.size()];
            for (int k=0; k < C.size();k++){
                RC[k] = C.get(k);
            }

            int[] RE = new int[E.size()];
            for (int k = 0; k < E.size();k++) {
                RE[k] = E.get(k);
            }

            results[i] = new Polynomial(RC, RE);
        }

        Polynomial finalP = new Polynomial();

        for (int i = 0; i < length1; i++){
            finalP = finalP.add(results[i]);
        }
        return finalP;
    }

    public void saveToFile(String file){
        try{
            FileWriter output = new FileWriter(file, false);
            if (this.coefficients == null){
                output.write("0");
                return;
            }
            if (this.exponents[0] != 0){
                output.write(this.coefficients[0] + "x" + this.exponents[0]);
            }
            else{
                output.write(""+this.coefficients[0]);
            }
            for (int i = 0; i < this.coefficients.length; i++){
                if (i == 0){
                    continue;
                }
                double C = this.coefficients[i];
                int E = this.exponents[i];
                // C != 0 is not a case because valid input is assumed
                if (C > 0 && E != 0){
                    output.write("+" + C + "x" + E);
                }
                else if (C > 0 && E == 0){
                    output.write("+" + C);
                }
                else if (C < 0 && E != 0){
                    output.write(C + "x" + E);
                }
                else if (C < 0 && E == 0){
                    output.write("" + C);
                }
            }

            output.close();
        }
        catch (IOException E){
            E.printStackTrace();
        }
    }
}
