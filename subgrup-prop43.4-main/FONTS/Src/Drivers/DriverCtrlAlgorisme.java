package Drivers;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import Domini.Controladors.CtrlAlgorisme;

public class DriverCtrlAlgorisme {
    CtrlAlgorisme Alg;

    public void testCreadora(){
        Alg = CtrlAlgorisme.obtenirInstancia();
    }

    public void testCrearTeclat() throws Exception{

        Set<Character> alfabet = new TreeSet<>();
        HashMap<String, Integer> entrada = new HashMap<>();
        System.out.println(" Cal generar el alfabet i la entrada per poder fer us de crear teclat");
        System.out.println();
        System.out.println(" A introdueixi l'alfabet net");
        System.out.println("(ha de ser una cadena de caracters no repetits, en minuscula , sense espais, sense accents ni simbols)");
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        String abc = scanner.nextLine();

        for (char caracter : abc.toCharArray()) {
            alfabet.add(caracter);
        }

        System.out.println(" introdueixi la entrada neta ( ha de ser en minuscules, sense simbols i les lletres han de pertanyer al alfabet) que te la forma:");
        System.out.println(" paraula1:freq1,paraula2:freq2,paraula3:freq3");
        System.out.println("  ");
        System.out.println(" Introdueix la entrada neta");
        System.out.println();

        String ent = scanner.nextLine();

        String[] grupsPF = ent.split(",");
        for (String grupPF : grupsPF) {
            String[] parts = grupPF.split(":");
            if (parts.length == 2) {
                String paraula = parts[0];
                int valorFrequencia = Integer.parseInt(parts[1]);
                if(valorFrequencia < 0) throw new Exception("ERROR: Hi ha un valor negatiu");
                entrada.put(paraula, valorFrequencia);
            }
        }

        System.out.print("Escull l'algorisme (0) -> QAP, (1) -> SA: ");
        int opcio = scanner.nextInt();
        scanner.nextLine();

        String res = Alg.crearTeclat(alfabet, entrada, opcio);

        String[] filas = res.split(",");
        Character[][] resultado = new Character[filas.length][];

        for (int i = 0; i < filas.length; i++) {
            resultado[i] = new Character[filas[i].length()];
            for (int j = 0; j < filas[i].length(); j++) {
                resultado[i][j] = filas[i].charAt(j);
            }
        }

        for(int i = 0; i < resultado.length; ++i){
            String s = "";
            for(int j = 0; j < resultado[0].length; ++j){
                if(resultado[i][j] == null) s+= " ";
                else s += resultado[i][j];
            }
            System.out.println(s);
        }
    }

    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);
        DriverCtrlAlgorisme da = new DriverCtrlAlgorisme();
        da.testCreadora();

        while (true) {
            System.out.println("1. Crear teclat");

            int opcioescollida = scanner.nextInt();
            scanner.nextLine();

            switch (opcioescollida) {
                case 1:
                    da.testCrearTeclat();
                    break;
                default:
                    System.out.println("Opció no vàlida. Torna a intentar-ho.");
                    break;
            }
        }
    }
}