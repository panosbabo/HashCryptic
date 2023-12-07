package com.example.hashcryptic;

import static com.example.hashcryptic.hashencryption.fileEncryption.encryptFile;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.hashcryptic.hashencryption.fileEncryption;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.crypto.NoSuchPaddingException;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

public class FileEncrypt extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final int PICK_FILE_REQUEST = 1;
    private static final String[] paths = {"AES 128", "AES 192", "AES 256"};
    private int pos;
    private EditText keyText_view;
    ClipboardManager cpb;

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
        if (keyText_view.getText().toString().isEmpty()) {
            Toast.makeText(this, "Must enter a key first", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*"); // Select all file types
        startActivityForResult(intent, PICK_FILE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                Uri selectedFileUri = data.getData();
                assert selectedFileUri != null;
                String filePath = selectedFileUri.getPath(); // Get the file path from URI
                String password = keyText_view.getText().toString();

                switch (pos) {
                    case 0:
                        try {
                            fileEncryption.encryptFile(selectedFileUri.toString(), password, 128);
                            System.out.println("File encrypted successfully");
                            Toast.makeText(this, "File Encrypted Successfully", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("Error while encrypting the file");
                            Toast.makeText(this, "Failed to encrypt file", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1:
                        try {
                            fileEncryption.encryptFile(selectedFileUri.toString(), password, 192);
                            System.out.println("File encrypted successfully");
                            Toast.makeText(this, "File Encrypted Successfully", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("Error while encrypting the file");
                            Toast.makeText(this, "Failed to encrypt file", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:
                        try {
                            fileEncryption.encryptFile(selectedFileUri.toString(), password, 256);
                            System.out.println("File encrypted successfully");
                            Toast.makeText(this, "File Encrypted Successfully", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("Error while encrypting the file");
                            Toast.makeText(this, "Failed to encrypt file", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
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
}