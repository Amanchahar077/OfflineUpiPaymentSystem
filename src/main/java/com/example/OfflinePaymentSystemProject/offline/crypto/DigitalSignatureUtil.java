package com.example.upi.offline.crypto;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

public class DigitalSignatureUtil {

    private static final String ALGORITHM = "SHA256withRSA";

    public static String sign(String data,
                              PrivateKey privateKey) throws Exception {

        Signature signature = Signature.getInstance(ALGORITHM);

        signature.initSign(privateKey);

        signature.update(data.getBytes());

        byte[] signedData = signature.sign();

        return Base64.getEncoder().encodeToString(signedData);
    }

    public static boolean verify(String data,
                                 String digitalSignature,
                                 PublicKey publicKey) throws Exception {

        Signature signature = Signature.getInstance(ALGORITHM);

        signature.initVerify(publicKey);

        signature.update(data.getBytes());

        byte[] decodedSignature =
                Base64.getDecoder().decode(digitalSignature);

        return signature.verify(decodedSignature);
    }
}