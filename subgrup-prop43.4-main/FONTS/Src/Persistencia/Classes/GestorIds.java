package Persistencia.Classes;

import java.io.*;

public class GestorIds {

    // ---------------------------------- ATRIBUTS ---------------------------------- //

    // Instancia estatica perque sigui una classe singleton
    private static GestorIds gstIds;

    // Atribut que conte el path de l'arxiu a modificar
    private String path;


    // ---------------------------------- METODES ---------------------------------- //

    // CONSTRUCTORA
    private GestorIds() {
        path = "../DATA/ids.txt";
        try {
            File fitxer = new File(path);
            if(fitxer.createNewFile()) {
                try(BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
                    bw.write("1");
                    bw.newLine();
                    bw.write("1");
                    bw.newLine();
                    bw.write("1");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }

    // Metode per obtenir la instancia d'una classe singleton.
    public static GestorIds obtenirInstancia() {
        if(gstIds == null)
            gstIds = new GestorIds();
        return gstIds;
    }

    // Metode per carregar els ids
    public Integer[] carregarIds() {
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            String id;
            Integer[] ids = new Integer[3];
            int i = 0;
            while((id = br.readLine()) != null) {
                ids[i] = Integer.parseInt(id);
                ++i;
            }
            return ids;
        } catch(IOException e) {
            e.getMessage();
        }
        return new Integer[3];
    }

    // Metode per actualitzar els ids
    public void actualitzarIds(int idTeclat, int idAlfabet, int idEntrada) throws IOException {
        Integer[] ids = carregarIds();
        ids[0] += idTeclat;
        ids[1] += idAlfabet;
        ids[2] += idEntrada;
        if(!new java.io.File(path).delete()) {
            throw new IOException("No s'ha pogut eliminar l'arxiu original");
        }
        try {
            File fitxer = new File(path);
            fitxer.createNewFile();
        } catch (IOException e) {
            e.getMessage();
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            bw.write(ids[0].toString());
            bw.newLine();
            bw.write(ids[1].toString());
            bw.newLine();
            bw.write(ids[2].toString());
        }
    }
}
