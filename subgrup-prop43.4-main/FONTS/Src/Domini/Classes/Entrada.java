package Domini.Classes;

import java.util.HashMap;
import java.util.ArrayList;

public abstract class Entrada {

    protected boolean eliminat;
    protected Integer idEntrada;
    protected String Nom;

    protected HashMap<String, Integer> LP;

    protected ArrayList<Integer> idTeclats;

    //CREADORA
    public Entrada(){
    }

    public HashMap<String, Integer> consultarLlistaParaules() {
        return LP;
    }

    //afegeix teclat associat
    public void afegirIdTeclat(Integer id) {
        idTeclats.add(id);
    }

    //Consultar Nom de la entrada
    public String consultarNomEntrada() {
        return Nom;
    }

    //Consultar si s'ha eliminat l'entrada
    public boolean consultarEliminat() {
        return eliminat;
    }

    //Consultar l'id d'entrada
    public Integer consultarIdentificador() {
        return idEntrada;
    }

    //Consulta si l'id de teclat pertany a l'alfabet
    public boolean consultaridTeclat(Integer idTeclat) {
        return this.idTeclats.contains(idTeclat);
    }

    //public abstract String consultar_original();

    //Consulta si te algun teclat
    public boolean teTeclats() {
        return this.idTeclats.size() != 0;
    }

    //Indicar que s'ha d'eliminar
    public void eliminarEntrada() {
        eliminat = true;
    }

    //Elimina l'id d'un teclat eliminat
    public void eliminarIdTeclat(Integer idTeclat) {
        this.idTeclats.remove(idTeclat);
    }

    //Modifica l'entrada i elimina referencies teclat
    public void ModificarContingut(HashMap<String, Integer> LP) {
        idTeclats = new ArrayList<>();
        this.LP = LP;
    }

    public abstract void Modificar_original(String s) throws Exception;

    //Converteix les dades d'string a Map
    protected HashMap<String,Integer> ompleHashMap(String llista) throws Exception {
        HashMap<String, Integer> LP = new HashMap<>();
        String[] grupsPF = llista.split(",");
        for (String grupPF : grupsPF) {
            String[] parts = grupPF.split(":");
            if (parts.length == 2) {
                String paraula = parts[0];
                int valorFrequencia = Integer.parseInt(parts[1]);
                if(valorFrequencia < 0)
                    throw new Exception("ERROR: format incorrecte");
                LP.put(paraula, valorFrequencia);
            }
            else
                throw new Exception("ERROR: format incorrecte");
        }
        return LP;
    }

}