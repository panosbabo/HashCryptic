package com.example.hashcryptic.hashencryption;

import android.annotation.SuppressLint;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;

public class fileEncryption {
    public static SecretKey generateSecretKey(int keySize) throws Exception {
        SecureRandom secureRandom = new SecureRandom();
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        // generate a key with a secure random
        keyGenerator.init(keySize, secureRandom);
        return keyGenerator.generateKey();
    }
    public static byte[] readFile(String filePath) throws IOException {
        try {
            File file = new File(filePath);
            // TODO: Resolve issue where NoSuchFile exception is received
            FileInputStream fis = new FileInputStream(file);
            // TODO: End
            byte[] fileBytes = new byte[(int) file.length()];
            fis.read(fileBytes);
            fis.close();
            return fileBytes;
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
            return null;
        }
    }

//    public String saveSecretKey(SharedPreferences sharedPref, SecretKey secretKey) {
//        String encodedKey = Base64.encodeToString(secretKey.getEncoded(), Base64.NO_WRAP);
//        sharedPref.edit().putString(AppConstants.secretKeyPref, encodedKey).apply();
//        return encodedKey;
//    }

    public static void saveFile(byte[] fileData, String path) throws Exception {
        File file = new File(path);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file, false));
        bos.write(fileData);
        bos.flush();
        bos.close();
    }

    public static byte[] encrypt(SecretKey yourKey, byte[] fileData) throws Exception {
        byte[] data = yourKey.getEncoded();
        SecretKeySpec skeySpec = new SecretKeySpec(data, 0, data.length, "AES");
        @SuppressLint({"GetInstance", "DeprecatedProvider"}) Cipher cipher = Cipher.getInstance("AES", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        return cipher.doFinal(fileData);
    }
}