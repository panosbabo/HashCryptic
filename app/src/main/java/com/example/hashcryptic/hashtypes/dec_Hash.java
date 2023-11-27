package com.example.hashcryptic.hashtypes;


public class dec_Hash {

    public static String decryptHash(String text) {
        String hash = text; // Replace with your own MD5 hash
        String plaintext = "";

        // Compare the generated hash with the target hash
        if (text.equals(hash)) {
            plaintext = hash;
            return plaintext;
        }

        // Print the plaintext value if found
        if (!plaintext.isEmpty()) {
            System.out.println("Plaintext: " + plaintext);
        } else {
            System.out.println("Plaintext not found.");
        }
        return null;
    }
}
