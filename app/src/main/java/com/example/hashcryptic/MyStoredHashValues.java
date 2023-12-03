package com.example.hashcryptic;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hashcryptic.db.Hash;
import com.example.hashcryptic.db.HashDatabase;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyStoredHashValues#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyStoredHashValues extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    // Variables for a recycler view adapter
    private StoredValuesAdapter hashListAdapter;
    private List<Hash> hashListItems;

    public MyStoredHashValues() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WishList.
     */
    // TODO: Rename and change types and number of parameters
    public static MyStoredHashValues newInstance(String param1, String param2) {
        MyStoredHashValues fragment = new MyStoredHashValues();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint({"MissingInflatedId", "CutPasteId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mystoredhashvalues, container, false);

        Button copy_btn = view.findViewById(R.id.copyencr_txt);

        // View Variables for the hash list
        RecyclerView hashListView;

        // Initializing Views to the related R.id's
        hashListView = (RecyclerView) view.findViewById(R.id.idHashList);

        // Initializing Recycler View for the Hash List
        RecyclerView recyclerView = view.findViewById(R.id.idHashList);
        // Setting layout to Linear
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Adding a divider line as decoration item
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        // Setting the adapter to WishListAdapter
        hashListAdapter = new StoredValuesAdapter(getContext());
        recyclerView.setAdapter(hashListAdapter);

        // Loading all items in the WishList
        loadHashList();

        // Creating a descent textview for the wish list page title
        TextView titleview = new TextView(getContext());
        titleview = view.findViewById(R.id.hashlist_titleview);
        titleview.setTypeface(Typeface.DEFAULT_BOLD);

        hashListView.setVisibility(View.VISIBLE);

        return view;
    }

    // Reference: This part of the following code is from an online Android example https://github.com/ravizworldz/AndroidRoomDB_Java
    // Function that allows to load all items from the hash list
    private void loadHashList() {
        HashDatabase db = HashDatabase.getDbInstance(this.getContext());
        hashListItems = db.hashDao().gethash();
        hashListAdapter.setHashList(hashListItems);
    }

    // Ordinary onActivityResult
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 100) {
            loadHashList();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    // Reference Complete
}