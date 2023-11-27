package com.example.hashcryptic.hashtypes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class enc_SHA512 {
    public static String encryptSHA512(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(text.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();

            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}