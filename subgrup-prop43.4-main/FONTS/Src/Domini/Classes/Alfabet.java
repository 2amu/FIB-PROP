package Domini.Classes;

import java.util.Set;
import java.util.TreeSet;
import java.text.Normalizer;
import java.util.ArrayList;
import java.lang.String;

public class Alfabet {
    // no se puede permitir caracteres repetidos

    private Integer idAlfabet;
    private String nomAlfabet;
    private Boolean eliminat;
    private ArrayList<Integer> idTeclats;
    private Set<Character> alfabet;

    //CREADORA
    public Alfabet(int idAlfabet, String nomAlfabet, Boolean eliminat, ArrayList<Integer> idTeclats, String alfabet) {
        this.idAlfabet = idAlfabet;
        this.nomAlfabet = nomAlfabet;
        this.eliminat = eliminat;
        this.idTeclats = idTeclats;
        Set<Character> caracteresAlfabet = new TreeSet<>();

        //Comprobar que no hi han caracters repetits
        for(char c : alfabet.toCharArray()){
            if(!caracteresAlfabet.add(c)){
                //throw new Exception("ERROR: S'ha trobat un caracter repetit");
            }
        }
        caracteresAlfabet = netejarAlfabet(caracteresAlfabet);

        this.alfabet = caracteresAlfabet;
    }

    //afegeix teclat associat
    public void afegirIdTeclat(Integer idTeclat) {
        this.idTeclats.add(idTeclat);
    }

    //Consultar si teclat pertany a l'alfabet
    public boolean consultarIdTeclat(Integer idTeclat) {
        return this.idTeclats.contains(idTeclat);
    }

    //Consultar alfabet
    public Set<Character> consultarAlfabet() {
        return alfabet;
    }

    //Consultar Nom d'alfabet
    public String consultarNomAlfabet() {
        return nomAlfabet;
    }

    //Consultar si s'ha eliminat
    public boolean consultarEliminat() {
        return eliminat;
    }

    //Indicar que s'ha d'eliminar
    public void eliminarAlfabet() {
        this.eliminat = true;
    }

    //Elimina idteclat associat
    public void eliminarIdTeclat(Integer idTeclat) {
        this.idTeclats.remove(idTeclat);
    }

    public void modificarAlfabet(String nouAlfabet) {
        idTeclats = new ArrayList<>();
        Set<Character> caracteresAlfabet = new TreeSet<>();
        for(char c:nouAlfabet.toCharArray()) {
            caracteresAlfabet.add(c);
        }
        alfabet = caracteresAlfabet;
    }

    public boolean teTeclats() {
        return idTeclats.size() != 0;
    }

    //NETEJADORA D'ALFABET
    private Set<Character> netejarAlfabet(Set<Character> Abc) {
        Set<Character> RecentAbc = new TreeSet<>();
        for(Character c : Abc) {
            if (Character.isLetter(c)) {
                String s = Normalizer.normalize(Character.toString(c), Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
                c = s.charAt(0);
                c = Character.toLowerCase(c);

                RecentAbc.add(c);
            }
        }
        return RecentAbc;
    }

}