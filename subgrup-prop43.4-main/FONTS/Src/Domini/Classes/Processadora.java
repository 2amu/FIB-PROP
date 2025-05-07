package Domini.Classes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Processadora {
    // ---------------------------------- ATRIBUTS ---------------------------------- //

    // Instancia estatica perque sigui una classe singleton
    private static Processadora processadora;


    // ---------------------------------- METODES ---------------------------------- //

    // CONSTRUCTORA
    private Processadora() {

    }

    public String permAPos(Set<Character>alfabet,int[] permutacio){
        ArrayList <Character> Abc = new ArrayList<>();
        for(Character c : alfabet){
            Abc.add(c);
        }
        ArrayList <Integer> perm = new ArrayList<>();
        for(int p : permutacio){
            perm.add(p);
        }
        int N = permutacio.length;
        Character[][] resultado = new Character[1+((N/2)*2)][1+((N/2)*2)];
        int dir = -1;
        int horitz = 0;
        int vert = 0;
        for(int k = 0; k < permutacio.length; ++k){
            resultado[N/2-vert][N/2+horitz] = Abc.get(perm.indexOf(k));
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
            else if (dir == 3){
                ++horitz;
                ++vert;
                if(horitz == 0){
                    dir = 0;
                    ++vert;
                }
            }
            else{
                dir = 0;
                ++vert;
            }
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (Character[] row : resultado) {
            for (Character col : row) {
                if(col == null) col = ' ';
                stringBuilder.append(col);
            }
            // Valor per distingir canvi de linea
            stringBuilder.append(".");
        }
        String tecl = stringBuilder.toString();

        return tecl;
    }

    public int[][] ProcessaDades(Set<Character> Alfabet, Map<String, Integer> LP) {
        int[][] matrix = new int[Alfabet.size()][Alfabet.size()];

        ArrayList <Character> Abc = new ArrayList<>();

        for(Character c : Alfabet){
            Abc.add(c);
        }

        Iterator<Map.Entry<String, Integer>> iterator = LP.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            String key = entry.getKey();

            Integer value = entry.getValue();
            Character letra_ant = null;

            for(Character c : key.toCharArray()){
                if(letra_ant == null);
                else{
                    matrix[Abc.indexOf(letra_ant)][Abc.indexOf(c)] += value;
                    //sumar letra en el matrix
                }
                letra_ant = c;
            }
        }
        return matrix;
    }

    // Metode per obtenir la instancia d'una classe singleton.
    public static Processadora obtenirInstancia() {
        if(processadora == null)
            processadora = new Processadora();
        return processadora;
    }
}