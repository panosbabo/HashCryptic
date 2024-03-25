package com.example.hashcryptic;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.widget.Toast;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileHelper {

    public static final int PICK_DIRECTORY_REQUEST_CODE = 123;

    public static void createTextFile(Activity activity, String fileName, String content) {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_TITLE, fileName);
        intent.putExtra("content", content); // Pass content as an extra
        activity.startActivityForResult(intent, PICK_DIRECTORY_REQUEST_CODE);
    }

    public static String saveTextFile(Activity activity, Intent data) {
        Uri uri = data.getData();
        String context = data.getStringExtra("content"); // Retrieve content from extra
        try {
            OutputStream outputStream = activity.getContentResolver().openOutputStream(uri);
            if (outputStream != null) {
                outputStream.write(context.getBytes());
                outputStream.close();
                Toast.makeText(activity, "File saved successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, "Failed to open output stream", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(activity, "Failed to save file", Toast.LENGTH_SHORT).show();
        }
        return uri.toString();
    }
}

