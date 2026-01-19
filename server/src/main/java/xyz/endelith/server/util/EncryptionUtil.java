package xyz.endelith.server.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;

public final class EncryptionUtil {

    private EncryptionUtil() {}

    public static byte[] decryptRsa(KeyPair keyPair, byte[] bytes) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
            return cipher.doFinal(bytes);
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Failed to decrypt RSA data", e);
        }
    }

    public static String generateServerId(KeyPair keyPair, byte[] secret) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update("".getBytes("ISO_8859_1"));
            messageDigest.update(secret);
            messageDigest.update(keyPair.getPublic().getEncoded());
            return new BigInteger(messageDigest.digest()).toString(16);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new IllegalStateException("SHA-1 algorithm is not available", e);
        }
    }
}
