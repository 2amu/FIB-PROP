package Domini.Classes;

import java.util.Arrays;

public class Node {
    int[] permutacions;
    double CostCota;
    int colocats;
    int level;


    public Node(int n){
        permutacions = new int[n];
        Arrays.fill(permutacions,-1);
        CostCota = 0;
        colocats = 0;
        level = 0;
    }

    public Node(int[] p, double cost,int col, int lev){
        permutacions = p;
        CostCota = cost;
        colocats = col;
        level = lev;
    }

    public int consultar_perm(int i){
        return permutacions[i];
    }

    public int[] consultar_totperm(){
        return permutacions;
    }

    public int consultar_totperm_length(){
        return permutacions.length;
    }

    public int consultar_level(){
        return level;
    }

    public double consultar_cost(){
        return CostCota;
    }

    public int consultar_colocats(){
        return colocats;
    }
}