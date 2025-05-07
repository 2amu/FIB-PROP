package Domini.Classes;

import java.util.ArrayList;
import java.util.HashMap;

public class Collections {
    // ---------------------------------- ATRIBUTS ---------------------------------- //

    // Instancia estatica perque sigui una classe singleton
    private static Collections coleccio;

    // Atribut que conte tots els teclats de l'usuari actual
    private HashMap<Integer, Teclat> teclats;

    // Atribut que conte tots els alfabets de l'usuari actual
    private HashMap<Integer, Alfabet> alfabets;

    // Atribut que conte totes les entrades de l'usuari actual
    private HashMap<Integer, Entrada> entrades;

    // Atribut que conte els noms de tots els teclats de l'usuari actual
    private ArrayList<String> nomsTeclats;

    // Atribut que conte els noms de tots els alfabets de l'usuari actual
    private ArrayList<String> nomsAlfabets;

    // Atribut que conte els noms de totes les entrades de l'usuari actual
    private ArrayList<String> nomsEntrades;

    // Atribut que conte el seguent identificador de teclat a assignar
    private int seguentIdTeclat;

    // Atribut que conte el seguent identificador d'alfabet a assignar
    private int seguentIdAlfabet;

    // Atribut que conte el seguent identificador d'entrada a assignar
    private int seguentIdEntrada;


    // ---------------------------------- METODES ---------------------------------- //

    // CONSTRUCTORA

    private Collections() {
        teclats = new HashMap<>();
        alfabets = new HashMap<>();
        entrades = new HashMap<>();
        nomsTeclats = new ArrayList<>();
        nomsAlfabets = new ArrayList<>();
        nomsEntrades = new ArrayList<>();
        seguentIdTeclat = 1;
        seguentIdAlfabet = 1;
        seguentIdEntrada = 1;
    }

    // Metode per obtenir la instancia d'una classe singleton
    public static Collections obtenirInstancia() {
        if(coleccio == null)
            coleccio = new Collections();
        return coleccio;
    }


    // CONSULTORES

    // Metode que consulta el teclat amb l'id = idTeclat
    public Teclat consultarTeclat(int idTeclat) throws Exception {
        if(!teclats.containsKey(idTeclat))
            throw new Exception("ERROR: el teclat no existeix");

        return teclats.get(idTeclat);
    }

    // Metode que consulta l'alfabet amb l'id = idAlfabet
    public Alfabet consultarAlfabet(int idAlfabet) throws Exception {
        if(!alfabets.containsKey(idAlfabet))
            throw new Exception("ERROR: l'alfabet no existeix");

        return alfabets.get(idAlfabet);
    }

    // Metode que consulta l'entrada amb l'id = idEntrada
    public Entrada consultarEntrada(int idEntrada) throws Exception {
        if(!entrades.containsKey(idEntrada))
            throw new Exception("ERROR: l'entrada no existeix, id: " + idEntrada);

        return entrades.get(idEntrada);
    }

    // Metode que consulta si existeix un teclat amb nom = nomTeclat
    public boolean consultarNomTeclat(String nomTeclat) {
        return nomsTeclats.contains(nomTeclat);
    }

    // Metode que consulta si existeix un alfabet amb nom = nomAlfabet
    public boolean consultarNomAlfabet(String nomAlfabet) {
        return nomsAlfabets.contains(nomAlfabet);
    }

    // Metode que consulta si existeix una entrada amb nom = nomEntrada
    public boolean consultarNomEntrada(String nomEntrada) {
        return nomsEntrades.contains(nomEntrada);
    }

    // Metode que consulta el seguent id de teclat a assignar
    public int consultarSeguentIdTeclat() {
        return seguentIdTeclat;
    }

    // Metode que consulta el seguent id d'alfabet a assignar
    public int consultarSeguentIdAlfabet() {
        return seguentIdAlfabet;
    }

    // Metode que consulta el seguent id d'entrada a assignar
    public int consultarSeguentIdEntrada() {
        return seguentIdEntrada;
    }


    // MODIFICADORES

    // Metode que neteja els atributs de la classe, buidant aixi tots els maps i arrays
    public void reset() {
        teclats.clear();
        alfabets.clear();
        entrades.clear();
        nomsTeclats.clear();
        nomsAlfabets.clear();
        nomsEntrades.clear();
    }

    // Metode que afegeix un teclat al HashMap teclats i el seu nom a la llista nomsTeclats
    public void afegirTeclat(int idTeclat, String nomTeclat, Teclat teclat) {
        teclats.put(idTeclat, teclat);
        nomsTeclats.add(nomTeclat);
    }

    // Metode que afegeix un alfabet al HashMap alfabets i el seu nom a la llista nomsAlfabets
    public void afegirAlfabet(int idAlfabet, String nomAlfabet, Alfabet alfabet) {
        alfabets.put(idAlfabet, alfabet);
        nomsAlfabets.add(nomAlfabet);
    }

    // Metode que afegeix una entrada al HashMap entrada i el seu nom a la llista nomsEntrades
    public void afegirEntrada(int idEntrada, String nomEntrada, Entrada entrada) {
        entrades.put(idEntrada, entrada);
        nomsEntrades.add(nomEntrada);
    }

    // Metode que elimina un teclat del HashMap teclats i el seu nom de la llista nomsTeclats
    public void eliminarTeclat(int idTeclat, String nomTeclat) {
        teclats.remove(idTeclat);
        nomsTeclats.remove(nomTeclat);
    }

    // Metode que elimina un alfabet del HashMap alfabets i el seu nom de la llista nomsAlfabets
    public void eliminarAlfabet(int idAlfabet, String nomAlfabet) {
        alfabets.remove(idAlfabet);
        nomsAlfabets.remove(nomAlfabet);
    }

    // Metode que elimina una entrada del HashMap entrades i el seu nom de la llista nomsEntrades
    public void eliminarEntrada(int idEntrada, String nomEntrada) {
        entrades.remove(idEntrada);
        nomsEntrades.remove(nomEntrada);
    }

    // Metode que inicialitza els seguents ids a assignar
    public void assignarIds(Integer[] ids) {
        seguentIdTeclat = ids[0];
        seguentIdAlfabet = ids[1];
        seguentIdEntrada = ids[2];
    }

    // Metode que incrementa el seguent id de teclat a assignar
    public void incrementarIdTeclat() {
        ++seguentIdTeclat;
    }

    // Metode que incrementa el seguent id d'alfabet a assignar
    public void incrementarIdAlfabet() {
        ++seguentIdAlfabet;
    }

    // Metode que incrementa el seguent id d'entrada a assignar
    public void incrementarIdEntrada() {
        ++seguentIdEntrada;
    }

}