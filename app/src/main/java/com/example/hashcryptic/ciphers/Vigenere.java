package com.example.hashcryptic.ciphers;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.hashcryptic.R;


public class Vigenere extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vigenere);

        Button encrCaesar, decrCaesar;
        EditText encryText, keySize;
        TextView message;

        encrCaesar = findViewById(R.id.encrVigenere);
        decrCaesar = findViewById(R.id.decrVigenere);
        message = findViewById(R.id.vgnr_msg_cntxt);

        // link the edittext and textview with its id
        encryText = findViewById(R.id.encrypt_Vigenere_text);
        keySize = findViewById(R.id.vigenereKey_size);

        // onClick function of encrypt text button
        encrCaesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (encryText.getText().toString().isEmpty() || keySize.getText().toString().isEmpty()) {
                    Toast.makeText(Vigenere.this, "Please enter text and key to cipher", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (keySize.getText().toString().matches(".*\\d.*")) {
                    Toast.makeText(Vigenere.this, "Please enter a non-numeric key", Toast.LENGTH_SHORT).show();
                }
                else {
                    // get the string from the textview and trim all spaces
                    String key = keySize.getText().toString().trim();
                    String encryptedText = vignrEncrypt(encryText.getText().toString().trim(), key);
                    message.setText(encryptedText.toUpperCase());
                }
            }
        });

        decrCaesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Intent function to move to another activity
                // get text from edittext
                if (encryText.getText().toString().isEmpty() || keySize.getText().toString().isEmpty()) {
                    Toast.makeText(Vigenere.this, "Please enter cipher and key to decrypt", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (keySize.getText().toString().matches(".*\\d.*")) {
                    Toast.makeText(Vigenere.this, "Please enter a non-numeric key", Toast.LENGTH_SHORT).show();
                }
                else {
                    // get the string from the textview and trim all spaces
                    String key = keySize.getText().toString().trim();
                    String decryptText = vignrDecrypt(encryText.getText().toString().trim(), key);
                    message.setText(decryptText.toUpperCase());
                }
            }
        });
    }

    // Private function for Vigenere Encryption
    private String vignrEncrypt(String plaintext, String key) {
        StringBuilder ciphertext = new StringBuilder();
        int keyIndex = 0;
        for (int i = 0; i < plaintext.length(); i++) {
            char plainChar = plaintext.charAt(i);
            if (Character.isLetter(plainChar)) {
                char keyChar = key.charAt(keyIndex);
                int shift = Character.toUpperCase(keyChar) - 'A';
                char encryptedChar;
                if (Character.isUpperCase(plainChar)) {
                    encryptedChar = (char) ('A' + (plainChar - 'A' + shift) % 26);
                } else {
                    encryptedChar = (char) ('a' + (plainChar - 'a' + shift) % 26);
                }
                ciphertext.append(encryptedChar);
                keyIndex = (keyIndex + 1) % key.length();
            } else {
                ciphertext.append(plainChar);
            }
        }
        return ciphertext.toString();
    }

    // Private function for Vigenere Decryption
    private String vignrDecrypt(String ciphertext, String key) {
        StringBuilder plaintext = new StringBuilder();
        int keyIndex = 0;
        for (int i = 0; i < ciphertext.length(); i++) {
            char cipherChar = ciphertext.charAt(i);
            if (Character.isLetter(cipherChar)) {
                char keyChar = key.charAt(keyIndex);
                int shift = Character.toUpperCase(keyChar) - 'A';
                char decryptedChar;
                if (Character.isUpperCase(cipherChar)) {
                    decryptedChar = (char) ('A' + (cipherChar - 'A' - shift + 26) % 26);
                } else {
                    decryptedChar = (char) ('a' + (cipherChar - 'a' - shift + 26) % 26);
                }
                plaintext.append(decryptedChar);
                keyIndex = (keyIndex + 1) % key.length();
            } else {
                plaintext.append(cipherChar);
            }
        }
        return plaintext.toString();
    }
}