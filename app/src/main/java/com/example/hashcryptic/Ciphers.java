package com.example.hashcryptic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hashcryptic.ciphers.Caesar;
import com.example.hashcryptic.ciphers.HillCipher;
import com.example.hashcryptic.ciphers.RailFence;
import com.example.hashcryptic.ciphers.Vigenere;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EncryptPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Ciphers extends Fragment {

    public Ciphers() {
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
    public static Ciphers newInstance(String param1, String param2) {
        Ciphers fragment = new Ciphers();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Button caesarCiph;
        Button vigenereCiph;
        Button railFenceCiph;
        Button HillCiph;

        // Inflate the layout for this fragment
        View cipherList = inflater.inflate(R.layout.fragment_ciphers, container, false);
        caesarCiph = cipherList.findViewById(R.id.caesarCiph_btn);
        vigenereCiph = cipherList.findViewById(R.id.vigenereCiph_btn);
        railFenceCiph = cipherList.findViewById(R.id.railFenceCiph_btn);
        HillCiph = cipherList.findViewById(R.id.hillCiph_btn);

        // Button function listener for the Profile Editing page activity
        caesarCiph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creating an Intent for the next activity of the profile editing page
                Intent intent = new Intent(Ciphers.super.getContext(), Caesar.class);
                startActivity(intent);
            }
        });

        vigenereCiph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creating an Intent for the next activity of the profile editing page
                Intent intent = new Intent(Ciphers.super.getContext(), Vigenere.class);
                startActivity(intent);
            }
        });

        railFenceCiph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creating an Intent for the next activity of the profile editing page
                Intent intent = new Intent(Ciphers.super.getContext(), RailFence.class);
                startActivity(intent);
            }
        });

        HillCiph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creating an Intent for the next activity of the profile editing page
                Intent intent = new Intent(Ciphers.super.getContext(), HillCipher.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return cipherList;
    }
}