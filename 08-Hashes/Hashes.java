import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hashes {
    int npass = 0;

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
        String charset = "abcdefABCDEF1234567890!";  // Conjunt de caràcters
        char[] pw = new char[6];  // Array per formar contrasenyes de fins a 6 caràcters
        String foundPassword = null;
        
        // Bucle per cada longitud de contrasenya (de 1 a 6)
        for (int len = 1; len <= 6; len++) {
            for (int i = 0; i < Math.pow(charset.length(), len); i++) {
                // Convertir el nombre a la contrasenya corresponent
                for (int j = 0; j < len; j++) {
                    pw[j] = charset.charAt((i / (int) Math.pow(charset.length(), j)) % charset.length());
                }
                
                String generatedPassword = new String(pw, 0, len);
                String generatedHash = (alg.equals("SHA-512")) ? getSHA512AmbSalt(generatedPassword, salt) : getPBKDF2AmbSalt(generatedPassword, salt);
                
                npass++;  // Incrementem el comptador de contrasenyes provades
                
                if (generatedHash.equals(hash)) {
                    foundPassword = generatedPassword;
                    return foundPassword;  // Retornem la contrasenya trobada
                }
            }
        }
        return foundPassword;  // Si no es troba, retornem null
    }
    public String getInterval(long t1, long t2){
        long interval = t2 - t1;
        return String.format("%d ms", interval);
    }

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
}
