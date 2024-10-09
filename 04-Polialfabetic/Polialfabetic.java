import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Polialfabetic {

    public final static int clauSecreta = 347328423;
    public final static char[] ABC= "AÀÁÄBCÇDEÉÈËFGHIÍÌÏJKLMNÑOÓÒÖPQRSTUÚÙÜVWXYZ".toCharArray();
    public static ArrayList abcOriginal = new ArrayList<Character>();
    public static ArrayList abcPermutat = new ArrayList<Character>();
    public static Random random;
    public static void main(String[] args) {
        String msgs[] = {"Test 01 àrbritre, coixí, Perímetre", "Test 02 Taüll, DÍA, año", "Test 03 Peça, Òrrius, Bòvila"};
        String msgsXifrats[] = new String[msgs.length];

        System.out.println("Xifratge:\n--------");
        for (int i = 0; i < msgs.length; i++) {
            initRandom(clauSecreta);
            msgsXifrats[i] = xifraPoliAlfa(msgs[i]);
            System.out.printf("%-34s -> %s%n", msgs[i], msgsXifrats[i]);
        }

        System.out.println("Desxifratge:\n-----------");
        for (int i = 0; i < msgs.length; i++) {
            initRandom(clauSecreta);
            String msg = desxifraPoliAlfa(msgsXifrats[i]);
            System.out.printf("%-34s -> %s%n", msgsXifrats[i], msg);
        }
    }

    public static void inicialitzaListas() {
        for(char c: ABC){
            abcOriginal.add(c);
            abcPermutat.add(c);
        }
    }

    public static void initRandom(int clauSecreta) {
        random = new Random(clauSecreta);
    }

    public static void permutaAlfabet(){
        Collections.shuffle(abcPermutat, random);
    }

    public static String xifraPoliAlfa(String str) {
        return poliAlfa(str, true);
    }

    public static String desxifraPoliAlfa(String str) {
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

}
