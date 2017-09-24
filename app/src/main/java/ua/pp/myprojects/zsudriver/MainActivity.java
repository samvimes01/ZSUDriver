/*
 * Copyright (c) 2017. Oleksandr Korneiko
 * This file is subject to the terms and conditions defined in
 * file "LICENSE", which is part of this source code package
 *
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

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

import static ua.pp.myprojects.zsudriver.R.id.milUnit;
import static ua.pp.myprojects.zsudriver.R.id.role;
import static ua.pp.myprojects.zsudriver.R.id.subUnit;
import static ua.pp.myprojects.zsudriver.R.id.userId;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "MainActivity";

    public static final String ANONYMOUS = "anonymous";
    public static final int RC_SIGN_IN = 1;

    private User mUser;


    private ProgressBar mProgressBar;
    private Button mJournalButton;
    private static String mUsername;
    private static TextView mUsernameView;
    private static TextView mUserIdView;
    private static TextView mUserUnitView;
    private static TextView mUserSubUnitView;
    private static TextView mUserRoleView;

    // Firebase instance variables
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private static FirebaseChild mFbsNode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mUsername = ANONYMOUS;
        // Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFbsNode = FirebaseChild.getInstance();


        // Initialize references to views
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mJournalButton = (Button) findViewById(R.id.btnJournal);
        mUserIdView = (TextView) findViewById(R.id.userName);
        mUsernameView = (TextView) findViewById(userId);
        mUserUnitView = (TextView) findViewById(milUnit);
        mUserSubUnitView = (TextView) findViewById(subUnit);
        mUserRoleView = (TextView) findViewById(role);

        // Initialize progress bar
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);
        mJournalButton.setVisibility(View.INVISIBLE);


        mJournalButton.setOnClickListener(this);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser fbsUser = firebaseAuth.getCurrentUser();
                if (fbsUser != null) {
                    // User is signed in
                    mUser = User.getInstance(fbsUser.getDisplayName(), fbsUser.getUid());
                    mFbsNode.getSnapshotMap(mFbsNode.getNodeReference(mFbsNode.USERS, mUser.getUserId()), mUser);
                    findViewById(R.id.mainConstrLt).setVisibility(View.VISIBLE);
                    mJournalButton.setVisibility(View.VISIBLE);

//                    onSignedInInitialize(mUser);
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

    public static void onSignedInInitialize(User user) {
        mUsername = user.getDisplayName();
        mUsernameView.setText(mUsername);
        mUserIdView.setText(user.getUserId());
        mUserUnitView.setText(user.getMilUnitAccess());
        mUserSubUnitView.setText(user.getSubUnitAccess());
        mUserRoleView.setText(user.getRole());
    }

    private void onSignedOutCleanup() {
        mUsername = ANONYMOUS;
        findViewById(R.id.mainConstrLt).setVisibility(View.INVISIBLE);
    }



    @Override
    public void onClick(View view) {
        Intent intent;

        switch(view.getId()) {
            case R.id.btnJournal:
                intent = new Intent(this, SubUnitCarsActivity.class);
                intent.putExtra("milUnit", mUser.getMilUnitAccess().toString());
                intent.putExtra("subUnit", mUser.getSubUnitAccess().toString());
                startActivity(intent);
                break;
        }
    }


}
