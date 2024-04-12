package com.example.hashcryptic.rsa;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class RSAEncryption {

    private static String TAG = "KEYERROR";
    // Convert base64 encoded public key string to PublicKey object
//    public static PublicKey stringToPublicKey(String publicKeyString) throws Exception {
////        byte[] publicKeyBytes = Base64.decode(publicKeyString, Base64.DEFAULT);
////        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
////        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
////        return keyFactory.generatePublic(keySpec);
//
//        // Convert password to char array
//        char[] passwordChars = publicKeyString.toCharArray();
//
//        // Generate a random salt (recommended for password-based key derivation)
//        byte[] salt = new byte[16];
//        // You should use a secure random number generator to generate the salt
//        // Here, we're using a fixed salt for demonstration purposes
//        Arrays.fill(salt, (byte) 0x00);
//
//        // Number of iterations for the key derivation process (recommended to use a high number)
//        int iterations = 10000;
//
//        // Length of the derived key (in bits)
//        int keyLength = 256;
//
//        // Create PBEKeySpec with password, salt, iteration count, and key length
//        KeySpec spec = new PBEKeySpec(passwordChars, salt, iterations, keyLength);
//
//        // Get instance of SecretKeyFactory for PBKDF2
//        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
//
//        // Derive the key
//        byte[] derivedKey = factory.generateSecret(spec).getEncoded();
//
//        // Extract bytes from the derived key if it's longer than keyLength
//        byte[] keyBytes = Arrays.copyOf(derivedKey, keyLength / 8);
//
//        // Get instance of KeyFactory for RSA
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//
//        // Create RSAPublicKeySpec using the derived key bytes
//        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(1, keyBytes), BigInteger.valueOf(65537)); // Assuming public exponent is fixed
//
//        // Generate RSAPublicKey from the key specification
//        return keyFactory.generatePublic(publicKeySpec);
//    }

    // Convert base64 encoded private key string to PrivateKey object
//    public static PrivateKey stringToPrivateKey(String privateKeyString) throws Exception {
////        byte[] privateKeyBytes = Base64.decode(privateKeyString, Base64.DEFAULT);
////        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
////        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
////        return keyFactory.generatePrivate(keySpec);
//
//        // Convert password to char array
//        char[] passwordChars = privateKeyString.toCharArray();
//
//        // Generate a random salt (recommended for password-based key derivation)
//        byte[] salt = new byte[16];
//        // You should use a secure random number generator to generate the salt
//        // Here, we're using a fixed salt for demonstration purposes
//        Arrays.fill(salt, (byte) 0x00);
//
//        // Number of iterations for the key derivation process (recommended to use a high number)
//        int iterations = 10000;
//
//        // Length of the derived key (in bits)
//        int keyLength = 256;
//
//        // Create PBEKeySpec with password, salt, iteration count, and key length
//        KeySpec spec = new PBEKeySpec(passwordChars, salt, iterations, keyLength);
//
//        // Get instance of SecretKeyFactory for PBKDF2
//        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
//
//        // Derive the key
//        byte[] derivedKey = factory.generateSecret(spec).getEncoded();
//
//        // Extract bytes from the derived key if it's longer than keyLength
//        byte[] keyBytes = Arrays.copyOf(derivedKey, keyLength / 8);
//
//        // Get instance of KeyFactory for RSA
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//
//        // Create RSAPublicKeySpec using the derived key bytes
//        RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(new BigInteger(1, keyBytes), BigInteger.valueOf(65537)); // Assuming public exponent is fixed
//
//        // Generate RSAPublicKey from the key specification
//        return keyFactory.generatePrivate(privateKeySpec);
//    }
//
//    public static byte[] stringToByte (String key) throws InvalidKeySpecException, NoSuchAlgorithmException {
//
//        // Convert key to char array
//        char[] passwordChars = key.toCharArray();
//
//        // Generate a random salt (recommended for password-based key derivation)
//        byte[] salt = new byte[16];
//        // You should use a secure random number generator to generate the salt
//        // Here, we're using a fixed salt for demonstration purposes
//        Arrays.fill(salt, (byte) 0x00);
//
//        // Number of iterations for the key derivation process (recommended to use a high number)
//        int iterations = 10000;
//
//        // Length of the derived key (in bits)
//        int keyLength = 256;
//
//        // Create PBEKeySpec with password, salt, iteration count, and key length
//        KeySpec spec = new PBEKeySpec(passwordChars, salt, iterations, keyLength);
//
//        // Get instance of SecretKeyFactory for PBKDF2
//        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
//
//        // Derive the key
//        return factory.generateSecret(spec).getEncoded();
//    }

    // Encrypt text using RSA public key
//    public static String encrypt(String text, PublicKey publicKey) throws Exception {
////        PublicKey publicKey = stringToPublicKey(publicKeyString);
////        PublicKey publicKey = generateKeyPair().getPublic();
//
//        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
//
//        byte[] encryptedBytes = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
//        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
//    }
//
//    // Decrypt text using RSA private key
//    public static String decrypt(String encryptedText,  PrivateKey privateKey) throws Exception {
////        PrivateKey privateKey = stringToPrivateKey(privateKeyString);
////        PrivateKey privateKey = generateKeyPair().getPrivate();
//
//        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//        cipher.init(Cipher.DECRYPT_MODE, privateKey);
//
//        byte[] encryptedBytes = Base64.decode(encryptedText, Base64.DEFAULT);
//        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
//        return new String(decryptedBytes, StandardCharsets.UTF_8);
//    }


//    // Reconstruct PublicKey from byte array
//    public static PublicKey getPublicKeyFromBytes(byte[] publicKeyBytes) throws Exception {
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
//        return keyFactory.generatePublic(keySpec);
//    }
//
//    // Reconstruct PrivateKey from byte array
//    public static PrivateKey getPrivateKeyFromBytes(byte[] privateKeyBytes) throws Exception {
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
//        return keyFactory.generatePrivate(keySpec);
//    }
//
//    public static KeyPair randomgenerateKeyPair() throws NoSuchAlgorithmException {
//        // Generate an RSA key pair
//        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//        keyPairGenerator.initialize(2048); // Example key size
//        return keyPairGenerator.generateKeyPair();
//    }

    // Generate KeyPair (public-private key pair) from strings
//    public static KeyPair generateKeyPairString(String publicKeyString, String privateKeyString) throws Exception {
//        // Get instance of KeyFactory for RSA
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//
//        // Reconstruct PublicKey from string
//        byte[] publicKeyBytes = decodeKeyString(publicKeyString);
//        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
//        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
//
//        // Reconstruct PrivateKey from string
//        byte[] privateKeyBytes = decodeKeyString(privateKeyString);
//        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
//        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
//
//        // Return the generated KeyPair
//        return new KeyPair(publicKey, privateKey);
//    }

    // Encrypt using public key
    public static byte[] encrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data.getBytes());
    }

    // Decrypt using private key
    public static String decrypt(byte[] encryptedData, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedData = cipher.doFinal(encryptedData);
        return new String(decryptedData);
    }

    // Generate RSA key pair from two different words
    public static KeyPair generateKeyPair(String word1, String word2) throws NoSuchAlgorithmException {
        // Concatenate the two words to create a seed for key generation
        String seed = word1 + word2;

        // Use the seed to generate a secure random number generator
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(seed.getBytes());

        // Use the secure random number generator to generate the key pair
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048, secureRandom); // Key size
        return keyPairGenerator.generateKeyPair();
    }

    public static KeyPair generateStringPair (String publicKeyString, String privateKeyString) throws NoSuchAlgorithmException, InvalidKeySpecException {

        String seed = publicKeyString + privateKeyString;
        // Use a key derivation function (KDF) to generate a fixed-size key from the seed
        byte[] keyBytes = deriveKey(seed.getBytes());

        // Generate private key from the derived key
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(new BigInteger(1, keyBytes), BigInteger.valueOf(65537)); // Use a proper public exponent
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

        // Generate corresponding public key
        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(1, keyBytes), BigInteger.valueOf(65537)); // Use a proper public exponent
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

        return new KeyPair(publicKey, privateKey);

    }

    // Convert keys to char array
//        String seed = publicKeyString + privateKeyString;
//        char[] keys = seed.toCharArray();
//
//        // Use a key derivation function (KDF) to generate a fixed-size key from the seed
////        byte[] keyBytes = deriveKey(seed.getBytes());
//
//        // Generate a random salt (recommended for password-based key derivation)
//        byte[] salt = new byte[16];
//        // You should use a secure random number generator to generate the salt
//        // Here, we're using a fixed salt for demonstration purposes
//        Arrays.fill(salt, (byte) 0x00);
//
//        // Number of iterations for the key derivation process (recommended to use a high number)
//        int iterations = 10000;
//
//        // Length of the derived key (in bits)
//        int keyLength = 2048;
//
//        // Create PBEKeySpec with password, salt, iteration count, and key length
//        KeySpec spec = new PBEKeySpec(keys, salt, iterations, keyLength);
//
//        // Get instance of SecretKeyFactory for PBKDF2
//        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
//
//        // Derive the key
//        byte[] derivedKey = factory.generateSecret(spec).getEncoded();
//
//        // Extract bytes from the derived key if it's longer than keyLength
//        byte[] keyBytes = Arrays.copyOf(derivedKey, keyLength / 8);
//
//        // Get instance of KeyFactory for RSA
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//
//        // Create RSAPublicKeySpec using the derived key bytes
//        RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(new BigInteger(1, keyBytes), BigInteger.valueOf(65537)); // Assuming public exponent is fixed
//        PrivateKey prvKey = keyFactory.generatePrivate(privateKeySpec);
//
//        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(1, keyBytes), BigInteger.valueOf(65537)); // Assuming public exponent is fixed
//        PublicKey pubKey = keyFactory.generatePublic(publicKeySpec);
//
//        return new KeyPair(pubKey, prvKey);
//    }

    // Key derivation function (KDF) - SHA-256 hashing
    private static byte[] deriveKey(byte[] seed) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(seed);
    }

    // Parse PEM-encoded public key
    public static PublicKey parsePEMPublicKey(String pemPublicKey) throws Exception {
        String publicKeyPEM = pemPublicKey
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        byte[] publicKeyBytes = Base64.decode(publicKeyPEM, Base64.DEFAULT);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        return keyFactory.generatePublic(keySpec);
    }

    // Parse PEM-encoded private key
    public static PrivateKey parsePEMPrivateKey(String pemPrivateKey) throws Exception {
        String privateKeyPEM = pemPrivateKey
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] privateKeyBytes = Base64.decode(privateKeyPEM, Base64.DEFAULT);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        return keyFactory.generatePrivate(keySpec);
    }

    // Convert key to PEM format string
    public static String convertToPEM(Key key, String type) {
        byte[] keyBytes = key.getEncoded();
        String base64Key = Base64.encodeToString(keyBytes, Base64.DEFAULT);

        StringBuilder pemFormat = new StringBuilder();
        pemFormat.append("-----BEGIN ").append(type).append("-----\n");

        // Append the base64-encoded key with line breaks every 64 characters
        for (int i = 0; i < base64Key.length(); i += 64) {
            pemFormat.append(base64Key.substring(i, Math.min(i + 64, base64Key.length()))).append("\n");
        }

        pemFormat.append("-----END ").append(type).append("-----");

        return pemFormat.toString();
    }
//
//    public static byte[] byteToencrypt (byte[] data) throws NoSuchAlgorithmException, InvalidKeySpecException {
//        // Convert password to char array
////        char[] passwordChars = data.toCharArray();
//        // Generate a random salt (recommended for password-based key derivation)
//        byte[] salt = new byte[16];
//        // You should use a secure random number generator to generate the salt
//        // Here, we're using a fixed salt for demonstration purposes
//        Arrays.fill(salt, (byte) 0x00);
//
//        // Number of iterations for the key derivation process (recommended to use a high number)
//        int iterations = 10000;
//
//        // Length of the derived key (in bits)
//        int keyLength = 256;
//
//        // Create PBEKeySpec with password, salt, iteration count, and key length
//        KeySpec spec = new PBEKeySpec(Arrays.toString(data).toCharArray(), salt, iterations, keyLength);
//        // Get instance of SecretKeyFactory for PBKDF2
//        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
//
//        // Derive the key
//        return factory.generateSecret(spec).getEncoded();
//    }
}
