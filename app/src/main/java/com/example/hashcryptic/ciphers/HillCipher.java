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


public class HillCipher extends AppCompatActivity {

    ClipboardManager cpb;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hill_cipher);

        Button encrHill, decrHill, copyResult;
        EditText encryText, key;
        TextView message;

        encrHill = findViewById(R.id.encrHill);
        decrHill = findViewById(R.id.decrHill);
        copyResult = findViewById(R.id.copyHill);
        message = findViewById(R.id.hill_msg_cntxt);

        // link the edittext and textview with its id
        encryText = findViewById(R.id.encrypt_Hill_text);
        key = findViewById(R.id.hillKey);

        // create a clipboard manager variable to copy text
        cpb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        // onClick function of encrypt text button
        encrHill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Constraints to resolve error handling using wrong type of entity
                if (encryText.getText().toString().isEmpty() || key.getText().toString().isEmpty()) {
                    Toast.makeText(HillCipher.this, "Please enter text and key to cipher", Toast.LENGTH_SHORT).show();
                }
                else if (encryText.getText().toString().matches(".*\\d.*") || key.getText().toString().matches(".*\\d.*")) {
                    Toast.makeText(HillCipher.this, "Please enter characters only", Toast.LENGTH_SHORT).show();
                }
                else if (encryText.getText().toString().length() < 4 || encryText.getText().toString().length() > 4) {
                    Toast.makeText(HillCipher.this, "Text size must be 4 characters", Toast.LENGTH_SHORT).show();
                }
                else if (key.getText().toString().length() < 4 || key.getText().toString().length() > 4) {
                    Toast.makeText(HillCipher.this, "Key size must be 4 numbers", Toast.LENGTH_SHORT).show();
                }
                else {
                    // get the string from the textview and trim all spaces
                    int[][] keyMatrix = new int[2][2];
                    int keyIndex = 0;

                    // Loop through the key and map it into a 2 by 2 array
                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 2; j++) {
                            keyMatrix[i][j] = (key.getText().toString().trim().toUpperCase()).codePointAt(keyIndex) % 65;
                            keyIndex++;
                        }
                    }
                    String encryptedText = hillEncrypt((encryText.getText().toString().trim().toUpperCase()), keyMatrix);
                    message.setText(encryptedText.toUpperCase());
                    Toast.makeText(HillCipher.this, "Message Encrypted Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        decrHill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Constraints to resolve error handling using wrong type of entity
                if (encryText.getText().toString().isEmpty() || key.getText().toString().isEmpty()) {
                    Toast.makeText(HillCipher.this, "Please enter cipher and key to decrypt", Toast.LENGTH_SHORT).show();
                }
                else if (encryText.getText().toString().matches(".*\\d.*") || key.getText().toString().matches(".*\\d.*")) {
                    Toast.makeText(HillCipher.this, "Please enter characters only", Toast.LENGTH_SHORT).show();
                }
                else if (encryText.getText().toString().length() < 4 || encryText.getText().toString().length() > 4) {
                    Toast.makeText(HillCipher.this, "Text size must be 4 characters", Toast.LENGTH_SHORT).show();
                }
                else if (key.getText().toString().length() < 4 || key.getText().toString().length() > 4) {
                    Toast.makeText(HillCipher.this, "Key size must be 4 numbers", Toast.LENGTH_SHORT).show();
                }
                else {
                    // get the string from the textview and trim all spaces
                    int[][] keyMatrix = new int[2][2];
                    int keyIndex = 0;

                    // Loop through the key and map it into a 2 by 2 array
                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 2; j++) {
                            keyMatrix[i][j] = (key.getText().toString().trim().toUpperCase()).codePointAt(keyIndex) % 65;
                            keyIndex++;
                        }
                    }
                    String encryptedText = hillDecrypt((encryText.getText().toString().trim().toUpperCase()), keyMatrix);
                    message.setText(encryptedText.toUpperCase());
                    Toast.makeText(HillCipher.this, "Message Decrypted Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        copyResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (message.getText().toString().isEmpty()) {
                    Toast.makeText(HillCipher.this, "Please enter text to encrypt/decrypt", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(HillCipher.this, "Message Copied", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // Hill Cipher Encryption function
    private String hillEncrypt(String plaintext, int[][] keyMatrix) {
        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", ""); // Remove non-alphabetic characters and convert to uppercase
        int n = keyMatrix.length;
        int blockSize = n;
        int padding = blockSize - plaintext.length() % blockSize;
        if (padding != blockSize) {
            for (int i = 0; i < padding; i++) {
                plaintext += "X"; // Add padding if necessary
            }
        }

        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i += blockSize) {
            String block = plaintext.substring(i, i + blockSize);
            for (int j = 0; j < blockSize; j++) {
                int sum = 0;
                for (int k = 0; k < blockSize; k++) {
                    sum += (keyMatrix[j][k] * (block.charAt(k) - 'A'));
                }
                char encryptedChar = (char) ((sum % 26) + 'A');
                ciphertext.append(encryptedChar);
            }
        }
        return ciphertext.toString();
    }

    // Function the calculates the inverse of a 2 by 2 matrix
    private int[][] calculateInverse(int[][] matrix) {
        int det = (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]) % 26;
        // Compute the determinant modulo mod to ensure it's within the range [0, mod - 1]
        if (det < 0) {
            det += 26;
        }

        // Calculate the determinant inverse modulo mod using modular multiplicative inverse
        int detInv = -1;
        for (int i = 0; i < 26; i++) {
            if ((det * i) % 26 == 1) {
                detInv = i;
                break;
            }
        }

        // Calculate the inverse matrix
        int[][] inverse = new int[2][2];
        inverse[0][0] = (matrix[1][1] * detInv) % 26;
        inverse[0][1] = (-matrix[0][1] * detInv) % 26;
        inverse[1][0] = (-matrix[1][0] * detInv) % 26;
        inverse[1][1] = (matrix[0][0] * detInv) % 26;

        // Ensure all elements are positive by adding mod if necessary
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (inverse[i][j] < 0) {
                    inverse[i][j] += 26;
                }
            }
        }

        return inverse;
    }

    // Hill Cipher Decryption function
    private String hillDecrypt(String ciphertext, int[][] keyMatrix) {
        StringBuilder plainText = new StringBuilder();
        int[][] inverseKeyMatrix = calculateInverse(keyMatrix);

        for (int i = 0; i < ciphertext.length(); i += keyMatrix.length) {
            int[] vector = new int[keyMatrix.length];
            for (int j = 0; j < keyMatrix.length; j++) {
                for (int k = 0; k < keyMatrix.length; k++) {
                    vector[j] += (ciphertext.charAt(i + k) - 'A') * inverseKeyMatrix[j][k];
                    vector[j] %= 26;
                    if (vector[j] < 0) {
                        vector[j] += 26;
                    }
                }
                plainText.append((char) ('A' + vector[j]));
            }
        }
        return plainText.toString();
    }

}