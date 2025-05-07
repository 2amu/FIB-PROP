package Tests;

import Domini.Classes.Text;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.Assert.*;

public class TextTest {

    @Test
    public void testConsultarText() {
        Text text = new Text(1, "TextProva", false, new ArrayList<>(), "és un text de prova");
        assertEquals("és un text de prova", text.consultarText());
    }

    @Test
    public void testModificar_original() throws Exception {
        Text text = new Text(1, "TextTest", false, new ArrayList<>(), "Text inicial");
        assertEquals("Text inicial", text.consultarText());

        text.Modificar_original("Text modificat");
        assertEquals("Text modificat", text.consultarText());
    }


}
