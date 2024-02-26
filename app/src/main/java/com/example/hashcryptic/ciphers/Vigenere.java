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
        message = findViewById(R.id.msg_cntxt);

        // link the edittext and textview with its id
        encryText = findViewById(R.id.encrypt_Vigenere_text);
        keySize = findViewById(R.id.vigenereKey_size);

        // onClick function of encrypt text button
        encrCaesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (encryText.getText().toString().isEmpty() || keySize.getText().toString().isEmpty()) {
                    Toast.makeText(Vigenere.this, "Please enter text and key to cipher", Toast.LENGTH_SHORT).show();
                }
                else {
                    // get the string from the textview and trim all spaces

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
                }
                else {
                    // get the string from the textview and trim all spaces

                }
            }
        });
    }
}