/*
*    Copyright (C) 2017 Oleksandr Korneiko
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*/

package ua.pp.myprojects.zsudriver;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

import static ua.pp.myprojects.zsudriver.R.id.userId;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    private static final String TAG = "MainActivity";

    public static final String ANONYMOUS = "anonymous";
    public static final int RC_SIGN_IN = 1;


    private ProgressBar mProgressBar;
    private Button mJournalButton;
    private String mUsername;
    private TextView mUsernameView;
    private TextView mUserIdView;

    // Firebase instance variables
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mUsername = ANONYMOUS;
        // Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();


        // Initialize references to views
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mJournalButton = (Button) findViewById(R.id.btnJournal);
        mUserIdView = (TextView) findViewById(R.id.userName);
        mUsernameView = (TextView) findViewById(userId);

        // Initialize progress bar
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        mJournalButton.setOnClickListener(this);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    onSignedInInitialize(user.getDisplayName(), user.getUid());
                } else {
                    // User is signed out
                    onSignedOutCleanup();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(
                                            Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onSignedInInitialize(String username, String userId) {
        mUsername = username;
        mUsernameView.setText(username);
        mUserIdView.setText(userId);
        Toast toast = Toast.makeText(this, "Hello " + username, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void onSignedOutCleanup() {
        mUsername = ANONYMOUS;
        mUsernameView.setText("");
        mUserIdView.setText("");
    }



    @Override
    public void onClick(View view) {
        Intent intent;

        switch(view.getId()) {
            case R.id.btnJournal:
                intent = new Intent(this, JournalActivity.class);
                startActivity(intent);
                break;
        }
    }
}
