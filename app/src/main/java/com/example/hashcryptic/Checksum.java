package com.example.hashcryptic;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.hashcryptic.hashtypes.enc_MD5;
import com.example.hashcryptic.hashtypes.enc_SHA256;
//import com.example.hashcryptic.untils.checksum_class;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Checksum extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final int PICK_FILE_REQUEST = 1;
    private static final String[] paths = {"MD5", "SHA-1", "SHA-256", "SHA-512"};
    private String globchksum = null;
    private TextView enctv;
    private int pos;
    ClipboardManager cpb;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checksum);

        Button choose_file = findViewById(R.id.button_choose_file);
        Button copy_btn = findViewById(R.id.copyhash_value);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(Checksum.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // link the edittext and textview with its id
        enctv = findViewById(R.id.chcksm_msg_cntxt);

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
                    Toast.makeText(Checksum.this, "Checksum Copied", Toast.LENGTH_SHORT).show();
                }
            }
        });

        choose_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // algorithm and get the checksum code
                pickFile();
                enctv.setText(globchksum);
            }
        });
    }

    private void pickFile() {
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

                switch (pos) {
                    case 0:
                        // Calculate checksum for MD5
                        globchksum = calculateChecksum(selectedFileUri, "MD5");
                        break;
                    case 1:
                        // Calculate checksum for SHA-256
                        globchksum = calculateChecksum(selectedFileUri, "SHA-1");
                        break;
                    case 2:
                        // Calculate checksum for SHA-256
                        globchksum = calculateChecksum(selectedFileUri, "SHA-256");
                        break;
                    case 3:
                        // Calculate checksum for SHA-256
                        globchksum = calculateChecksum(selectedFileUri, "SHA-512");
                        break;
                }

                if (globchksum != null) {
                    enctv.setText(globchksum);
                    Toast.makeText(this, "Checksum: " + globchksum, Toast.LENGTH_SHORT).show();
                } else {
                    globchksum = "Failed Checksum";
                    enctv.setText(globchksum);
                    Toast.makeText(this, "Failed to calculate checksum", Toast.LENGTH_SHORT).show();
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

    public String calculateChecksum(Uri uri, String hashtype) {
        try {
            String checksumHex = "";
            InputStream inputStream = getContentResolver().openInputStream(uri);
            MessageDigest digest = MessageDigest.getInstance(hashtype);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }

            ((InputStream) inputStream).close();
            byte[] checksum = digest.digest();

            // Convert checksum bytes to hexadecimal representation
            StringBuilder sb = new StringBuilder();
            for (byte b : checksum) {
                sb.append(String.format("%02x", b));
            }
            checksumHex = sb.toString();

            return checksumHex;

            // Do something with the checksumHex
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}