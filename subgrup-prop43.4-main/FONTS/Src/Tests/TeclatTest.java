package Tests;

import Domini.Classes.Teclat;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TeclatTest {

    @Test
    public void testConsultarTeclat() {
        String teclatEsperat = "abc,def,ghi";
        Teclat teclat = new Teclat(1, "TeclatTest", 1, 1, teclatEsperat);
        assertArrayEquals(teclatEsperat.getBytes(), teclat.consultarTeclat().toString().getBytes());
    }

    @Test
    public void testConsultarNomTeclat() {
        Teclat teclat = new Teclat(1, "TeclatTest", 1, 1, "a");
        assertEquals("TeclatTest", teclat.consultarNomTeclat());
    }

    @Test
    public void testConsultarIdEntrada() {
        Teclat teclat = new Teclat(1, "TeclatTest", 1, 5, "a");
        assertEquals(Integer.valueOf(5), teclat.consultarIdEntrada());
    }

    @Test
    public void testConsultarIdAlfabet() {
        Teclat teclat = new Teclat(1, "TeclatTest", 7, 1, "a");
        assertEquals(Integer.valueOf(7), teclat.consultarIdAlfabet());
    }

    @Test
    public void testReassignarEntrada() {
        String teclatInicial = "abc,def,ghi";;
        Teclat teclat = new Teclat(1, "TeclatTest", 1, 1, teclatInicial);

        String teclatNou = "jkl,mno,pqr";;
        teclat.reassignarEntrada(teclatNou, 10);

        assertArrayEquals(new String[]{teclatNou}, teclat.consultarTeclat());
        assertEquals(Integer.valueOf(10), teclat.consultarIdEntrada());
    }

    @Test
    public void testReassignarAlfabet() {
        Character[][] teclatInicial = {{'a', 'b', 'c'}, {'d', 'e', 'f'}, {'g', 'h', 'i'}};
        Teclat teclat = new Teclat(1, "TeclatTest", 1, 1, "a");

        Character[][] teclatNou = {{'1', '2', '3'}, {'4', '5', '6'}, {'7', '8', '9'}};
        teclat.reassignarAlfabet(Arrays.deepToString(teclatNou), 20);

        assertArrayEquals(teclatNou, teclat.consultarTeclat());
        assertEquals(Integer.valueOf(20), teclat.consultarIdAlfabet());
    }
}
