
package Tests;

import org.junit.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import Domini.Classes.Usuari;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


public class UsuariTest {

    @Test
    public void testConsultarNomUsuari() {
        ArrayList<Integer> buida = new ArrayList<>();
        Usuari usuari = new Usuari("NomProva", "ContrasenyaProva", buida, buida, buida);
        assertEquals("NomProva", usuari.consultarNomUsuari());
    }

    @Test
    public void testConsultarContrasenya() {
        ArrayList<Integer> buida = new ArrayList<>();
        Usuari usuari = new Usuari("NomProva", "ContrasenyaProva", buida, buida, buida);
        assertEquals("ContrasenyaProva", usuari.consultarContrasenya());
    }

    @Test
    public void testConsultarTeclats() {
        ArrayList<Integer> buida = new ArrayList<>();
        ArrayList<Integer> teclats = new ArrayList<>(List.of(1,3));
        Usuari usuari = new Usuari("NomProva", "ContrasenyaProva", teclats, buida, buida);
        assertTrue(usuari.consultarTeclats().contains(1));
        assertTrue(usuari.consultarTeclats().contains(3));
        assertFalse(usuari.consultarTeclats().contains(2));
    }

    @Test
    public void testConsultarAlfabets() {
        ArrayList<Integer> buida = new ArrayList<>();
        ArrayList<Integer> alfabets = new ArrayList<>(List.of(2,3));
        Usuari usuari = new Usuari("NomProva", "ContrasenyaProva", buida, alfabets, buida);
        assertTrue(usuari.consultarAlfabets().contains(2));
        assertTrue(usuari.consultarAlfabets().contains(3));
        assertFalse(usuari.consultarAlfabets().contains(1));
    }

    @Test
    public void testConsultarEntrades() {
        ArrayList<Integer> buida = new ArrayList<>();
        ArrayList<Integer> entrades = new ArrayList<>(List.of(1,2));
        Usuari usuari = new Usuari("NomProva", "ContrasenyaProva", buida, buida, entrades);
        assertTrue(usuari.consultarEntrades().contains(1));
        assertTrue(usuari.consultarEntrades().contains(2));
        assertFalse(usuari.consultarEntrades().contains(3));
    }

    @Test
    public void testModificarContrasenya() {
        ArrayList<Integer> buida = new ArrayList<>();
        Usuari usuari = new Usuari("NomProva", "ContrasenyaProva", buida, buida, buida);
        usuari.modificarContrasenya("ContrasenyaProva2");
        assertEquals("ContrasenyaProva2", usuari.consultarContrasenya());
    }

    @Test
    public void testAfegirEliminarTeclat() {
        ArrayList<Integer> buida = new ArrayList<>();
        Usuari usuari = new Usuari("NomProva", "ContrasenyaProva2", buida, buida, buida);
        usuari.afegirTeclat(1);
        usuari.afegirTeclat(2);
        assertEquals(2, usuari.consultarTeclats().size());
        usuari.eliminarTeclat(1);
        assertEquals(1, usuari.consultarTeclats().size());
        assertFalse(usuari.consultarTeclats().contains(1));
        assertTrue(usuari.consultarTeclats().contains(2));
    }

    @Test
    public void testAfegirEliminarAlfabet() {
        ArrayList<Integer> buida = new ArrayList<>();
        Usuari usuari = new Usuari("NomProva", "ContrasenyaProva", buida, buida, buida);
        usuari.afegirAlfabet(1);
        usuari.afegirAlfabet(3);
        assertEquals(2, usuari.consultarAlfabets().size());
        usuari.eliminarAlfabet(1);
        assertEquals(1, usuari.consultarAlfabets().size());
        assertFalse(usuari.consultarAlfabets().contains(1));
        assertTrue(usuari.consultarAlfabets().contains(3));
    }

    @Test
    public void testAfegirEliminarEntrada() {
        ArrayList<Integer> buida = new ArrayList<>();
        Usuari usuari = new Usuari("NomProva", "ContrasenyaProva", buida, buida, buida);
        usuari.afegirEntrada(2);
        usuari.afegirEntrada(3);
        assertEquals(2, usuari.consultarEntrades().size());
        usuari.eliminarEntrada(2);
        assertEquals(1, usuari.consultarEntrades().size());
        assertFalse(usuari.consultarEntrades().contains(2));
        assertTrue(usuari.consultarEntrades().contains(3));
    }
}
