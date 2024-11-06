package iticbcn.xifratge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class XifradorMonoalfabetic implements Xifrador{

    public final static char[] ABC= "AÀÁÄBCÇDEÉÈËFGHIÍÌÏJKLMNÑOÓÒÖPQRSTUÚÙÜVWXYZ".toCharArray();
    public static ArrayList abcOriginal = new ArrayList<Character>();
    public static ArrayList abcPermutat = new ArrayList<Character>();

    static {
        inicialitzaListas();
        permutaAlfabet(); 
    }

    public static void inicialitzaListas() {
        abcOriginal.clear();
        abcPermutat.clear();
        for(char c: ABC){
            abcOriginal.add(c);
            abcPermutat.add(c);
        }
    }

    public static void permutaAlfabet(){
        Collections.shuffle(abcPermutat);
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
        if (clau != null) {
            throw new ClauNoSuportada("Xifratge monoalfabètic no suporta clau != null");
        }
        String resultado = monoAlfabet(msg, true);
        return new TextXifrat(resultado.getBytes());
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        if (clau != null) {
            throw new ClauNoSuportada("Xifratge monoalfabètic no suporta clau != null");
        }
        String msgCifrado = new String(xifrat.getBytes());
        return monoAlfabet(msgCifrado, false);
    }
}
