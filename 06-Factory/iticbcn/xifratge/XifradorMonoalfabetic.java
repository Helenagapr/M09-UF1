package iticbcn.xifratge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class XifradorMonoalfabetic implements Xifrador{
    public final static char[] ABC= "AÀÁÄBCÇDEÉÈËFGHIÍÌÏJKLMNÑOÓÒÖPQRSTUÚÙÜVWXYZ".toCharArray();

    public final static List LISTA = Arrays.asList("La gata", "Monotomia", "Helena García", "yogürt");
    public static ArrayList abcOriginal = new ArrayList<Character>();
    public static ArrayList abcPermutat = new ArrayList<Character>();

    public static void main(String[] args) {
        inicialitzaListas();
        System.out.println("Permutem el abecedari");
        permutaAlfabet(abcPermutat);
        
        for(int i = 0; i< LISTA.size(); i++){
            System.out.println("\n-------------------------------");
            System.out.println("Cadena: " + LISTA.get(i));
            String xifra = xifraMonoAlfa(LISTA.get(i).toString());
            String desxifra = desxifraMonoAlfa(xifra);
            System.out.println("Xifrad: " +  xifra + "\n" + 
            "Desxifrad: " + desxifra);
            System.out.println("-------------------------------\n");
            }
    }

    public static void inicialitzaListas() {
        for(char c: ABC){
            abcOriginal.add(c);
            abcPermutat.add(c);
        }
    }

    public static void permutaAlfabet(ArrayList alfabet){
        Collections.shuffle(alfabet);
    }

    public static String xifraMonoAlfa(String str){
        return monoAlfabet(str, true);
    }

    public static String desxifraMonoAlfa(String str){
        return monoAlfabet(str, false);
    }

    public static String monoAlfabet(String str, boolean pos){
        
        StringBuffer cadenaXifrada = new StringBuffer();
        for (int i= 0; i< str.length(); i++){
            char letra = str.charAt(i);
            int index;
            if(pos){
                index = abcOriginal.indexOf(Character.toUpperCase(letra));
            } else {
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'xifra'");
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'desxifra'");
    }
}
