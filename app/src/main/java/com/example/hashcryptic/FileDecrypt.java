package com.example.hashcryptic;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import com.example.hashcryptic.db.ProfileDatabase;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class FileDecrypt extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final int PICK_FILE_REQUEST = 1;
    private static final String ALGORITHM = "AES";
    private static final String[] paths = {"AES 128", "AES 192", "AES 256"};
    private static final String TRANSFORMATION = "AES";
    private static final int BUFFER_SIZE = 8192;
    private static final String TAG = "EncryptionActivity";
    private static String USERNAME, EMAIL, AGE, mySecretKey;
    private static SecretKey secretKey = null;
    private static Switch PROFILESWITCH;
    private EditText decrKey;

    private int pos;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filedecrypt);

        // Calling database from Profile
        ProfileDatabase db  = ProfileDatabase.getDbInstance(this.getApplicationContext());
        if (!db.profileDao().getprofile().isEmpty()) {
            USERNAME = db.profileDao().getprofile().get(0).personUsername;
            EMAIL = db.profileDao().getprofile().get(0).personEmail;
            AGE = db.profileDao().getprofile().get(0).personAge;
        }

        PROFILESWITCH = findViewById(R.id.personalKeySwitch);
        decrKey = findViewById(R.id.decrypt_filekey);
        Button choose_file = findViewById(R.id.button_choose_file);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FileDecrypt.this,
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        choose_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PROFILESWITCH.isChecked()) {
                    if (!decrKey.getText().toString().isEmpty()) {
                        Toast.makeText(FileDecrypt.this, "Cannot use both Profile key and secret key", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (db.profileDao().getprofile().isEmpty()) {
                        Toast.makeText(FileDecrypt.this, "Profile details are empty\nProfile page must be updated", Toast.LENGTH_LONG).show();
                    }
                    else if (!db.profileDao().getprofile().isEmpty()) {
                        // Checking permission to be granted
                        checkPermissionsAndPickFile();
                    }
                }
                if (!PROFILESWITCH.isChecked() && decrKey.getText().toString().isEmpty()) {
                    Toast.makeText(FileDecrypt.this, "Must choose a key to\nbe used for Decryption", Toast.LENGTH_SHORT).show();
                }
                else if (!decrKey.getText().toString().isEmpty()){
                    // Checking permission to be granted
                    checkPermissionsAndPickFile();
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

        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
//            String selectedFilePath = uri.getPath();
            try {
                // Checking if user decides whether to encrypt using username as key or randomly generated 128, 192 or 256 AES
                switch (pos) {
                    case 0:
                        decryptFile(uri, 128);
                        break;
                    case 1:
                        decryptFile(uri, 192);
                        break;
                    case 2:
                        decryptFile(uri, 256);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "Failed to decrypt file: " + e.getMessage());
                Toast.makeText(this, "Failed to decrypt file.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static SecretKey generateRandomKey(int keySize) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(keySize);
        return keyGenerator.generateKey();
    }

    public static SecretKey generateAESKey(String password, int keySize) {
        try {
            byte[] salt = new byte[16];

            // Personal number of iterations for extra security -- 1776 doesn't sound familiar?
            int iterations = 1776;

            KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, iterations, keySize);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] keyBytes = keyFactory.generateSecret(keySpec).getEncoded();

            return new SecretKeySpec(keyBytes, "AES");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void decryptFile(Uri uri, int keySize) {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;

        try {
            ContentResolver resolver = getContentResolver();
            inputStream = resolver.openInputStream(uri);

            // Retrieve the display name of the file
            String displayName = getDisplayName(resolver, uri);
            String wordToMatch = "encrypted_";

            // Check for encrypted file rename
            String decryptedName = displayName;
            if (displayName.startsWith(wordToMatch)) {
                // Renaming decrypted file properly
                decryptedName = displayName.substring(wordToMatch.length());
            }

            // Output file initialization
            File outputDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

            // Construct output file path
            File outputFileObject = new File(outputDir, "decrypted_" + decryptedName);

            // Cipher instantiation
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);

            if (PROFILESWITCH.isChecked()) {
                // Initializing key from Profile details with chosen key size
                mySecretKey = USERNAME + EMAIL + AGE;
            }
            else {
                // Initializing key from input text with chosen key size
                mySecretKey = decrKey.getText().toString();
            }
            SecretKey myKey = generateAESKey(mySecretKey, keySize);
            // Cipher initialization for personal key
            cipher.init(Cipher.DECRYPT_MODE, myKey);

            // Initialize output stream
            outputStream = new FileOutputStream(outputFileObject);

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            // File Decryption
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

            Log.d(TAG, "File decrypted successfully. Output file path: " + outputFileObject.getAbsolutePath());
            Toast.makeText(FileDecrypt.this, "Successfully decrypted:\n" + outputFileObject.getName(), Toast.LENGTH_LONG).show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(FileDecrypt.this, "File saved in Downloads", Toast.LENGTH_SHORT).show();
                }
            }, 2000);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Failed to decrypt file: " + e.getMessage());
            Toast.makeText(FileDecrypt.this, "Failed to decrypt file", Toast.LENGTH_SHORT).show();
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
