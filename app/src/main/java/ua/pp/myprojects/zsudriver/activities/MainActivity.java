/*
 * Copyright (c) 2017. Oleksandr Korneiko
 * This file is subject to the terms and conditions defined in
 * file "LICENSE", which is part of this source code package
 *
 */

package ua.pp.myprojects.zsudriver.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.Collections;

import ua.pp.myprojects.zsudriver.R;
import ua.pp.myprojects.zsudriver.fbdbs.FirebaseChild;
import ua.pp.myprojects.zsudriver.interfaces.SnapshotRetrieveListener;
import ua.pp.myprojects.zsudriver.models.User;


public class MainActivity extends ActivityBasic implements SnapshotRetrieveListener {

    public static final int RC_SIGN_IN = 1;

    private ProgressBar mProgressBar;
    private Button mJournalButton;
    private TextView mUsernameView;
    private TextView mUserIdView;
    private TextView mUserUnitView;
    private TextView mUserSubUnitView;
    private TextView mUserRoleView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize references to views
        mProgressBar = findViewById(R.id.progressBar);
        mJournalButton = findViewById(R.id.btnJournal);
        mUserIdView =  findViewById(R.id.userName);
        mUsernameView =  findViewById(R.id.userId);
        mUserUnitView =  findViewById(R.id.milUnit);
        mUserSubUnitView = findViewById(R.id.subUnit);
        mUserRoleView = findViewById(R.id.role);

        // View INVISIBLE until data loaded
        setInvisibility();

        mJournalButton.setOnClickListener(this);

    }

    @Override
    protected void onSignedInInitialize() {

        User.getInstance();
        User.setDisplayName(fbsUser.getDisplayName());
        User.setUserId(fbsUser.getUid());
        mFbsNode.getDataSnapshot(mFbsNode.getNodeReference(FirebaseChild.USERS).child(User.getUserId()), MainActivity.this);

    }

    @Override
    protected void onSignedOutCleanup() {
        setInvisibility();

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(
                                Collections.singletonList(new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                        .build(),
                RC_SIGN_IN);

        mProgressBar.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btnJournal:
                intent = new Intent(this, SubUnitCarsActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void retrieveDataSnapshot(DataSnapshot dataSnapshot) {
        User.getInstance().setFbsSnapshotData(mFbsNode.getSnapshotMap(dataSnapshot));
        setUserViews();
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);
        setVisibility();
    }

    @Override
    public void onFailed(DatabaseError databaseError) {

    }
    @Override
    public void onDataSnapshotNonExists() {
        // activity with user register
        intent = new Intent(this, NewUserActivity.class);
        startActivity(intent);
        Toast toast = Toast.makeText(this, "User not registered", Toast.LENGTH_SHORT);
        toast.show();
    }


    private void setUserViews() {
        mUsernameView.setText(User.getDisplayName());
        mUserIdView.setText(User.getUserId());
        mUserUnitView.setText(User.getMilUnit());
        mUserSubUnitView.setText(User.getSubUnit());
        mUserRoleView.setText(User.getRole());
    }

    public void setInvisibility() {
        mUsernameView.setVisibility(View.INVISIBLE);
        mUserIdView.setVisibility(View.INVISIBLE);
        mUserUnitView.setVisibility(View.INVISIBLE);
        mUserSubUnitView.setVisibility(View.INVISIBLE);
        mUserRoleView.setVisibility(View.INVISIBLE);
        mJournalButton.setVisibility(View.INVISIBLE);
    }

    public void setVisibility() {
        mUsernameView.setVisibility(View.VISIBLE);
        mUserIdView.setVisibility(View.VISIBLE);
        mUserUnitView.setVisibility(View.VISIBLE);
        mUserSubUnitView.setVisibility(View.VISIBLE);
        mUserRoleView.setVisibility(View.VISIBLE);
        mJournalButton.setVisibility(View.VISIBLE);
    }
}
