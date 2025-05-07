package Domini.Classes;

import java.util.ArrayList;
import java.util.HashMap;

public class Text extends Entrada {
    protected String T;

    //CREADORA
    public Text(int idEntrada, String NomEntrada, boolean eliminat, ArrayList<Integer> idTeclats, String x) {
        this.idTeclats = idTeclats;
        this.eliminat = eliminat;
        Nom = NomEntrada;
        this.idEntrada = idEntrada;
        LP = new HashMap<>();
        LP = TextaLp(x);

        T = x;
    }
    //Consulta text
    public String consultarText() {
        return T;
    }

    //modifica text
    public void Modificar_original(String s) throws Exception{
        super.ModificarContingut(TextaLp(s));
        T = s;
    }


    private HashMap<String, Integer> TextaLp(String x) {
        HashMap<String, Integer> LPtext = new HashMap<>();
        String[] paraules = x.split(" ");
        for (String paraula : paraules) {
            if (LPtext.containsKey(paraula)) {
                LPtext.put(paraula, LPtext.get(paraula) + 1);
            }
            else {
                LPtext.put(paraula, 1);
            }
        }
        return LPtext;
    }
}