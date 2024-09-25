

public class RotX {
    public final static char[] ABCLOWER= "aàáäbcçdeéèëfghiíìïjklmnñoóòöpqrstuúùüvwxyz".toCharArray();
    public final static char[] ABCUPPER= "AÀÁÄBCÇDEÉÈËFGHIÍÌÏJKLMNÑOÓÒÖPQRSTUÚÙÜVWXYZ".toCharArray();

    public static void main(String[] args) {
        
    }

    public static String xifraRotX(String str, int num) {
        return rotX(str, true, num);
    }

    public static String desxifraRotX(String str, int num){
        return rotX(str, true, num);
    }

    public static String rotX(String str, boolean pos, int num){
        StringBuffer cadenaXifrada = new StringBuffer();
        for (int i = 0; i < str.length(); i++){
            char letra = str.charAt(i);
            int index = new String(ABCLOWER).indexOf(letra);
            

        }

        return cadenaXifrada.toString();
    }

}
