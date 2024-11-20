import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import java.util.HexFormat;
import java.util.List;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hashes {
    int npass = 0;
    public List<String> combinations;
    public char[] forcaBruta = {'a','b','c','d','e','f','A','B','C','D','E','F','1','2','3','4','5','6','7','8','9','0','!'};

    public Hashes(){
        generateCombinations();
    }

    private void generateCombinations() {
        combinations = new ArrayList<>();
        for (char c1 : forcaBruta) {
            combinations.add(String.valueOf(c1));
            for (char c2 : forcaBruta) {
                combinations.add("" + c1 + c2);
                for (char c3 : forcaBruta) {
                    combinations.add("" + c1 + c2 + c3);
                    for (char c4 : forcaBruta) {
                        combinations.add("" + c1 + c2 + c3 + c4);
                        for (char c5 : forcaBruta) {
                            combinations.add("" + c1 + c2 + c3 + c4 + c5);
                            for (char c6 : forcaBruta) {
                                combinations.add("" + c1 + c2 + c3 + c4 + c5 + c6);
                            }
                        }
                    }
                }
            }
        }
        System.out.println(combinations.size());
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
            int keyLength = 256;  // Longitud de la clau en bits
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

        npass = 0;
        
        for (String pwd : combinations) {
            String candidateHash = null;
            if (alg.equals("SHA-512")) {
                candidateHash = getSHA512AmbSalt(pwd, salt);
            } else if (alg.equals("PBKDF2")) {
                candidateHash = getPBKDF2AmbSalt(pwd, salt);
            }

            if (candidateHash != null && candidateHash.equals(hash)) {
                return pwd;
            }else{
                npass ++;
            }
        }
        return null;  // Si no es troba, retornem null

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
