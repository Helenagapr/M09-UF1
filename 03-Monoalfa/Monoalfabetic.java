import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Monoalfabetic {
    public final static char[] ABC= "AÀÁÄBCÇDEÉÈËFGHIÍÌÏJKLMNÑOÓÒÖPQRSTUÚÙÜVWXYZ".toCharArray();

    public final static List LISTA = Arrays.asList("La gata", "Monotomia", "Helena García", "yogürt");
    public static ArrayList abcOriginal = new ArrayList<Character>();
    public static ArrayList abcPermutat = new ArrayList<Character>();

    public static void main(String[] args) {
        inicialitzaListas();
        permutaAlfabet(abcPermutat);
    }

    public static void inicialitzaListas() {
        for(char c: ABC){
            abcOriginal.add(c);
            abcPermutat.add(c);
        }
    }

    public static void permutaAlfabet(ArrayList alfabet){
        Collections.shuffle(alfabet);
        System.out.println(alfabet);
    }
}
