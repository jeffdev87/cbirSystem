package main.java.com.cbir;

/**
 *
 * @author Jefferson William Teixeira
 */
public class DistanceFunctions {
    
    public static double distEuclidiana(double vetPrin[], double vetSec[], int dim) {
        double soma = distManhatan(vetPrin, vetSec, dim);
        return (Math.sqrt(soma));
    }

    public static double distManhatan(double vetPrin[], double vetSec[], int dim) {
        double soma = 0;
        for (int i = 0; i < dim; i++)
            soma += Math.pow((vetPrin[i] - vetSec[i]), 2);
        return (soma);
    }

    public static double[] parseSringToDouble (String vet[], int dim) throws Exception {
        double aux[] = new double[dim];
        
        for (int i = 0; i < dim; i++)
            aux[i] = Double.parseDouble(vet[i]);

        return aux;
    }
}
