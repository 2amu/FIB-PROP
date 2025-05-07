package Domini.Classes;

import java.util.ArrayList;
import java.awt.Point;
import java.util.Arrays;
import java.util.Queue;

import java.util.LinkedList;

public class QAP {

    // ---------------------------------- ATRIBUTS ---------------------------------- //

    // Instancia estatica perque sigui una classe singleton
    private static QAP qap;

    int[][] matrix; // Cal guardar version original
    double[][] distancia; // Nomes cal fer-ho un cop

    int[] permutacions; // "Millor solucio"
    double CostCota; // cost "Millor solucio"

    int N; // num lineas -> s'ha donat per suposat que matriu es cuadrada

    int tamSol; //nomes cal per hungarian

    int[] SquareInRow; //nomes cal per hungarian
    int[] SquareInCol; //nomes cal per hungarian

    // ---------------------------------- METODES ---------------------------------- //

    // CONSTRUCTORA
    private QAP() {
    }

    // Metode per obtenir la instancia d'una classe singleton.
    public static QAP obtenirInstancia() {
        if(qap == null)
            qap = new QAP();
        return qap;
    }

    //Metode d'optimitzacio QAP
    public int[] ejecutarQAP(int[][] mat) {

        CostCota = Integer.MAX_VALUE; // crec que no cal
        N = mat.length;

        matrix = mat;

        permutacions = new int[N];
        Arrays.fill(permutacions,-1);

        // No cal fer-ho aqui
        crear_matdis();

        // calcul cota inicial

        first_candidate(); // Simbol inicial a partir del cual construir el teclat

        evolution();

        //cost cota greedy
        CostCota = Primer_terme(permutacions,distancia);

        //Branch & Bound

        Queue<Node> cola = new LinkedList<>();
        cola.add(new Node(N));

        while(!cola.isEmpty()){
            Node nodo = cola.poll();
            int i = nodo.consultar_colocats();
            if(i == N && (nodo.consultar_cost() < CostCota)){
                //best value
                permutacions = nodo.consultar_totperm();
                CostCota = nodo.consultar_cost();

            }
            else if(nodo.consultar_level() < N){
                for(int k = 0; k < N; ++k){
                    if(nodo.consultar_perm(k) == -1){ //potser es poden reduir iteracions
                        int[] nou_perm = Arrays.copyOf(nodo.consultar_totperm(), nodo.consultar_totperm_length());
                        nou_perm[k] = nodo.consultar_level();
                        double prim_t = Primer_terme(nou_perm, distancia);

                        //si esta complet no cal segon_tercer terme
                        int colocats = nodo.consultar_colocats() + 1;
                        double segon_tercer_t = 0;
                        if(colocats < N) segon_tercer_t = Hungarian_Algorithm(crear_mathung(nou_perm,colocats));

                        double cost_total = prim_t + segon_tercer_t;
                        if(CostCota > cost_total){
                            cola.add(new Node(nou_perm,cost_total,colocats,nodo.consultar_level()+1));
                        }
                    }
                }
            }
        }

        return permutacions;
    }



    //Greedy

    private void evolution(){ // Succesors al primer simbol
        int tam_perm = 1;
        while(tam_perm < N){
            int maxVal = -1;
            int indx = -1;
            for(int i = 0; i < N; ++i){
                if(permutacions[i] == -1){
                    int suma = 0;
                    for(int j = 0; j < N; ++j){
                        if(i != j && (permutacions[j] != -1)){
                            suma += matrix[i][j] + matrix[j][i];
                        }
                    }
                    if(suma > maxVal){
                        indx = i;
                        maxVal = suma;
                    }
                }
            }
            permutacions[indx] = tam_perm;
            ++tam_perm;
        }
    }


    private void first_candidate(){ // Primer simbol
        int maxRowIndex = -1;
        int valueRowMax = -1;
        for (int row = 0; row < N; row++) {
            int simbolInteractions = 0;
            for (int col = 0; col < N; col++) {
                if (row != col){
                    simbolInteractions = simbolInteractions + matrix[row][col] + matrix[col][row];
                }
            }
            if(simbolInteractions > valueRowMax){
                valueRowMax = simbolInteractions;
                maxRowIndex = row;
            }
        }

        permutacions[maxRowIndex] = 0;
    }

    // Fi Greedy

    //Branch & Bound
    private double Primer_terme(int[] perm, double[][] dist){ // Optimizable -> simetria
        double res = 0;
        for(int i = 0; i < N; ++i){
            for(int j = 0; j < N; ++j){
                if(i != j && perm[i] != -1 && perm[j] != -1) res += matrix[i][j] * dist[perm[i]][perm[j]];
            }
        }

        return res;
    }

    private void crear_matdis(){
        distancia = new double[N][N];

        int[][] locations = new int[N][2];
        int dir = 0;
        int horitz = 0;
        int vert = 1;
        for(int k = 1; k < N; ++k){
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
        for(int i = 0; i < N; ++i){
            for(int j = i+1; j < N; ++j){
                double d = Math.sqrt(Math.pow(locations[i][0]-locations[j][0],2)+Math.pow(locations[i][1]-locations[j][1],2));
                distancia[i][j] = d;
                distancia[j][i] = d;
            }
        }
    }

    private double cost_newnode(int[] perm, double[][] dist, int indx){ // Optimizable -> simetria
        double res = 0;

        for(int i = 0; i < N; ++i){ // index, instalacio colocada en una ubicacio
            if(i != indx && perm[i] != -1 && perm[indx] != -1){
                res += matrix[i][indx] * dist[perm[i]][perm[indx]];
                res += matrix[indx][i] * dist[perm[indx]][perm[i]];
            }
        }
        return res;
    }

    private double[][] crear_mathung(int[] perm, int colocats){
        int rows = N-colocats;
        double[][] mathung = new double[rows][rows];

        int[] perm_2 = new int[perm.length];
        System.arraycopy(perm, 0, perm_2, 0, perm.length);

        ArrayList<Integer> no_colocat = new ArrayList<>();// No colocat representa les instalacions no colocades
        ArrayList<Integer> libres = new ArrayList<>(); // representa les ubicacions lliures
        for(int w = 0; w < N ; ++w){
            libres.add(w);
        }

        for(int k = 0; k < N; ++k){
            if(perm[k] == -1) no_colocat.add(k);
            else libres.remove(Integer.valueOf(perm[k]));
        }

        for(int i = 0; i < rows; ++i){
            int indx = no_colocat.get(i);
            // Trobar 1r no colocat
            for(int j = 0; j < rows; ++j){
                perm_2[indx] = libres.get(j); //"ubicacions lliure";
                double nou_cost = cost_newnode(perm_2,distancia, indx);
                perm_2[indx] = -1;
                mathung[i][j] = nou_cost;
            }
        }

        return mathung;
    }

    // Fi B&B

    // Hungarian

    private void Treure_minims(double[][] mat){

        for(int row = 0; row < mat.length; ++row){//cada fila
            double min = Double.MAX_VALUE;
            for(int j = 0; j < mat[0].length; ++j){
                if(mat[row][j] < min) min = mat[row][j];
            }
            for(int z = 0; z < mat[0].length; ++z){
                mat[row][z] -= min;
            }
        }

        for(int col = 0; col < mat.length; ++col){//cada columna
            double min = Double.MAX_VALUE;
            for(int j = 0; j < mat[0].length; ++j){
                if(mat[j][col] < min) min = mat[j][col];
            }
            for(int z = 0; z < mat[0].length; ++z){
                mat[z][col] = mat[z][col]-min;
            }
        }
    }

    private void Lineas_minimo(double[][] mat){
        int[] rowHasSquare = new int[mat.length];
        int[] colHasSquare = new int[mat[0].length];
        tamSol = 0;
        ArrayList<Point> fasig = new ArrayList<>();

        Asig_zeros(mat, fasig, 0, rowHasSquare, colHasSquare);
    }

    private void Asig_zeros(double[][] mat, ArrayList<Point> fasig, int level, int[] rowHasSquare,int[] colHasSquare){ // Pot ser mes eficient
        if(fasig.size() > tamSol){
            tamSol = fasig.size();
            SquareInRow = new int[mat.length];
            SquareInCol = new int[mat[0].length];
            Arrays.fill(SquareInRow,-1);
            Arrays.fill(SquareInCol,-1);
            for(int k = 0; k < tamSol; ++k){
                Point pos = fasig.get(k);
                SquareInRow[(int)pos.getY()] = (int)pos.getX();
                SquareInCol[(int)pos.getX()] = (int)pos.getY();
            }
            if(tamSol == mat.length) return;
        }

        for(int i = level; i < mat.length; ++i){
            for(int j = 0; j < mat[0].length; ++j){
                if(mat[i][j] == 0 && rowHasSquare[i] == 0 && colHasSquare[j] == 0){
                    fasig.add(new Point(j,i));
                    rowHasSquare[i] = 1;
                    colHasSquare[j] = 1;
                    Asig_zeros(mat, fasig, i+1, rowHasSquare,colHasSquare);
                    rowHasSquare[i] = 0;
                    colHasSquare[j] = 0;
                    fasig.remove(fasig.size()-1);
                }
            }
        }
    }

    private void MarcarCol(double[][] mat, int[] MarcColumnes, int[] MarcFiles){ // em podria saltar columnes
        int variacions = 0;
        for(int i = 0; i < mat.length; ++i){
            for(int j = 0; j < mat[0].length; ++j){
                if(mat[i][j] == 0 && MarcFiles[i] == 1 && MarcColumnes[j] == 0){
                    MarcColumnes[j] = 1;
                    ++variacions;
                }
            }
        }

        if(variacions != 0) MarcarFiles(mat,MarcColumnes,MarcFiles);
    }

    private void MarcarFiles(double[][] mat, int[] MarcColumnes, int[] MarcFiles){ // em podria saltar columnes
        int variacions = 0;
        for(int i = 0; i < mat.length; ++i){
            for(int j = 0; j < mat[0].length; ++j){
                if(mat[i][j] == 0 && MarcFiles[i] == 0 && MarcColumnes[j] == 1 && (SquareInRow[i] == j)){
                    MarcFiles[i] = 1;
                    ++variacions;
                }
            }
        }

        if(variacions != 0) MarcarCol(mat,MarcColumnes,MarcFiles);
    }

    private double ValorMinNoCobert(double [][] mat, int[] MarcColumnes, int[] MarcFiles){
        double minUncoveredValue = Double.MAX_VALUE;

        for (int i = 0; i < mat.length; ++i) {
            for (int j = 0; j < mat[0].length; ++j) {
                if (MarcFiles[i] == 1 && MarcColumnes[j] == 0 && (minUncoveredValue > mat[i][j])) {
                    minUncoveredValue = mat[i][j];
                }
            }
        }
        return minUncoveredValue;
    }

    private void AjustarMatriu(double[][] mat, int[] MarcColumnes, int[] MarcFiles, double valMin){
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if (MarcFiles[i] == 0 && MarcColumnes[j] == 1) {
                    mat[i][j] += valMin;
                }
                else if (MarcColumnes[j] == 0 && MarcFiles[i] == 1) {
                    mat[i][j] -= valMin;
                }
            }
        }

    }

    private void PerCobrir(double[][] mat){
        //marcar files sense assignació ( els -1 )
        // repetir seguents 2 passos
        //marcar columnes amb algun zero en una fila marcada
        //marcar files asignacio en columna marcada

        int[] MarcFiles = new int[mat.length];
        for(int i = 0; i < mat.length; ++i) if(SquareInRow[i] == -1) MarcFiles[i] = 1;
        int[] MarcColumnes = new int[mat[0].length];

        MarcarCol(mat,MarcColumnes, MarcFiles);

        double valMin = ValorMinNoCobert(mat,MarcColumnes,MarcFiles);

        AjustarMatriu(mat, MarcColumnes, MarcFiles, valMin);
    }

    private double Hungarian_Algorithm(double[][] mat){

        double[][] mat_original = new double[mat.length][mat[0].length];

        for(int z = 0; z < mat.length; ++z){
            for(int d = 0; d < mat[0].length; ++d){
                mat_original[z][d] = mat[z][d];
            }
        }

        Treure_minims(mat);

        SquareInRow = new int[N];
        SquareInCol = new int[N];

        Lineas_minimo(mat);

        if(tamSol == mat.length){
            double res = 0;
            for(int i = 0; i < mat.length; ++i){
                res += mat_original[i][SquareInRow[i]];
            }
            return res;
        }
        else { // Minim linias per recobrir
            while(tamSol < mat.length){
                PerCobrir(mat);
                Lineas_minimo(mat);
            }

            double res = 0;
            for(int i = 0; i < mat.length; ++i){
                res += mat_original[i][SquareInRow[i]];
            }
            //System.out.println("coste total -> "+res);
            return res;
        }
    }

    // Fi Hungarian
}