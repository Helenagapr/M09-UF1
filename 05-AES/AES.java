import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.Base64;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class AES {
    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    private static final int MIDA_IV = 16;
    private static byte[] iv = new byte[MIDA_IV];
    private static final String CLAU = "LaClauSecretaQueVulguis";

    public static void main(String[] args) {
        String msgs[] = {"Lorem ipsum dicet", "Hola Andrés cómo está tu cuñado", "Àgora ïlla Ôtto"};
        for (int i = 0; i < msgs.length; i++) {
            String msg = msgs[i];
            byte[] bXifrats = null;
            String desxifrat = "";
            try {
                bXifrats = xifraAES(msg, CLAU);
                desxifrat = desxifraAES (bXifrats, CLAU);
            } catch (Exception e) {
                System.err.println("Error de xifrat: " + e.getLocalizedMessage ());
            }

            System.out.println("--------------------" );
            System.out.println("Msg: " + msg);
            System.out.println("Enc: " + new String(bXifrats));
            System.out.println("DEC: " + desxifrat);
        }
    }

    public static byte[] xifraAES(String msg, String clau) throws Exception {
        //Obtenir els bytes de l’String
        byte[] clauBytes = CLAU.getBytes("UTF-8");
        byte[] msgBytes = msg.getBytes("UTF-8");

        // Genera IvParameterSpec
        IvParameterSpec ivSpec = createIv();
        byte[] iv = ivSpec.getIV();

        // Genera hash
        SecretKeySpec secretKey = generateHash(clauBytes);

        // Encrypt.
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        byte[] encryptedMessage = cipher.doFinal(msgBytes);

        // Combinar IV i part xifrada.
        byte[] combined = combineIvAndEncryptedMessage(iv, encryptedMessage);

        // return iv+msgxifrat
        return combined;
    }

    public static String desxifraAES(byte[] bIvIMsgXifrat, String clau) throws Exception {

        // Extreure l'IV.
        byte[] iv = extractIv(bIvIMsgXifrat);
        byte[] encryptedMessage = Arrays.copyOfRange(bIvIMsgXifrat, iv.length, bIvIMsgXifrat.length);
        // Extreure la part xifrada.

        // Fer hash de la clau

        // Desxifrar.

        // return String desxifrat
    }

    public static IvParameterSpec createIv() throws Exception {
        // Generar un IV aleatorio
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv); // Rellena el arreglo con bytes aleatorios
        return new IvParameterSpec(iv); // Crea el IvParameterSpec
    }

    public static SecretKeySpec generateHash(byte[] inputBytes) throws NoSuchAlgorithmException {
        // Crear una instancia de MessageDigest para SHA-256
        MessageDigest digest = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] hashBytes = digest.digest(inputBytes); // Generar el hash

        return new SecretKeySpec(hashBytes, ALGORISME_XIFRAT);
    }
    
    private static byte[] combineIvAndEncryptedMessage(byte[] iv, byte[] encryptedMessage) {
        byte[] combined = new byte[iv.length + encryptedMessage.length];
        System.arraycopy(iv, 0, combined, 0, iv.length); // Copiar el IV al inicio
        System.arraycopy(encryptedMessage, 0, combined, iv.length, encryptedMessage.length); // Copiar el mensaje cifrado
        return combined; // Devolver el mensaje combinado
    }

    private static byte[] extractIv(byte[] bIvIMsgXifrat) {
       
        // Copiar el IV desde el arreglo combinado
        System.arraycopy(bIvIMsgXifrat, 0, iv, 0, MIDA_IV);
        
        return iv; // Devolver el IV
    }
    


    
}
