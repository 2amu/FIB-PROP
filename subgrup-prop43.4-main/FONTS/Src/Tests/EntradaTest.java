package Tests;

import Domini.Classes.Entrada;
import org.junit.Test;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.*;

public class EntradaTest {

    @Test
    public void testConsultarLlistaParaules() {
        Entrada entrada = new EntradaMock();
        HashMap<String, Integer> LP = new HashMap<>();
        LP.put("Alex baena", 3);
        LP.put("gerard moreno", 5);
        entrada.ModificarContingut(LP);
        assertEquals(LP, entrada.consultarLlistaParaules());
    }

    @Test
    public void testAfegirEliminarIdTeclat() {
        Entrada entrada = new EntradaMock();
        entrada.afegirIdTeclat(1);
        assertTrue(entrada.consultaridTeclat(1));
        assertTrue(entrada.teTeclats());

        entrada.afegirIdTeclat(2);
        assertTrue(entrada.consultaridTeclat(1));
        assertTrue(entrada.consultaridTeclat(2));
        assertTrue(entrada.teTeclats());

        entrada.eliminarIdTeclat(1);
        assertFalse(entrada.consultaridTeclat(1));
        assertTrue(entrada.consultaridTeclat(2));
        assertTrue(entrada.teTeclats());

        entrada.eliminarIdTeclat(2);
        assertFalse(entrada.consultaridTeclat(1));
        assertFalse(entrada.consultaridTeclat(2));
        assertFalse(entrada.teTeclats());
    }

    @Test
    public void testConsultarNomEntrada() {
        Entrada entrada = new EntradaMock();
        assertEquals("Fantasy", entrada.consultarNomEntrada());
    }

    @Test
    public void testConsultarEliminat() {
        Entrada entrada = new EntradaMock();
        assertFalse(entrada.consultarEliminat());
        entrada.eliminarEntrada();
        assertTrue(entrada.consultarEliminat());
    }

    @Test
    public void testConsultarIdentificador() {
        Entrada entrada = new EntradaMock();
        assertEquals(Integer.valueOf(1), entrada.consultarIdentificador());
    }

    @Test
    public void testConsultarIdTeclat() {
        Entrada entrada = new EntradaMock();
        entrada.afegirIdTeclat(1);
        entrada.afegirIdTeclat(2);
        assertTrue(entrada.consultaridTeclat(1));
        assertTrue(entrada.consultaridTeclat(2));
        assertFalse(entrada.consultaridTeclat(3));
    }

    @Test
    public void testTeTeclats() {
        Entrada entrada = new EntradaMock();
        assertFalse(entrada.teTeclats());
        entrada.afegirIdTeclat(1);
        assertTrue(entrada.teTeclats());
    }

    @Test
    public void testEliminarEntrada() {
        Entrada entrada = new EntradaMock();
        assertFalse(entrada.consultarEliminat());
        entrada.eliminarEntrada();
        assertTrue(entrada.consultarEliminat());
    }

    @Test
    public void testModificarContingut() {
        Entrada entrada = new EntradaMock();
        HashMap<String, Integer> LPNou = new HashMap<>();
        LPNou.put("nouParaula", 2);
        entrada.ModificarContingut(LPNou);
        assertEquals(LPNou, entrada.consultarLlistaParaules());
    }

    private static class EntradaMock extends Entrada {
        EntradaMock() {
            this.idEntrada = 1;
            this.Nom = "Fantasy";
            this.idTeclats = new ArrayList<>();
            this.LP = new HashMap<>();
        }

        @Override
        public void Modificar_original(String s) throws Exception {
            // No cal implementar aquest mètode pel propòsit d'aquest test
        }
    }
}