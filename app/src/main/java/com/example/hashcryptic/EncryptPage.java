package com.example.hashcryptic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EncryptPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EncryptPage extends Fragment {

    public EncryptPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EncryptDecryptFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EncryptPage newInstance(String param1, String param2) {
        EncryptPage fragment = new EncryptPage();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Button encryptButton;
        Button decryptButton;
        Button checksumButton;

        // Inflate the layout for this fragment
        View encryptView = inflater.inflate(R.layout.fragment_encrypt_decrypt, container, false);
        encryptButton = encryptView.findViewById(R.id.encryptBtn);
        decryptButton = encryptView.findViewById(R.id.decryptBtn);
        checksumButton = encryptView.findViewById(R.id.chksumBtn);

        // Button function listener for the Profile Editing page activity
        encryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creating an Intent for the next activity of the profile editing page
                Intent intent = new Intent(EncryptPage.super.getContext(), EncryptText.class);
                startActivity(intent);
            }
        });

        decryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creating an Intent for the next activity of the profile editing page
                Intent intent = new Intent(EncryptPage.super.getContext(), DecryptText.class);
                startActivity(intent);
            }
        });

        checksumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creating an Intent for the next activity of the profile editing page
                Intent intent = new Intent(EncryptPage.super.getContext(), Checksum.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return encryptView;
    }
}