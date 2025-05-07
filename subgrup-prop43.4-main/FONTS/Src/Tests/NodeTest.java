package Tests;

import Domini.Classes.Node;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class NodeTest {

    @Test
    public void testConsultarPerm() {

        Node node = new Node(3);
        assertEquals(-1, node.consultar_perm(0));
        assertEquals(-1, node.consultar_perm(1));
        assertEquals(-1, node.consultar_perm(2));

        int[] permutacions = {2, 0, 1};
        node = new Node(permutacions, 50, 3,3);
        
        assertEquals(2, node.consultar_perm(0));
        assertEquals(0, node.consultar_perm(1));
        assertEquals(1, node.consultar_perm(2));
    }

    @Test
    public void testConsultarTotPerm() {
        Node node = new Node(3);
        int[] permutacions = {-1,-1,-1};
        assertArrayEquals(permutacions, node.consultar_totperm());

        permutacions = new int[]{2, 0, 1};
        node = new Node(permutacions, 50, 3,3);

        assertArrayEquals(permutacions, node.consultar_totperm());
    }

    @Test
    public void testConsultarTotPermLength() {
        Node node = new Node(23);
        assertEquals(23, node.consultar_totperm_length());

        int[] permutacions = {2, 0, 1};
        node = new Node(permutacions, 50, 3,3);

        assertEquals(permutacions.length, node.consultar_totperm_length());
    }

    @Test
    public void testConsultarLevel() {
        Node node = new Node(3);
        assertEquals(0, node.consultar_level());

        node = new Node(new int[]{1, 2, 0}, 0, 0, 2);
        assertEquals(2, node.consultar_level());
    }

    @Test
    public void testConsultarCost() {
        Node node = new Node(3);
        assertEquals(0.0, node.consultar_cost(), 0.01);

        node = new Node(null,10,20,44);
        assertEquals(10, node.consultar_cost(), 0.01);
    }

    @Test
    public void testConsultarColocats() {
        Node node = new Node(3);
        assertEquals(0, node.consultar_colocats());

        node = new Node(null, 4, 33, 20);
        assertEquals(33, node.consultar_colocats());
    }
}
