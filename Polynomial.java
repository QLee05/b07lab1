public class Polynomial{
    public double [] coefficients;

    public Polynomial(){
        coefficients = new double[1];
        coefficients[0] = 0;
    }

    public Polynomial(double [] array){
        int length = array.length;
        this.coefficients = new double [length];
        for (int i=0; i< length; i++){
            this.coefficients[i] = array[i];
        }
    }

    public Polynomial add(Polynomial P){
        int length1 = this.coefficients.length;
        int length2 = P.coefficients.length;
        
        if (length1 <= length2){
            
            double [] A = new double [length2];
            for (int i = 0; i < length1; i++){
                A[i] = P.coefficients[i] + this.coefficients[i];
            }

            for (int i = length1; i < length2; i++){
                A[i] = P.coefficients[i];
            }
            Polynomial result = new Polynomial(A);
            return result;
        }
        
        else{
            double [] A = new double [length1];
            for (int i = 0; i < length2; i++){
                A[i] = P.coefficients[i] + this.coefficients[i];
            }

            for (int i = length2; i < length1; i++){
                A[i] = this.coefficients[i];
            }
            Polynomial result = new Polynomial(A);
            return result;
        }

    }

    public double evaluate(double x){
        int length = this.coefficients.length;
        double total = 0;
        for (int i = 0; i < length; i++){
            total += this.coefficients[i]*(Math.pow(x, i));
        }

        return total;
    }

    public boolean hasRoot(double x){
        return this.evaluate(x) == 0;
    }
}