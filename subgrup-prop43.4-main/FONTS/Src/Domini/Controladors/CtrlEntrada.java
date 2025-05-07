package Domini.Controladors;

import Domini.Classes.Teclat;
import Domini.Classes.Alfabet;
import Domini.Classes.Entrada;
import Domini.Classes.Text;
import Domini.Classes.LlistaParaules;
import Domini.Classes.Collections;
import Domini.Classes.Netejadora;

import java.util.*;

public class CtrlEntrada {
    // ---------------------------------- ATRIBUTS ---------------------------------- //

    // Instancia estatica perque sigui una classe singleton
    private static CtrlEntrada ctrl;

    // Instancia de collections
    private Collections colleccions;

    // Instancia de netejadora
    private Netejadora netejadora;


    // ---------------------------------- METODES ---------------------------------- //

    // CONSTRUCTORA
    private CtrlEntrada() {
        colleccions = Collections.obtenirInstancia();
        netejadora = new Netejadora();
    }

    // Metode per obtenir la instancia d'una classe singleton.
    public static CtrlEntrada obtenirInstancia() {
        if(ctrl == null)
            ctrl = new CtrlEntrada();
        return ctrl;
    }


    // ---------------------------------- DADES ---------------------------------- //

    // Metode que crea els teclats de l'usuari i els guarda a Collections
    public void carregarTeclats(String[] teclats) throws Exception {
        // Els teclats els guardem de la seguent manera:
        // teclat = [ idTeclat,nomTeclat,idAlfabet,idEntrada,teclat ] , es a dir separant els atributs per una coma
        int idTeclat;
        String nomTeclat;
        int idAlfabet;
        int idEntrada;
        String teclat;

        if(!teclats[0].equals(" ")) {
            for (String teclatI : teclats) {
                String[] atributs = teclatI.split(",");
                idTeclat = Integer.parseInt(atributs[0]);
                nomTeclat = atributs[1];
                idAlfabet = Integer.parseInt(atributs[2]);
                idEntrada = Integer.parseInt(atributs[3]);
                teclat = atributs[4];
                crearTeclat(idTeclat, nomTeclat, idAlfabet, idEntrada, teclat);
            }
        }
    }

    // Metode que crea els alfabets de l'usuari i els guarda a Collections
    public void carregarAlfabets(String[] alfabets) throws Exception {
        // Els alfabets els guardem de la seguent manera:
        // alfabet = [ idAlfabet,nomAlfabet,eliminat,idTeclat1.idTeclat2. ... .idTeclatn,alfabet ] , es a dir separant els atributs per comes, i els elements de l'array dels idTeclats per punts
        int idAlfabet;
        String nomAlfabet;
        boolean eliminat;
        ArrayList<Integer> idTeclats = new ArrayList<>();
        if(!alfabets[0].equals(" ")) {
            for (String alfabetI : alfabets) {                                      // per a cada alfabet
                String[] atributs = alfabetI.split(",");
                idAlfabet = Integer.parseInt(atributs[0]);
                nomAlfabet = atributs[1];
                eliminat = (atributs[2].equals("true"));
                String[] ids = atributs[3].split("\\.");
                if (!ids[0].equals(" ")) {
                    for (String id : ids)
                        idTeclats.add(Integer.valueOf(id));
                }
                String lletres = atributs[4];

                crearAlfabet(idAlfabet, nomAlfabet, eliminat, idTeclats, lletres);
            }
        }
    }

    // Metode que crea les entrades de l'usuari i les guarda a Collections
    public void carregarEntrades(String[] entrades) throws Exception {
        // Les entrades les guardem de la seguent manera:
        // entrada = [ idEntrada,nomEntrada,eliminat,tipus,idTeclat1.idTeclat2. ... .idTeclatn, entrada ] , es a dir separant els atributs per comes, i els elements de l'array dels idTeclats per punts
        int idEntrada;
        String nomEntrada;
        boolean eliminat;
        String tipus;
        ArrayList<Integer> idTeclats = new ArrayList<>();
        String entrada;

        if(!entrades[0].equals(" ")) {
            for (String entradaI : entrades) {
                String[] atributs = entradaI.split(",");
                idEntrada = Integer.parseInt(atributs[0]);
                nomEntrada = atributs[1];
                eliminat = (atributs[2].equals("true"));
                tipus = atributs[3];
                String[] ids = atributs[4].split("\\.");
                if (!ids[0].equals(" ")) {
                    for (String id : ids)
                        idTeclats.add(Integer.valueOf(id));
                }
                entrada = atributs[5];
                crearEntrada(idEntrada, nomEntrada, eliminat, tipus, idTeclats, entrada);
            }
        }
    }

    // Metode que indica quins son els seguents ids a assignar i ho guarda a Collections
    public void carregarSeguentsIds(Integer[] ids) {
        colleccions.assignarIds(ids);
    }

    // Metode que esborra totes les dades que hi havia de l'usuari
    public void reset() {
        colleccions.reset();
    }


    // ---------------------------------- TECLAT ---------------------------------- //

    // Metode que crea un teclat i el guarda a Collections, guardant tambe el seu id a l'alfabet i l'entrada associats
    public int crearTeclat(int idTeclat, String nomTeclat, int idAlfabet, int idEntrada, String teclat) throws Exception {
        if(idTeclat == -1) {
            idTeclat = colleccions.consultarSeguentIdTeclat();                                  // agafem el seguent id de teclat a assignar
            colleccions.incrementarIdTeclat();                                                  // incrementem el seguent id de teclat a assignar
        }
        Teclat nouTeclat = new Teclat(idTeclat, nomTeclat, idAlfabet, idEntrada, teclat);       // creem una instancia de la classe Teclat
        colleccions.afegirTeclat(idTeclat, nomTeclat, nouTeclat);                               // el guardem a collections
        colleccions.consultarAlfabet(idAlfabet).afegirIdTeclat(idTeclat);                       // afegim el seu id a l'alfabet associat
        colleccions.consultarEntrada(idEntrada).afegirIdTeclat(idTeclat);                       // afegim el seu id a l'entrada associada
        return idTeclat;
    }

    // Metode que elimina un teclat treient-lo de Collections, i tambe elimina les seves referencies
    public void eliminarTeclat(int idTeclat) throws Exception {
        Teclat teclat = colleccions.consultarTeclat(idTeclat);                                  // agafem el teclat a eliminar

        int idAlfabet = teclat.consultarIdAlfabet();                                            // consultem l'id del seu alfabet associat
        colleccions.consultarAlfabet(idAlfabet).eliminarIdTeclat(idTeclat);                     // eliminem l'id del teclat a l'alfabet associat
        Alfabet alfabet = colleccions.consultarAlfabet(idAlfabet);
        boolean teTeclats = alfabet.teTeclats();
        boolean eliminat = alfabet.consultarEliminat();
        if(!teTeclats && eliminat)                                                              // si no te cap teclat associat i esta marcat com a eliminat:
            colleccions.eliminarAlfabet(idAlfabet, alfabet.consultarNomAlfabet());              // eliminem l'objecte de la classe Alfabet

        int idEntrada = teclat.consultarIdEntrada();                                            // consultem l'id de la seva entrada associada
        colleccions.consultarEntrada(idEntrada).eliminarIdTeclat(idTeclat);                     // eliminem l'id del teclat a l'entrada associada
        Entrada entrada = colleccions.consultarEntrada(idEntrada);
        boolean teTeclats2 = entrada.teTeclats();
        boolean eliminat2 = entrada.consultarEliminat();
        if(!teTeclats2 && eliminat2)                                                            // si no te cap teclat associat i esta marcada com a eliminada:
            colleccions.eliminarEntrada(idEntrada, entrada.consultarNomEntrada());              // eliminem l'objecte de la classe Entrada

        colleccions.eliminarTeclat(idTeclat, teclat.consultarNomTeclat());                      // eliminem l'objecte de la classe Teclat
    }

    // Metode que modifica un teclat reassignant un alfabet o una entrada
    public void modificarTeclat(int idTeclat, boolean nouAlfabet, int nouId, String teclat) throws Exception {
        if(nouAlfabet) {
            int idAlfabet = colleccions.consultarTeclat(idTeclat).consultarIdAlfabet();         // consultem l'id de l'alfabet antic
            colleccions.consultarAlfabet(idAlfabet).eliminarIdTeclat(idTeclat);                 // eliminem l'id del teclat en aquest
            Alfabet alfabet = colleccions.consultarAlfabet(idAlfabet);
            boolean teTeclats = alfabet.teTeclats();
            boolean eliminat = alfabet.consultarEliminat();
            if(!teTeclats && eliminat)                                                          // si no te cap teclat associat i esta marcat com a eliminat:
                colleccions.eliminarAlfabet(idAlfabet, alfabet.consultarNomAlfabet());          // eliminem l'objecte de la classe Alfabet

            colleccions.consultarTeclat(idTeclat).reassignarAlfabet(teclat, nouId);             // modifiquem el teclat
            colleccions.consultarAlfabet(nouId).afegirIdTeclat(idTeclat);                       // afegim l'id del teclat en el nou alfabet assignat
        }
        else {
            int idEntrada = colleccions.consultarTeclat(idTeclat).consultarIdEntrada();         // consultem l'id de l'entrada antiga
            colleccions.consultarEntrada(idEntrada).eliminarIdTeclat(idTeclat);                 // eliminem l'id del teclat en aquesta
            Entrada entrada = colleccions.consultarEntrada(idEntrada);
            boolean teTeclats = entrada.teTeclats();
            boolean eliminat = entrada.consultarEliminat();
            if(!teTeclats && eliminat)                                                          // si no te cap teclat associat i esta marcada com a eliminada:
                colleccions.eliminarEntrada(idEntrada, entrada.consultarNomEntrada());          // eliminem l'objecte de la classe Entrada

            colleccions.consultarTeclat(idTeclat).reassignarEntrada(teclat, nouId);             // modifiquem el teclat
            colleccions.consultarEntrada(nouId).afegirIdTeclat(idTeclat);                       // afegim l'id del teclat en la nova entrada assignada
        }
    }

    // Metode que consulta tots els atributs d'un teclat
    public String[] consultarTeclat(int idTeclat) throws Exception {
        Teclat teclat = colleccions.consultarTeclat(idTeclat);                                                                      // agafem el teclat a consultar

        String stringAlfabet = "no es pot veure";                                                                                   // si l'alfabet ha sigut modificat no es podra veure
        String stringEntrada = "no es pot veure";                                                                                   // si l'entrada ha sigut modificada no es podra veure

        if(colleccions.consultarAlfabet(teclat.consultarIdAlfabet()).consultarIdTeclat(idTeclat)) {
            Set<Character> alfabet = colleccions.consultarAlfabet(teclat.consultarIdAlfabet()).consultarAlfabet();              // agafem l'alfabet associat al teclat
            StringBuilder stringBuilder = new StringBuilder();                                                                      // ho passem a String
            alfabet.forEach(stringBuilder::append);
            stringAlfabet = stringBuilder.toString();
        }

        if(colleccions.consultarEntrada(teclat.consultarIdEntrada()).consultaridTeclat(idTeclat)) {
            if(colleccions.consultarEntrada(teclat.consultarIdEntrada()) instanceof Text) {
                stringEntrada = ((Text) colleccions.consultarEntrada(teclat.consultarIdEntrada())).consultarText();
            }
            else {
                HashMap<String, Integer> entrada = colleccions.consultarEntrada(teclat.consultarIdEntrada()).consultarLlistaParaules();         // agafem l'entrada associada al teclat
                StringBuilder resultat = new StringBuilder();                                                                                   // ho passem a String
                for (Map.Entry<String, Integer> entry : entrada.entrySet()) {
                    resultat.append(entry.getKey()).append(" ").append(entry.getValue()).append(", ");
                }
                stringEntrada = resultat.toString();
            }
        }

        StringBuilder stringBuilder = new StringBuilder();

        Character[][] tectemp = teclat.consultarTeclat();

        for (Character[] row : tectemp) {
            for (Character col : row) {
                if(col == null) col = ' ';
                stringBuilder.append(col);
            }
            // Valor per distingir canvi de linea
            stringBuilder.append(".");
        }


        // Convertir el StringBuilder a String
        String tecl = stringBuilder.toString();

        return new String[] {tecl, teclat.consultarNomTeclat(), stringAlfabet, stringEntrada};                  // retornem una array amb el contingut del teclat, el seu nom, el seu alfabet i la seva entrada
    }

    // Metode que consulta l'id de l'alfabet d'un teclat
    public int consultarIdAlfabet(int idTeclat) throws Exception {
        return colleccions.consultarTeclat(idTeclat).consultarIdAlfabet();
    }

    // Metode que consulta l'id de l'entrada d'un teclat
    public int consultarIdEntrada(int idTeclat) throws Exception {
        return colleccions.consultarTeclat(idTeclat).consultarIdEntrada();
    }

    // Metode que comprova que no existeixi un teclat amb nom = nomTeclat
    public void comprovarNomTeclat(String nomTeclat) throws Exception {
        if(colleccions.consultarNomTeclat(nomTeclat))
            throw new Exception("ERROR: ja existeix un teclat amb aquest nom");
    }

    // Metode que consulta el nom d'un teclat
    public String consultarNomTeclat(int idTeclat) throws Exception {
        return colleccions.consultarTeclat(idTeclat).consultarNomTeclat();
    }


    // ---------------------------------- ALFABET ---------------------------------- //

    // Metode que crea un alfabet i el guarda a Collections
    public int crearAlfabet(int idAlfabet, String nomAlfabet, boolean eliminat, ArrayList<Integer> idTeclats, String alfabet) throws Exception {
        if(idAlfabet == -1) {
            idAlfabet = colleccions.consultarSeguentIdAlfabet();                                            // agafem el seguent id d'alfabet a assignar
            colleccions.incrementarIdAlfabet();                                                             // incrementem el seguent id d'alfabet a assignar
        }
        Alfabet nouAlfabet = new Alfabet(idAlfabet, nomAlfabet, eliminat, idTeclats, alfabet);              // creem una instancia de la classe Alfabet
        colleccions.afegirAlfabet(idAlfabet, nomAlfabet, nouAlfabet);                                       // el guardem a collections

        return idAlfabet;
    }

    // Metode que elimina un alfabet en cas que no tingui teclats associats, en cas contrari es marca com a eliminat
    public boolean eliminarAlfabet(int idAlfabet) throws Exception {
        Alfabet alfabet = colleccions.consultarAlfabet(idAlfabet);                              // agafem l'alfabet a eliminar

        boolean teTeclats = alfabet.teTeclats();
        if(!teTeclats)                                                                          // si no te cap teclat associat:
            colleccions.eliminarAlfabet(idAlfabet, alfabet.consultarNomAlfabet());              // eliminem l'objecte de la classe Alfabet
        else                                                                                    // si en te algun:
            colleccions.consultarAlfabet(idAlfabet).eliminarAlfabet();                          // posem el boolea eliminat a true

        return teTeclats;                                                                       // retornem un boolea que indica si te teclats
    }

    // Metode que modifica el contingut d'un alfabet
    public void modificarAlfabet(int idAlfabet, String alfabet) throws Exception {
        Set<Character> setAlfabet = new TreeSet<>();
        for(char c:alfabet.toCharArray()) {
            setAlfabet.add(c);
        }
        if(setAlfabet.equals(colleccions.consultarAlfabet(idAlfabet).consultarAlfabet()))
            throw new Exception("ERROR: l'alfabet no pot ser el mateix que ja hi havia");
        colleccions.consultarAlfabet(idAlfabet).modificarAlfabet(alfabet);                      // modifiquem l'alfabet
    }

    // Metode que consulta tots els atributs d'un alfabet
    public String[] consultarAlfabet(int idAlfabet) throws Exception {
        Alfabet alfabet = colleccions.consultarAlfabet(idAlfabet);                              // agafem el teclat a consultar

        if(alfabet.consultarEliminat())
            throw new Exception("ERROR: l'alfabet ha sigut eliminat");
        Set<Character> contingutAlfabet = alfabet.consultarAlfabet();
        StringBuilder stringBuilder = new StringBuilder();                                      // ho passem a String
        contingutAlfabet.forEach(stringBuilder::append);
        String stringAlfabet = stringBuilder.toString();

        return new String[] {stringAlfabet, alfabet.consultarNomAlfabet()};                     // retornem una array amb el contingut de l'alfabet i el seu nom
    }

    // Metode que consulta el contingut de l'alfabet
    public Set<Character> consultarContingutAlfabet(int idAlfabet) throws Exception {
        return colleccions.consultarAlfabet(idAlfabet).consultarAlfabet();
    }

    // Metode que obte l'alfabet netejat
    public Set<Character> obtenirAlfabetNetejat(int idAlfabet) throws Exception {
        Set<Character> alfabet = colleccions.consultarAlfabet(idAlfabet).consultarAlfabet();
        //return netejadora.netejarAlfabet(alfabet);
        return alfabet;
    }

    // Metode que comprova que no existeixi un alfabet amb nom = nomAlfabet
    public void comprovarNomAlfabet(String nomAlfabet) throws Exception {
        if(colleccions.consultarNomAlfabet(nomAlfabet))
            throw new Exception("ERROR: ja existeix un alfabet amb aquest nom");
    }

    // Metode que comprova si un alfabet esta marcat com a eliminat
    public boolean alfabetEliminat(int idAlfabet) throws Exception {
        return colleccions.consultarAlfabet(idAlfabet).consultarEliminat();
    }

    // Metode que consulta el nom d'un alfabet
    public String consultarNomAlfabet(int idAlfabet) throws Exception {
        return colleccions.consultarAlfabet(idAlfabet).consultarNomAlfabet();
    }


    // ---------------------------------- ENTRADA ---------------------------------- //

    // Metode que crea una entrada i la guarda a Collections
    public int crearEntrada(int idEntrada, String nomEntrada, boolean eliminat, String tipus, ArrayList<Integer> idTeclats, String contingutEntrada) throws Exception {
        if(idEntrada == -1) {
            idEntrada = colleccions.consultarSeguentIdEntrada();                                                                // agafem el seguent id d'entrada a assignar
            colleccions.incrementarIdEntrada();                                                                                 // incrementem el seguent id d'entrada a assignar
        }
        if(tipus.equals("text")) {
            Text text = new Text(idEntrada, nomEntrada, eliminat, idTeclats, contingutEntrada);                                 // creem una instancia de la subclasse Text
            colleccions.afegirEntrada(idEntrada, nomEntrada, text);                                                             // ho guardem a collections
        }
        else {
            LlistaParaules llistaParaules = new LlistaParaules(idEntrada, nomEntrada, eliminat, idTeclats, contingutEntrada);   // creem una instancia de la subclasse LlistaParaules
            colleccions.afegirEntrada(idEntrada, nomEntrada, llistaParaules);                                                   // ho guardem a collections
        }

        return idEntrada;
    }

    // Metode que elimina una entrada en cas que no tingui teclats associats, en cas contrari es marca com a eliminada
    public boolean eliminarEntrada(int idEntrada) throws Exception {
        Entrada entrada = colleccions.consultarEntrada(idEntrada);                              // agafem l'entrada a eliminar

        boolean teTeclats = entrada.teTeclats();
        if(!teTeclats)                                                                          // si no te cap teclat associat:
            colleccions.eliminarEntrada(idEntrada, entrada.consultarNomEntrada());              // eliminem l'objecte de la classe Entrada
        else                                                                                    // si en te algun:
            colleccions.consultarEntrada(idEntrada).eliminarEntrada();                          // posem el boolea eliminat a true

        return teTeclats;                                                                       // retornem un boolea que indica si te teclats
    }

    // Metode que modifica el contingut d'una entrada
    public void modificarEntrada(int idEntrada, String contingutEntrada) throws Exception {
        Entrada entrada = colleccions.consultarEntrada(idEntrada);
        if(entrada instanceof Text) {
            if(contingutEntrada.equals(((Text) colleccions.consultarEntrada(idEntrada)).consultarText()))
                throw new Exception("ERROR: l'entrada no pot ser la mateixa que ja hi havia");
        }
        else {

            HashMap<String, Integer> llistaParaules = colleccions.consultarEntrada(idEntrada).consultarLlistaParaules();
            StringBuilder stringBuilder = new StringBuilder();                                      // ho passem a String
            for(Map.Entry<String, Integer> paraula : llistaParaules.entrySet()) {
                stringBuilder.append(paraula.getKey()).append(":").append(paraula.getValue()).append(",");
            }
            String centrada = stringBuilder.toString();

            if(contingutEntrada.equals(centrada))
                throw new Exception("ERROR: l'entrada no pot ser la mateixa que ja hi havia");
        }
        colleccions.consultarEntrada(idEntrada).Modificar_original(contingutEntrada);                      // modifiquem l'entrada
    }

    // Metode que consulta tots els atributs d'una entrada
    public String[] consultarEntrada(int idEntrada) throws Exception {
        Entrada entrada = colleccions.consultarEntrada(idEntrada);                              // agafem l'entrada a consultar

        String contingutEntrada;
        if(entrada.consultarEliminat()) throw new Exception("ERROR: l'entrada ha sigut eliminada");
        if(entrada instanceof Text) {
            contingutEntrada = ((Text) entrada).consultarText();
        }
        else {
            HashMap<String, Integer> llistaParaules = entrada.consultarLlistaParaules();
            StringBuilder stringBuilder = new StringBuilder();                                      // ho passem a String
            for(Map.Entry<String, Integer> paraula : llistaParaules.entrySet()) {
                stringBuilder.append(paraula.getKey()).append(":").append(paraula.getValue()).append(",");
            }
            if(stringBuilder.length() > 0)
                stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length());
            contingutEntrada = stringBuilder.toString();
        }
        return new String[] {contingutEntrada, entrada.consultarNomEntrada()};

    }

    // Metode que obte l'entrada netejada
    public HashMap<String, Integer> obtenirEntradaNetejada(int idEntrada, Set<Character> alfabet) throws Exception {
        HashMap<String, Integer> entrada = colleccions.consultarEntrada(idEntrada).consultarLlistaParaules();
        return netejadora.netejarEntrada(alfabet, entrada);
    }

    // Metode que comprova que no existeixi una entrada amb nom = nomEntrada
    public void comprovarNomEntrada(String nomEntrada) throws Exception {
        if(colleccions.consultarNomEntrada(nomEntrada))
            throw new Exception("ERROR: ja existeix una entrada amb aquest nom");
    }

    // Metode que comprova si una entrada esta marcada com a eliminada
    public boolean entradaEliminada(int idEntrada) throws Exception {
        return colleccions.consultarEntrada(idEntrada).consultarEliminat();
    }

    // Metode que consulta el nom d'una entrada
    public String consultarNomEntrada(int idEntrada) throws Exception {
        return colleccions.consultarEntrada(idEntrada).consultarNomEntrada();
    }
}