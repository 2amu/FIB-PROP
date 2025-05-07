package Tests;

import Domini.Classes.QAP;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class QAPTest {

    @Test
    public void testAlgorismeQAP() {
        
        int[][] mat = {
                {0, 1, 3},
                {1, 0, 2},
                {4, 1, 0}
        };

        QAP algorisme = QAP.obtenirInstancia();
        int[] result = algorisme.ejecutarQAP(mat);

        
        int[] expected = {1, 2, 0};

        assertArrayEquals(expected, result);
    }

    
}
