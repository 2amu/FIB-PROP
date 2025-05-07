package Domini.Classes;

import java.util.ArrayList;

public class Usuari {
    // ---------------------------------- ATRIBUTS ---------------------------------- //

    // Atribut que identifica un Usuari
    private String nomUsuari;
    // Atribut que conte la contrasenya de l'usuari
    private String contrasenya;
    // Atribut que conte els ids de tots els teclats de l'usuari
    private ArrayList<Integer> teclats;
    // Atribut que conte els ids de tots els alfabets de l'usuari
    private ArrayList<Integer> alfabets;
    // Atribut que conte els ids de totes les entrades de l'usuari
    private ArrayList<Integer> entrades;


    // ---------------------------------- METODES ---------------------------------- //

    // CONSTRUCTORA

    public Usuari(String nom, String contrasenya, ArrayList<Integer> teclats, ArrayList<Integer> alfabets, ArrayList<Integer> entrades) {
        this.nomUsuari = nom;
        this.contrasenya = contrasenya;
        this.teclats = teclats;
        this.alfabets = alfabets;
        this.entrades = entrades;
    }


    // CONSULTORES

    // Metode que consulta el nom de l'usuari
    public String consultarNomUsuari() {
        return this.nomUsuari;
    }

    // Metode que consulta la contrasenya de l'usuari
    public String consultarContrasenya() {
        return this.contrasenya;
    }

    // Metode que consulta els teclats de l'usuari
    public ArrayList<Integer> consultarTeclats() {
        return this.teclats;
    }
    // Metode que consulta els alfabets de l'usuari
    public ArrayList<Integer> consultarAlfabets() {
        return this.alfabets;
    }
    // Metode que consulta les entrades de l'usuari
    public ArrayList<Integer> consultarEntrades() {
        return this.entrades;
    }


    // MODIFICADORES

    // Metode que modifica la contrasenya de l'usuari
    public void modificarContrasenya(String novaContrasenya) {
        this.contrasenya = novaContrasenya;
    }

    // Metode que afegeix l'id d'un teclat a la llista teclats
    public void afegirTeclat(Integer idTeclat) {
        teclats.add(idTeclat);
    }

    // Metode que afegeix l'id d'un alfabet a la llista alfabets
    public void afegirAlfabet(Integer idAlfabet) {
        alfabets.add(idAlfabet);
    }

    // Metode que afegeix l'id d'una entrada a la llista d'entrades
    public void afegirEntrada(Integer idEntrada) {
        entrades.add(idEntrada);
    }

    // Metode que elimina l'id d'un teclat de la llista teclats
    public void eliminarTeclat(Integer idTeclat) {
        teclats.remove(idTeclat);
    }

    // Metode que elimina l'id d'un alfabet de la llista alfabets
    public void eliminarAlfabet(Integer idAlfabet) {
        alfabets.remove(idAlfabet);
    }

    // Metode que elimina l'id d'una entrada de la llista d'entrades
    public void eliminarEntrada(Integer idEntrada) {
        entrades.remove(idEntrada);
    }
}
