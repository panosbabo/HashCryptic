package com.example.hashcryptic;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hashcryptic.hashtypes.dec_Hash;
import com.example.hashcryptic.hashtypes.enc_MD5;
import com.example.hashcryptic.hashtypes.enc_SHA1;
import com.example.hashcryptic.hashtypes.enc_SHA256;
import com.example.hashcryptic.hashtypes.enc_SHA512;

public class DecryptText extends AppCompatActivity {

    private Button decrpt_btn;
    private Button copy_btn;
    private EditText etdec;
    private TextView dectv;
    ClipboardManager cpb;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrypt_text);

        decrpt_btn = findViewById(R.id.buttonDecrypt);
        copy_btn = findViewById(R.id.copydecr_txt);

        // link the edittext and textview with its id
        etdec = findViewById(R.id.decrypt_editText);
        dectv = findViewById(R.id.decr_msg_cntxt);

        // create a clipboard manager variable to copy text
        cpb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        // onClick function of copy text button
        copy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the string from the textview and trim all spaces
                String data = dectv.getText().toString().trim();

                // check if the textview is not empty
                if (!data.isEmpty()) {

                    // copy the text in the clip board
                    ClipData temp = ClipData.newPlainText("text", data);
                    cpb.setPrimaryClip(temp);

                    // display message that the text has been copied
                    Toast.makeText(DecryptText.this, "Text Copied", Toast.LENGTH_SHORT).show();
                }
            }
        });

        decrpt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent function to move to another activity
                // get text from edittext
                if (etdec.getText().toString().isEmpty()) {
                    Toast.makeText(DecryptText.this, "Please enter text to decrypt.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    String temp = etdec.getText().toString();
                    String rv0 = dec_Hash.decryptHash(temp);
                    dectv.setText(rv0);
                }
            }
        });
    }
}