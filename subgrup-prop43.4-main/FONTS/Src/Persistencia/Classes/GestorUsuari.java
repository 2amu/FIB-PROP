package Persistencia.Classes;

import java.io.*;
import java.util.ArrayList;

public class GestorUsuari {

    // ---------------------------------- ATRIBUTS ---------------------------------- //

    // Instancia estatica perque sigui una classe singleton
    private static GestorUsuari gstUsuari;

    // Atribut que conte el path de l'arxiu a modificar
    private String path;


    // ---------------------------------- METODES ---------------------------------- //

    // CONSTRUCTORA
    private GestorUsuari() {
        path = "../DATA/usuaris.txt";
        try {
            File fitxer = new File(path);
            fitxer.createNewFile();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    // Metode per obtenir la instancia d'una classe singleton.
    public static GestorUsuari obtenirInstancia() {
        if(gstUsuari == null)
            gstUsuari = new GestorUsuari();
        return gstUsuari;
    }

    // Metode per carregar un usuari
    public String carregarUsuari(String nomUsuari) {
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            String usuari;
            while((usuari = br.readLine()) != null) {
                String[] atributs = usuari.split(",");
                if(atributs[0].equals(nomUsuari))
                    return usuari;
            }
        } catch(IOException e) {
            e.getMessage();
        }
        return "no existeix";
    }

    // Metode per guardar un usuari
    public void guardarUsuari(String nomUsuari, String contrasenya, ArrayList<Integer> teclats, ArrayList<Integer> alfabets, ArrayList<Integer> entrades) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            StringBuilder stringBuilder = new StringBuilder();
            for(int id : teclats) {
                stringBuilder.append(id);
                stringBuilder.append(".");
            }
            String stringTeclats;
            if(stringBuilder.length() > 0)
                stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length());
            else
                stringBuilder.append(" ");
            stringTeclats = stringBuilder.toString();

            StringBuilder stringBuilder2 = new StringBuilder();
            for(int id : alfabets) {
                stringBuilder2.append(id);
                stringBuilder2.append(".");
            }
            String stringAlfabets;
            if(stringBuilder2.length() > 0)
                stringBuilder2.delete(stringBuilder2.length()-1, stringBuilder2.length());
            else
                stringBuilder2.append(" ");
            stringAlfabets = stringBuilder2.toString();

            StringBuilder stringBuilder3 = new StringBuilder();
            for(int id : entrades) {
                stringBuilder3.append(id);
                stringBuilder3.append(".");
            }
            String stringEntrades;
            if(stringBuilder3.length() > 0)
                stringBuilder3.delete(stringBuilder3.length()-1, stringBuilder3.length());
            else
                stringBuilder3.append(" ");
            stringEntrades = stringBuilder3.toString();

            String usuari = nomUsuari + "," + contrasenya + "," + stringTeclats + "," + stringAlfabets + "," + stringEntrades;
            bw.write(usuari);
            bw.newLine();
        } catch(IOException e) {
            e.getMessage();
        }
    }

    // Metode per eliminar un usuari
    public void eliminarUsuari(String nomUsuari) throws IOException {
        String pathtmp = "tmp.txt";
        try {
            File fitxer = new File(pathtmp);
            fitxer.createNewFile();
        } catch (IOException e) {
            e.getMessage();
        }
        try(BufferedReader br = new BufferedReader(new FileReader(path));
            BufferedWriter bw = new BufferedWriter(new FileWriter(pathtmp))) {
            String usuari;
            while((usuari = br.readLine()) != null) {
                String[] atributs = usuari.split(",");
                if (!atributs[0].equals(nomUsuari)) {
                    bw.write(usuari);
                    bw.newLine();
                }
            }
            if(!new java.io.File(path).delete()) {
                throw new IOException("No s'ha pogut eliminar l'arxiu original");
            }
            if(!new java.io.File(pathtmp).renameTo(new java.io.File(path))) {
                throw new IOException("No s'ha pogut renombrar l'arxiu temporal");
            }
        }
    }
}