package Tests;

import Domini.Classes.Processadora;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProcessadoraTest {

    @Test
    public void testPermAPos() {
        Processadora processadora = Processadora.obtenirInstancia();
        Set<Character> alfabet = new TreeSet<>();
        alfabet.add('A');
        alfabet.add('B');
        alfabet.add('C');

        int[] permutacio = {2, 0, 1};

        String expectedPosicionament = "-C-.-BA.---";

        String resultat = processadora.permAPos(alfabet, permutacio);

        assertNotNull(resultat);
        assertArrayEquals(expectedPosicionament.getBytes(), resultat.getBytes());
    }

    @Test
    public void testProcessaDades() {
        Processadora processadora = Processadora.obtenirInstancia();
        Set<Character> alfabet = new TreeSet<>();
        alfabet.add('A');
        alfabet.add('B');
        alfabet.add('C');

        Map<String, Integer> LP = new HashMap<>();
        LP.put("ABC", 2);
        LP.put("BCA", 1);

        int[][] expectedMatrix = {
                {0, 2, 0},
                {0, 0, 3},
                {1, 0, 0}
        };

        int[][] resultat = processadora.ProcessaDades(alfabet, LP);

        assertNotNull(resultat);
        assertArrayEquals(expectedMatrix, resultat);
    }
}
