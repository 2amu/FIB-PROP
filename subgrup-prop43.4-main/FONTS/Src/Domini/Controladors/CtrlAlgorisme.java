package Domini.Controladors;
import Domini.Classes.Processadora;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import Domini.Classes.QAP;
import Domini.Classes.SimulatedAnnealing;

public class CtrlAlgorisme {
    // ---------------------------------- ATRIBUTS ---------------------------------- //

    // Instancia estatica perque sigui una classe singleton
    private static CtrlAlgorisme ctrlalgorisme;

    // Instancia de l'algorisme QAP
    private QAP qap;

    // Instancia de l'algorisme Simulated Annealing
    private SimulatedAnnealing sa;

    // Instancia de la processadora
    private Processadora processadora;


    // ---------------------------------- METODES ---------------------------------- //

    // CONSTRUCTORA
    private CtrlAlgorisme() {
        qap = QAP.obtenirInstancia();
        sa = SimulatedAnnealing.obtenirInstancia();
        processadora = Processadora.obtenirInstancia();
    }

    // Metode per obtenir la instancia d'una classe singleton.
    public static CtrlAlgorisme obtenirInstancia() {
        if(ctrlalgorisme == null)
            ctrlalgorisme = new CtrlAlgorisme();
        return ctrlalgorisme;
    }

    // Metode per crear un teclat
    public String crearTeclat(Set<Character> alfabet, HashMap<String, Integer> entrada, int opcio) {

        int[][] matrix = processadora.ProcessaDades(alfabet,entrada);

        int[] permutacio;
        if(opcio == 0) permutacio = qap.ejecutarQAP(matrix);
        else permutacio = sa.ejecutarSimulatedAnnealing(matrix);
        return processadora.permAPos(alfabet,permutacio);
    }
}