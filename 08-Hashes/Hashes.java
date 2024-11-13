import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HexFormat;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hashes {
    int npass = 0;

    public static void main(String[] args) throws Exception {
        String salt = "qpoweiruañslkdfjz";
        String pw = "aaabF!";
        Hashes h = new Hashes();

        String[] aHashes = { h.getSHA512AmbSalt(pw, salt), h.getPBKDF2AmbSalt(pw, salt) };
        String pwTrobat = null;
        String[] algorismes = {"SHA-512", "PBKDF2"};

        for(int i=0; i< aHashes.length; i++){
            System.out.printf("===========================\n");
            System.out.printf("Algorisme: %s\n", algorismes[i]);
            System.out.printf("Hash: %s\n",aHashes[i]);
            System.out.printf("---------------------------\n");
            System.out.printf("-- Inici de força bruta ---\n");
            
            long t1 = System.currentTimeMillis();
            pwTrobat = h.forcaBruta(algorismes[i], aHashes[i], salt);
            long t2 = System.currentTimeMillis();
            
            System.out.printf("Pass : %s\n", pwTrobat);
            System.out.printf("Provats: %d\n", h.npass);
            System.out.printf("Temps : %s\n", h.getInterval(t1, t2));
            System.out.printf("---------------------------\n\n");
        }
    }

    public String getSHA512AmbSalt(String pw, String salt){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.update(salt.getBytes());
            byte[] hash = digest.digest(pw.getBytes());
            HexFormat hex = HexFormat.of();
            return hex.formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getPBKDF2AmbSalt(String pw, String salt){
        try {
            // Paràmetres per al PBKDF2
            int iterations = 10000;  // Nombre d'iteracions
            int keyLength = 512;  // Longitud de la clau en bits
            char[] password = pw.toCharArray();  // Convertim la contrasenya a un array de chars
            byte[] saltBytes = salt.getBytes();  // Convertim el salt a bytes

            // Creem el PBEKeySpec amb la contrasenya, el salt, les iteracions i la longitud de la clau
            PBEKeySpec spec = new PBEKeySpec(password, saltBytes, iterations, keyLength);
            
            // Obtenim el SecretKeyFactory per a PBKDF2 amb HMACSHA512
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            
            // Generem el hash
            byte[] hash = factory.generateSecret(spec).getEncoded();
            
            // Convertim el resultat a hexadecimal
            HexFormat hex = HexFormat.of();
            return hex.formatHex(hash);
        } catch (Exception  e) {
            e.printStackTrace();
            return null;
        }
    }

    public String forcaBruta(String alg, String hash, String salt){
        // Definición del conjunto de caracteres a usar
        char[] charset = "abcdefABCDEF1234567890!".toCharArray();
        char[] guess = new char[6];  // Array para la contraseña generada
        npass = 0;  // Reiniciar el contador de contraseñas probadas

        // Bucle para probar todas las combinaciones de contraseñas de 6 caracteres
        for (int i1 = 0; i1 < charset.length; i1++) {
            guess[0] = charset[i1];
            for (int i2 = 0; i2 < charset.length; i2++) {
                guess[1] = charset[i2];
                for (int i3 = 0; i3 < charset.length; i3++) {
                    guess[2] = charset[i3];
                    for (int i4 = 0; i4 < charset.length; i4++) {
                        guess[3] = charset[i4];
                        for (int i5 = 0; i5 < charset.length; i5++) {
                            guess[4] = charset[i5];
                            for (int i6 = 0; i6 < charset.length; i6++) {
                                guess[5] = charset[i6];
                                
                                // Convertir el array 'guess' a una cadena de texto
                                String guessStr = new String(guess);
                                
                                // Generar el hash de la contraseña tentativa
                                String hashedGuess;
                                if (alg.equals("SHA-512")) {
                                    hashedGuess = getSHA512AmbSalt(guessStr, salt);
                                } else {
                                    hashedGuess = getPBKDF2AmbSalt(guessStr, salt);
                                }

                                npass++;  // Incrementar el contador de contraseñas probadas

                                // Comprobar si el hash generado coincide con el proporcionado
                                if (hashedGuess.equals(hash)) {
                                    return guessStr;  // Retornar la contraseña correcta
                                }
                            }
                        }
                    }
                }
            }
        }   
        return null;
    }
    
    public String getInterval(long t1, long t2){
        long interval = t2 - t1;  // Calculamos el intervalo en milisegundos
    
        // Convertir el intervalo a días, horas, minutos, segundos y milisegundos
        long days = interval / (1000 * 60 * 60 * 24);
        interval %= (1000 * 60 * 60 * 24);  // Resto de milisegundos después de los días
        
        long hours = interval / (1000 * 60 * 60);
        interval %= (1000 * 60 * 60);  // Resto de milisegundos después de las horas
        
        long minutes = interval / (1000 * 60);
        interval %= (1000 * 60);  // Resto de milisegundos después de los minutos
        
        long seconds = interval / 1000;
        long millis = interval % 1000;  // Los milisegundos restantes
        
        // Formatear el intervalo en el formato deseado
        return String.format("%d dies / %d hores / %d minuts / %d segons / %d millis", days, hours, minutes, seconds, millis);
    }
}
