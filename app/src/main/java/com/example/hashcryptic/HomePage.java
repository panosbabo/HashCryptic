package com.example.hashcryptic;

import static com.example.hashcryptic.db.HashDatabase.getDbInstance;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.example.hashcryptic.db.HashDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomePage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home_page.
     */
    // TODO: Rename and change types and number of parameters
    public static HomePage newInstance(String param1, String param2) {
        HomePage fragment = new HomePage();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        Button myhash_values = view.findViewById(R.id.hashvlsBtn);

        HashDatabase db  = getDbInstance(this.getContext());

        myhash_values.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!db.hashDao().gethash().isEmpty()) {
                    // Fragment transaction to bring my stored hash values on screen
                    MyStoredHashValues myHashValues = new MyStoredHashValues();
                    // New fragment on stack
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, myHashValues)
                            .commit();
                }
                else {
                    // Message displayed for catching error of profile update
                    Toast.makeText(getContext(), "You have No Hash Values stored", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

}