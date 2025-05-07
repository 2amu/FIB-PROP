package Drivers;

import Domini.Controladors.CtrlDomini;
import Persistencia.Controladors.CtrlPersistencia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Boolean.TRUE;

public class DriverCtrlPersistencia {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CtrlPersistencia ctrlPersistencia = CtrlPersistencia.obtenirInstancia();

        while (TRUE) { // Menu d'opcions
            System.out.println("\nMENU");
            System.out.println("1. Carregar seguents ids");
            System.out.println("2. Carregar usuari");
            System.out.println("3. Carregar contrasenya");
            System.out.println("4. Guardar usuari");
            System.out.println("5. Eliminar usuari");
            System.out.println("6. Modificar contrasenya");
            System.out.println("7. Carregar teclats");
            System.out.println("8. Guardar teclat");
            System.out.println("9. Eliminar teclat");
            System.out.println("10. Modificar teclat");
            System.out.println("11. Carregar alfabets");
            System.out.println("12. Guardar alfabet");
            System.out.println("13. Eliminar alfabet");
            System.out.println("14. Modificar alfabet");
            System.out.println("15. Carregar entrades");
            System.out.println("16. Guardar entrada");
            System.out.println("17. Eliminar entrada");
            System.out.println("18. Modificar entrada");
            System.out.println("");
            System.out.println("0. Sortir - Finalitzar execució");
            System.out.println("");

            int opcioescollida = scanner.nextInt();
            scanner.nextLine();

            switch (opcioescollida) {
                case 0:
                    return;
                case 1:     // Carregar seguents ids
                    try {
                        Integer[] ids = ctrlPersistencia.carregarSeguentsIds();
                        System.out.println("seguent id teclat: " + ids[0]);
                        System.out.println("seguent id alfabet: " + ids[1]);
                        System.out.println("seguent id entrada: " + ids[2]);
                    } catch (Exception e) {
                        System.out.println("Error al carregar els seguents ids: " + e.getMessage());
                    }
                    break;


                case 2:     // Carregar usuari
                    try {
                        System.out.println("Introdueix el nom de l'usuari:");
                        String nomUsuari = scanner.nextLine();
                        String infoUsuari = ctrlPersistencia.carregarUsuari(nomUsuari);
                        String[] atributs = infoUsuari.split(",");
                        System.out.println("nomUsuari: " + atributs[0]);
                        System.out.println("contrasenya: " + atributs[1]);

                        System.out.print("ids teclats: ");
                        String[] idsTeclats = atributs[2].split("\\.");
                        for (String id : idsTeclats) {
                            System.out.print(id + " ");
                        }
                        System.out.println("");

                        System.out.print("ids alfabets: ");
                        String[] idsAlfabets = atributs[3].split("\\.");
                        for (String id : idsAlfabets) {
                            System.out.print(id + " ");
                        }
                        System.out.println();

                        System.out.print("ids entrades: ");
                        String[] idsEntrades = atributs[4].split("\\.");
                        for (String id : idsEntrades) {
                            System.out.print(id + " ");
                        }
                        System.out.println();
                    } catch (Exception e) {
                        System.out.println("Error al consultar la informació de l'usuari: " + e.getMessage());
                    }
                    break;


                case 3:     // Carregar contrasenya
                    try {
                        System.out.println("Introdueix el nom de l'usuari:");
                        String nomUsuari = scanner.nextLine();
                        String contrasenya = ctrlPersistencia.carregarContrasenya(nomUsuari);
                        System.out.println("contrasenya: " + contrasenya);
                    } catch (Exception e) {
                        System.out.println("Error al consultar la informació de l'usuari: " + e.getMessage());
                    }
                    break;


                case 4:     // Guardar usuari
                    try {
                        System.out.println("Introdueix el nom de l'usuari:");
                        String nomUsuari = scanner.nextLine();
                        System.out.println("Introdueix la contrasenya de l'usuari:");
                        String contrasenya = scanner.nextLine();
                        ctrlPersistencia.guardarUsuari(nomUsuari, contrasenya);
                    } catch (Exception e) {
                        System.out.println("Error al guardar l'usuari a persistencia: " + e.getMessage());
                    }
                    break;


                case 5:     // Eliminar usuari
                    try {
                        System.out.println("Introdueix el nom de l'usuari:");
                        String nomUsuari = scanner.nextLine();
                        ctrlPersistencia.eliminarUsuari(nomUsuari);
                    } catch (IOException e) {
                        System.out.println("Error al eliminar l'usuari de persistencia: " + e.getMessage());
                    }
                    break;


                case 6:     // Modificar contrasenya
                    try {
                        System.out.println("Introdueix el nom de l'usuari:");
                        String nomUsuari = scanner.nextLine();
                        System.out.println("Introdueix la nova contrasenya de l'usuari:");
                        String contrasenya = scanner.nextLine();
                        ctrlPersistencia.modificarContrasenya(contrasenya, nomUsuari);
                    } catch (IOException e) {
                        System.out.println("Error al modificar la contrasenya: " + e.getMessage());
                    }
                    break;


                case 7:     // Carregar teclats
                    try {
                        System.out.println("Introdueix el nom de l'usuari:");
                        String nomUsuari = scanner.nextLine();
                        String[] teclats = ctrlPersistencia.carregarTeclats(nomUsuari);
                        for (String teclatI : teclats) {
                            String[] atributs = teclatI.split(",");
                            System.out.println("id teclat: " + atributs[0]);
                            System.out.println("nom teclat: " + atributs[1]);
                            System.out.println("id alfabet: " + atributs[2]);
                            System.out.println("id entrada: " + atributs[3]);
                            System.out.println("contingut: " + atributs[4]);
                            System.out.println("");
                        }
                    } catch (Exception e) {
                        System.out.println("Error al carregar els seguents teclats: " + e.getMessage());
                    }
                    break;


                case 8:     // Guardar teclat
                    try {
                        System.out.println("Introdueix el nom de l'usuari:");
                        String nomUsuari = scanner.nextLine();
                        System.out.println("Introdueix l'id del teclat:");
                        int idTeclat = Integer.parseInt(scanner.nextLine());
                        System.out.println("Introdueix el nom del teclat:");
                        String nomTeclat = scanner.nextLine();
                        System.out.println("Introdueix l'id de l'alfabet:");
                        int idAlfabet = Integer.parseInt(scanner.nextLine());
                        System.out.println("Introdueix l'id de l'entrada:");
                        int idEntrada = Integer.parseInt(scanner.nextLine());
                        System.out.println("Introdueix el contingut del teclat:");
                        String teclat = scanner.nextLine();
                        ctrlPersistencia.guardarTeclat(idTeclat, nomTeclat, idAlfabet, idEntrada, teclat, nomUsuari);
                    } catch (Exception e) {
                        System.out.println("Error al guardar el teclat a persistencia: " + e.getMessage());
                    }
                    break;


                case 9:     // Eliminar teclat
                    try {
                        System.out.println("Introdueix el nom de l'usuari:");
                        String nomUsuari = scanner.nextLine();
                        System.out.println("Introdueix l'id del teclat:");
                        int idTeclat = Integer.parseInt(scanner.nextLine());
                        ctrlPersistencia.eliminarTeclat(idTeclat, nomUsuari);
                    } catch (IOException e) {
                        System.out.println("Error al eliminar el teclat de persistencia: " + e.getMessage());
                    }
                    break;

                case 10:     // Modificar teclat
                    try {
                        System.out.println("Introdueix el nom de l'usuari:");
                        String nomUsuari = scanner.nextLine();
                        System.out.println("Introdueix l'id del teclat:");
                        int idTeclat = Integer.parseInt(scanner.nextLine());
                        System.out.println("Introdueix si es reassigna l'alfabet:");
                        boolean nouAlfabet = Boolean.parseBoolean(scanner.nextLine());
                        System.out.println("Introdueix el nou id associat al teclat:");
                        int nouId = Integer.parseInt(scanner.nextLine());
                        System.out.println("Introdueix el contingut del nou teclat:");
                        String nouTeclat = scanner.nextLine();
                        ctrlPersistencia.modificarTeclat(idTeclat, nouAlfabet, nouId, nouTeclat, nomUsuari);
                    } catch (IOException e) {
                        System.out.println("Error al modificar la contrasenya: " + e.getMessage());
                    }
                    break;

                case 11:     // Carregar alfabets
                    try {
                        System.out.println("Introdueix el nom de l'usuari:");
                        String nomUsuari = scanner.nextLine();
                        String[] alfabets = ctrlPersistencia.carregarAlfabets(nomUsuari);
                        for (String alfabetI : alfabets) {
                            String[] atributs = alfabetI.split(",");
                            System.out.println("id alfabet: " + atributs[0]);
                            System.out.println("nom alfabet: " + atributs[1]);
                            System.out.println("eliminat: " + atributs[2]);
                            System.out.print("ids teclats: ");
                            String[] idsTeclats = atributs[3].split("\\.");
                            for (String id : idsTeclats) {
                                System.out.print(id + " ");
                            }
                            System.out.println("");
                            System.out.println("contingut: " + atributs[4]);
                            System.out.println("");
                        }
                    } catch (Exception e) {
                        System.out.println("Error al carregar els seguents alfabets: " + e.getMessage());
                    }
                    break;


                case 12:     // Guardar alfabet
                    try {
                        System.out.println("Introdueix el nom de l'usuari:");
                        String nomUsuari = scanner.nextLine();
                        System.out.println("Introdueix l'id de l'alfabet:");
                        int idAlfabet = Integer.parseInt(scanner.nextLine());
                        System.out.println("Introdueix el nom de l'alfabet:");
                        String nomAlfabet = scanner.nextLine();
                        System.out.println("Introdueix el contingut de l'alfabet:");
                        String alfabet = scanner.nextLine();
                        ctrlPersistencia.guardarAlfabet(idAlfabet, nomAlfabet, alfabet, nomUsuari);
                    } catch (Exception e) {
                        System.out.println("Error al guardar l'alfabet a persistencia: " + e.getMessage());
                    }
                    break;


                case 13:     // Eliminar alfabet
                    try {
                        System.out.println("Introdueix el nom de l'usuari:");
                        String nomUsuari = scanner.nextLine();
                        System.out.println("Introdueix l'id de l'alfabet:");
                        int idAlfabet = Integer.parseInt(scanner.nextLine());
                        ctrlPersistencia.eliminarAlfabet(idAlfabet, nomUsuari);
                    } catch (IOException e) {
                        System.out.println("Error al eliminar l'alfabet de persistencia: " + e.getMessage());
                    }
                    break;


                case 14:     // Modificar alfabet
                    System.out.println("Introdueix l'id de l'alfabet:");
                    int idAlfabet = Integer.parseInt(scanner.nextLine());
                    System.out.println("Introdueix el nou contingut de l'alfabet:");
                    String alfabet = scanner.nextLine();
                    ctrlPersistencia.modificarAlfabet(idAlfabet, alfabet);
                    break;


                case 15:     // Carregar entrades
                    try {
                        System.out.println("Introdueix el nom de l'usuari:");
                        String nomUsuari = scanner.nextLine();
                        String[] entrades = ctrlPersistencia.carregarEntrades(nomUsuari);
                        for (String entradaI : entrades) {
                            String[] atributs = entradaI.split(",");
                            System.out.println("id entrada: " + atributs[0]);
                            System.out.println("nom entrada: " + atributs[1]);
                            System.out.println("eliminada: " + atributs[2]);
                            System.out.println("tipus: " + atributs[3]);
                            System.out.print("ids teclats: ");
                            String[] idsTeclats = atributs[4].split("\\.");
                            for (String id : idsTeclats) {
                                System.out.print(id + " ");
                            }
                            System.out.println("");
                            System.out.println("contingut: " + atributs[5]);
                            System.out.println("");
                        }
                    } catch (Exception e) {
                        System.out.println("Error al carregar les seguents entrades: " + e.getMessage());
                    }
                    break;


                case 16:     // Guardar entrada
                    try {
                        System.out.println("Introdueix el nom de l'usuari:");
                        String nomUsuari = scanner.nextLine();
                        System.out.println("Introdueix l'id de l'entrada:");
                        int idEntrada = Integer.parseInt(scanner.nextLine());
                        System.out.println("Introdueix el nom de l'entrada:");
                        String nomEntrada = scanner.nextLine();
                        System.out.println("Introdueix el tipus de l'entrada:");
                        String tipus = scanner.nextLine();
                        System.out.println("Introdueix el contingut de l'entrada:");
                        String entrada = scanner.nextLine();
                        ctrlPersistencia.guardarEntrada(idEntrada, nomEntrada, tipus, entrada, nomUsuari);
                    } catch (Exception e) {
                        System.out.println("Error al guardar l'alfabet a persistencia: " + e.getMessage());
                    }
                    break;


                case 17:     // Eliminar entrada
                    try {
                        System.out.println("Introdueix el nom de l'usuari:");
                        String nomUsuari = scanner.nextLine();
                        System.out.println("Introdueix l'id de l'entrada:");
                        int idEntrada = Integer.parseInt(scanner.nextLine());
                        ctrlPersistencia.eliminarEntrada(idEntrada, nomUsuari);
                    } catch (IOException e) {
                        System.out.println("Error al eliminar l'entrada de persistencia: " + e.getMessage());
                    }
                    break;


                case 18:     // Modificar entrada
                    System.out.println("Introdueix l'id de l'entrada:");
                    int idEntrada = Integer.parseInt(scanner.nextLine());
                    System.out.println("Introdueix el nou contingut de l'entrada:");
                    String entrada = scanner.nextLine();
                    ctrlPersistencia.modificarEntrada(idEntrada, entrada);
                    break;
            }
        }
    }
}