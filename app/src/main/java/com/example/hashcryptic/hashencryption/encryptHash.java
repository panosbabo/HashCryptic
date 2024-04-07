package com.example.hashcryptic.hashencryption;

import android.widget.Toast;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.content.Context;

// class to be used for all provided Hash Encryption types
public class encryptHash {
    public static String encrypt2Hash(String text, String hashtype, Context context) {
        try {
            // Encryption type is chosen by the parameter String hashtype
            MessageDigest md = MessageDigest.getInstance(hashtype);
            md.update(text.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            // Every byte of message digest is handled accordingly for encryption process to chosen hash type
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            Toast.makeText(context.getApplicationContext(), "Message Encrypted Successfully", Toast.LENGTH_SHORT).show();
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
