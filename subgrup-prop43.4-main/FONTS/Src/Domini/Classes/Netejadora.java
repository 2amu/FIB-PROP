package Domini.Classes;

import java.util.*;
import java.text.Normalizer;

public class Netejadora {
    public Netejadora() {

    }

    //NETEJADORA DE LP
    public HashMap<String, Integer> netejarEntrada (Set<Character> AbcNet, HashMap<String, Integer> LP) throws Exception {
        HashMap<String, Integer> LpNet = new HashMap<>();

        for (String key : LP.keySet()) {
            Character[] NouString = new Character[key.length()];
            int contador = 0;

            for (Character c : key.toCharArray()) {

                if(Character.isLetter(c)) {
                    c = netejarLletres(c);

                    if (EsTrobaAbc(AbcNet, c)) {
                        NouString[contador] = c;
                        contador++;
                    }
                }
            }

            List<Character> llista = new ArrayList<>(Arrays.asList(NouString));
            llista.removeAll(Arrays.asList(new Character[] {null}));

            NouString = llista.toArray(new Character[0]);
            if (NouString.length > 0) {
                String res = new String(toCharArray(NouString));
                String NetString = res.trim();
                LpNet.put(NetString, LP.get(key));
            }
        }
        if (LpNet.isEmpty())
            throw new Exception("ERROR: L'entrada i l'alfabet no coincideixen en cap lletra");
        return LpNet;
    }

    //comprova si la lletra es troba
    private boolean EsTrobaAbc(Set<Character> Abc, Character c) {
        return Abc.contains(c);
    }

    //Converteix majuscules i accents en minuscules sense accents ni diacritics
    private Character netejarLletres(Character c) {
        String s;
        s = Normalizer.normalize(Character.toString(c), Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        c = s.charAt(0);
        return Character.toLowerCase(c);
    }

    private static char[] toCharArray(Character[] characterArray) {
        char[] result = new char[characterArray.length];

        for (int i = 0; i < characterArray.length; i++) {
            result[i] = characterArray[i].charValue();
        }

        return result;
    }
}

