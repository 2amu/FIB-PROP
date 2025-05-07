package Persistencia.Classes;

import java.io.*;

public class GestorTeclat {

    // ---------------------------------- ATRIBUTS ---------------------------------- //

    // Instancia estatica perque sigui una classe singleton
    private static GestorTeclat gstTeclat;

    // Atribut que conte el path de l'arxiu a modificar
    private String path;


    // ---------------------------------- METODES ---------------------------------- //

    // CONSTRUCTORA
    private GestorTeclat() {
        path = "../DATA/teclats.txt";
        try {
            File fitxer = new File(path);
            fitxer.createNewFile();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    // Metode per obtenir la instancia d'una classe singleton.
    public static GestorTeclat obtenirInstancia() {
        if(gstTeclat == null)
            gstTeclat = new GestorTeclat();
        return gstTeclat;
    }

    // Metode per carregar un Teclat
    public String carregarTeclat(int idTeclat) {
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            String teclat;
            while((teclat = br.readLine()) != null) {
                String[] atributs = teclat.split(",");
                if(Integer.parseInt(atributs[0]) == idTeclat)
                    return teclat;
            }
        } catch(IOException e) {
            e.getMessage();
        }
        return "no existeix";
    }

    // Metode per guardar un Teclat
    public void guardarTeclat(int idTeclat, String nomTeclat, int idAlfabet, int idEntrada, String contingutTeclat) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            String teclat = idTeclat + "," + nomTeclat + "," + idAlfabet + "," + idEntrada + "," + contingutTeclat;
            bw.write(teclat);
            bw.newLine();
        } catch(IOException e) {
            e.getMessage();
        }
    }

    // Metode per eliminar un Teclat
    public void eliminarTeclat(int idTeclat) throws IOException {
        String pathtmp = "tmp.txt";
        try {
            File fitxer = new File(pathtmp);
            fitxer.createNewFile();
        } catch (IOException e) {
            e.getMessage();
        }
        try(BufferedReader br = new BufferedReader(new FileReader(path));
            BufferedWriter bw = new BufferedWriter(new FileWriter(pathtmp))) {
            String teclat;
            while((teclat = br.readLine()) != null) {
                String[] atributs = teclat.split(",");
                if (!(Integer.parseInt(atributs[0]) == idTeclat)) {
                    bw.write(teclat);
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