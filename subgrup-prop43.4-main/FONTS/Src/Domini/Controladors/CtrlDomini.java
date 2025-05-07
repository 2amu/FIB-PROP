package Domini.Controladors;

import Persistencia.Controladors.CtrlPersistencia;
import Domini.Classes.Usuari;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class CtrlDomini {
    // ---------------------------------- ATRIBUTS ---------------------------------- //

    // Instancia estatica perque sigui una classe singleton
    private static CtrlDomini ctrl;

    // Instancia de l'usuari
    private Usuari usuariActual;

    // Instancia del controlador d'entrada
    private CtrlEntrada ctrlEntrada;

    // Instancia del controlador d'algorisme
    private CtrlAlgorisme ctrlAlgorisme;

    // Instancia del controlador de persistencia
    private CtrlPersistencia ctrlPersistencia;


    // ---------------------------------- METODES ---------------------------------- //

    // CONSTRUCTORA
    private CtrlDomini() {
        usuariActual = null;
        ctrlEntrada = CtrlEntrada.obtenirInstancia();
        ctrlAlgorisme = CtrlAlgorisme.obtenirInstancia();
        ctrlPersistencia = CtrlPersistencia.obtenirInstancia();
    }

    // Metode per obtenir la instancia d'una classe singleton.
    public static CtrlDomini obtenirInstancia() {
        if(ctrl == null)
            ctrl = new CtrlDomini();
        return ctrl;
    }


    // ---------------------------------- USUARI ---------------------------------- //

    // Metode per iniciar sessio
    public void iniciarSessio(String nomUsuari, String contrasenya) throws Exception {
        if(usuariActual != null)
            throw new Exception("ERROR: sessio ja iniciada");
        if(nomUsuari.isEmpty())
            throw new Exception("ERROR: introdueix un nom d'usuari");
        if(!existeixUsuari(nomUsuari))
            throw new Exception("ERROR: l'usuari no existeix");
        if(!comprovarContrasenya(nomUsuari, contrasenya))
            throw new Exception("ERROR: contrasenya incorrecta");

        // usuari = [ nomUsuari,contrasenya,idTeclat1.idTeclat2. ... .idTeclatN,idAlfabet1.idAlfabet2. ... .idAlfabetM, idEntrada1.idEntrada2. ... .idEntradaK ]
        String[] atributs = ctrlPersistencia.carregarUsuari(nomUsuari).split(",");                  // carreguem l'usuari de persistencia

        String[] idsTeclats = atributs[2].split("\\.");
        ArrayList<Integer> teclats = new ArrayList<>();
        if(!idsTeclats[0].equals(" ")) {
            for(String id : idsTeclats)
                teclats.add(Integer.valueOf(id));
        }

        String[] idsAlfabets = atributs[3].split("\\.");
        ArrayList<Integer> alfabets = new ArrayList<>();
        if(!idsAlfabets[0].equals(" ")) {
            for(String id : idsAlfabets)
                alfabets.add(Integer.valueOf(id));
        }

        String[] idsEntrades = atributs[4].split("\\.");
        ArrayList<Integer> entrades = new ArrayList<>();
        if(!idsEntrades[0].equals(" ")) {
            for(String id : idsEntrades)
                entrades.add(Integer.valueOf(id));
        }

        ctrlEntrada.carregarAlfabets(ctrlPersistencia.carregarAlfabets(nomUsuari));                 // carreguem els seus alfabets
        ctrlEntrada.carregarEntrades(ctrlPersistencia.carregarEntrades(nomUsuari));                 // carreguem les seves entrades
        ctrlEntrada.carregarTeclats(ctrlPersistencia.carregarTeclats(nomUsuari));                   // carreguem els seus teclats
        ctrlEntrada.carregarSeguentsIds(ctrlPersistencia.carregarSeguentsIds());                    // carreguem els seguents ids a assignar
        usuariActual = new Usuari(nomUsuari, contrasenya, teclats, alfabets, entrades);             // creem el nou usuari
    }

    // Metode per tancar sessio
    public void tancarSessio() throws Exception {
        if(usuariActual == null)
            throw new Exception("ERROR: sessio no iniciada");

        usuariActual = null;
        ctrlEntrada.reset();                                                                                                        // esborrem totes les dades que hi havia de l'usuari
    }

    // Metode per crear un usuari
    public void crearUsuari(String nomUsuari, String contrasenya) throws Exception {
        if(usuariActual != null) {
            throw new Exception("ERROR: sessio ja iniciada");
        }
        if(existeixUsuari(nomUsuari))
            throw new Exception("ERROR: ja existeix un usuari amb aquest nom");
        if(nomUsuari.length() < 1 || nomUsuari.length() > 15)
            throw new Exception("ERROR: el nom de l'usuari ha de tenir entre 1 i 15 caracters");
        if(nomUsuari.contains(" "))
            throw new Exception("ERROR: el nom de l'usuari no pot contenir espais en blanc");
        if(contrasenya.length() < 8 || contrasenya.length() > 15)
            throw new Exception("ERROR: la contrasenya ha de tenir entre 8 i 15 caracters");
        if(contrasenya.contains(" "))
            throw new Exception("ERROR: la contrasenya no pot contenir espais en blanc");
        if(!contrasenyaCorrecta(contrasenya))
            throw new Exception("ERROR: la contrasenya ha de contenir una majuscula, un digit i un simbol");

        usuariActual = new Usuari(nomUsuari, contrasenya, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());                 // creem una nova instancia d'Usuari
        ctrlPersistencia.guardarUsuari(nomUsuari, contrasenya);                                                                     // la guardem a persistencia
        ctrlEntrada.carregarSeguentsIds(ctrlPersistencia.carregarSeguentsIds());                                                    // carreguem els seguents ids a assignar
    }


    // Metode per eliminar un usuari
    public void eliminarUsuari() throws Exception {
        if(usuariActual == null)
            throw new Exception("ERROR: sessio no iniciada");

        ctrlPersistencia.eliminarUsuari(usuariActual.consultarNomUsuari());                         // eliminem de persistencia l'usuari i tots els seus teclats, entrades i alfabets associats
        usuariActual = null;
        ctrlEntrada.reset();                                                                        // esborrem totes les dades que hi havia de l'usuari
    }

    // Metode per modificar la contrasenya d'un usuari
    public void modificarContrasenyaUsuari(String novaContrasenya) throws Exception {
        if(usuariActual == null)
            throw new Exception("ERROR: sessio no iniciada");
        if(novaContrasenya.equals(usuariActual.consultarContrasenya()))
            throw new Exception("ERROR: la contrasenya no pot ser la mateixa que abans");
        if(novaContrasenya.length() < 8 || novaContrasenya.length() > 15)
            throw new Exception("ERROR: la contrasenya ha de tenir entre 8 i 15 caracters");
        if(novaContrasenya.contains(" "))
            throw new Exception("ERROR: la contrasenya no pot contenir espais en blanc");
        if(!contrasenyaCorrecta(novaContrasenya))
            throw new Exception("ERROR: la contrasenya ha de contenir una majuscula, un digit i un simbol");

        usuariActual.modificarContrasenya(novaContrasenya);                                            // modifiquem la contrasenya de l'usuari actual
        ctrlPersistencia.modificarContrasenya(novaContrasenya, usuariActual.consultarNomUsuari());     // fem els canvis tambe a persistencia
    }

    // Metode per consultar els ids dels teclats d'un usuari
    public ArrayList<String> consultarTeclats() throws Exception {
        if(usuariActual == null)
            throw new Exception("ERROR: sessio no iniciada");

        ArrayList<String> teclats = new ArrayList<>();
        ArrayList<Integer> idsTeclats = usuariActual.consultarTeclats();
        for(int id : idsTeclats) {
            teclats.add(id + "," + ctrlEntrada.consultarNomTeclat(id));
        }
        return teclats;
    }

    // Metode per consultar els ids dels alfabets d'un usuari
    public ArrayList<String> consultarAlfabets() throws Exception {
        if(usuariActual == null)
            throw new Exception("ERROR: sessio no iniciada");

        ArrayList<String> alfabets = new ArrayList<>();
        ArrayList<Integer> idsAlfabets = usuariActual.consultarAlfabets();
        for(int id : idsAlfabets) {
            if(!ctrlEntrada.alfabetEliminat(id))
                alfabets.add(id + "," + ctrlEntrada.consultarNomAlfabet(id));
        }
        return alfabets;
    }

    // Metode per consultar els ids de les entrades d'un usuari
    public ArrayList<String> consultarEntrades() throws Exception {
        if(usuariActual == null)
            throw new Exception("ERROR: sessio no iniciada");

        ArrayList<String> entrades = new ArrayList<>();
        ArrayList<Integer> idsEntrades = usuariActual.consultarEntrades();
        for(int id : idsEntrades) {
            if(!ctrlEntrada.entradaEliminada(id))
                entrades.add(id + "," + ctrlEntrada.consultarNomEntrada(id));
        }
        return entrades;
    }


    // ---------------------------------- TECLAT ---------------------------------- //

    // Metode per crear un teclat
    public void crearTeclat(String nomTeclat, int idAlfabet, int idEntrada, int opcio) throws Exception {
        if(usuariActual == null)
            throw new Exception("ERROR: sessio no iniciada");
        if(nomTeclat.length() < 1 || nomTeclat.length() > 15)
            throw new Exception("ERROR: el nom del teclat ha de tenir entre 1 i 15 caracters");

        ctrlEntrada.comprovarNomTeclat(nomTeclat);                                                                                                      // comprovem que no hi hagi cap teclat amb aquest nom
        Set<Character> alfabet = ctrlEntrada.obtenirAlfabetNetejat(idAlfabet);                                                                          // obtenim el contingut de l'alfabet
        HashMap<String, Integer> entrada = ctrlEntrada.obtenirEntradaNetejada(idEntrada, alfabet);                                                      // obtenim la seva entrada netejada
        String teclat = ctrlAlgorisme.crearTeclat(alfabet, entrada, opcio);                                                                             // l'algorisme crea el teclat
        int idTeclat = ctrlEntrada.crearTeclat(-1, nomTeclat, idAlfabet, idEntrada, teclat);                                                    // creem un nou obejcte Teclat

        usuariActual.afegirTeclat(idTeclat);                                                                                                            // afegim el seu id a la llista de teclats de l'usuari actual
        ctrlPersistencia.guardarTeclat(idTeclat, nomTeclat, idAlfabet, idEntrada, teclat, usuariActual.consultarNomUsuari());                           // guardem a persistencia el nou teclat
    }

    // Metode per eliminar un teclat
    public void eliminarTeclat(int idTeclat) throws Exception {
        if(usuariActual == null)
            throw new Exception("ERROR: sessio no iniciada");

        ctrlEntrada.eliminarTeclat(idTeclat);                                                               // eliminem el teclat
        usuariActual.eliminarTeclat(idTeclat);                                                              // eliminem el seu id de la llista de teclats de l'usuari actual
        ctrlPersistencia.eliminarTeclat(idTeclat, usuariActual.consultarNomUsuari());                       // l'eliminem tambe a persistencia
    }

    // Metode per modificar un teclat
    public void modificarTeclat(int idTeclat, boolean nouAlfabet, int nouId, int opcio) throws Exception {
        if(usuariActual == null)
            throw new Exception("ERROR: sessio no iniciada");
        int idAlfabet;
        int idEntrada;
        idEntrada = ctrlEntrada.consultarIdEntrada(idTeclat);
        idAlfabet = ctrlEntrada.consultarIdAlfabet(idTeclat);
        if(nouAlfabet) {
            if(idAlfabet == nouId)
                throw new Exception("ERROR: l'alfabet no pot ser el mateix que ja hi havia");
            idAlfabet = nouId;
        }
        else {
            if(idEntrada == nouId)
                throw new Exception("ERROR: l'entrada no pot ser la mateixa que ja hi havia");
            idEntrada = nouId;
        }

        Set<Character> alfabet = ctrlEntrada.consultarContingutAlfabet(idAlfabet);                                  // obtenim el contingut de l'alfabet
        HashMap<String, Integer> entrada = ctrlEntrada.obtenirEntradaNetejada(idEntrada, alfabet);                  // obtenim la seva entrada netejada
        String teclat = ctrlAlgorisme.crearTeclat(alfabet, entrada, opcio);                                         // l'algorisme crea el teclat
        ctrlEntrada.modificarTeclat(idTeclat, nouAlfabet, nouId, teclat);                                           // modifiquem el teclat
        ctrlPersistencia.modificarTeclat(idTeclat, nouAlfabet, nouId, teclat, usuariActual.consultarNomUsuari());   // el modifiquem tambe a persistencia
    }

    // Metode per consultar un teclat
    public String[] consultarTeclat(int idTeclat) throws Exception {
        if(usuariActual == null)
            throw new Exception("ERROR: sessio no iniciada");

        return ctrlEntrada.consultarTeclat(idTeclat);
    }


    // ---------------------------------- ALFABET ---------------------------------- //

    // Metode per crear un alfabet
    public void crearAlfabet(String nomAlfabet, String alfabet) throws Exception {
        if(usuariActual == null)
            throw new Exception("ERROR: sessio no iniciada");
        if(nomAlfabet.length() < 1 || nomAlfabet.length() > 15)
            throw new Exception("ERROR: el nom de l'alfabet ha de tenir entre 1 i 15 caracters");
        if(alfabet.length() == 0)
            throw new Exception("ERROR: l'alfabet no pot ser buit");
        if(alfabet.length() > 30)
            throw new Exception("ERROR: l'alfabet pot tenir com a maxim 30 caracters");

        ctrlEntrada.comprovarNomAlfabet(nomAlfabet);
        int idAlfabet = ctrlEntrada.crearAlfabet(-1, nomAlfabet, false, new ArrayList<>(), alfabet);        // creem el nou objecte Alfabet
        usuariActual.afegirAlfabet(idAlfabet);                                                                              // afegim el seu id a la llista d'alfabets de l'usuari actual
        ctrlPersistencia.guardarAlfabet(idAlfabet, nomAlfabet, alfabet, usuariActual.consultarNomUsuari());                   // guardem a persistencia el nou alfabet
    }

    // Metode per eliminar un alfabet
    public void eliminarAlfabet(int idAlfabet) throws Exception {
        if(usuariActual == null)
            throw new Exception("ERROR: sessio no iniciada");

        boolean teTeclats = ctrlEntrada.eliminarAlfabet(idAlfabet);                                     // eliminem l'alfabet
        if(!teTeclats)
            usuariActual.eliminarAlfabet(idAlfabet);                                                    // eliminem el seu id de la llista d'alfabets de l'usuari actual en cas que no tingui teclats associats
        ctrlPersistencia.eliminarAlfabet(idAlfabet, usuariActual.consultarNomUsuari());                 // l'eliminem tambe a persistencia
    }

    // Metode per modificar un alfabet
    public void modificarAlfabet(int idAlfabet, String alfabet) throws Exception {
        if(usuariActual == null)
            throw new Exception("ERROR: sessio no iniciada");
        if(alfabet.length() == 0)
            throw new Exception("ERROR: l'alfabet no pot ser buit");
        if(alfabet.length() > 30)
            throw new Exception("ERROR: l'alfabet pot tenir com a maxim 30 caracters");

        ctrlEntrada.modificarAlfabet(idAlfabet, alfabet);                                               // modifiquem l'alafabet
        ctrlPersistencia.modificarAlfabet(idAlfabet, alfabet);                                          // el modifiquem tambe a persistencia
    }

    // Metode per consultar un alfabet
    public String[] consultarAlfabet(int idAlfabet) throws Exception {
        if(usuariActual == null)
            throw new Exception("ERROR: sessio no iniciada");

        return ctrlEntrada.consultarAlfabet(idAlfabet);
    }


    // ---------------------------------- ENTRADA ---------------------------------- //

    // Metode per crear una entrada
    public void crearEntrada(String nomEntrada, String tipus, String entrada) throws Exception {
        if(usuariActual == null)
            throw new Exception("ERROR: sessio no iniciada");
        if(nomEntrada.length() < 1 || nomEntrada.length() > 15)
            throw new Exception("ERROR: el nom de l'entrada ha de tenir entre 1 i 15 caracters");
        if(entrada.length() == 0)
            throw new Exception("ERROR: l'entrada no pot ser buida");
        if(entrada.length() > 500)
            throw new Exception("ERROR: l'entrada pot tenir com a maxim 500 caracters");

        ctrlEntrada.comprovarNomEntrada(nomEntrada);
        int idEntrada = ctrlEntrada.crearEntrada(-1, nomEntrada, false, tipus, new ArrayList<>(),entrada);                                                   // creem el nou objecte Entrada
        usuariActual.afegirEntrada(idEntrada);                                                                                  // afegim el seu id a la llista d'entrades de l'usuari actual
        ctrlPersistencia.guardarEntrada(idEntrada, nomEntrada, tipus, entrada, usuariActual.consultarNomUsuari());                // guardem a persistencia la nova entrada
    }

    // Metode per eliminar una entrada
    public void eliminarEntrada(int idEntrada) throws Exception {
        if(usuariActual == null)
            throw new Exception("ERROR: sessio no iniciada");

        boolean teTeclats = ctrlEntrada.eliminarEntrada(idEntrada);                                     // eliminem l'entrada
        if(!teTeclats)
            usuariActual.eliminarEntrada(idEntrada);                                                    // eliminem el seu id de la llista d'entrades de l'usuari actual en cas que no tingui teclats associats
        ctrlPersistencia.eliminarEntrada(idEntrada, usuariActual.consultarNomUsuari());                 // l'eliminem tambe a persistencia
    }

    // Metode per modificar una entrada
    public void modificarEntrada(int idEntrada, String entrada) throws Exception {
        if(usuariActual == null)
            throw new Exception("ERROR: sessio no iniciada");
        if(entrada.length() == 0)
            throw new Exception("ERROR: l'entrada no pot ser buida");
        if(entrada.length() > 500)
            throw new Exception("ERROR: l'entrada pot tenir com a maxim 500 caracters");

        ctrlEntrada.modificarEntrada(idEntrada, entrada);                                               // modifiquem l'alafabet
        ctrlPersistencia.modificarEntrada(idEntrada, entrada);                                          // el modifiquem tambe a persistencia
    }

    // Metode per consultar una entrada
    public String[] consultarEntrada(int idEntrada) throws Exception {
        if(usuariActual == null)
            throw new Exception("ERROR: sessio no iniciada");

        return ctrlEntrada.consultarEntrada(idEntrada);
    }


    // ---------------------------------- AUXILIARS ---------------------------------- //

    // Metode que consulta si l'usuari amb la clau nomUsuari existeix
    private boolean existeixUsuari(String nomUsuari) {
        return !ctrlPersistencia.carregarUsuari(nomUsuari).equals("no existeix");
    }

    // Metode que consulta si la contrasenya de l'usuari identificat per nomUsuari coincideix amb el parametre contrasenya
    private boolean comprovarContrasenya(String nomUsuari, String contrasenya) {
        return ctrlPersistencia.carregarContrasenya(nomUsuari).equals(contrasenya);
    }

    // Metode que comprova si la contrasenya conte una majuscula, un digit, i un simbol
    private boolean contrasenyaCorrecta(String contrasenya) {
        boolean majuscula = false;
        boolean digit = false;
        boolean simbol = false;
        for(int i = 0; i < contrasenya.length() && (!majuscula || !digit || !simbol); ++i) {
            char caracter = contrasenya.charAt(i);
            if(Character.isUpperCase(caracter))
                majuscula = true;
            else if(Character.isDigit(caracter))
                digit = true;
            else if(!Character.isLetterOrDigit(caracter))
                simbol = true;
        }
        return (majuscula && digit && simbol);
    }
}