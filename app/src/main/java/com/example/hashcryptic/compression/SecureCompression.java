package com.example.hashcryptic.compression;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.zip.DeflaterOutputStream;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

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

    // Byte to Hexadecimal String Function
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}
