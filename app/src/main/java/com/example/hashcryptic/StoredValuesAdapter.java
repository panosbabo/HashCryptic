package com.example.hashcryptic;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    // Holder for the Texts and Image Views and Remove Button
    public HashLT (@NonNull View itemView) {
        super(itemView);

        // Views initialized to related Resource id's
        myhashID = itemView.findViewById(R.id.hashID);
        myhashType = itemView.findViewById(R.id.hashType);
        myhashValue = itemView.findViewById(R.id.hashValue);

        Button copy_btn = itemView.findViewById(R.id.copyencr_txt);

        copy_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                clipboardcopyApadter(myhashValue.getText().toString());
                Toast.makeText(itemView.getContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

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

        // Sharing Button option
        Button share_to = itemView.findViewById(R.id.shareto_item_btn);

        share_to.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                // Passing hash value to clipboard
                clipboardcopyApadter(myhashValue.getText().toString());

                // Passing hash value to new share to intent
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, myhashValue.getText().toString());
                itemView.getContext().startActivity(Intent.createChooser(shareIntent, "Share to:"));

                // display message for Choose share to option
                Toast.makeText(itemView.getContext(), "Choose share to option", Toast.LENGTH_SHORT).show();
            }
        });

        // Button for QRCode
        Button qr_code = itemView.findViewById(R.id.qrcode_btn);

        qr_code.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){

                // Passing hash value to new share to intent
                Intent shareIntent = new Intent(itemView.getContext(), QRCode_Gen.class);
                shareIntent.setType("text/plain");
                shareIntent.putExtra("hashvalue", myhashValue.getText().toString());
                itemView.getContext().startActivity(shareIntent);
            }
        });
    }

    // Function to run Dao DELETE command for the specific item selected from the hash list
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

    // Function to be used for clipboard in the adapter
    public String clipboardcopyApadter(String dat) {
        ClipboardManager clipboardManager = (ClipboardManager) itemView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text", dat);
        clipboardManager.setPrimaryClip(clipData);

        return clipData.toString();
    }
}