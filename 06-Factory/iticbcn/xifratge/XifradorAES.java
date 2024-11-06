package iticbcn.xifratge;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Arrays;

public class XifradorAES implements Xifrador{
    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";
    private static final int MIDA_IV = 16;
    private static byte[] iv = new byte[MIDA_IV];

    public static IvParameterSpec createIv() throws Exception {
        // Generar un IV aleatorio
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv); // Rellena el arreglo con bytes aleatorios
        return new IvParameterSpec(iv); // Crea el IvParameterSpec
    }

    private static SecretKeySpec generateHash(String clau) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[] clauBytes = clau.getBytes("UTF-8");
        MessageDigest digest = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] hashBytes = digest.digest(clauBytes);
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
    

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        try {
            IvParameterSpec ivSpec = createIv();
            SecretKeySpec secretKey = generateHash(clau);

            Cipher cipher = Cipher.getInstance(FORMAT_AES);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
            byte[] encryptedMessage = cipher.doFinal(msg.getBytes("UTF-8"));

            byte[] combined = combineIvAndEncryptedMessage(iv, encryptedMessage);
            return new TextXifrat(combined);
        } catch (Exception e) {
            throw new ClauNoSuportada("Error en cifrado AES: " + e.getMessage());
        }
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        try {
            byte[] bIvIMsgXifrat = xifrat.getBytes();
            byte[] iv = extractIv(bIvIMsgXifrat);
            byte[] encryptedMessage = Arrays.copyOfRange(bIvIMsgXifrat, iv.length, bIvIMsgXifrat.length);

            SecretKeySpec secretKey = generateHash(clau);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            Cipher cipher = Cipher.getInstance(FORMAT_AES);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
            byte[] decryptedBytes = cipher.doFinal(encryptedMessage);

            return new String(decryptedBytes, "UTF-8");
        } catch (Exception e) {
            throw new ClauNoSuportada("Error en descifrado AES: " + e.getMessage());
        }
    }

    
}
