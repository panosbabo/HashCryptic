package com.example.hashcryptic;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hashcryptic.compression.SecureCompression;
import com.example.hashcryptic.db.Hash;
import com.example.hashcryptic.db.HashDatabase;
import com.example.hashcryptic.hashencryption.*;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class EncryptText extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String[] paths = {"MD5", "SHA-1", "SHA-224", "SHA-256", "SHA-384", "SHA-512"};
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
        List<Hash> hashListItems = db.hashDao().gethash();

        Button encrpt_btn = findViewById(R.id.buttonEncrypt);
        Button copy_btn = findViewById(R.id.copyencr_txt);
        Button store_btn = findViewById(R.id.store_hash);
        Button share_btn = findViewById(R.id.share_hash);
        Switch compressionSw = findViewById(R.id.compressionSwitch);

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


        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etenc.getText().toString().isEmpty()) {
                    Toast.makeText(EncryptText.this, "Please enter text to encrypt", Toast.LENGTH_SHORT).show();
                }
                else {
                    // copy the text in the clip board
                    String data = enctv.getText().toString().trim();
                    ClipData temp = ClipData.newPlainText("text", data);
                    cpb.setPrimaryClip(temp);

                    // Passing hash value to new share to intent
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, data);
                    startActivity(Intent.createChooser(shareIntent, "Share to:"));

                    // display message for Choose share to option
                    Toast.makeText(EncryptText.this, "Choose share to option", Toast.LENGTH_SHORT).show();
                }
            }
        });



        // onClick function of copy text button
        copy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etenc.getText().toString().isEmpty()) {
                    Toast.makeText(EncryptText.this, "Please enter text to encrypt", Toast.LENGTH_SHORT).show();
                }
                else {
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

                    // pass the string to the encryption along with each encryption type
                    // algorithm and get the encrypted code
                    switch (pos) {
                        case 0:
                            globtype = "MD5";
                            try {
                                if (compressionSw.isChecked()){
                                    String tempByte = SecureCompression.compressb4encryption(temp);
                                    globhashvalu = encryptHash.encrypt2Hash(tempByte, globtype);
                                    globhashtxt = temp;
                                }
                                else {
                                    globhashvalu = encryptHash.encrypt2Hash(temp, globtype);
                                    globhashtxt = temp;
                                }
                            } catch (IOException | IllegalBlockSizeException | BadPaddingException |
                                     InvalidKeyException | NoSuchPaddingException |
                                     NoSuchAlgorithmException e) {
                                throw new RuntimeException(e);
                            }
                            enctv.setText(globhashvalu);
                            break;
                        case 1:
                            globtype = "SHA-1";
                            try {
                                if (compressionSw.isChecked()){
                                    String tempByte = SecureCompression.compressb4encryption(temp);
                                    globhashvalu = encryptHash.encrypt2Hash(tempByte, globtype);
                                    globhashtxt = temp;
                                }
                                else {
                                    globhashvalu = encryptHash.encrypt2Hash(temp, globtype);
                                    globhashtxt = temp;
                                }
                            } catch (IOException | IllegalBlockSizeException | BadPaddingException |
                                     InvalidKeyException | NoSuchPaddingException |
                                     NoSuchAlgorithmException e) {
                                throw new RuntimeException(e);
                            }
                            enctv.setText(globhashvalu);
                            break;
                        case 2:
                            globtype = "SHA-224";
                            try {
                                if (compressionSw.isChecked()){
                                    String tempByte = SecureCompression.compressb4encryption(temp);
                                    globhashvalu = encryptHash.encrypt2Hash(tempByte, globtype);
                                    globhashtxt = temp;
                                }
                                else {
                                    globhashvalu = encryptHash.encrypt2Hash(temp, globtype);
                                    globhashtxt = temp;
                                }
                            } catch (IOException | IllegalBlockSizeException | BadPaddingException |
                                     InvalidKeyException | NoSuchPaddingException |
                                     NoSuchAlgorithmException e) {
                                throw new RuntimeException(e);
                            }
                            enctv.setText(globhashvalu);
                            break;
                        case 3:
                            globtype = "SHA-256";
                            try {
                                if (compressionSw.isChecked()){
                                    String tempByte = SecureCompression.compressb4encryption(temp);
                                    globhashvalu = encryptHash.encrypt2Hash(tempByte, globtype);
                                    globhashtxt = temp;
                                }
                                else {
                                    globhashvalu = encryptHash.encrypt2Hash(temp, globtype);
                                    globhashtxt = temp;
                                }
                            } catch (IOException | IllegalBlockSizeException | BadPaddingException |
                                     InvalidKeyException | NoSuchPaddingException |
                                     NoSuchAlgorithmException e) {
                                throw new RuntimeException(e);
                            }
                            enctv.setText(globhashvalu);
                            break;
                        case 4:
                            globtype = "SHA-384";
                            try {
                                if (compressionSw.isChecked()){
                                    String tempByte = SecureCompression.compressb4encryption(temp);
                                    globhashvalu = encryptHash.encrypt2Hash(tempByte, globtype);
                                    globhashtxt = temp;
                                }
                                else {
                                    globhashvalu = encryptHash.encrypt2Hash(temp, globtype);
                                    globhashtxt = temp;
                                }
                            } catch (IOException | IllegalBlockSizeException | BadPaddingException |
                                     InvalidKeyException | NoSuchPaddingException |
                                     NoSuchAlgorithmException e) {
                                throw new RuntimeException(e);
                            }
                            enctv.setText(globhashvalu);
                            break;
                        case 5:
                            globtype = "SHA-512";
                            try {
                                if (compressionSw.isChecked()){
                                    String tempByte = SecureCompression.compressb4encryption(temp);
                                    globhashvalu = encryptHash.encrypt2Hash(tempByte, globtype);
                                    globhashtxt = temp;
                                }
                                else {
                                    globhashvalu = encryptHash.encrypt2Hash(temp, globtype);
                                    globhashtxt = temp;
                                }
                            } catch (IOException | IllegalBlockSizeException | BadPaddingException |
                                     InvalidKeyException | NoSuchPaddingException |
                                     NoSuchAlgorithmException e) {
                                throw new RuntimeException(e);
                            }
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
                        if (compressionSw.isChecked()) {
                            hash.hashType = globtype + " + Compression";
                            hash.hashTxt = globhashtxt;
                            hash.hashValue = globhashvalu;
                        }
                        else {
                            hash.hashType = globtype;
                            hash.hashTxt = globhashtxt;
                            hash.hashValue = globhashvalu;
                        }

                        // Creating checkpoint for Hash store progression
                        boolean duplicate = false;

                        // Loop that iterates through the entire hash database
                        for (int i = 0; hashListItems.size() > i; i++) {

                            // Checks for any duplicates
                            if (Objects.equals(hashListItems.get(i).hashValue, hash.hashValue))
                            {
                                duplicate = true;
                                Toast.makeText(EncryptText.this, "Hash already been stored", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }

                        // If there are no duplicates
                        if (!duplicate) {
                            // Calling Dao Commands for Hash INSERT
                            db.hashDao().insertHash(hash);

                            // Message displayed for successful hash storing
                            // display message that the text has been copied
                            Toast.makeText(EncryptText.this, "Hash Stored Successfully", Toast.LENGTH_SHORT).show();

                            // Setting Result OK
                            setResult(RESULT_OK);

                            // Returning to previous activity as a refresh
                            finish();
                            Intent intent = new Intent(EncryptText.this, MainActivity.class);
                            startActivity(intent);
                        }

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
            case 4:
                pos = 4;
                break;
            case 5:
                pos = 5;
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