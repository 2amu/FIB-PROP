package Persistencia.Classes;

import java.io.*;
import java.util.ArrayList;

public class GestorAlfabet {

    // ---------------------------------- ATRIBUTS ---------------------------------- //

    // Instancia estatica perque sigui una classe singleton
    private static GestorAlfabet gstAlfabet;

    // Atribut que conte el path de l'arxiu a modificar
    private String path;


    // ---------------------------------- METODES ---------------------------------- //

    // CONSTRUCTORA
    private GestorAlfabet() {
        path = "../DATA/alfabets.txt";
        try {
            File fitxer = new File(path);
            fitxer.createNewFile();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    // Metode per obtenir la instancia d'una classe singleton.
    public static GestorAlfabet obtenirInstancia() {
        if(gstAlfabet == null)
            gstAlfabet = new GestorAlfabet();
        return gstAlfabet;
    }

    // Metode per carregar un Alfabet
    public String carregarAlfabet(int idAlfabet) {
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            String alfabet;
            while((alfabet = br.readLine()) != null) {
                String[] atributs = alfabet.split(",");
                if(Integer.parseInt(atributs[0]) == idAlfabet)
                    return alfabet;
            }
        } catch(IOException e) {
            e.getMessage();
        }
        return "no existeix";
    }

    // Metode per guardar un Alfabet
    public void guardarAlfabet(int idAlfabet, String nomAlfabet, Boolean eliminat, ArrayList<Integer> idTeclats, String contingutAlfabet) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            StringBuilder stringBuilder = new StringBuilder();
            for(int id : idTeclats) {
                stringBuilder.append(id);
                stringBuilder.append(".");
            }
            String stringTeclats;
            if(stringBuilder.length() > 0)
                stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length());
            else
                stringBuilder.append(" ");
            stringTeclats = stringBuilder.toString();

            String alfabet = idAlfabet + "," + nomAlfabet + "," + eliminat + "," + stringTeclats + "," + contingutAlfabet;
            bw.write(alfabet);
            bw.newLine();
        } catch(IOException e) {
            e.getMessage();
        }
    }

    // Metode per eliminar un Alfabet
    public void eliminarAlfabet(int idAlfabet) {
        String pathtmp = "tmp.txt";
        try {
            File fitxer = new File(pathtmp);
            fitxer.createNewFile();
        } catch (IOException e) {
            e.getMessage();
        }
        try(BufferedReader br = new BufferedReader(new FileReader(path));
            BufferedWriter bw = new BufferedWriter(new FileWriter(pathtmp))) {
            String alfabet;
            while((alfabet = br.readLine()) != null) {
                String[] atributs = alfabet.split(",");
                if (!(Integer.parseInt(atributs[0]) == idAlfabet)) {
                    bw.write(alfabet);
                    bw.newLine();
                }
            }
            if(!new java.io.File(path).delete()) {
                throw new IOException("No s'ha pogut eliminar l'arxiu original");
            }
            if(!new java.io.File(pathtmp).renameTo(new java.io.File(path))) {
                throw new IOException("No s'ha pogut renombrar l'arxiu temporal");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

