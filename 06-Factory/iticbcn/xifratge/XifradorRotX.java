package iticbcn.xifratge;

public class XifradorRotX implements Xifrador{
    public final static char[] ABCLOWER= "aàáäbcçdeéèëfghiíìïjklmnñoóòöpqrstuúùüvwxyz".toCharArray();
    public final static char[] ABCUPPER= "AÀÁÄBCÇDEÉÈËFGHIÍÌÏJKLMNÑOÓÒÖPQRSTUÚÙÜVWXYZ".toCharArray();


    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        try {
            int num = Integer.parseInt(clau); // Convertimos clau a int

            // Validación de la clave
            if (num < 0 || num > 40) {
                throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
            }

            String resultado = rotX(msg, true, num); // Ciframos el mensaje
            return new TextXifrat(resultado.getBytes()); // Convertimos a TextXifrat

        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        try {
            int num = Integer.parseInt(clau); // Convertimos clau a int

            // Validación de la clave
            if (num < 0 || num > 40) {
                throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
            }

            String msgCifrado = new String(xifrat.getBytes()); // Extraemos el mensaje cifrado
            return rotX(msgCifrado, false, num); // Desciframos el mensaje

        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }
    } 


    public static String rotX(String str, boolean pos, int num){
        StringBuffer cadenaXifrada = new StringBuffer();
        for (int i = 0; i < str.length(); i++){
            char letra = str.charAt(i);
            int index = new String(ABCUPPER).indexOf(letra);
            if (index != -1) { // es mayuscula
                if (pos) {
                    index = (index + num) % ABCUPPER.length;  // Calculamos el nuevo índice
                    if (index < 0) {
                        index += ABCUPPER.length;  // Aseguramos que sea positivo
                    }
                } else {
                    index = (index - num) % ABCUPPER.length;  // Calculamos el nuevo índice
                    if (index < 0) {
                        index += ABCUPPER.length;  // Aseguramos que sea positivo
                    }
                }
                cadenaXifrada.append(ABCUPPER[index]);

            } else {
                index = new String(ABCLOWER).indexOf(letra);
                if ( index != -1) { // es minuscula
                    if (pos) {
                        index = (index + num) % ABCLOWER.length;  // Calculamos el nuevo índice
                        if (index < 0) {
                            index += ABCLOWER.length;  // Aseguramos que sea positivo
                        }
                    } else {
                        index = (index - num) % ABCLOWER.length;  // Calculamos el nuevo índice
                        if (index < 0) {
                            index += ABCLOWER.length;  // Aseguramos que sea positivo
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
    
}
