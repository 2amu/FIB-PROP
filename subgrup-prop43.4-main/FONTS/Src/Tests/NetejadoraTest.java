package Tests;

import Domini.Classes.Netejadora;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class NetejadoraTest {

    @Test
    public void testnetejarEntrada() throws Exception {
        Set<Character> AbcNet = new TreeSet<>();
        AbcNet.add('a');
        AbcNet.add('c');
        AbcNet.add('k');
        AbcNet.add('j');
        AbcNet.add('h');

        HashMap<String, Integer> brut = new HashMap<>();
        brut.put("hola", 2);
        brut.put("kilo", 1);
        brut.put("modc", 3);
        brut.put("jjjj", 1);
        brut.put("irooo", 5);


        HashMap<String, Integer> LPesperat = new HashMap<>();
        Character[] keyneta = {'h', 'a'};
        LPesperat.put(Arrays.toString(keyneta), 2);
        keyneta = new Character[]{'k'};
        LPesperat.put(Arrays.toString(keyneta), 1);
        keyneta = new Character[]{'c'};
        LPesperat.put(Arrays.toString(keyneta), 3);
        keyneta = new Character[]{'j', 'j', 'j', 'j'};
        LPesperat.put(Arrays.toString(keyneta), 1);

        Netejadora Neteja = new Netejadora();
        HashMap<String, Integer> net = Neteja.netejarEntrada(AbcNet, brut);
        assertEquals(LPesperat, net);
    }

}