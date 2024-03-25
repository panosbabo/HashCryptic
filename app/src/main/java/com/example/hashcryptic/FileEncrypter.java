package com.example.hashcryptic;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

public class FileEncrypter {

    private static final String AES_MODE = "AES/CBC/PKCS7Padding";
    private static final String SECRET_KEY_ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int KEY_LENGTH = 128;
    private static final int IV_LENGTH = 16;

    // Your encryption password. Ensure it's kept secure.
    private static final String PASSWORD = "YourEncryptionPassword";
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    public static final int PICK_DIRECTORY_REQUEST_CODE = 123;

    public static void createTextFile(Activity activity, String fileName, InputStream inputStream) {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_TITLE, fileName);
        intent.putExtra("input_stream", inputStream.toString()); // Pass input stream as an extra
        activity.startActivityForResult(intent, PICK_DIRECTORY_REQUEST_CODE);
    }

    public static void saveTextFile(Activity activity, Intent data) {
        Uri uri = data.getData();
        InputStream inputStream = data.getParcelableExtra("input_stream"); // Retrieve input stream from extra
        try {
            OutputStream outputStream = activity.getContentResolver().openOutputStream(uri);
            if (outputStream != null) {
//                encryptAndWrite(outputStream, inputStream);
                outputStream.close();
                Toast.makeText(activity, "File saved successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, "Failed to encrypt file", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(activity, "Failed to save file", Toast.LENGTH_SHORT).show();
        }
    }

    private static void encryptAndWrite(OutputStream outputStream, InputStream inputStream) throws IOException {
        try {

            SecureRandom random = new SecureRandom();
            byte[] iv = new byte[IV_LENGTH];
            random.nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_KEY_ALGORITHM);
            SecretKey key = new SecretKeySpec(factory.generateSecret(new javax.crypto.spec.PBEKeySpec(PASSWORD.toCharArray())).getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance(AES_MODE);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

            CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                cipherOutputStream.write(buffer, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                throw new IOException("Encryption failed", e);
            }
        }
    }

//            SecureRandom random = new SecureRandom();
//            byte[] iv = new byte[IV_LENGTH];
//            random.nextBytes(iv);
//            AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
//
//            SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_KEY_ALGORITHM);
//            SecretKey key = new SecretKeySpec(factory.generateSecret(new javax.crypto.spec.PBEKeySpec(PASSWORD.toCharArray())).getEncoded(), "AES");
//
//            Cipher cipher = Cipher.getInstance(AES_MODE);
//            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
//
//            CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);
//
//            byte[] buffer = new byte[1024];
//            int bytesRead;
//            while ((bytesRead = inputStream.read(buffer)) != -1) {
//                cipherOutputStream.write(buffer, 0, bytesRead);
//            }
//            cipherOutputStream.close();
//            inputStream.close();
//    }
}
