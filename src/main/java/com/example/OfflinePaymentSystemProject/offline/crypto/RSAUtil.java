package com.example.upi.offline.crypto;

import javax.crypto.SecretKey;
import java.security.*;
import java.util.Base64;

import javax.crypto.Cipher;

public class RSAUtil {

    private static final String ALGORITHM = "RSA";

    public static KeyPair generateKeyPair() throws Exception {

        KeyPairGenerator generator =
                KeyPairGenerator.getInstance(ALGORITHM);

        generator.initialize(2048);

        return generator.generateKeyPair();
    }

    public static String encrypt(String data,
                                 PublicKey publicKey) throws Exception {

        Cipher cipher = Cipher.getInstance(ALGORITHM);

        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] encrypted =
                cipher.doFinal(data.getBytes());

        return Base64.getEncoder()
                .encodeToString(encrypted);
    }

    public static String decrypt(String encryptedData,
                                 PrivateKey privateKey) throws Exception {

        Cipher cipher = Cipher.getInstance(ALGORITHM);

        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] decoded =
                Base64.getDecoder().decode(encryptedData);

        byte[] decrypted =
                cipher.doFinal(decoded);

        return new String(decrypted);
    }
}