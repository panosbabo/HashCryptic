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

public class Caesar extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caesar);

        Button encrCaesar, decrCaesar;
        EditText encryText, keySize;
        TextView message;

        encrCaesar = findViewById(R.id.encrCaesar);
        decrCaesar = findViewById(R.id.decrCaesar);
        message = findViewById(R.id.msg_cntxt);

        // link the edittext and textview with its id
        encryText = findViewById(R.id.encrypt_Caesar_text);
        keySize = findViewById(R.id.caesarKey_size);

        // onClick function of encrypt text button
        encrCaesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (encryText.getText().toString().isEmpty() || keySize.getText().toString().isEmpty()) {
                    Toast.makeText(Caesar.this, "Please enter text and key to cipher", Toast.LENGTH_SHORT).show();
                }
                else if (Integer.parseInt(keySize.getText().toString()) < 1 || Integer.parseInt(keySize.getText().toString()) > 26) {
                    Toast.makeText(Caesar.this, "Key size must be between 1-26", Toast.LENGTH_SHORT).show();
                }
                else {
                    // get the string from the textview and trim all spaces
                    String enryptTextToCaesar = encryptCaesar(encryText.getText().toString().trim(), Integer.parseInt(keySize.getText().toString().trim()));
                    message.setText(enryptTextToCaesar.toUpperCase());
                }
            }
        });

        decrCaesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Intent function to move to another activity
                // get text from edittext
                if (encryText.getText().toString().isEmpty() || keySize.getText().toString().isEmpty()) {
                    Toast.makeText(Caesar.this, "Please enter cipher and key to decrypt", Toast.LENGTH_SHORT).show();
                }
                else if (Integer.parseInt(keySize.getText().toString()) < 1 || Integer.parseInt(keySize.getText().toString()) > 26) {
                    Toast.makeText(Caesar.this, "Key size must be between 1-26", Toast.LENGTH_SHORT).show();
                }
                else {
                    // get the string from the textview and trim all spaces
                    String enryptTextToCaesar = decryptCaesar(encryText.getText().toString().trim(), Integer.parseInt(keySize.getText().toString().trim()));
                    message.setText(enryptTextToCaesar.toUpperCase());
                }
            }
        });
    }

    // Encrypts text using a shift specified by the key
    private String encryptCaesar(String text, int key) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (Character.isLetter(ch)) {
                if (Character.isUpperCase(ch)) {
                    char encryptedChar = (char) ('A' + (ch - 'A' + key) % 26);
                    result.append(encryptedChar);
                } else if (Character.isLowerCase(ch)) {
                    char encryptedChar = (char) ('a' + (ch - 'a' + key) % 26);
                    result.append(encryptedChar);
                }
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    // Decrypts cipher using a shift specified by the key
    private String decryptCaesar(String text, int key) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (Character.isLetter(ch)) {
                if (Character.isUpperCase(ch)) {
                    char decryptedChar = (char) ('A' + (ch - 'A' - key) % 26);
                    result.append(decryptedChar);
                } else if (Character.isLowerCase(ch)) {
                    char decryptedChar = (char) ('a' + (ch - 'a' - key) % 26);
                    result.append(decryptedChar);
                }
            } else {
                result.append(ch);
            }
        }

        return result.toString();
    }
}