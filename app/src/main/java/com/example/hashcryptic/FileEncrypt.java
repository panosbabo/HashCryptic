package com.example.hashcryptic;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class FileEncrypt extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final int PICK_FILE_REQUEST = 1;
    private String DESTINATION;
    private static final String ALGORITHM = "AES";
    private static final String[] paths = {"AES 128", "AES 192", "AES 256"};
    private int pos;
    private EditText keyText_view;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filenecrypt);

        Button choose_file = findViewById(R.id.button_choose_file);
        keyText_view = findViewById(R.id.key_editText);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(FileEncrypt.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        choose_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickFile();
            }
        });
    }

    private void pickFile() {
//        if (keyText_view.getText().toString().isEmpty()) {
//            Toast.makeText(this, "Must enter a key first", Toast.LENGTH_SHORT).show();
//            return;
//        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*"); // Select all file types
        startActivityForResult(intent, PICK_FILE_REQUEST);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedFileUri = data.getData();
        assert selectedFileUri != null;
        String filePath = selectedFileUri.getPath(); // Get the file path from URI
        File selectedFile = new File(selectedFileUri.toString());
        Handler handler = new Handler();
        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK) {
            String password = keyText_view.getText().toString();
//                Toast.makeText(this, "Your key is: \""+ password +"\"" , Toast.LENGTH_SHORT).show();
            switch (pos) {
                case 0:
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            // Code to be executed
                            try {
                                String key = "8Vl4e7JLLuUO3It+01s89w==";
//                                    Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
                                String fileName = "encrypted.txt";
                                InputStream inputStream = getContentResolver().openInputStream(selectedFileUri);
//                                    OutputStream outputStream = getContentResolver().openOutputStream(selectedFileUri);
                                assert inputStream != null;
                                FileEncrypter.createTextFile(FileEncrypt.this, fileName, inputStream);

                                File f_inputStream = new File(inputStream.toString());
                                File f_outputStream = new File(fileName.toString());


//                                    fileEncryption.encrypt(key, new File(inputStream.toString()), new File(outputStream.toString()));
//                                    File encryptedFile = new File(selectedFileUri.toString());
//                                    Toast.makeText(FileEncrypt.this, "File is: " + selectedFileUri.getPath(), Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(FileEncrypt.this, "The output file is: " + encryptedFile, Toast.LENGTH_LONG).show();

//                                    encrypt(key, selectedFile, new File(selectedFileUri.toString()));
//                                    SecretKey my_key = fileEncryption.generateSecretKey(128);
//                                    byte[] readData = fileEncryption.readFile(filePath);
//                                    byte[] encryptedData = fileEncryption.encrypt(my_key, readData);
//                                    saveFile(encryptedData, filePath + ".encrypted");
//                                    System.out.println("File encrypted successfully");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // UI-related code
//                                            Toast.makeText(FileEncrypt.this, "File is: " + selectedFileUri.getPath(), Toast.LENGTH_SHORT).show();
//                                            Toast.makeText(FileEncrypt.this, "File Encrypted Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.println("Error while encrypting the file");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // UI-related code
//                                            Toast.makeText(FileEncrypt.this, "Failed to encrypt file", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(FileEncrypt.this, "Exception: " + e, Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    };
                    handler.post(runnable);
                    break;
                case 1:
                    break;
                case 2:
                    break;
            }
        }
        if (requestCode == FileEncrypter.PICK_DIRECTORY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            FileEncrypter.saveTextFile(FileEncrypt.this, data);
//            FileEncrypter.encryptAndWrite(outputStream, inputStream);
//            Toast.makeText(FileEncrypt.this, "File is: " + DESTINATION + " ! ! !", Toast.LENGTH_SHORT).show();
        }

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
