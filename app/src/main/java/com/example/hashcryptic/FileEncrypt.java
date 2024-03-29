package com.example.hashcryptic;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import android.os.Build;
import android.os.Environment;

import com.example.hashcryptic.db.ProfileDatabase;

public class FileEncrypt extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final int PICK_FILE_REQUEST = 1;
    private static final String ALGORITHM = "AES";
    private static final String[] paths = {"AES 128", "AES 192", "AES 256"};
    private static final String TRANSFORMATION = "AES";
    private static final int BUFFER_SIZE = 8192;
    private static final String TAG = "EncryptionActivity";
    private static int KEY_SIZE = 128;
    private static String USERNAME;
    private static SecretKey secretKey = null;
    private static Switch USERNAMESWITCH;

    private int pos;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filenecrypt);

        // Calling database from Profile
        ProfileDatabase db  = ProfileDatabase.getDbInstance(this.getApplicationContext());
        USERNAME = db.profileDao().getprofile().get(0).personUsername;

        Button choose_file = findViewById(R.id.button_choose_file);
        USERNAMESWITCH = findViewById(R.id.personalKeySwitch);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FileEncrypt.this,
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        choose_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Checking permission to be granted
                checkPermissionsAndPickFile();
//                pickfile();
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
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @SuppressLint("NewApi")
    private void checkPermissionsAndPickFile() {
        if (hasWriteExternalStoragePermission()) {
            pickfile();
        } else {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FILE_REQUEST);
        }
    }

    private void pickfile() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select file"), PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Handler handler = new Handler();

        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            String selectedFilePath = uri.getPath();
            try {
                switch (pos) {
                    case 0:
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    secretKey = generateRandomKey(128);
                                } catch (NoSuchAlgorithmException e) {
                                    throw new RuntimeException(e);
                                }
                                encryptFile(uri, selectedFilePath, secretKey);
                            }
                        };
                        handler.post(runnable);
                        break;
                    case 1:
                        try {
                            secretKey = generateRandomKey(192);
                        } catch (NoSuchAlgorithmException e) {
                            throw new RuntimeException(e);
                        }
                        encryptFile(uri, selectedFilePath, secretKey);
                        break;
                    case 2:
                        try {
                            secretKey = generateRandomKey(256);
                        } catch (NoSuchAlgorithmException e) {
                            throw new RuntimeException(e);
                        }
                        encryptFile(uri, selectedFilePath, secretKey);
                        break;
                }
            }catch(Exception e){
                e.printStackTrace();
                Log.e(TAG, "Failed to encrypt file: " + e.getMessage());
                Toast.makeText(this, "Failed to encrypt file.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static SecretKey generateRandomKey(int KEY_SIZE) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(KEY_SIZE);
        return keyGenerator.generateKey();
    }

    private void encryptFile(Uri uri, String outputFile, SecretKey secretKey) {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;

        try {
            ContentResolver resolver = getContentResolver();
            inputStream = resolver.openInputStream(uri);

            // Retrieve the display name of the file
            String displayName = getDisplayName(resolver, uri);

            File outputDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            // Get the directory path of the input file
//            String inputFilePath = uri.getPath();
//            String outputFileName = "encrypted_" + new File(inputFilePath).getName();
//            String outputDir = new File(inputFilePath).getParentFile().getAbsolutePath() + File.separator + outputFileName;

//            if (!outputDir.exists()) {
//                if (!outputDir.mkdirs()) {
//                    throw new IOException("Failed to create output directory");
//                }
//            }

            // Construct output file path
            File outputFileObject = new File(outputDir,"encrypted_" + displayName);

//            byte[] keyBytes = new byte[KEY_SIZE / 8];
//            SecureRandom secureRandom = new SecureRandom();
//            secureRandom.nextBytes(keyBytes);
//            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, ALGORITHM);
//
//            // Generate a random IV
//            byte[] ivBytes = new byte[16];
//            secureRandom.nextBytes(ivBytes);
//            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
//
//            // Initialize the cipher with encryption mode
//            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
//            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);
//
//            // Initialize encryption cipher
//            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);

            if (!USERNAMESWITCH.isChecked()) {

                Cipher cipher = Cipher.getInstance(TRANSFORMATION);
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);

                // Initialize output stream
                outputStream = new FileOutputStream(outputFileObject);

                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead;

                // Encrypt file
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    byte[] outputBytes = cipher.update(buffer, 0, bytesRead);
                    if (outputBytes != null) {
                        outputStream.write(outputBytes);
                    }
                }

                byte[] outputBytes = cipher.doFinal();
                if (outputBytes != null) {
                    outputStream.write(outputBytes);
                }

                Log.d(TAG, "File encrypted successfully. Output file path: " + outputFileObject.getAbsolutePath());
                Toast.makeText(FileEncrypt.this, "Successfully encrypted: " + outputFileObject.getName(), Toast.LENGTH_SHORT).show();
            }
            else if (USERNAMESWITCH.isChecked()) {
                Toast.makeText(FileEncrypt.this, "Locked", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Failed to encrypt file: " + e.getMessage());
            Toast.makeText(FileEncrypt.this, "Failed to encrypt file.", Toast.LENGTH_SHORT).show();
        } finally {
            // Close streams
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Helper method to retrieve the display name of a content URI
    private String getDisplayName(ContentResolver resolver, Uri uri) {
        String displayName = null;
        Cursor cursor = resolver.query(uri, null, null, null, null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                int displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                if (displayNameIndex != -1) {
                    displayName = cursor.getString(displayNameIndex);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return displayName;
    }

    // Add permission check if targeting API level 23 or higher
    private boolean hasWriteExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PICK_FILE_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                pickfile();
            } else {
                Toast.makeText(this, "Permission denied. Cannot proceed.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
