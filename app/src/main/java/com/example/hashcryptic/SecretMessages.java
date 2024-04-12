package com.example.hashcryptic;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.hashcryptic.db.ProfileDatabase;
import com.example.hashcryptic.rsa.RSAEncryption;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class SecretMessages extends AppCompatActivity {
    private EditText etenc;
    private TextView enctv;
    private String PUBLIC_KEY, PRIVATE_KEY;
    private PrivateKey privateKey;
    private PublicKey publicKey = null;
    private byte[] encryptedData = null;
    ClipboardManager cpb;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret_messages);

        ProfileDatabase db  = ProfileDatabase.getDbInstance(this.getApplicationContext());
        if (!db.profileDao().getprofile().isEmpty()) {
            PRIVATE_KEY = db.profileDao().getprofile().get(0).personUsername;

        }
        else {
            PRIVATE_KEY = "empty";
        }

        Button encrpt_btn = findViewById(R.id.buttonEncrypt);
        Button copy_btn = findViewById(R.id.copyencr_txt);
        Button share_btn = findViewById(R.id.share_hash);
        Button decry_btn = findViewById(R.id.buttonDecrypt);
        Button qr_code = findViewById(R.id.qrcode_rsa_btn);
        EditText publicKeyEdittext = findViewById(R.id.publickey_editText);
        TextView privateKeytitle = findViewById(R.id.privatekey_msg_title);
        TextView usernameKey = findViewById(R.id.username_msg_cntxt);

        // link the edittext and textview with its id
        etenc = findViewById(R.id.encrpt_editText);
        etenc.setSingleLine(true);
        enctv = findViewById(R.id.encr_msg_cntxt);
        publicKeyEdittext.setSingleLine(true);
        usernameKey.setText(PRIVATE_KEY);

        // create a clipboard manager variable to copy text
        cpb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etenc.getText().toString().isEmpty()) {
                    Toast.makeText(SecretMessages.this, "Please enter text to encrypt", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(SecretMessages.this, "Choose share to option", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // onClick function of copy text button
        copy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etenc.getText().toString().isEmpty()) {
                    Toast.makeText(SecretMessages.this, "Please enter text to encrypt", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(SecretMessages.this, "Result Copied", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        encrpt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent function to move to another activity
                // get text from edittext
                if (etenc.getText().toString().isEmpty() || publicKeyEdittext.getText().toString().isEmpty()) {
                    Toast.makeText(SecretMessages.this, "Please enter text and\n public key to encrypt", Toast.LENGTH_SHORT).show();
                    return;
                }
                String message = etenc.getText().toString();
                String lastTwoChars = message.substring(message.length() - 2);
                if (lastTwoChars.equals("==") || (message.length() > 150)) {
                    Toast.makeText(SecretMessages.this, "Please enter normal text to encrypt", Toast.LENGTH_SHORT).show();
                }
                else {
                    PUBLIC_KEY = publicKeyEdittext.getText().toString();
                    // Encrypting a Message with private key and then public key
                    try {

                        // Generate RSA key pair from the words
                        KeyPair keyPair = RSAEncryption.generateKeyPair(PUBLIC_KEY, PRIVATE_KEY);
//                        KeyPair keyPair = RSAEncryption.generateStringPair(PUBLIC_KEY, PRIVATE_KEY);

                        // Convert public and private keys to PEM format strings
                        String pemPublicKey = RSAEncryption.convertToPEM(keyPair.getPublic(), "PUBLIC KEY");
                        String pemPrivateKey = RSAEncryption.convertToPEM(keyPair.getPrivate(), "PRIVATE KEY");

                        // Parse PEM-encoded public and private keys
                        publicKey = RSAEncryption.parsePEMPublicKey(pemPublicKey);
                        privateKey = RSAEncryption.parsePEMPrivateKey(pemPrivateKey);

//                        publicKey = keyPair.getPublic();
//                        privateKey = keyPair.getPrivate();

                        // Encrypt using public key
                        encryptedData = RSAEncryption.encrypt(message, publicKey);
                        String byteToText = Base64.encodeToString(encryptedData, Base64.DEFAULT);
                        enctv.setText(byteToText);
//                        Toast.makeText(SecretMessages.this, "publicKey: " + publicKey.toString(), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        decry_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent function to move to another activity
                // get text from edittext
                if (etenc.getText().toString().isEmpty()) {
                    Toast.makeText(SecretMessages.this, "Please enter text to decrypt", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (publicKey == null) {
                    Toast.makeText(SecretMessages.this, "Please encrypt first a text message", Toast.LENGTH_SHORT).show();
                }
                else if (!etenc.getText().toString().matches(".*==$")){
                    String message = etenc.getText().toString();
                    try {
                        byte[] byteToDecrypt = Base64.decode(message, Base64.DEFAULT);
                        String decryptedText = RSAEncryption.decrypt(byteToDecrypt, privateKey);
                        enctv.setText(decryptedText);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    Toast.makeText(SecretMessages.this, "Failed to Decrypt", Toast.LENGTH_SHORT).show();
                }
            }
        });

        qr_code.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){

                // Passing rsa message to new share to intent
                Intent intent = new Intent(getApplicationContext(), QRCode_Gen.class);
                intent.putExtra("hashvalue", enctv.getText().toString());
                startActivity(intent);
            }
        });
    }
}