package Presentacio.Controladors;

import Domini.Controladors.*;
import Presentacio.Vistes.*;

import javax.swing.*;
import java.util.ArrayList;

public class CtrlPresentacio {
    private static CtrlPresentacio ctrlPresentacio;

    private static CtrlDomini ctrlDomini;

    private CtrlPresentacio() {
        ctrlDomini = CtrlDomini.obtenirInstancia();
    }

    public static CtrlPresentacio obtenirInstancia() {
        if (ctrlPresentacio == null)
            ctrlPresentacio = new CtrlPresentacio();
        return ctrlPresentacio;
    }

    public static void iniPresentacio() {
        VistaLogin VL = new VistaLogin();
        VL.setVisible(true);
    }

    public static void VistaSignin() {
        VistaSignIn VS = new VistaSignIn();
        VS.setVisible(true);
    }

    public static void VistaPrincipal() {
        VistaPrincipal VP = new VistaPrincipal();
        VP.setVisible(true);
    }

    public static void VistaTeclat() {
        VistaTeclat VT = new VistaTeclat();
        VT.setVisible(true);
    }

    public static void VistaAlfabet() {
        VistaAlfabet VA = new VistaAlfabet();
        VA.setVisible(true);
    }

    public static void VistaEntrada() {
        VistaEntrada VE = new VistaEntrada();
        VE.setVisible(true);
    }

    public static void VistaUsuari() {
        VistaUsuari VU = new VistaUsuari();
        VU.setVisible(true);
    }

    public static void crearUsuari(String nomUsuari, String contrasenya) throws Exception {
        ctrlDomini.crearUsuari(nomUsuari, contrasenya);
    }

    public static void eliminarUsuari() throws Exception {
        ctrlDomini.eliminarUsuari();
    }

    public static void iniciarSessio(String nomUsuari, String contrasenya) throws Exception {
        ctrlDomini.iniciarSessio(nomUsuari, contrasenya);
    }

    public static void tancarSessio() throws Exception {
        ctrlDomini.tancarSessio();
    }

    public static void modificarContrasenyaUsuari(String novaContrasenya) throws Exception {
        ctrlDomini.modificarContrasenyaUsuari(novaContrasenya);
    }



    public static void crearTeclat(String nomTeclat, int idAlfabet, int idEntrada, int opcio) throws Exception {
        ctrlDomini.crearTeclat(nomTeclat, idAlfabet, idEntrada, opcio);
    }

    public static void eliminarTeclat(int idTeclat) throws Exception {
        ctrlDomini.eliminarTeclat(idTeclat);
    }

    public static ArrayList<String> consultarTeclats() throws Exception {
        return ctrlDomini.consultarTeclats();
    }

    public static String[] consultarTeclat(int idTeclat) throws Exception {
        return ctrlDomini.consultarTeclat(idTeclat);
    }

    public static void modificarTeclat(int idTeclat, boolean nouAlfabet, int nouId, int opcio) throws Exception {
        ctrlDomini.modificarTeclat(idTeclat, nouAlfabet, nouId, opcio);
    }

    public static void crearAlfabet(String nomAlfabet, String alfabet) throws Exception {
        ctrlDomini.crearAlfabet(nomAlfabet, alfabet);
    }

    public static void eliminarAlfabet(int idAlfabet) throws Exception {
        ctrlDomini.eliminarAlfabet(idAlfabet);
    }

    public static ArrayList<String> consultarAlfabets() throws Exception {
        return ctrlDomini.consultarAlfabets();
    }

    public static String[] consultarAlfabet(int idAlfabet) throws Exception {
        return ctrlDomini.consultarAlfabet(idAlfabet);
    }

    public static void modificarAlfabet(int idAlfabet, String noualfabet) throws Exception {
        ctrlDomini.modificarAlfabet(idAlfabet, noualfabet);
    }

    public static void crearEntrada(String nomEntrada, String tipus, String entrada) throws Exception {
        ctrlDomini.crearEntrada(nomEntrada, tipus, entrada);
    }

    public static void eliminarEntrada(int idEntrada) throws Exception {
        ctrlDomini.eliminarEntrada(idEntrada);
    }

    public static ArrayList<String> consultarEntrades() throws Exception {
        return ctrlDomini.consultarEntrades();
    }

    public static String[] consultarEntrada(int idEntrada) throws Exception {
        return ctrlDomini.consultarEntrada(idEntrada);
    }

    public static void modificarEntrada(int idEntrada, String entrada) throws Exception {
        ctrlDomini.modificarEntrada(idEntrada, entrada);
    }
}