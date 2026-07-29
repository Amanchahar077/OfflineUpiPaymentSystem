package com.example.OfflinePaymentSystemProject.offline.crypto;

import com.example.OfflinePaymentSystemProject.entity.Device;
import com.example.OfflinePaymentSystemProject.repository.DeviceRepository;
import com.example.OfflinePaymentSystemProject.offline.dto.SecurePacketData;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

@Service
public class CryptoService {

    private final DeviceRepository deviceRepository;

    public CryptoService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public SecurePacketData securePacket(
            String senderDeviceId,
            String receiverDeviceId,
            String paymentData
    ) throws Exception {

        // Generate AES Key
        SecretKey aesKey = AESUtil.generateKey();
        String encodedAESKey = Base64.getEncoder().encodeToString(aesKey.getEncoded());

        // Encrypt payment data using AES
        String encryptedPayload =
                AESUtil.encrypt(paymentData, aesKey);

        Device sender = deviceRepository
                .findByDeviceId(senderDeviceId)
                .orElseThrow(() -> new RuntimeException("Sender device not found"));

        Device receiver = deviceRepository
                .findByDeviceId(receiverDeviceId)
                .orElseThrow(() -> new RuntimeException("Receiver device not found"));

        PublicKey receiverPublicKey =
                RSAUtil.decodePublicKey(
                        receiver.getPublicKey()
                );

        // Encrypt AES key using RSA Public Key
        String encryptedAESKey =
                RSAUtil.encrypt(
                        encodedAESKey,
                        receiverPublicKey
                );

        PrivateKey senderPrivateKey =
                RSAUtil.decodePrivateKey(
                        sender.getPrivateKey()
                );

        // Sign original payment data
        String digitalSignature =
                DigitalSignatureUtil.sign(
                        paymentData,
                        senderPrivateKey
                );

        return new SecurePacketData(
                encryptedPayload,
                encryptedAESKey,
                digitalSignature
        );
    }

    public String decryptPacket(
            String receiverDeviceId,
            SecurePacketData securePacketData
    ) throws Exception {
        Device receiver = deviceRepository
                .findByDeviceId(receiverDeviceId)
                .orElseThrow(() -> new RuntimeException("Receiver device not found"));

        PrivateKey receiverPrivateKey =
                RSAUtil.decodePrivateKey(
                        receiver.getPrivateKey()
                );

        // Decrypt AES Key using Receiver's RSA Private Key
        String decodedAESKeyStr = RSAUtil.decrypt(
                securePacketData.getEncryptedAESKey(),
                receiverPrivateKey
        );
        byte[] decodedAESKeyBytes = Base64.getDecoder().decode(decodedAESKeyStr);
        SecretKey aesKey = new javax.crypto.spec.SecretKeySpec(decodedAESKeyBytes, "AES");

        // Decrypt payload using AES
        return AESUtil.decrypt(
                securePacketData.getEncryptedPayload(),
                aesKey
        );
    }

    public boolean verifySignature(
            String senderDeviceId,
            String payload,
            String signature
    ) throws Exception {
        Device sender = deviceRepository
                .findByDeviceId(senderDeviceId)
                .orElseThrow(() -> new RuntimeException("Sender device not found"));

        PublicKey senderPublicKey =
                RSAUtil.decodePublicKey(
                        sender.getPublicKey()
                );

        return DigitalSignatureUtil.verify(payload, signature, senderPublicKey);
    }
}