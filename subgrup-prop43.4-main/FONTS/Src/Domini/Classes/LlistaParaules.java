package Domini.Classes;

import java.util.ArrayList;
import java.util.HashMap;

public class LlistaParaules extends Entrada {

    //CREADORA
    // Format: paraula1:3,paraula2:5,paraula3:1
    public LlistaParaules(int id, String NomEntrada, boolean eliminat, ArrayList<Integer> idTeclats, String llista) throws Exception {
        this.idTeclats = idTeclats;
        this.eliminat = eliminat;
        this.Nom = NomEntrada;
        this.idEntrada = id;
        LP = new HashMap<>();
        LP = ompleHashMap(llista);
    }

    public void Modificar_original(String s) throws Exception{
        LP.clear();
        super.ModificarContingut(ompleHashMap(s));
    }

}