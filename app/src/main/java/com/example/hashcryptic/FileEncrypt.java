package com.example.hashcryptic;

import static com.example.hashcryptic.hashencryption.fileEncryption.decrypt;
import static com.example.hashcryptic.hashencryption.fileEncryption.encrypt;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.hashcryptic.hashencryption.fileEncryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class FileEncrypt extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int PICK_FILE_REQUEST_CODE = 1;
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    private static final int PICK_FILE_REQUEST = 1;
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
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, PICK_FILE_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Handler handler = new Handler();
        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            String filePath = getFilePathFromUri(uri);
            File selectedFile = new File(filePath);
            Toast.makeText(FileEncrypt.this, "The selected file is: " + selectedFile, Toast.LENGTH_LONG).show();
                switch (pos) {
                    case 0:
                        // Code to be executed
                        try {
                            String key = "ThisIsASecretKey"; // Change this to your own secret key
                            Toast.makeText(FileEncrypt.this, "The selected file is: " + selectedFile, Toast.LENGTH_LONG).show();
                            // Encrypt the selected file
                            File encryptedFile = new File(getExternalFilesDir(null), filePath + "-encryptedText.txt");
                            Toast.makeText(FileEncrypt.this, "The output file is: " + encryptedFile, Toast.LENGTH_LONG).show();
                            encrypt(key, selectedFile, encryptedFile);
                            Toast.makeText(FileEncrypt.this, "File encrypted successfully.", Toast.LENGTH_SHORT).show();

                            // Decrypt the encrypted file
//                            File decryptedFile = new File(getExternalFilesDir(null), "decrypted.txt");
//                            decrypt(key, encryptedFile, decryptedFile);
//                            Toast.makeText(FileEncrypt.this, "File decrypted successfully.", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("Error while encrypting the file");
                        }
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
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

    private String getFilePathFromUri(Uri uri) {
        String filePath = "";
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            try (Cursor cursor = getContentResolver().query(uri, projection, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow("_data");
                    filePath = cursor.getString(columnIndex);
                }
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            filePath = uri.getPath();
        }
        return filePath;
    }

    public void encrypt(String key, File inputFile, File outputFile) throws Exception {
        doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
    }

    public void decrypt(String key, File inputFile, File outputFile) throws Exception {
        doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
    }

    private void doCrypto(int cipherMode, String key, File inputFile, File outputFile) throws Exception {
        Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(cipherMode, secretKey);

        try (FileInputStream inputStream = new FileInputStream(inputFile);
             FileOutputStream outputStream = new FileOutputStream(outputFile);
             CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                cipherOutputStream.write(buffer, 0, bytesRead);
            }
        }
    }
}