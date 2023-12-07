package com.example.hashcryptic.hashencryption;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;

public class fileEncryption {
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String SECRET_KEY_FACTORY_ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final String KEY_SPEC_ALGORITHM = "AES";
    private static final int ITERATION_COUNT = 65536;
    private static final int SALT_LENGTH = 16;

    public static void encryptFile(String filePath, String password, int keySize) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException,
            InvalidAlgorithmParameterException, InvalidKeyException, IOException {
        File inputFile = new File(filePath);
        File encryptedFile = new File(filePath + ".encrypted");

        FileInputStream fileInputStream = new FileInputStream(inputFile);
        FileOutputStream fileOutputStream = new FileOutputStream(encryptedFile);

        byte[] salt = generateSalt();
        SecretKey secretKey = generateSecretKey(password, salt, keySize);
        byte[] iv = generateInitializationVector();

        fileOutputStream.write(salt);
        fileOutputStream.write(iv);

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
        CipherOutputStream cipherOutputStream = new CipherOutputStream(fileOutputStream, cipher);

        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            cipherOutputStream.write(buffer, 0, bytesRead);
        }

        cipherOutputStream.flush();
        cipherOutputStream.close();
        fileInputStream.close();
        fileOutputStream.close();

//        inputFile.delete();
    }

    private static byte[] generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);

        return salt;
    }

    private static SecretKey generateSecretKey(String password, byte[] salt, int key_size) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_KEY_FACTORY_ALGORITHM);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, key_size);
        SecretKey secretKey = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), KEY_SPEC_ALGORITHM);

        return secretKey;
    }

    private static byte[] generateInitializationVector() {
        byte[] iv = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);

        return iv;
    }
}
