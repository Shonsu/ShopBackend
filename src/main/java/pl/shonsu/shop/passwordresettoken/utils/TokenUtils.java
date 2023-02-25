package pl.shonsu.shop.passwordresettoken.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class TokenUtils {
    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);
    public static final String HASH_ALGORITHM = "SHA-256";

    public static String generateToken() {
        byte[] random = new byte[64];
        new SecureRandom().nextBytes(random);
//        StringBuilder token = new StringBuilder();
//        for (byte b : random) {
//            token.append(String.format("%02x", b));
//        }
//        return token.toString();
        return bytesToHex(random);
    }

    public static String hashToken(String token) {

        byte[] result;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(HASH_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        result = md.digest(token.getBytes());

        return bytesToHex(result);
    }

    private static String bytesToHex(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8);
    }
}
