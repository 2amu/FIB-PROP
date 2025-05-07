package Domini.Classes;

import java.util.Arrays;
import java.util.Random;

public class SimulatedAnnealing {

    // ---------------------------------- ATRIBUTS ---------------------------------- //

    // Instancia estatica perque sigui una classe singleton
    private static SimulatedAnnealing sa;

    private static final int k = 8;
    private static final int limit = 50;
    private static final double lambda = 0.0001;

    // ---------------------------------- METODES ---------------------------------- //

    // CONSTRUCTORA
    private SimulatedAnnealing() {}

    // Metode per obtenir la instancia d'una classe singleton.
    public static SimulatedAnnealing obtenirInstancia() {
        if(sa == null)
            sa = new SimulatedAnnealing();
        return sa;
    }

    //Metode d'optimitzacio QAP
    public int[] ejecutarSimulatedAnnealing(int[][] mat){// Matriz de tamaño alfabeto x alfabeto
        int steps=10000;

        int N = mat.length;

        double costmillor;
        int[] millor = new int[N];
        double[][] distancia = new double[N][N];
        crear_matdis(distancia);

        int[] permutacions = new int[N];
        Arrays.fill(permutacions,-1);

        // calcul cota inicial

        first_candidate(mat, permutacions); // Simbol inicial a partir del cual construir el teclat

        evolution(mat, permutacions);

        System.arraycopy(permutacions, 0, millor, 0, permutacions.length);
        costmillor = getHeuristicValue(millor, distancia, mat);

        Random rnd = new Random();

        for(int step = 0; step < steps; ++step) {
            double temp = getTemp(step);
            if (temp == 0.0) {
                break;
            }
            if (permutacions.length > 0) {
                int[]next = new int[N];
                System.arraycopy(permutacions, 0, next, 0, permutacions.length);

                int rand1 = rnd.nextInt(N);
                int rand2;
                do {
                    rand2 = rnd.nextInt(N);
                } while (rand1 == rand2);
                swap(rand1, rand2, next);
                double costnext = getHeuristicValue(next,distancia,mat);
                double deltaE = costnext - getHeuristicValue(permutacions,distancia,mat);
                double al = rnd.nextDouble();
                double prob = 1.0 / (1.0 + Math.exp(deltaE / temp));
                if (deltaE < 0.0 || al > prob) {
                    //permutacions = next;
                    System.arraycopy(next, 0, permutacions, 0, N);
                    if (costnext < costmillor) {
                        costmillor = costnext;
                        System.arraycopy(permutacions, 0, millor, 0, N);
                    }
                }
            }
        }
        return millor;
    }


    //Succesor -> genere solo 1 succesor escogido random

    // HEURISTIC VALUE - Coste trafico instalaciones ya colocadas
    private double getHeuristicValue(int[] perm, double[][] dist, int[][] matrix) {
        double res = 0;
        for(int i = 0; i < matrix.length; ++i){
            for(int j = 0; j < matrix[0].length; ++j){
                if(i != j && perm[i] != -1 && perm[j] != -1) res += matrix[i][j] * dist[perm[i]][perm[j]];
            }
        }

        return res;
    }

    //Funcio per crear matriu distancia
    private void crear_matdis(double[][] distancia){

        int[][] locations = new int[distancia.length][2];
        int dir = 0;
        int horitz = 0;
        int vert = 1;
        for(int k = 1; k < distancia.length; ++k){
            locations[k][0] = horitz;
            locations[k][1] = vert;
            if(dir == 0){
                ++horitz;
                --vert;
                if(vert == 0) dir = 1;
            }
            else if(dir == 1){
                --horitz;
                --vert;
                if(horitz == 0) dir = 2;
            }
            else if(dir == 2){
                --horitz;
                ++vert;
                if(vert == 0) dir = 3;
            }
            else{
                ++horitz;
                ++vert;
                if(horitz == 0){
                    dir = 0;
                    ++vert;
                }
            }
        }
        for(int i = 0; i < distancia.length; ++i){
            for(int j = i+1; j < distancia[0].length; ++j){
                double d = Math.sqrt(Math.pow(locations[i][0]-locations[j][0],2)+Math.pow(locations[i][1]-locations[j][1],2));
                distancia[i][j] = d;
                distancia[j][i] = d;
            }
        }
    }

    //Operador Swap

    private void swap(int idxi , int idxj, int[] perm){
        int aux = perm[idxi];
        perm[idxi] = perm[idxj];
        perm[idxj] = aux;
    }


    //Greedy

    private void evolution(int[][] matrix, int[] perm){ // Succesors al primer simbol
        int tam_perm = 1;
        while(tam_perm < matrix.length){
            int maxVal = -1;
            int indx = -1;
            for(int i = 0; i < matrix.length; ++i){
                if(perm[i] == -1){
                    int suma = 0;
                    for(int j = 0; j < matrix[0].length; ++j){
                        if(i != j && (perm[j] != -1)){
                            suma += matrix[i][j] + matrix[j][i];
                        }
                    }
                    if(suma > maxVal){
                        indx = i;
                        maxVal = suma;
                    }
                }
            }
            perm[indx] = tam_perm;
            ++tam_perm;
        }
    }


    private void first_candidate(int[][] matrix, int[] perm){ // Primer simbol
        int maxRowIndex = -1;
        int valueRowMax = -1;
        for (int row = 0; row < matrix.length; row++) {
            int simbolInteractions = 0;
            for (int col = 0; col < matrix[0].length; col++) {
                if (row != col){
                    simbolInteractions = simbolInteractions + matrix[row][col] + matrix[col][row];
                }
            }
            if(simbolInteractions > valueRowMax){
                valueRowMax = simbolInteractions;
                maxRowIndex = row;
            }
        }

        perm[maxRowIndex] = 0;
    }

    // Fi Greedy

    //Funcio per obtenir la temperatura

    private double getTemp(int t) {
        double tempD = (double)(t / limit * limit);
        int temp = (int)tempD;
        double res = (double)k * Math.exp(-1.0 * lambda * (double)temp);
        return res;
    }

}