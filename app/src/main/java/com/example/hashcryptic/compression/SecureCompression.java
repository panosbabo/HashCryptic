package com.example.hashcryptic.compression;

import android.annotation.SuppressLint;
import com.example.hashcryptic.db.Profile;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.zip.DeflaterOutputStream;
import java.security.Key;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class SecureCompression {

    // Function to be used for compression before encryption when generating hash values
    public static String compressb4encryption(String text) throws IOException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {

        // Conversion to Byte array
        byte[] uncompressedData = text.getBytes(StandardCharsets.UTF_8);

        // Compression
        ByteArrayOutputStream compressedStream = new ByteArrayOutputStream();
        DeflaterOutputStream deflaterStream = new DeflaterOutputStream(compressedStream);
        deflaterStream.write(uncompressedData);
        deflaterStream.close();
        byte[] compressedData = compressedStream.toByteArray();

        return bytesToHex(compressedData);
    }

    // Function to be used for file encryption with the use of a unique signature using Profile's details
    public static byte[] compressWithencryption(String text, byte[] compressedData) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        // TODO: To be deleted after confirmation of good functionality
//        ProfileDatabase db  = ProfileDatabase.getDbInstance(this.getContext());
//        profile = db.profileDao().getprofile().get(0);
        // Instantiation of Profile to be used for file encryption
        Profile profile = new Profile();

        byte[] keyBytes = profile.personUsername.getBytes(); // Example key (16 bytes for AES-128)
        Key key = new SecretKeySpec(keyBytes, "AES");
        @SuppressLint("GetInstance") Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return cipher.doFinal(compressedData);
    }

    public static byte[] compressDecryption(String text, byte[] decryptedData) {
        // Decryption
//        cipher.init(Cipher.DECRYPT_MODE, key);
//        byte[] decryptedData = cipher.doFinal(encryptedData);
//
//        // Decompression
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(decryptedData);
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        byte[] buffer = new byte[1024];
//        int bytesRead;
//        while ((bytesRead = inputStream.read(buffer)) != -1) {
//            outputStream.write(buffer, 0, bytesRead);
//        }
//        String decryptedAndDecompressedData = new String(outputStream.toByteArray(), "UTF-8");
//
//        System.out.println("Decompressed and decrypted data: " + decryptedAndDecompressedData);
        return null;
    }

    // Byte to Hexadecimal String Function
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}
