package com.example.hashcryptic;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.util.HashMap;
import java.util.Map;
import android.os.Bundle;
import android.widget.TextView;

public class QRCode_Gen extends AppCompatActivity {

    private TextView hashtv;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_gen);

        ImageView imageView = findViewById(R.id.qrcode_image);
        hashtv = findViewById(R.id.hash_txt_view);

        int width = 800;
        int height = 800;

        // Get the intent that started this activity
        Intent intent = getIntent();

        // Get the string data from the intent
        String hash = ((Intent) intent).getStringExtra("hashvalue");
        hashtv.setText(hash);

        try {
            Bitmap bitmap = generateQRCode(hash, width, height);
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        Button qr_code = findViewById(R.id.shareto_qrcode_item_btn);

        qr_code.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){

                // Passing hash value to new share to intent
//                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                shareIntent.setType("text/plain");
//                shareIntent.putExtra();
//                startActivity(Intent.createChooser(shareIntent, "Share to:"));

            }
        });

    }

    private Bitmap generateQRCode(String text, int width, int height) throws WriterException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }

        return bitmap;
    }
}