package com.example.hashcryptic.ciphers;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.hashcryptic.R;


public class Vigenere extends AppCompatActivity {

    ClipboardManager cpb;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vigenere);

        Button encrVignr, decrVignr, copyResult;
        EditText encryText, keySize;
        TextView message;

        encrVignr = findViewById(R.id.encrVigenere);
        decrVignr = findViewById(R.id.decrVigenere);
        copyResult = findViewById(R.id.copyVignr);
        message = findViewById(R.id.vgnr_msg_cntxt);

        // link the edittext and textview with its id
        encryText = findViewById(R.id.encrypt_Vigenere_text);
        keySize = findViewById(R.id.vigenereKey_size);

        // create a clipboard manager variable to copy text
        cpb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        // onClick function of encrypt text button
        encrVignr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Constraints to resolve error handling using wrong type of entity
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
                    Toast.makeText(Vigenere.this, "Message Encrypted Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        decrVignr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Constraints to resolve error handling using wrong type of entity
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
                    Toast.makeText(Vigenere.this, "Message Decrypted Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        copyResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (message.getText().toString().isEmpty()) {
                    Toast.makeText(Vigenere.this, "Please enter text to encrypt/decrypt", Toast.LENGTH_SHORT).show();
                }
                else {
                    // get the string from the textview and trim all spaces
                    String data = message.getText().toString().trim();

                    // check if the textview is not empty
                    if (!data.isEmpty()) {

                        // copy the text in the clip board
                        ClipData temp = ClipData.newPlainText("text", data);
                        cpb.setPrimaryClip(temp);

                        // display message that the text has been copied
                        Toast.makeText(Vigenere.this, "Message Copied", Toast.LENGTH_SHORT).show();
                    }
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