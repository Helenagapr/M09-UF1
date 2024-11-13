public class RotX {
    public final static char[] ABCLOWER= "aàáäbcçdeéèëfghiíìïjklmnñoóòöpqrstuúùüvwxyz".toCharArray();
    public final static char[] ABCUPPER= "AÀÁÄBCÇDEÉÈËFGHIÍÌÏJKLMNÑOÓÒÖPQRSTUÚÙÜVWXYZ".toCharArray();
    public final static String[] LISTA = {"PapEl", "Hola que hace", "mamAmíÀ", "Me gustÀn las Maanzçanas"};

    public static void main(String[] args) {
        int num = 0;
       for (int i = 0; i < LISTA.length; i++){
            num += 5;
            String xifrad = xifraRotX(LISTA[i], num);
            String desxifrad = desxifraRotX(xifrad, num);
            System.out.println("\n--------------------------------------------\n" +
                "Cadena a xifrar: " + LISTA[i] + "\n" +
                "Rotant " + num + " posicions" + "\n" +
                "Xifrad: " + xifrad + "\n" +
                "Desxifrad: " + desxifrad + 
                "\n--------------------------------------------\n" +
                "Probem a força bruta totes le rotacions: \n");
                forcaBrutaRotX(xifrad);
       }

    }

    public static String xifraRotX(String str, int num) {
        return rotX(str, true, num);
    }

    public static String desxifraRotX(String str, int num){
        return rotX(str, false, num);
    }

    public static String rotX(String str, boolean pos, int num){
        StringBuffer cadenaXifrada = new StringBuffer();
        for (int i = 0; i < str.length(); i++){
            char letra = str.charAt(i);
            int index = new String(ABCUPPER).indexOf(letra);
            if (index != -1) { // es mayuscula
                if(pos){
                    index += num;
                    if (index > ABCUPPER.length-1){
                        index -= ABCUPPER.length;
                    }
                } else {
                    index -= num;
                    if(index < 0){
                        index += ABCUPPER.length;
                    }
                }
                cadenaXifrada.append(ABCUPPER[index]);

            } else {
                index = new String(ABCLOWER).indexOf(letra);
                if ( index != -1) { // es minuscula
                    if(pos){
                        index += num;
                        if (index > ABCLOWER.length-1){
                            index -= ABCLOWER.length;
                        }
                    } else {
                        index -= num;
                        if(index < 0){
                            index += ABCLOWER.length;
                        }
                    }
                    cadenaXifrada.append(ABCLOWER[index]);
                } else { // es otro carapter
                    cadenaXifrada.append(letra);
                }
            }
        }
        return cadenaXifrada.toString();
    }

    public static void forcaBrutaRotX(String cadenaXifrada){
        int longitud = ABCLOWER.length;
        for (int i = 0; i < longitud; i++){
            System.out.println("["+i+"] Desxifrant: " + desxifraRotX(cadenaXifrada, i));
        }
    } 
}
