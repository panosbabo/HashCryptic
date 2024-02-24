package com.example.hashcryptic;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class QRCode_Gen extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_gen);

        TextView hashtv;
        ImageView qrCode = findViewById(R.id.qrcode_image);
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
            qrCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        Button qr_code = findViewById(R.id.shareto_qrcode_item_btn);

        qr_code.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                // Passing ImageView to be handled for share to option
                shareImage(qrCode);
            }
        });

    }

    private void shareImage(ImageView qr_code) {

        BitmapDrawable bitmapDrawable = (BitmapDrawable) qr_code.getDrawable();
        if (bitmapDrawable == null) {
            Toast.makeText(this, "Image not found", Toast.LENGTH_SHORT).show();
            return;
        }

        // Creating Executor Service to handle image processing in a different thread than main
        ExecutorService service = Executors.newSingleThreadExecutor();

        service.execute(new Runnable() {
            @Override
            public void run() {
                // Creating temporary file for the image path
                File imagePath = new File(getExternalCacheDir(), "images");
                File newFile = new File(imagePath, "image.png");
                Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.example.hashcryptic.fileprovider", newFile);

                // Generating bitmap for the image
                Bitmap bitmap = bitmapDrawable.getBitmap();
                File cachePath = new File(getExternalCacheDir(), "images");
                cachePath.mkdir(); // Create the cache directory if not exists
                try {
                    // Overwrite existing file
                    FileOutputStream stream = new FileOutputStream(cachePath + "/image.png");
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Creating new intent for share option as an image
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/*");
                shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                startActivity(Intent.createChooser(shareIntent, "Share image to"));
            }
        });
    }

    private Bitmap generateQRCode(String text, int width, int height) throws WriterException {
        // HashMap creation for the QRCode image to be generated
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        // Using QRCodeWriter to create a barcode format for the given string hash value
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        // Bitmap creation of the given barcode format hash value
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        // Indented for loop to cycle through the entire size of the QRCode image
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                // Black & white pixel generation by the given Hashmap depended of the given string hash value
                bitmap.setPixel(i, j, bitMatrix.get(i, j) ? Color.BLACK : Color.WHITE);
            }
        }

        return bitmap;
    }
}