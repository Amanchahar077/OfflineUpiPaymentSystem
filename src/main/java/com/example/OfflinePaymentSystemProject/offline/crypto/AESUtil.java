package com.example.upi.offline.crypto;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class AESUtil {

    private static final String ALGORITHM = "AES";

    public static SecretKey generateKey() throws Exception {

        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);

        keyGenerator.init(128);

        return keyGenerator.generateKey();
    }

    public static String encrypt(String data,
                                 SecretKey key) throws Exception {

        Cipher cipher = Cipher.getInstance(ALGORITHM);

        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encrypted = cipher.doFinal(data.getBytes());

        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String encryptedData,
                                 SecretKey key) throws Exception {

        Cipher cipher = Cipher.getInstance(ALGORITHM);

        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] decoded = Base64.getDecoder().decode(encryptedData);

        byte[] decrypted = cipher.doFinal(decoded);

        return new String(decrypted);
    }

}
