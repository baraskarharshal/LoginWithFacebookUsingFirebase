package com.apps.rdjsmartapps.loginwithfacebookusingfirebase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.io.IOException;
import java.net.URL;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth firebase;
    private Intent mainIntent;
    private Button logOut;
    TextView name;
    ProfilePictureView profilePictureView;

    private static final String TAG = "ProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        logOut = (Button) findViewById(R.id.log_out);
        name = (TextView) findViewById(R.id.name);
        profilePictureView = (ProfilePictureView) findViewById(R.id.friendProfilePicture);
        mainIntent = new Intent(getApplicationContext(), MainActivity.class);

        firebase = firebase.getInstance();

        if(firebase.getCurrentUser() != null) {
            FirebaseUser firebaseUser = firebase.getCurrentUser();
            Bundle inBundle = getIntent().getExtras();
            String id = inBundle.get("id").toString();

            String name1 = firebaseUser.getDisplayName();
            // Set profile values

            name.setText(name1);
            profilePictureView.setProfileId(id);
        }

        // If no user is logged in then go back to login activity

        if(firebase.getCurrentUser() == null && LoginManager.getInstance() == null){
            finish();
            startActivity(mainIntent);
        }

        logOut.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(view == logOut) {

                    // Log out
                    Toast.makeText(getApplicationContext(), "Signing out...", Toast.LENGTH_SHORT).show();

                    // Sign out for firebase user
                    if(firebase.getCurrentUser() != null) {
                        firebase.signOut();
                        LoginManager.getInstance().logOut();
                    }

                    finish();
                    startActivity(mainIntent);
                }
            }
        });


    }
}
