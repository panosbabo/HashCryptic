package com.example.hashcryptic;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.hashcryptic.db.HashDatabase;

public class DecryptText extends AppCompatActivity {

    private Button decrpt_btn;
    private Button copy_btn;
    private EditText etdec;
    private TextView dectv;
    private boolean found;
    ClipboardManager cpb;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrypt_text);

        // Calling Hashdatabase for decryption
        HashDatabase db  = HashDatabase.getDbInstance(this.getApplicationContext());

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
                if (dectv.getText().toString().isEmpty()) {
                    Toast.makeText(DecryptText.this, "Please enter hash to decrypt", Toast.LENGTH_SHORT).show();
                }
                else {
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
            }
        });

        decrpt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Intent function to move to another activity
                // get text from edittext
                if (etdec.getText().toString().isEmpty()) {
                    Toast.makeText(DecryptText.this, "Please enter hash to decrypt", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    found = false;
                    // Receiving Plaintext from database
                    for (int i = 0; i < db.hashDao().gethash().size(); i++){
                        if (etdec.getText().toString().matches(db.hashDao().gethash().get(i).hashValue)) {
                            String data = db.hashDao().gethash().get(i).hashTxt;
                            dectv.setText(data);
//                            Toast.makeText(DecryptText.this, "Plaintext found!", Toast.LENGTH_SHORT).show();
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        dectv.setText("");
                        Toast.makeText(DecryptText.this, "Plaintext NOT found!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}