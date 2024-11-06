package iticbcn.xifratge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class XifradorPolialfabetic implements Xifrador{

    public final static int clauSecreta = 347328423;
    public final static char[] ABC= "AÀÁÄBCÇDEÉÈËFGHIÍÌÏJKLMNÑOÓÒÖPQRSTUÚÙÜVWXYZ".toCharArray();
    public static ArrayList abcOriginal = new ArrayList<Character>();
    public static ArrayList abcPermutat = new ArrayList<Character>();
    public static Random random;

    public static void inicialitzaListas() {
        abcOriginal.clear();
        abcPermutat.clear();
        for(char c: ABC){
            abcOriginal.add(c);
            abcPermutat.add(c);
        }
    }

    public static void initRandom(long clauSecreta) {
        random = new Random(clauSecreta);
    }

    public static void permutaAlfabet(){
        Collections.shuffle(abcPermutat, random);
    }

    public static String xifraPoliAlfa(String str) {
        inicialitzaListas();
        return poliAlfa(str, true);
        
    }

    public static String desxifraPoliAlfa(String str) {
        inicialitzaListas();
        return poliAlfa(str, false); 
    }

    public static String poliAlfa(String str, boolean pos){
        StringBuffer cadenaXifrada = new StringBuffer();
        for (int i= 0; i< str.length(); i++){
            char letra = str.charAt(i);
            permutaAlfabet();
            int index;
            if (pos){
                index = abcOriginal.indexOf(Character.toUpperCase(letra));
            }else {
                index = abcPermutat.indexOf(Character.toUpperCase(letra));
            }
            if(index != -1){
                if (Character.isUpperCase(letra)){
                    if (pos) {
                        cadenaXifrada.append(abcPermutat.get(index));
                    }else {
                        cadenaXifrada.append(abcOriginal.get(index));
                    }
                } else {
                    if(pos){
                        cadenaXifrada.append(Character.toLowerCase(((Character) abcPermutat.get(index)).charValue()));
                    }else {
                        cadenaXifrada.append(Character.toLowerCase(((Character) abcOriginal.get(index)).charValue()));
                    }
                }
            }else {
                cadenaXifrada.append(letra);
            }
        }

        return cadenaXifrada.toString();
    }

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        try {
            long clauLong = Long.parseLong(clau); 
            initRandom(clauLong); 
            inicialitzaListas(); 
            String resultado = poliAlfa(msg, true); 
            return new TextXifrat(resultado.getBytes()); 
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("La clau per xifrat Polialfabètic ha de ser un String convertible a long.");
        }
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        try {
            long clauLong = Long.parseLong(clau); 
            initRandom(clauLong); 
            inicialitzaListas(); 
            String msgCifrado = new String(xifrat.getBytes()); 
            return poliAlfa(msgCifrado, false); 
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("La clau per desxifrat Polialfabètic ha de ser un String convertible a long.");
        }
    }

}
