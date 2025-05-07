package Tests;

import Domini.Classes.Alfabet;
import org.junit.Test;
import java.util.Set;
import java.util.TreeSet;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class AlfabetTest {

    @Test
    public void testConsultarAlfabet() {
        Set<Character> alfabetEsperat = new TreeSet<>();
        alfabetEsperat.add('a');
        alfabetEsperat.add('b');
        alfabetEsperat.add('c');

        String alfabetCrear = "abc";
        Alfabet alfabet = new Alfabet(1, "AlfabetTest", false, new ArrayList<>(), alfabetCrear);

        assertEquals(alfabetEsperat, alfabet.consultarAlfabet());
    }

    @Test
    public void testConsultarNomAlfabet() {
        Alfabet alfabet = new Alfabet(1, "AlfabetTest", false, new ArrayList<>(), "");
        assertEquals("AlfabetTest", alfabet.consultarNomAlfabet());
    }

    @Test
    public void testConsultarEliminat() {
        Alfabet alfabet = new Alfabet(1, "AlfabetTest", true, new ArrayList<>(), "");
        assertTrue(alfabet.consultarEliminat());
    }

    @Test
    public void testEliminarAlfabet() {
        Alfabet alfabet = new Alfabet(1, "AlfabetTest", false, new ArrayList<>(), "");
        assertFalse(alfabet.consultarEliminat());
        alfabet.eliminarAlfabet();
        assertTrue(alfabet.consultarEliminat());
    }

    @Test
    public void testAfegirEliminarIdTeclat() {
        Alfabet alfabet = new Alfabet(1, "AlfabetTest", false, new ArrayList<>(), "");
        assertFalse(alfabet.consultarIdTeclat(1));
        alfabet.afegirIdTeclat(1);
        assertTrue(alfabet.consultarIdTeclat(1));
        alfabet.eliminarIdTeclat(1);
        assertFalse(alfabet.consultarIdTeclat(1));
    }

    @Test
    public void testModificarAlfabet() {
        Alfabet alfabet = new Alfabet(1, "AlfabetTest", false, new ArrayList<>(), "");
        alfabet.modificarAlfabet("");
        assertTrue(alfabet.consultarAlfabet().isEmpty());
        assertFalse(alfabet.teTeclats());
    }

    @Test
    public void testTeTeclats() {
        Alfabet alfabetSenseTeclats = new Alfabet(1, "AlfabetSenseTeclats", false, new ArrayList<>(), "");
        assertFalse(alfabetSenseTeclats.teTeclats());

        Alfabet alfabetAmbTeclats = new Alfabet(2, "AlfabetAmbTeclats", false, new ArrayList<>(), "");
        alfabetAmbTeclats.afegirIdTeclat(1);
        assertTrue(alfabetAmbTeclats.teTeclats());
    }
}
