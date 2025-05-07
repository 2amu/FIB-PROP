package Tests;
import Persistencia.Classes.GestorUsuari;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;

public class PersistenciaTest {
    @Test
    public void testCreadora() throws IOException {
        GestorUsuari gstUsuari = GestorUsuari.obtenirInstancia();
        assertTrue("das", Boolean.TRUE);
    }
}
