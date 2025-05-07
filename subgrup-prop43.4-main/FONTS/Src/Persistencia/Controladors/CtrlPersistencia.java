package Persistencia.Controladors;

import Persistencia.Classes.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class CtrlPersistencia {

    // ---------------------------------- ATRIBUTS ---------------------------------- //

    // Instancia estatica perque sigui una classe singleton
    private static CtrlPersistencia ctrlPersistencia;

    // Instancia del gestor d'usuari
    private GestorUsuari gstUsuari;

    // Instancia del gestor de teclat
    private GestorTeclat gstTeclat;

    // Instancia del gestor d'alfabet
    private GestorAlfabet gstAlfabet;

    // Instancia del gestor d'entrada
    private GestorEntrada gstEntrada;

    // Instancia del gestor d'ids
    private GestorIds gstIds;

    // ---------------------------------- METODES ---------------------------------- //

    // CONSTRUCTORA
    private CtrlPersistencia() {
        gstUsuari = GestorUsuari.obtenirInstancia();
        gstTeclat = GestorTeclat.obtenirInstancia();
        gstAlfabet = GestorAlfabet.obtenirInstancia();
        gstEntrada = GestorEntrada.obtenirInstancia();
        gstIds = GestorIds.obtenirInstancia();
    }

    // Metode per obtenir la instancia d'una classe singleton.
    public static CtrlPersistencia obtenirInstancia() {
        if(ctrlPersistencia == null)
            ctrlPersistencia = new CtrlPersistencia();
        return ctrlPersistencia;
    }

    // Metode per carregar els seguents ids a assignar
    public Integer[] carregarSeguentsIds() {
        return gstIds.carregarIds();
    }


    // ---------------------------------- USUARI ---------------------------------- //

    // Metode per carregar un usuari
    public String carregarUsuari(String nomUsuari) {
        return gstUsuari.carregarUsuari(nomUsuari);
    }

    // Metode per consultar la contrasenya d'un usuari
    public String carregarContrasenya(String nomUsuari) {
        String usuari = gstUsuari.carregarUsuari(nomUsuari);
        String[] atributs = usuari.split(",");
        return atributs[1];
    }

    // Metode per guardar un usuari nou
    public void guardarUsuari(String nomUsuari, String contrasenya) {
        gstUsuari.guardarUsuari(nomUsuari, contrasenya, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    // Metode per eliminar un usuari
    public void eliminarUsuari(String nomUsuari) throws IOException {
        String usuari = gstUsuari.carregarUsuari(nomUsuari);
        String[] atributs = usuari.split(",");

        String[] idsTeclats = atributs[2].split("\\.");
        if(!idsTeclats[0].equals(" ")) {
            for(String num : idsTeclats)
                gstTeclat.eliminarTeclat(Integer.parseInt(num));
        }

        String[] idsAlfabets = atributs[3].split("\\.");
        if(!idsAlfabets[0].equals(" ")) {
            for(String num : idsAlfabets)
                gstAlfabet.eliminarAlfabet(Integer.parseInt(num));
        }

        String[] idsEntrades = atributs[4].split("\\.");
        if(!idsEntrades[0].equals(" ")) {
            for(String num : idsEntrades)
                gstEntrada.eliminarEntrada(Integer.parseInt(num));
        }

        gstUsuari.eliminarUsuari(nomUsuari);
    }

    // Metode per modificar la contrasenya d'un usuariº
    public void modificarContrasenya(String novaContrasenya, String nomUsuari) throws IOException {
        String usuari = gstUsuari.carregarUsuari(nomUsuari);
        String[] atributs = usuari.split(",");
        String[] idsTeclats = atributs[2].split("\\.");
        ArrayList<Integer> teclats = new ArrayList<>();
        if(!idsTeclats[0].equals(" ")) {
            for (String id : idsTeclats)
                teclats.add(Integer.parseInt(id));
        }
        String[] idsAlfabets = atributs[3].split("\\.");
        ArrayList<Integer> alfabets = new ArrayList<>();
        if(!idsAlfabets[0].equals(" ")) {
            for (String id : idsAlfabets)
                alfabets.add(Integer.parseInt(id));
        }
        String[] idsEntrades = atributs[4].split("\\.");
        ArrayList<Integer> entrades = new ArrayList<>();
        if(!idsEntrades[0].equals(" ")) {
            for (String id : idsEntrades)
                entrades.add(Integer.parseInt(id));
        }

        gstUsuari.eliminarUsuari(nomUsuari);
        gstUsuari.guardarUsuari(nomUsuari, novaContrasenya, teclats, alfabets, entrades);
    }


    // ---------------------------------- TECLAT ---------------------------------- //

    // Metode per carregar els teclats d'un usuari
    public String[] carregarTeclats(String nomUsuari) {
        String usuari = gstUsuari.carregarUsuari(nomUsuari);
        String[] atributs = usuari.split(",");
        String[] idsTeclats = atributs[2].split("\\.");
        if(idsTeclats[0].equals(" ")) {
            return new String[] {" "};
        }
        String[] teclats = new String[idsTeclats.length];
        int i = 0;
        for(String id : idsTeclats) {
            teclats[i] = gstTeclat.carregarTeclat(Integer.parseInt(id));
            ++i;
        }

        return teclats;
    }

    // Metode per guardar un teclat nou
    public void guardarTeclat(int idTeclat, String nomTeclat, int idAlfabet, int idEntrada, String teclat, String nomUsuari) throws IOException {
        String alfabet = gstAlfabet.carregarAlfabet(idAlfabet);              // afegim l'id teclat a l'alfabet associat
        String[] atributsAlfabet = alfabet.split(",");
        String[] ids = atributsAlfabet[3].split("\\.");
        ArrayList<Integer> nousIds = new ArrayList<>();
        if(!ids[0].equals(" ")) {
            for (String id : ids)
                nousIds.add(Integer.parseInt(id));
        }
        nousIds.add(idTeclat);
        gstAlfabet.eliminarAlfabet(idAlfabet);
        gstAlfabet.guardarAlfabet(idAlfabet, atributsAlfabet[1], Boolean.valueOf(atributsAlfabet[2]), nousIds, atributsAlfabet[4]);
        String entrada = gstEntrada.carregarEntrada(idEntrada);             // afegim l'id teclat a l'entrada associada
        String[] atributsEntrada = entrada.split(",");
        String[] ids2 = atributsEntrada[4].split("\\.");
        ArrayList<Integer> nousIds2 = new ArrayList<>();
        if(!ids2[0].equals(" ")) {
            for (String id : ids2)
                nousIds2.add(Integer.parseInt(id));
        }
        nousIds2.add(idTeclat);
        gstEntrada.eliminarEntrada(idEntrada);
        gstEntrada.guardarEntrada(idEntrada, atributsEntrada[1], Boolean.valueOf(atributsEntrada[2]), atributsEntrada[3], nousIds2, atributsEntrada[5]);
        String usuari = gstUsuari.carregarUsuari(nomUsuari);                // afegim l'id teclat a l'usuari
        String[] atributsUsuari = usuari.split(",");
        String[] ids3 = atributsUsuari[2].split("\\.");
        ArrayList<Integer> nousIds3 = new ArrayList<>();
        if(!ids3[0].equals(" ")) {
            for (String id : ids3) {
                nousIds3.add(Integer.parseInt(id));
            }
        }
        nousIds3.add(idTeclat);
        String[] idsAlf = atributsUsuari[3].split("\\.");
        ArrayList<Integer> idsAlfabets = new ArrayList<>();
        if(!idsAlf[0].equals(" ")) {
            for (String id : idsAlf)
                idsAlfabets.add(Integer.parseInt(id));
        }
        String[] idsEnt = atributsUsuari[4].split("\\.");
        ArrayList<Integer> idsEntrades = new ArrayList<>();
        if(!idsEnt[0].equals(" ")) {
            for (String id : idsEnt)
                idsEntrades.add(Integer.parseInt(id));
        }
        gstUsuari.eliminarUsuari(nomUsuari);
        gstUsuari.guardarUsuari(nomUsuari, atributsUsuari[1], nousIds3, idsAlfabets, idsEntrades);

        gstTeclat.guardarTeclat(idTeclat, nomTeclat, idAlfabet, idEntrada, teclat);
        gstIds.actualitzarIds(1,0,0);
    }

    // Metode per eliminar un teclat
    public void eliminarTeclat(int idTeclat, String nomUsuari) throws IOException {
        String teclat = gstTeclat.carregarTeclat(idTeclat);
        String[] atributs = teclat.split(",");
        int idAlfabet = Integer.parseInt(atributs[2]);
        int idEntrada = Integer.parseInt(atributs[3]);

        String alfabet = gstAlfabet.carregarAlfabet(idAlfabet);             // eliminem l'id teclat de l'alfabet associat
        String[] atributsAlfabet = alfabet.split(",");
        String[] ids = atributsAlfabet[3].split("\\.");
        ArrayList<Integer> nousIds = new ArrayList<>();
        for (String id : ids) {
            if(Integer.parseInt(id) != idTeclat)
                nousIds.add(Integer.parseInt(id));
        }
        gstAlfabet.eliminarAlfabet(idAlfabet);
        if(!nousIds.isEmpty() || !Boolean.parseBoolean(atributsAlfabet[2]))
            gstAlfabet.guardarAlfabet(idAlfabet, atributsAlfabet[1], Boolean.valueOf(atributsAlfabet[2]), nousIds, atributsAlfabet[4]);
        String entrada = gstEntrada.carregarEntrada(idEntrada);             // eliminem l'id teclat de l'entrada associada
        String[] atributsEntrada = entrada.split(",");
        String[] ids2 = atributsEntrada[4].split("\\.");
        ArrayList<Integer> nousIds2 = new ArrayList<>();
        for (String id : ids2) {
            if(Integer.parseInt(id) != idTeclat)
                nousIds2.add(Integer.parseInt(id));
        }
        gstEntrada.eliminarEntrada(idEntrada);
        if(!nousIds2.isEmpty() || !Boolean.parseBoolean(atributsEntrada[2]))
            gstEntrada.guardarEntrada(idEntrada, atributsEntrada[1], Boolean.valueOf(atributsEntrada[2]), atributsEntrada[3], nousIds2, atributsEntrada[5]);
        String usuari = gstUsuari.carregarUsuari(nomUsuari);                // eliminem l'id teclat de l'usuari
        String[] atributsUsuari = usuari.split(",");
        String[] ids3 = atributsUsuari[2].split("\\.");
        ArrayList<Integer> nousIds3 = new ArrayList<>();
        for (String id : ids3) {
            if(Integer.parseInt(id) != idTeclat)
                nousIds3.add(Integer.parseInt(id));
        }
        String[] idsAlf = atributsUsuari[3].split("\\.");
        ArrayList<Integer> idsAlfabets = new ArrayList<>();
        if(!idsAlf[0].equals(" ")) {
            for (String id : idsAlf)
                idsAlfabets.add(Integer.parseInt(id));
        }
        String[] idsEnt = atributsUsuari[4].split("\\.");
        ArrayList<Integer> idsEntrades = new ArrayList<>();
        if(!idsEnt[0].equals(" ")) {
            for (String id : idsEnt)
                idsEntrades.add(Integer.parseInt(id));
        }
        gstUsuari.eliminarUsuari(nomUsuari);
        gstUsuari.guardarUsuari(nomUsuari, atributsUsuari[1], nousIds3, idsAlfabets, idsEntrades);

        gstTeclat.eliminarTeclat(idTeclat);
    }

    // Metode per modificar un teclat
    public void modificarTeclat(int idTeclat, boolean nouAlfabet, int nouId, String nouTeclat, String nomUsuari) throws IOException {
        String teclat = gstTeclat.carregarTeclat(idTeclat);
        String[] atributs = teclat.split(",");

        if(nouAlfabet) {
            int anticId = Integer.parseInt(atributs[2]);                    // eliminem l'id teclat de l'antic alfabet associat
            String alfabetAnt = gstAlfabet.carregarAlfabet(anticId);
            String[] atributsAlfabetAnt = alfabetAnt.split(",");
            String[] idsAnt = atributsAlfabetAnt[3].split("\\.");
            ArrayList<Integer> nousIdsAnt = new ArrayList<>();
            if(!idsAnt[0].equals(" ")) {
                for (String id : idsAnt) {
                    if (Integer.parseInt(id) != idTeclat)
                        nousIdsAnt.add(Integer.parseInt(id));
                }
            }

            gstAlfabet.eliminarAlfabet(anticId);
            if(!nousIdsAnt.isEmpty() || !Boolean.parseBoolean(atributsAlfabetAnt[2]))
                gstAlfabet.guardarAlfabet(anticId, atributsAlfabetAnt[1], Boolean.valueOf(atributsAlfabetAnt[2]), nousIdsAnt, atributsAlfabetAnt[4]);

            else {
                String usuari = gstUsuari.carregarUsuari(nomUsuari);                                                // eliminem l'id alfabet d'usuari
                String[] atributsUsuari = usuari.split(",");
                String[] idsAlfabets = atributsUsuari[3].split("\\.");
                ArrayList<Integer> nousIds = new ArrayList<>();
                for (String id : idsAlfabets) {
                    if(Integer.parseInt(id) != anticId)
                        nousIds.add(Integer.parseInt(id));
                }
                String[] idsTec2 = atributsUsuari[2].split("\\.");
                ArrayList<Integer> idsTeclats = new ArrayList<>();
                if(!idsTec2[0].equals(" ")) {
                    for (String id : idsTec2)
                        idsTeclats.add(Integer.parseInt(id));
                }
                String[] idsEnt = atributsUsuari[4].split("\\.");
                ArrayList<Integer> idsEntrades = new ArrayList<>();
                if(!idsEnt[0].equals(" ")) {
                    for (String id : idsEnt)
                        idsEntrades.add(Integer.parseInt(id));
                }
                gstUsuari.eliminarUsuari(nomUsuari);
                gstUsuari.guardarUsuari(nomUsuari, atributsUsuari[1], idsTeclats, nousIds, idsEntrades);
            }

            String alfabet = gstAlfabet.carregarAlfabet(nouId);             // afegim l'id teclat al nou alfabet associat
            String[] atributsAlfabet = alfabet.split(",");
            String[] ids = atributsAlfabet[3].split("\\.");
            ArrayList<Integer> nousIds = new ArrayList<>();
            if(!ids[0].equals(" ")) {
                for (String id : ids) {
                    nousIds.add(Integer.parseInt(id));
                }
            }
            nousIds.add(idTeclat);
            gstAlfabet.eliminarAlfabet(nouId);
            gstAlfabet.guardarAlfabet(nouId, atributsAlfabet[1], Boolean.valueOf(atributsAlfabet[2]), nousIds, atributsAlfabet[4]);

            gstTeclat.eliminarTeclat(idTeclat);
            gstTeclat.guardarTeclat(idTeclat, atributs[1], nouId, Integer.parseInt(atributs[3]), nouTeclat);
        }

        else {
            int anticId = Integer.parseInt(atributs[3]);                    // eliminem l'id teclat de l'antiga entrada associada
            String entradaAnt = gstEntrada.carregarEntrada(anticId);
            String[] atributsEntradaAnt = entradaAnt.split(",");
            String[] idsAnt = atributsEntradaAnt[4].split("\\.");
            ArrayList<Integer> nousIdsAnt = new ArrayList<>();
            if(!idsAnt[0].equals(" ")) {
                for (String id : idsAnt) {
                    if (Integer.parseInt(id) != idTeclat)
                        nousIdsAnt.add(Integer.parseInt(id));
                }
            }
            gstEntrada.eliminarEntrada(anticId);
            if(!nousIdsAnt.isEmpty() || !Boolean.parseBoolean(atributsEntradaAnt[2]))
                gstEntrada.guardarEntrada(anticId, atributsEntradaAnt[1], Boolean.valueOf(atributsEntradaAnt[2]), atributsEntradaAnt[3], nousIdsAnt, atributsEntradaAnt[5]);

            else {
                String usuari = gstUsuari.carregarUsuari(nomUsuari);                                                                    // eliminem l'id entrada d'usuari
                String[] atributsUsuari = usuari.split(",");
                String[] idsEntrades = atributsUsuari[4].split("\\.");
                ArrayList<Integer> nousIds = new ArrayList<>();
                for (String id : idsEntrades) {
                    if(Integer.parseInt(id) != anticId)
                        nousIds.add(Integer.parseInt(id));
                }
                String[] idsTec2 = atributsUsuari[2].split("\\.");
                ArrayList<Integer> idsTeclats = new ArrayList<>();
                if(!idsTec2[0].equals(" ")) {
                    for (String id : idsTec2)
                        idsTeclats.add(Integer.parseInt(id));
                }
                String[] idsAlf = atributsUsuari[3].split("\\.");
                ArrayList<Integer> idsAlfabets = new ArrayList<>();
                if(!idsAlf[0].equals(" ")) {
                    for (String id : idsAlf)
                        idsAlfabets.add(Integer.parseInt(id));
                }
                gstUsuari.eliminarUsuari(nomUsuari);
                gstUsuari.guardarUsuari(nomUsuari, atributsUsuari[1], idsTeclats, idsAlfabets, nousIds);
            }

            String entrada = gstEntrada.carregarEntrada(nouId);             // afegim l'id teclat a la nova entrada associada
            String[] atributsEntrada = entrada.split(",");
            String[] ids = atributsEntrada[4].split("\\.");
            ArrayList<Integer> nousIds = new ArrayList<>();
            if(!ids[0].equals(" ")) {
                for (String id : ids) {
                    nousIds.add(Integer.parseInt(id));
                }
            }
            nousIds.add(idTeclat);
            gstEntrada.eliminarEntrada(nouId);
            gstEntrada.guardarEntrada(nouId, atributsEntrada[1], Boolean.valueOf(atributsEntrada[2]), atributsEntrada[3], nousIds, atributsEntrada[5]);

            gstTeclat.eliminarTeclat(idTeclat);
            gstTeclat.guardarTeclat(idTeclat, atributs[1], Integer.parseInt(atributs[2]), nouId, nouTeclat);
        }
    }



    // ---------------------------------- ALFABET ---------------------------------- //

    // Metode per carregar els alfabets d'un usuari
    public String[] carregarAlfabets(String nomUsuari) {
        String usuari = gstUsuari.carregarUsuari(nomUsuari);
        String[] atributs = usuari.split(",");
        String[] idsAlfabets = atributs[3].split("\\.");
        if(idsAlfabets[0].equals(" ")) {
            return new String[] {" "};
        }
        String[] alfabets = new String[idsAlfabets.length];
        int i = 0;
        for(String id : idsAlfabets) {
            alfabets[i] = gstAlfabet.carregarAlfabet(Integer.parseInt(id));
            ++i;
        }

        return alfabets;
    }

    // Metode per guardar un alfabet nou
    public void guardarAlfabet(int idAlfabet, String nomAlfabet, String alfabet, String nomUsuari) throws IOException {
        String usuari = gstUsuari.carregarUsuari(nomUsuari);                // afegim l'id alfabet a l'usuari
        String[] atributsUsuari = usuari.split(",");
        String[] ids = atributsUsuari[3].split("\\.");
        ArrayList<Integer> nousIds = new ArrayList<>();
        if(!ids[0].equals(" ")) {
            for (String id : ids)
                nousIds.add(Integer.parseInt(id));
        }
        nousIds.add(idAlfabet);
        String[] idsTec = atributsUsuari[2].split("\\.");
        ArrayList<Integer> idsTeclats = new ArrayList<>();
        if(!idsTec[0].equals(" ")) {
            for (String id : idsTec)
                idsTeclats.add(Integer.parseInt(id));
        }
        String[] idsEnt = atributsUsuari[4].split("\\.");
        ArrayList<Integer> idsEntrades = new ArrayList<>();
        if(!idsEnt[0].equals(" ")) {
            for (String id : idsEnt)
                idsEntrades.add(Integer.parseInt(id));
        }
        gstUsuari.eliminarUsuari(nomUsuari);
        gstUsuari.guardarUsuari(nomUsuari, atributsUsuari[1], idsTeclats, nousIds, idsEntrades);

        gstAlfabet.guardarAlfabet(idAlfabet, nomAlfabet, Boolean.FALSE, new ArrayList<>(), alfabet);
        gstIds.actualitzarIds(0,1,0);
    }

    // Metode per eliminar un alfabet
    public void eliminarAlfabet(int idAlfabet, String nomUsuari) throws IOException {
        String alfabet = gstAlfabet.carregarAlfabet(idAlfabet);
        String[] atributs = alfabet.split(",");
        boolean teTeclats = !atributs[3].equals(" ");
        String[] ids = atributs[3].split("\\.");
        ArrayList<Integer> idsTec = new ArrayList<>();
        if(!ids[0].equals(" ")) {
            for (String id : ids)
                idsTec.add(Integer.parseInt(id));
        }

        if(teTeclats) {
            gstAlfabet.eliminarAlfabet(idAlfabet);
            gstAlfabet.guardarAlfabet(idAlfabet, atributs[1], Boolean.TRUE, idsTec, atributs[4]);               // marquem l'atribut eliminat com a cert
        }

        else {
            String usuari = gstUsuari.carregarUsuari(nomUsuari);                                                // eliminem l'id alfabet d'usuari
            String[] atributsUsuari = usuari.split(",");
            String[] idsAlfabets = atributsUsuari[3].split("\\.");
            ArrayList<Integer> nousIds = new ArrayList<>();
            for (String id : idsAlfabets) {
                if(Integer.parseInt(id) != idAlfabet)
                    nousIds.add(Integer.parseInt(id));
            }
            String[] idsTec2 = atributsUsuari[2].split("\\.");
            ArrayList<Integer> idsTeclats = new ArrayList<>();
            if(!idsTec2[0].equals(" ")) {
                for (String id : idsTec2)
                    idsTeclats.add(Integer.parseInt(id));
            }
            String[] idsEnt = atributsUsuari[4].split("\\.");
            ArrayList<Integer> idsEntrades = new ArrayList<>();
            if(!idsEnt[0].equals(" ")) {
                for (String id : idsEnt)
                    idsEntrades.add(Integer.parseInt(id));
            }
            gstUsuari.eliminarUsuari(nomUsuari);
            gstUsuari.guardarUsuari(nomUsuari, atributsUsuari[1], idsTeclats, nousIds, idsEntrades);

            gstAlfabet.eliminarAlfabet(idAlfabet);
        }
    }

    // Metode per modificar un alfabet
    public void modificarAlfabet(int idAlfabet, String contingutAlfabet) {
        String alfabet = gstAlfabet.carregarAlfabet(idAlfabet);
        String[] atributs = alfabet.split(",");
        String[] ids = atributs[3].split("\\.");
        ArrayList<Integer> idsTec = new ArrayList<>();
        if(!ids[0].equals(" ")) {
            for (String id : ids)
                idsTec.add(Integer.parseInt(id));
        }

        gstAlfabet.eliminarAlfabet(idAlfabet);
        gstAlfabet.guardarAlfabet(idAlfabet, atributs[1], Boolean.valueOf(atributs[2]), idsTec, contingutAlfabet);
    }


    // ---------------------------------- ENTRADA ---------------------------------- //

    // Metode per carregar les entrades d'un usuari
    public String[] carregarEntrades(String nomUsuari) {
        String usuari = gstUsuari.carregarUsuari(nomUsuari);
        String[] atributs = usuari.split(",");
        String[] idsEntrades = atributs[4].split("\\.");
        if(idsEntrades[0].equals(" ")) {
            return new String[] {" "};
        }
        String[] entrades = new String[idsEntrades.length];
        int i = 0;
        for(String id : idsEntrades) {
            entrades[i] = gstEntrada.carregarEntrada(Integer.parseInt(id));
            ++i;
        }

        return entrades;
    }

    // Metode per guardar una entrada nova
    public void guardarEntrada(int idEntrada, String nomEntrada, String tipus, String entrada, String nomUsuari) throws IOException {
        String usuari = gstUsuari.carregarUsuari(nomUsuari);                                                                        // afegim l'id entrada a l'usuari
        String[] atributsUsuari = usuari.split(",");
        String[] ids = atributsUsuari[4].split("\\.");
        ArrayList<Integer> nousIds = new ArrayList<>();
        if(!ids[0].equals(" ")) {
            for (String id : ids)
                nousIds.add(Integer.parseInt(id));
        }
        nousIds.add(idEntrada);
        String[] idsTec = atributsUsuari[2].split("\\.");
        ArrayList<Integer> idsTeclats = new ArrayList<>();
        if(!idsTec[0].equals(" ")) {
            for (String id : idsTec)
                idsTeclats.add(Integer.parseInt(id));
        }
        String[] idsAlf = atributsUsuari[3].split("\\.");
        ArrayList<Integer> idsAlfabets = new ArrayList<>();
        if(!idsAlf[0].equals(" ")) {
            for (String id : idsAlf)
                idsAlfabets.add(Integer.parseInt(id));
        }
        gstUsuari.eliminarUsuari(nomUsuari);
        gstUsuari.guardarUsuari(nomUsuari, atributsUsuari[1], idsTeclats, idsAlfabets, nousIds);

        gstEntrada.guardarEntrada(idEntrada, nomEntrada, Boolean.FALSE, tipus, new ArrayList<>(), entrada);
        gstIds.actualitzarIds(0,0,1);
    }

    // Metode per eliminar una entrada
    public void eliminarEntrada(int idEntrada, String nomUsuari) throws IOException {
        String entrada = gstEntrada.carregarEntrada(idEntrada);
        String[] atributs = entrada.split(",");
        boolean teTeclats = !atributs[4].equals(" ");
        String[] ids = atributs[4].split("\\.");
        ArrayList<Integer> idsTec = new ArrayList<>();
        if(!ids[0].equals(" ")) {
            for (String id : ids)
                idsTec.add(Integer.parseInt(id));
        }

        if(teTeclats) {
            gstEntrada.eliminarEntrada(idEntrada);
            gstEntrada.guardarEntrada(idEntrada, atributs[1], Boolean.TRUE, atributs[3], idsTec, atributs[5]);                      // marquem l'atribut eliminat com a cert
        }

        else {
            String usuari = gstUsuari.carregarUsuari(nomUsuari);                                                                    // eliminem l'id entrada d'usuari
            String[] atributsUsuari = usuari.split(",");
            String[] idsEntrades = atributsUsuari[4].split("\\.");
            ArrayList<Integer> nousIds = new ArrayList<>();
            for (String id : idsEntrades) {
                if(Integer.parseInt(id) != idEntrada)
                    nousIds.add(Integer.parseInt(id));
            }
            String[] idsTec2 = atributsUsuari[2].split("\\.");
            ArrayList<Integer> idsTeclats = new ArrayList<>();
            if(!idsTec2[0].equals(" ")) {
                for (String id : idsTec2)
                    idsTeclats.add(Integer.parseInt(id));
            }
            String[] idsAlf = atributsUsuari[3].split("\\.");
            ArrayList<Integer> idsAlfabets = new ArrayList<>();
            if(!idsAlf[0].equals(" ")) {
                for (String id : idsAlf)
                    idsAlfabets.add(Integer.parseInt(id));
            }
            gstUsuari.eliminarUsuari(nomUsuari);
            gstUsuari.guardarUsuari(nomUsuari, atributsUsuari[1], idsTeclats, idsAlfabets, nousIds);

            gstEntrada.eliminarEntrada(idEntrada);
        }
    }

    // Metode per modificar una entrada
    public void modificarEntrada(int idEntrada, String contingutEntrada) {
        String entrada = gstEntrada.carregarEntrada(idEntrada);
        String[] atributs = entrada.split(",");
        String[] ids = atributs[4].split("\\.");
        ArrayList<Integer> idsTec = new ArrayList<>();
        if(!ids[0].equals(" ")) {
            for (String id : ids)
                idsTec.add(Integer.parseInt(id));
        }

        gstEntrada.eliminarEntrada(idEntrada);
        gstEntrada.guardarEntrada(idEntrada, atributs[1], Boolean.valueOf(atributs[2]), atributs[3], idsTec, contingutEntrada);
    }
}