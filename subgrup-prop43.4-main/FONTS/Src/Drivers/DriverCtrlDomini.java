package Drivers;

import Domini.Controladors.CtrlDomini;
import java.util.*;

public class DriverCtrlDomini {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CtrlDomini ctrlDomini = CtrlDomini.obtenirInstancia();

        boolean usuariAutenticat = false;
        while (true) {

            System.out.println("1. Registrar-se");
            System.out.println("2. Iniciar sessió");
            System.out.println("");
            System.out.println("0. Sortir - Finalitzar execució");
            System.out.println("");

            int opcioescollida = scanner.nextInt();
            scanner.nextLine();

            switch (opcioescollida) {
                case 0: // Finalitza l'execucio
                    return;
                case 1: // Es registra - Caldra comprobar que no existeixi

                    System.out.print("Introdueix el usuari: ");
                    String nomUsuari = scanner.nextLine();

                    System.out.print("Introdueix la contrasenya: ");
                    String contrasenya = scanner.nextLine();

                    try {
                        ctrlDomini.crearUsuari(nomUsuari, contrasenya);
                        System.out.println("Usuari registrat amb èxit.");
                        usuariAutenticat = true;
                    } catch (Exception e) {
                        System.out.println("Error al crear l'usuari: " + e.getMessage());
                    }
                    break;
                case 2: // Es logeja - Caldra comprobar que hi existeixi
                    System.out.print("Introdueix el nom de l'usuari: ");
                    String nomUsuariLogin = scanner.nextLine();

                    System.out.print("Introdueix la contrasenya: ");
                    String contrasenyaLogin = scanner.nextLine();

                    try {
                        ctrlDomini.iniciarSessio(nomUsuariLogin, contrasenyaLogin);
                        System.out.println("Inici de sessió correcte.");
                        usuariAutenticat = true;
                    } catch (Exception e) {
                        System.out.println("Error al iniciar sessió: " + e.getMessage());
                    }
                    break;


                default:
                    System.out.println("Opció no vàlida. Torna a intentar-ho.");
            }

            while(usuariAutenticat){ // Menu d'opcions
                System.out.println("\nMENU");
                System.out.println("1. Modificar usuari"); // Funciona - regulin
                System.out.println("2. Consultar usuari"); // Funciona - regulin
                System.out.println("3. Tancar sessió"); // Funciona - regulin
                System.out.println("4. Eliminar usuari"); // Funciona - regulin
                System.out.println("5. Crear teclat");// Funciona
                System.out.println("6. Modificar teclat"); // Funciona
                System.out.println("7. Eliminar teclat"); // Funciona
                System.out.println("8. Crear entrada");
                System.out.println("9. Modificar entrada"); // Funciona
                System.out.println("10. Eliminar entrada"); // Funciona
                System.out.println("11. Crear alfabet"); // Funciona
                System.out.println("12. Modificar alfabet"); // Funciona
                System.out.println("13. Eliminar alfabet"); // Funciona
                System.out.println("14. Consultar alfabet"); // Funciona
                System.out.println("15. Consultar entrada");// Funciona
                System.out.println("16. Consultar teclat");// Funciona
                System.out.println("");
                System.out.println("0. Sortir - Finalitzar execució");
                System.out.println("");

                opcioescollida = scanner.nextInt();
                scanner.nextLine();

                switch (opcioescollida) {
                    case 0:
                        return;
                    case 1:    //Modificar usuari
                        System.out.print("\nIntrodueix la nova contrasenya: ");
                        String novaContrasenya = scanner.nextLine();
                        try {
                            ctrlDomini.modificarContrasenyaUsuari(novaContrasenya);
                            System.out.println("Contrasenya modificada amb èxit.");
                        } catch (Exception e) {
                            System.out.println("Error al modificar la contrasenya: " + e.getMessage());
                        }
                        break;


                    case 2:    //Consultar usuari
                        try {

                            System.out.println("\nInformació de l'usuari:");

                            System.out.println("\nSelecciona què vols consultar");
                            System.out.println("1. Teclats");
                            System.out.println("2. Alfabets");
                            System.out.println("3. Entrades");
                            int opcioConsulta = scanner.nextInt();
                            scanner.nextLine();

                            switch (opcioConsulta) {
                                case 1:
                                    // Consultar teclats
                                    ArrayList<String> teclats = ctrlDomini.consultarTeclats();
                                    ArrayList<Integer> idsTeclats = new ArrayList<>();
                                    for(String teclat : teclats) {
                                        String[] parts = teclat.split(",");
                                        idsTeclats.add(Integer.valueOf(parts[0]));
                                    }
                                    if (!idsTeclats.isEmpty()) {
                                        System.out.println("Teclats: " + idsTeclats);
                                    } else {
                                        System.out.println("No hi ha teclats al perfil.");
                                    }
                                    break;

                                case 2:
                                    // Consultar alfabets
                                    ArrayList<String> alfabets = ctrlDomini.consultarAlfabets();
                                    ArrayList<Integer> idsAlfabets = new ArrayList<>();
                                    for(String alfabet : alfabets) {
                                        String[] parts = alfabet.split(",");
                                        idsAlfabets.add(Integer.valueOf(parts[0]));
                                    }
                                    if (!idsAlfabets.isEmpty()) {
                                        System.out.println("Alfabets: " + idsAlfabets);
                                    } else {
                                        System.out.println("No hi ha alfabets al perfil.");
                                    }
                                    break;

                                case 3:
                                    // Consultar entrades
                                    ArrayList<String> entrades = ctrlDomini.consultarEntrades();
                                    ArrayList<Integer> idsEntrades = new ArrayList<>();
                                    for(String entrada : entrades) {
                                        String[] parts = entrada.split(",");
                                        idsEntrades.add(Integer.valueOf(parts[0]));
                                    }
                                    if (!idsEntrades.isEmpty()) {
                                        System.out.println("Entrades: " + idsEntrades);
                                    } else {
                                        System.out.println("No hi ha entrades al perfil.");
                                    }
                                    break;

                                default:
                                    System.out.println("Opció no vàlida. Torna a intentar-ho.");
                                    break;
                            }
                        } catch (Exception e) {
                            System.out.println("Error al consultar la informació de l'usuari: " + e.getMessage());
                        }
                        break;


                    case 3:   //Tancar sessió - posaria case 0
                        try{
                            ctrlDomini.tancarSessio();
                            System.out.println("Sessió tancada amb èxit.");
                            usuariAutenticat = false;
                        }
                        catch (Exception e){
                            System.out.println("Error al tancar sessio: " + e.getMessage());
                        }
                        break;
                    case 4:   //eliminar usuari
                        try{
                            ctrlDomini.eliminarUsuari();
                            System.out.println("Usuari eliminat amb èxit.");
                            usuariAutenticat = false;
                        }
                        catch (Exception e){
                            System.out.println("Error al eliminar usuari: " + e.getMessage());
                        }
                        break;
                    case 5: // Crear teclat
                        try {
                            System.out.print("Introdueix el nom del teclat: ");
                            String nomTeclat = scanner.nextLine();

                            System.out.print("Introdueix l'ID de l'alfabet: ");
                            int idAlfabet = scanner.nextInt();
                            scanner.nextLine();

                            System.out.print("Introdueix l'ID de l'entrada: ");
                            int idEntrada = scanner.nextInt();
                            scanner.nextLine();

                            System.out.print("Escolleix l'algorisme (0) -> QAP, (1) -> SA: ");
                            int opcio = scanner.nextInt();
                            scanner.nextLine();

                            ctrlDomini.crearTeclat(nomTeclat, idAlfabet, idEntrada, opcio);
                            System.out.println("Teclat creat amb èxit.");

                        } catch (Exception e) {
                            System.out.println("Error al crear el teclat: " + e.getMessage());
                        }
                        break;

                    case 6: // Modificar teclat
                        try {
                            // Mostrar la lista de teclats del perfil
                            ArrayList<String> teclats = ctrlDomini.consultarTeclats();
                            ArrayList<Integer> idsTeclats = new ArrayList<>();
                            for(String teclat : teclats) {
                                String[] parts = teclat.split(",");
                                idsTeclats.add(Integer.valueOf(parts[0]));
                            }
                            if (!idsTeclats.isEmpty()) {
                                System.out.println("Teclats disponibles: " + idsTeclats);
                                System.out.print("Introdueix l'ID del teclat que vols modificar: ");
                                int idTeclatModificar = scanner.nextInt();
                                scanner.nextLine();

                                // Mostrar opciones de modificación
                                System.out.println("Opcions de modificació:");
                                System.out.println("1. Reassignar alfabet");
                                System.out.println("2. Reassignar entrada");

                                int opcioModificar = scanner.nextInt();
                                scanner.nextLine();

                                int opcio;

                                switch (opcioModificar) {
                                    case 1: // MODIFICAR ALFABET
                                        ArrayList<String> alfabets = ctrlDomini.consultarAlfabets();
                                        ArrayList<Integer> idsAlfabets = new ArrayList<>();
                                        for(String alfabet : alfabets) {
                                            String[] parts = alfabet.split(",");
                                            idsAlfabets.add(Integer.valueOf(parts[0]));
                                        }

                                        if (idsAlfabets.isEmpty()) {
                                            System.out.println("No hi ha cap alfabet disponible.");
                                        } else {
                                            System.out.println("\nIDs alfabets disponibles: " + idsAlfabets);

                                        }
                                        System.out.print("Introdueix el nou ID: ");
                                        int nouId = scanner.nextInt();
                                        scanner.nextLine();

                                        System.out.print("Escolleix l'algorisme (0) -> QAP, (1) -> SA: ");
                                        opcio = scanner.nextInt();
                                        scanner.nextLine();

                                        boolean nouAlfabet = true;
                                        ctrlDomini.modificarTeclat(idTeclatModificar, nouAlfabet, nouId, opcio);
                                        System.out.println("Teclat modificat amb èxit.");
                                        break;
                                    case 2: // MODIFICAR ENTRADA
                                        ArrayList<String> entrades = ctrlDomini.consultarEntrades();
                                        ArrayList<Integer> idsEntrades = new ArrayList<>();
                                        for(String entrada : entrades) {
                                            String[] parts = entrada.split(",");
                                            idsEntrades.add(Integer.valueOf(parts[0]));
                                        }

                                        if (idsEntrades.isEmpty()) {
                                            System.out.println("No hi ha cap entrada disponible.");
                                        } else {
                                            System.out.println("\nIDs entrades disponibles: " + idsEntrades);

                                        }
                                        System.out.print("Introdueix el nou ID: ");
                                        int nouId2 = scanner.nextInt();
                                        scanner.nextLine();

                                        System.out.print("Escolleix l'algorisme (0) -> QAP, (1) -> SA: ");
                                        opcio = scanner.nextInt();
                                        scanner.nextLine();

                                        boolean nouAlfabet2 = false;
                                        ctrlDomini.modificarTeclat(idTeclatModificar, nouAlfabet2, nouId2, opcio);
                                        System.out.println("Teclat modificat amb èxit.");
                                        break;

                                    default:
                                        System.out.println("Opció no vàlida. Torna a intentar-ho.");
                                }
                            } else {
                                System.out.println("No hi ha teclats al perfil.");
                            }
                        } catch (Exception e) {
                            System.out.println("Error al modificar el teclat: " + e.getMessage());
                        }
                        break;

                    case 7:  //eliminar teclat
                        try {

                            System.out.println("Llista de teclats del perfil:");
                            ArrayList<String> teclats = ctrlDomini.consultarTeclats();
                            ArrayList<Integer> idsTeclats = new ArrayList<>();
                            for(String teclat : teclats) {
                                String[] parts = teclat.split(",");
                                idsTeclats.add(Integer.valueOf(parts[0]));
                            }
                            if (idsTeclats.isEmpty()) {
                                System.out.println("No hi ha cap teclat disponible.");
                            } else {
                                System.out.println("Entrades: " + idsTeclats);

                                System.out.print("Introdueix l'ID del teclat que vols eliminar: ");
                                int idTeclatEliminar = scanner.nextInt();
                                scanner.nextLine();

                                // Eliminar el teclat
                                ctrlDomini.eliminarTeclat(idTeclatEliminar);

                                System.out.println("Teclat eliminat amb èxit.");
                            }
                        } catch (Exception e) {
                            System.out.println("Error al eliminar el teclat: " + e.getMessage());
                        }
                        break;


                    case 8: // Crear entrada
                        try {
                            System.out.println("Selecciona el tipus d'entrada:");
                            System.out.println("1. Llista de paraules");
                            System.out.println("2. Text");
                            int tipusEntrada = scanner.nextInt();
                            scanner.nextLine();

                            String tipus;
                            switch (tipusEntrada) {
                                case 1:
                                    tipus = "Entrada";
                                    break;
                                case 2:
                                    tipus = "text";
                                    break;
                                default:
                                    throw new Exception("ERROR: Opció no vàlida per al tipus d'entrada.");
                            }

                            System.out.print("Introdueix el contingut de l'entrada: ");
                            String contingutEntrada = scanner.nextLine();

                            if (contingutEntrada.isEmpty()) {
                                throw new Exception("ERROR: L'entrada no pot estar buida.");
                            }

                            System.out.print("Introdueix el nom de l'entrada: ");
                            String nomEntrada = scanner.nextLine();

                            ctrlDomini.crearEntrada(nomEntrada, tipus, contingutEntrada);
                            System.out.println("Entrada creada amb èxit.");

                        } catch (Exception e) {
                            System.out.println("Error al crear l'entrada: " + e.getMessage());
                        }
                        break;

                    case 9: // Modificar entrada
                        try {
                            ArrayList<String> entrades = ctrlDomini.consultarEntrades();
                            ArrayList<Integer> idsEntrades = new ArrayList<>();
                            for(String entrada : entrades) {
                                String[] parts = entrada.split(",");
                                idsEntrades.add(Integer.valueOf(parts[0]));
                            }
                            if (idsEntrades.isEmpty()) {
                                System.out.println("No hi ha entrades al perfil.");
                            }
                            else {
                                System.out.println("Entrades: " + idsEntrades);

                                System.out.print("Introdueix l'ID de l'entrada que vols modificar: ");
                                int idEntradaEliminar = scanner.nextInt();
                                scanner.nextLine();
                                System.out.print("Introdueix nou contingut: ");
                                String novaEntrada = scanner.nextLine();


                                ctrlDomini.modificarEntrada(idEntradaEliminar, novaEntrada);
                                System.out.println("Entrada modificada amb èxit.");
                            }

                        } catch (Exception e) {
                            System.out.println("Error al modificar l'entrada: " + e.getMessage());
                        }
                        break;

                    case 10: // Eliminar entrada
                        try {
                            ArrayList<String> entrades = ctrlDomini.consultarEntrades();
                            ArrayList<Integer> idsEntrades = new ArrayList<>();
                            for(String entrada : entrades) {
                                String[] parts = entrada.split(",");
                                idsEntrades.add(Integer.valueOf(parts[0]));
                            }
                            if (idsEntrades.isEmpty()) {
                                System.out.println("No hi ha entrades al perfil.");
                            } else {
                                System.out.println("Entrades: " + idsEntrades);

                                System.out.print("Introdueix l'ID de l'entrada que vols eliminar: ");
                                int idEntradaEliminar = scanner.nextInt();
                                scanner.nextLine();

                                ctrlDomini.eliminarEntrada(idEntradaEliminar);
                                System.out.println("Entrada eliminada amb èxit.");
                            }
                        } catch (Exception e) {
                            System.out.println("Error al eliminar l'entrada: " + e.getMessage());
                        }
                        break;


                    case 11: // Crear alfabet
                        try {
                            System.out.print("Introdueix els caràcters que conté l'alfabet: ");
                            //Set<Character> caracteresAlfabet = new TreeSet<>();
                            String abc =scanner.nextLine();

                            if (abc.isEmpty()) {
                                System.out.println("ERROR: L'alfabet no pot ser buit.");
                            } else {

                                System.out.print("Introdueix el nom de l'alfabet: ");
                                String nomAlfabet = scanner.nextLine();

                                ctrlDomini.crearAlfabet(nomAlfabet, abc);
                                System.out.println("Alfabet creat amb èxit.");
                            }
                        } catch (Exception e) {
                            System.out.println("Error al crear l'alfabet: " + e.getMessage());
                        }
                        break;

                    case 12: // modificar alfabet
                        try {
                            System.out.println("\nAlfabets disponibles:");
                            ArrayList<String> alfabets = ctrlDomini.consultarAlfabets();
                            ArrayList<Integer> idsAlfabets = new ArrayList<>();
                            for(String alfabet : alfabets) {
                                String[] parts = alfabet.split(",");
                                idsAlfabets.add(Integer.valueOf(parts[0]));
                            }

                            if (idsAlfabets.isEmpty()) {
                                System.out.println("No hi ha cap alfabet disponible.");
                            } else {
                                System.out.println("\nIDs alfabets disponibles: " + idsAlfabets);

                                System.out.print("Introdueix l'ID de l'alfabet que vols modificar: ");
                                int idAlfabetModificar = scanner.nextInt();
                                scanner.nextLine();
                                System.out.print("Introdueix nou contingut: ");
                                String nouAlfabet = scanner.nextLine();


                                ctrlDomini.modificarAlfabet(idAlfabetModificar, nouAlfabet);

                                System.out.println("Alfabet modificat amb èxit.");
                            }

                        } catch (Exception e) {
                            System.out.println("Error al modificar l'alfabet: " + e.getMessage());
                        }
                        break;

                    case 13: // Eliminar alfabet
                        try {
                            System.out.println("\nAlfabets disponibles:");
                            ArrayList<String> alfabets = ctrlDomini.consultarAlfabets();
                            ArrayList<Integer> idsAlfabets = new ArrayList<>();
                            for(String alfabet : alfabets) {
                                String[] parts = alfabet.split(",");
                                idsAlfabets.add(Integer.valueOf(parts[0]));
                            }

                            if (idsAlfabets.isEmpty()) {
                                System.out.println("No hi ha cap alfabet disponible.");
                            } else {
                                System.out.println("\nIDs alfabets disponibles: " + idsAlfabets);

                                System.out.print("Introdueix l'ID de l'alfabet que vols eliminar: ");
                                int idAlfabetEliminar = scanner.nextInt();
                                scanner.nextLine();

                                ctrlDomini.eliminarAlfabet(idAlfabetEliminar);

                                System.out.println("Alfabet eliminat amb èxit.");
                            }

                        } catch (Exception e) {
                            System.out.println("Error al eliminar l'alfabet: " + e.getMessage());
                        }
                        break;

                    case 14: //consultar alfabet
                        try {
                            System.out.println("\nAlfabets disponibles:");
                            ArrayList<String> alfabets = ctrlDomini.consultarAlfabets();
                            ArrayList<Integer> idsAlfabets = new ArrayList<>();
                            for(String alfabet : alfabets) {
                                String[] parts = alfabet.split(",");
                                idsAlfabets.add(Integer.valueOf(parts[0]));
                            }

                            if (idsAlfabets.isEmpty()) {
                                System.out.println("No hi ha cap alfabet disponible.");
                            } else {
                                System.out.println("\nIDs alfabets disponibles: " + idsAlfabets);
                                System.out.print("\nIntrodueix l'ID de l'alfabet que vols consultar: ");


                                try {
                                    int idAlfabet = scanner.nextInt();
                                    //scanner.nextLine();
                                    String infoAlfabet = ctrlDomini.consultarAlfabet(idAlfabet)[0];
                                    System.out.println("\nInformació de l'alfabet:");
                                    System.out.println(infoAlfabet);
                                } catch (Exception e) {
                                    if(e.getMessage() == null) System.out.println("Cal introduir un numero com a index");
                                    else System.out.println("Error al consultar l'alfabet: " + e.getMessage());
                                } finally {
                                    scanner.nextLine();
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Error al consultar l'alfabet: " + e.getMessage());
                        }
                        break;


                    case 15: //consultar entrada
                        try {
                            System.out.print("\nEntrades disponibles: ");

                            ArrayList<String> entrades = ctrlDomini.consultarEntrades();
                            ArrayList<Integer> idsEntrades = new ArrayList<>();
                            for(String entrada : entrades) {
                                String[] parts = entrada.split(",");
                                idsEntrades.add(Integer.valueOf(parts[0]));
                            }

                            if (idsEntrades.isEmpty()) {
                                System.out.println("No hi ha cap entrada disponible.");
                            } else {

                                System.out.println("\nIDs d'entrades disponibles: "  + idsEntrades);

                                System.out.print("\nIntrodueix l'ID de l'entrada que vols consultar: ");


                                try {
                                    int idEntrada = scanner.nextInt();
                                    //scanner.nextLine();
                                    String nomEntrada = ctrlDomini.consultarEntrada(idEntrada)[1];
                                    String infoEntrada = ctrlDomini.consultarEntrada(idEntrada)[0];
                                    System.out.println("\nInformació de l'entrada:");
                                    System.out.println("nom entrada:");
                                    System.out.println(nomEntrada);
                                    System.out.println("contingut entrada:");
                                    System.out.println(infoEntrada);
                                } catch (Exception e) {
                                    if(e.getMessage() == null) System.out.println("Cal introduir un numero com a index");
                                    else System.out.println("Error al consultar l'entrada: " + e.getMessage());
                                } finally {
                                    scanner.nextLine();
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Error al consultar l'entrada: " + e.getMessage());
                        }
                        break;


                    case 16: //consultar teclat
                        try {
                            System.out.print("\nTeclats disponibles: ");

                            ArrayList<String> teclats = ctrlDomini.consultarTeclats();
                            ArrayList<Integer> idsTeclats = new ArrayList<>();
                            for(String teclat : teclats) {
                                String[] parts = teclat.split(",");
                                idsTeclats.add(Integer.valueOf(parts[0]));
                            }

                            if (idsTeclats.isEmpty()) {
                                System.out.println("No hi ha cap teclat disponible.");
                            } else {
                                System.out.println("\nIDs teclats disponibles: " + idsTeclats);

                                System.out.print("\nIntrodueix l'ID del teclat que vols consultar: ");

                                try {
                                    int idTeclat = scanner.nextInt();
                                    //scanner.nextLine();
                                    String[] infoEntrada = ctrlDomini.consultarTeclat(idTeclat);

                                    String[] filas = infoEntrada[0].split(",");
                                    Character[][] matriz = new Character[filas.length][];

                                    for (int i = 0; i < filas.length; i++) {
                                        matriz[i] = new Character[filas[i].length()];
                                        for (int j = 0; j < filas[i].length(); j++) {
                                            matriz[i][j] = filas[i].charAt(j);
                                        }
                                    }


                                    System.out.println("\nInformació teclat:");
                                    System.out.println("");
                                    System.out.println("Contingut Teclat:");

                                    for(int i = 0; i < matriz.length; ++i){
                                        String s = "";
                                        for(int j = 0; j < matriz[0].length; ++j){
                                            s += matriz[i][j];
                                        }
                                        System.out.println(s);
                                    }

                                    System.out.println("Nom Teclat:");
                                    System.out.println("");
                                    System.out.println(infoEntrada[1]);
                                    System.out.println("");
                                    System.out.println("Contingut Alfabet:");
                                    System.out.println("");
                                    System.out.println(infoEntrada[2]);
                                    System.out.println("");
                                    System.out.println("Contingut Entrada:");
                                    System.out.println("");
                                    System.out.println(infoEntrada[3]);
                                } catch (Exception e) {
                                    if(e.getMessage() == null) System.out.println("Cal introduir un numero com a index");
                                    else System.out.println("Error al consultar teclat: " + e.getMessage());
                                } finally {
                                    scanner.nextLine();
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Error al consultar el teclat: " + e.getMessage());
                        }
                        break;


                    default:
                        System.out.println("Opció no vàlida. Torna a intentar-ho.");
                }
            }
        }
    }
}