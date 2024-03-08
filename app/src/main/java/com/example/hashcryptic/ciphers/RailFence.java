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

public class RailFence extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rail_fence);

        Button encrRailfence, decrRailfence;
        EditText encryText, rowSize;
        TextView message;

        encrRailfence = findViewById(R.id.encrRailFence);
        decrRailfence = findViewById(R.id.decrRailFence);
        message = findViewById(R.id.msg_cntxt);

        // link the edittext and textview with its id
        encryText = findViewById(R.id.encrypt_RailFence_text);
        rowSize = findViewById(R.id.railRow_size);

        // onClick function of encrypt text button
        encrRailfence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (encryText.getText().toString().isEmpty() || rowSize.getText().toString().isEmpty()) {
                    Toast.makeText(RailFence.this, "Please enter text and row size to cipher", Toast.LENGTH_SHORT).show();
                } else {
                    // get the string from the textview and trim all spaces
                    String enryptTextToRailFence = railencrypt(encryText.getText().toString().trim(), Integer.parseInt(rowSize.getText().toString().trim()));
                    message.setText(enryptTextToRailFence.toUpperCase());
                }
            }
        });

        decrRailfence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Intent function to move to another activity
                // get text from edittext
                if (encryText.getText().toString().isEmpty() || rowSize.getText().toString().isEmpty()) {
                    Toast.makeText(RailFence.this, "Please enter cipher and row size to decrypt", Toast.LENGTH_SHORT).show();
                } else {
                    // get the string from the textview and trim all spaces
                    String enryptTextToCaesar = raildecrypt(encryText.getText().toString().trim(), Integer.parseInt(rowSize.getText().toString().trim()));
                    message.setText(enryptTextToCaesar.toUpperCase());
                }
            }
        });
    }


    // RailFence Encryption function
    private String railencrypt(String plainText, int rails) {
        plainText = plainText.replaceAll("\\s", "");
        char[][] matrix = new char[rails][plainText.length()];
        int row = 0;
        int dir = 1;

        // Fill the matrix
        for (int i = 0; i < plainText.length(); i++) {
            matrix[row][i] = plainText.charAt(i);
            if (row == 0)
                dir = 1;
            else if (row == rails - 1)
                dir = -1;
            row += dir;
        }

        // Construct the cipher text from the matrix
        StringBuilder cipherText = new StringBuilder();
        for (int i = 0; i < rails; i++) {
            for (int j = 0; j < plainText.length(); j++) {
                if (matrix[i][j] != 0)
                    cipherText.append(matrix[i][j]);
            }
        }
        return cipherText.toString();
    }

    // RailFence Decryption function
    private String raildecrypt(String cipherText, int rails) {
        cipherText = cipherText.replaceAll("\\s", "");
        char[][] matrix = new char[rails][cipherText.length()];
        int row = 0;
        int dir = 1;
        int index = 0;

        // Fill the matrix
        for (int i = 0; i < cipherText.length(); i++) {
            matrix[row][i] = '*';
            if (row == 0)
                dir = 1;
            else if (row == rails - 1)
                dir = -1;
            row += dir;
        }

        // Fill the matrix with cipher text characters
        for (int i = 0; i < rails; i++) {
            for (int j = 0; j < cipherText.length(); j++) {
                if (matrix[i][j] == '*' && index < cipherText.length()) {
                    matrix[i][j] = cipherText.charAt(index++);
                }
            }
        }

        // Reconstruct the plain text from the matrix
        StringBuilder plainText = new StringBuilder();
        row = 0;
        dir = 1;
        for (int i = 0; i < cipherText.length(); i++) {
            plainText.append(matrix[row][i]);
            if (row == 0)
                dir = 1;
            else if (row == rails - 1)
                dir = -1;
            row += dir;
        }
        return plainText.toString();
    }

}