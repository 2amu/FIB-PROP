package Tests;

import Domini.Classes.LlistaParaules;
import org.junit.Test;

import java.util.HashMap;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class LlistaParaulesTest {

    @Test
    public void testConstructor() throws Exception {

        String llistaInicial = "paraula1:3,paraula2:5,paraula3:1";
        LlistaParaules llistaParaules = new LlistaParaules(1, "LlistaTest", false, new ArrayList<>(), llistaInicial);

        HashMap<String, Integer> expectedLP = new HashMap<>();
        expectedLP.put("paraula1", 3);
        expectedLP.put("paraula2", 5);
        expectedLP.put("paraula3", 1);

        assertEquals(expectedLP, llistaParaules.consultarLlistaParaules());
    }

    @Test
    public void testModificarOriginal() throws Exception {

        String llistaInicial2 = "paraula1:3,paraula2:5,paraula3:1";
        LlistaParaules llistaParaules2 = new LlistaParaules(1, "LlistaTest2", false, new ArrayList<>(), llistaInicial2);
        HashMap<String, Integer> expectedLP2 = new HashMap<>();
        expectedLP2.put("paraula1", 3);
        expectedLP2.put("paraula2", 5);
        expectedLP2.put("paraula3", 1);
        assertEquals(expectedLP2, llistaParaules2.consultarLlistaParaules());

        // Modificar
        String nouContingut = "paraula4:2,paraula5:1";
        llistaParaules2.Modificar_original(nouContingut);

        // Verificar
        HashMap<String, Integer> expectedLP3 = new HashMap<>();
        expectedLP3.put("paraula4", 2);
        expectedLP3.put("paraula5", 1);

        assertEquals(expectedLP3, llistaParaules2.consultarLlistaParaules());
    }

}
