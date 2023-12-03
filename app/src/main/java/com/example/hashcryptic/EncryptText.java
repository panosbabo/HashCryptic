package com.example.hashcryptic;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hashcryptic.db.Hash;
import com.example.hashcryptic.db.HashDatabase;
import com.example.hashcryptic.hashtypes.*;

public class EncryptText extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String[] paths = {"MD5", "SHA-1", "SHA-256", "SHA-512"};
    private String globhashvalu, globhashtxt, globtype;
    private EditText etenc;
    private TextView enctv;
    private int pos;
    ClipboardManager cpb;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt_text);

        // Calling Hash Value Database
        HashDatabase db  = HashDatabase.getDbInstance(this.getApplicationContext());

        Button encrpt_btn = findViewById(R.id.buttonEncrypt);
        Button copy_btn = findViewById(R.id.copyencr_txt);
        Button store_btn = findViewById(R.id.store_hash);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(EncryptText.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // link the edittext and textview with its id
        etenc = findViewById(R.id.encrpt_editText);
        enctv = findViewById(R.id.encr_msg_cntxt);

        // create a clipboard manager variable to copy text
        cpb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        // onClick function of copy text button
        copy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the string from the textview and trim all spaces
                String data = enctv.getText().toString().trim();

                // check if the textview is not empty
                if (!data.isEmpty()) {

                    // copy the text in the clip board
                    ClipData temp = ClipData.newPlainText("text", data);
                    cpb.setPrimaryClip(temp);

                    // display message that the text has been copied
                    Toast.makeText(EncryptText.this, "Hash Value Copied", Toast.LENGTH_SHORT).show();
                }
            }
        });

        encrpt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent function to move to another activity
                // get text from edittext
                if (etenc.getText().toString().isEmpty()) {
                    Toast.makeText(EncryptText.this, "Please enter text to encrypt", Toast.LENGTH_SHORT).show();
                }
                else {
                    String temp = etenc.getText().toString();

                    // pass the string to the encryption
                    // algorithm and get the encrypted code
                    switch (pos) {
                        case 0:
                            globtype = "MD5";
                            globhashvalu = enc_MD5.encryptMD5(temp);
                            globhashtxt = temp;
                            enctv.setText(globhashvalu);
                            break;
                        case 1:
                            globtype = "SHA-1";
                            globhashvalu = enc_SHA1.encryptSHA1(temp);
                            globhashtxt = temp;
                            enctv.setText(globhashvalu);
                            break;
                        case 2:
                            globtype = "SHA-256";
                            globhashvalu = enc_SHA256.encryptSHA256(temp);
                            globhashtxt = temp;
                            enctv.setText(globhashvalu);
                            break;
                        case 3:
                            globtype = "SHA-512";
                            globhashvalu = enc_SHA512.encryptSHA512(temp);
                            globhashtxt = temp;
                            enctv.setText(globhashvalu);
                            break;
                    }
                }
            }
        });

        store_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // check if the textview is not empty
                if (!enctv.getText().toString().isEmpty()) {

                    // Instantiation of a Hash object
                    Hash hash = new Hash();

                    try {
                        // Assigning user's hash type and value insert to Hash object
                        hash.hashType = globtype;
                        hash.hashTxt = globhashtxt;
                        hash.hashValue = globhashvalu;

                        // Calling Dao Commands for Hash INSERT
                        db.hashDao().insertHash(hash);

                        // Setting Result OK
                        setResult(RESULT_OK);

                        // Message displayed for successful hash storing
                        // display message that the text has been copied
                        Toast.makeText(EncryptText.this, "Hash Stored Successfully", Toast.LENGTH_SHORT).show();

                        // Returning to previous activity as a refresh
                        finish();
                        Intent intent = new Intent(EncryptText.this, MainActivity.class);
                        startActivity(intent);

                    } catch (SQLiteConstraintException e) {
                        // Message displayed for catching error of hash store creation
                        Toast.makeText(EncryptText.this, "Hash Failed to Store", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(EncryptText.this, "Please encrypt the text first", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                pos = 0;
                break;
            case 1:
                pos = 1;
                break;
            case 2:
                pos = 2;
                break;
            case 3:
                pos = 3;
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}