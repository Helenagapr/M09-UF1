public class Rot13 {

    public final static char[] minAbc = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    public final static char[] mayAbc = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    public static void main(String[] args) {

        System.out.println( xifraRot13("hEllo"));
        System.out.println(desxifraRot13("uRyyb"));
    }

    public static String xifraRot13 (String str) {

        String clau = "";

        for (int i= 0; i <= str.length()-1; i++) {
            int index = 0;
            char letra = str.charAt(i);
            if (Character.isUpperCase(letra)) {
                for (int j= 0; j < mayAbc.length; j++) {
                    if(mayAbc[j] == letra) {
                        index = j;
                        break;
                    }
                }
                index += 13;
                if (index > 25) {
                    index -= 26;
                }
                clau += mayAbc[index];
            }
            if (Character.isLowerCase(letra)) {
                for (int k= 0; k < minAbc.length; k++){
                    if(minAbc[k] == letra) {
                        index = k;
                        break;
                    }
                }
                index += 13;
                if (index > 25) {
                    index -= 26;
                }
                clau = clau + minAbc[index];
            }
        }
        return clau;
    }
        

    public static String desxifraRot13 (String str) {
        String clau = "";

        for (int i= 0; i <= str.length()-1; i++) {
            int index = 0;
            char letra = str.charAt(i);
            if (Character.isUpperCase(letra)) {
                for (int j= 0; j < mayAbc.length; j++) {
                    if(mayAbc[j] == letra) {
                        index = j;
                        break;
                    }
                }
                index -= 13;
                if (index < 0) {
                    index += 26;
                }
                clau = clau + mayAbc[index];
            }
            if (Character.isLowerCase(letra)) {
                for (int k= 0; k < minAbc.length; k++){
                    if(minAbc[k] == letra) {
                        index = k;
                        break;
                    }
                }
                index -= 13;
                if (index < 0) {
                    index += 26;
                }
                clau = clau + minAbc[index];
            }
        }
        return clau;
    }
}