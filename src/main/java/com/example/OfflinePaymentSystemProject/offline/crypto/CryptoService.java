package com.example.upi.offline.crypto;

import com.example.upi.offline.dto.SecurePacketData;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.util.Base64;
import com.example.upi.offline.crypto.AESUtil;
import com.example.upi.offline.crypto.RSAUtil;
import com.example.upi.offline.crypto.DigitalSignatureUtil;

@Service
public class CryptoService {

    public SecurePacketData securePacket(String paymentData) throws Exception {

        // Generate AES Key
        SecretKey aesKey = AESUtil.generateKey();

        // Encrypt payment data using AES
        String encryptedPayload =
                AESUtil.encrypt(paymentData, aesKey);

        // Generate RSA Keys
        KeyPair keyPair = RSAUtil.generateKeyPair();

        // Encrypt AES key using RSA Public Key
        String encryptedAESKey =
                RSAUtil.encrypt(
                        Base64.getEncoder().encodeToString(aesKey.getEncoded()),
                        keyPair.getPublic()
                );

        // Sign original payment data
        String digitalSignature =
                DigitalSignatureUtil.sign(
                        paymentData,
                        keyPair.getPrivate()
                );

        return new SecurePacketData(
                encryptedPayload,
                encryptedAESKey,
                digitalSignature
        );
    }

}