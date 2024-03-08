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
                if (encryText.getText().toString().isEmpty() || key.getText().toString().isEmpty()) {
                    Toast.makeText(HillCipher.this, "Please enter text and key to cipher", Toast.LENGTH_SHORT).show();
                    return;
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
                            keyMatrix[i][j] = (int) key.getText().toString().trim().charAt(keyIndex) % 65;
                            keyIndex++;
                        }
                    }
                    String encryptedText = hillEncrypt(encryText.getText().toString().trim(), keyMatrix);
                    message.setText(encryptedText.toUpperCase());
                }
            }
        });

        decrHill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Intent function to move to another activity
                // get text from edittext
                if (encryText.getText().toString().isEmpty() || key.getText().toString().isEmpty()) {
                    Toast.makeText(HillCipher.this, "Please enter cipher and key to decrypt", Toast.LENGTH_SHORT).show();
                    return;
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
                            keyMatrix[i][j] = (int) key.getText().toString().trim().charAt(keyIndex) % 65;
                            keyIndex++;
                        }
                    }
                    String encryptedText = hillDecrypt(encryText.getText().toString().trim(), keyMatrix);
                    message.setText(encryptedText.toUpperCase());
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

    // Hill Cipher Decryption function
    private String hillDecrypt(String ciphertext, int[][] keyMatrix) {
//        int n = keyMatrix.length;
//        int blockSize = n;
//        StringBuilder plaintext = new StringBuilder();
//        int[][] inverseKeyMatrix = calculateInverse(keyMatrix);
//
//        for (int i = 0; i < ciphertext.length(); i += blockSize) {
//            String block = ciphertext.substring(i, i + blockSize);
//            for (int j = 0; j < blockSize; j++) {
//                int sum = 0;
//                for (int k = 0; k < blockSize; k++) {
//                    sum += (inverseKeyMatrix[j][k] * (block.charAt(k) - 'A'));
//                }
//                char decryptedChar = (char) ((sum % 26 + 26) % 26 + 'A');
//                plaintext.append(decryptedChar);
//            }
//        }
//        return plaintext.toString();
        return ciphertext; // TODO: to be deleted!
    }

}