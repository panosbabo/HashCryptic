package com.example.hashcryptic;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hashcryptic.db.Hash;
import com.example.hashcryptic.db.HashDatabase;

import java.util.List;

public class StoredValuesAdapter extends RecyclerView.Adapter<HashLT> {

    List<Hash> hashesListItems;
    private Context context;

    public StoredValuesAdapter(Context context) {
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setHashList(List<Hash> hashesListItems) {
        this.hashesListItems = hashesListItems;
        notifyDataSetChanged();
    }

    // Adapter Initialized for the inflation of the hash list items
    @NonNull
    @Override
    public HashLT onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);

        return new HashLT(view).linkAdapter(this);
    }

    // A view holder for the adapter
    @Override
    public void onBindViewHolder(@NonNull HashLT holder, int position) {
        // Holder to display the item's details on screen
        holder.myhashID.setText(String.valueOf(this.hashesListItems.get(position).uid));
        holder.myhashType.setText(this.hashesListItems.get(position).hashType);
//        holder.myhashText.setText(this.hashesListItems.get(position).hashTxt);
        holder.myhashValue.setText(this.hashesListItems.get(position).hashValue);

    }

    // getItemCount to retrieve the size of hashesListItems
    @Override
    public int getItemCount() {
        return hashesListItems.size();
    }
}

// Recycler Viewer for the hash list
class HashLT extends RecyclerView.ViewHolder{

    // Variables to be used for the adapter
    TextView myhashID, myhashType, myhashValue;
    private StoredValuesAdapter adapter;
    private Context context;

    // TODO: Must fix clipboard on Recycler View
//    Context context = recyclerView.getContext();
//    ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);


    // Holder for the Texts and Image Views and Remove Button
    public HashLT (@NonNull View itemView) {
        super(itemView);

        Button copy_btn = itemView.findViewById(R.id.copyencr_txt);

        // TODO: Must fix clipboard on Recycler View
        // create a clipboard manager variable to copy text
//        cpb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

        // onClick function of copy text button
        copy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the string from the textview and trim all spaces
                String data = myhashValue.getText().toString().trim();

                // check if the textview is not empty
                if (!data.isEmpty()) {

                    // copy the text in the clip board
                    ClipData temp = ClipData.newPlainText("text", data);
                    // TODO: Must fix clipboard on Recycler View
//                    clipboardManager.setPrimaryClip(temp);

                    // display message that the text has been copied
                    Toast.makeText(itemView.getContext(), "Hash Value Copied", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Views initialized to related Resource id's
//        hashIDVw = itemView.findViewById(R.id.hashIDView);
        myhashID = itemView.findViewById(R.id.hashID);
        myhashType = itemView.findViewById(R.id.hashType);
//        myhashText = itemView.findViewById(R.id.hashText);
        myhashValue = itemView.findViewById(R.id.hashValue);

        // Creating an instance for the Remove item button
        Button removeItem = itemView.findViewById(R.id.remove_item_btn);

        // Function for the Remove Item button to remove each individual item from the list
        removeItem.setOnClickListener(v -> {
            // Calling function to run Dao command on DELETE
            removeItemList(getAdapterPosition());
            // Also removed from the adapter list
            adapter.hashesListItems.remove(getAdapterPosition());
            adapter.notifyItemRemoved(getAdapterPosition());

            Toast.makeText(v.getContext(), "Your hash has been deleted", Toast.LENGTH_SHORT).show();
        });
    }

    // Function to run Dao DELETE command for the specific item selected from the wishlist
    public void removeItemList(int position) {
        // Calling Database
        HashDatabase db = HashDatabase.getDbInstance(this.context);

        // Calling Database Access Object command for delete
        db.hashDao().delete(adapter.hashesListItems.get(position));
    }

    // Adapter initialization
    public HashLT linkAdapter(StoredValuesAdapter adapter){
        this.adapter = adapter;
        return this;
    }
}