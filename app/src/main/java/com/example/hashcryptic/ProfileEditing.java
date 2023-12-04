package com.example.hashcryptic;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.hashcryptic.db.Profile;
import com.example.hashcryptic.db.ProfileDatabase;

// Activity that allows the user to insert and edit their personal details.
public class ProfileEditing extends AppCompatActivity {

    //Private variables and "update profile" button
    private Context context;
    private Bitmap myImage;
    private EditText mUsernameEditText;
    private EditText mEmailEditText;
    private EditText mAgeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile_editing_page);

        // Calling database from Profile
        ProfileDatabase db  = ProfileDatabase.getDbInstance(this.getApplicationContext());

        // Button, EditText and Image views are being allocated to relevant xml identifiers
        Button btn_send = findViewById(R.id.update_prof_btn);
        mUsernameEditText = findViewById(R.id.usernameinsert);
        mEmailEditText = findViewById(R.id.emailinsert);
        mAgeEditText = findViewById(R.id.ageinsert);

        // Sets autofill for more convenient profile update
        if(!db.profileDao().getprofile().isEmpty()) {
            mUsernameEditText.setText(db.profileDao().getprofile().get(0).personUsername);
            mEmailEditText.setText(db.profileDao().getprofile().get(0).personEmail);
            mAgeEditText.setText(db.profileDao().getprofile().get(0).personAge);
        }


        // Function listener when the user clicks on update profile
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Variable creation to allow data parsed from user's inserts
                String usernamE = mUsernameEditText.getText().toString();
                String emaiL = mEmailEditText.getText().toString().trim();
                String agE = mAgeEditText.getText().toString();

                // Constraint to force the user enter all required details
                if (usernamE.isEmpty() || emaiL.isEmpty() || agE.isEmpty()) {
                    Toast.makeText(ProfileEditing.this, "Please make sure all details are correct.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Constraint to enter a valid email address
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if (!emaiL.matches(emailPattern)) {
                    Toast.makeText(ProfileEditing.this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Instantiation of a Profile object
                Profile profile = new Profile();

                // If statement that checks if user has already enter profile details
                if (db.profileDao().getprofile().isEmpty()) {
                    try {
                        // Assigning user's insert to Profile object
                        profile.personUsername = usernamE;
                        profile.personEmail = emaiL;
                        profile.personAge = agE;

                        // Calling Dao Commands for Profile INSERT
                        db.profileDao().insertProfile(profile);

                        // Setting Result OK
                        setResult(RESULT_OK);

                        // Message displayed for successful profile creation
                        Toast.makeText(ProfileEditing.this, "Profile has been created", Toast.LENGTH_SHORT).show();

                        // Returning to previous activity as a refresh
                        finish();
                        Intent intent = new Intent(ProfileEditing.this, MainActivity.class);
                        startActivity(intent);

                    } catch (SQLiteConstraintException e) {
                        // Message displayed for catching error of profile creation
                        Toast.makeText(ProfileEditing.this, "Profile has not been created", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    try {
                        /* Indexing profile to the first instance of the Profile Objects
                         This way, the user updates the first instance every time they
                         change their profile details. */
                        profile = db.profileDao().getprofile().get(0);

                        // Assigning user's insert to Profile object
                        profile.personUsername = usernamE;
                        profile.personEmail = emaiL;
                        profile.personAge = agE;

                        // Calling Dao Commands for Profile UPDATE
                        db.profileDao().updateProfile(profile);

                        // Setting Result OK
                        setResult(RESULT_OK);

                        // Message displayed for successful profile update
                        Toast.makeText(ProfileEditing.this, "Profile has been updated", Toast.LENGTH_SHORT).show();

                        // Returning to previous activity as a refresh
                        finish();
                        Intent intent = new Intent(ProfileEditing.this, MainActivity.class);
                        startActivity(intent);

                    } catch (SQLiteConstraintException e) {
                        // Message displayed for catching error of profile update
                        Toast.makeText(ProfileEditing.this, "Profile has not been updated", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}