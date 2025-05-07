package Persistencia.Classes;

import java.io.*;
import java.util.ArrayList;

public class GestorEntrada {

    // ---------------------------------- ATRIBUTS ---------------------------------- //

    // Instancia estatica perque sigui una classe singleton
    private static GestorEntrada gstEntrada;

    // Atribut que conte el path de l'arxiu a modificar
    private String path;


    // ---------------------------------- METODES ---------------------------------- //

    // CONSTRUCTORA
    private GestorEntrada() {
        path = "../DATA/entrades.txt";
        try {
            File fitxer = new File(path);
            fitxer.createNewFile();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    // Metode per obtenir la instancia d'una classe singleton.
    public static GestorEntrada obtenirInstancia() {
        if(gstEntrada == null)
            gstEntrada = new GestorEntrada();
        return gstEntrada;
    }

    // Metode per carregar un Entrada
    public String carregarEntrada(int idEntrada) {
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            String entrada;
            while((entrada = br.readLine()) != null) {
                String[] atributs = entrada.split(",");
                if(Integer.parseInt(atributs[0]) == idEntrada)
                    return entrada;
            }
        } catch(IOException e) {
            e.getMessage();
        }
        return "no existeix";
    }

    // Metode per guardar un Entrada
    public void guardarEntrada(int idEntrada, String nomEntrada, Boolean eliminat, String tipus, ArrayList<Integer> idTeclats, String contingutEntrada) {
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

            String entrada = idEntrada + "," + nomEntrada + "," + eliminat + "," + tipus + "," + stringTeclats + "," + contingutEntrada;
            bw.write(entrada);
            bw.newLine();
        } catch(IOException e) {
            e.getMessage();
        }
    }

    // Metode per eliminar un Entrada
    public void eliminarEntrada(int idEntrada) {
        String pathtmp = "tmp.txt";
        try {
            File fitxer = new File(pathtmp);
            fitxer.createNewFile();
        } catch (IOException e) {
            e.getMessage();
        }
        try(BufferedReader br = new BufferedReader(new FileReader(path));
            BufferedWriter bw = new BufferedWriter(new FileWriter(pathtmp))) {
            String entrada;
            while((entrada = br.readLine()) != null) {
                String[] atributs = entrada.split(",");
                if (!(Integer.parseInt(atributs[0]) == idEntrada)) {
                    bw.write(entrada);
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
