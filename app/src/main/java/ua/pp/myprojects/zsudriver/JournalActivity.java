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
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class JournalActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    private static final String TAG = "MainActivity";

    public static final String ANONYMOUS = "anonymous";


    private ListView mMessageListView;
    private JournalAdapter mJournalAdapter;
    private ProgressBar mProgressBar;
    private FloatingActionButton mAddJItemButton;

    private String car;

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
        setContentView(R.layout.activity_journal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFbsNode = FirebaseChild.getInstance();

        Intent intent = getIntent();

        car = intent.getStringExtra("car");


        String milUnit = User.getMilUnitAccess().toString();
        String subUnit = User.getSubUnitAccess().toString();

        mMessagesDatabaseReference = mFbsNode.getNodeReference(mFbsNode.getNodeReference(mFbsNode.MIL_UNIT, milUnit).child("subUnits"), subUnit).child("journal").child(car);


//        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("milUnit").child("А4104").child("subUnits").child("medUnit").child("journal");
        attachDatabaseReadListener();

        // Initialize references to views
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar2);
        mMessageListView = (ListView) findViewById(R.id.lstV_month);

        mAddJItemButton = (FloatingActionButton) findViewById(R.id.fab);


        // Initialize message ListView and its adapter
        List<JournalItem> journalItems = new ArrayList<>();
        mJournalAdapter = new JournalAdapter(this, R.layout.item_message, journalItems);
        mMessageListView.setAdapter(mJournalAdapter);

        // Initialize progress bar
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        mAddJItemButton.setOnClickListener(this);


//check for authorization
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    onSignedInInitialize();
                } else {
                    onSignedOutCleanup();
                    finish();
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
        mJournalAdapter.clear();
        detachDatabaseReadListener();
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

    private void onSignedInInitialize() {
        attachDatabaseReadListener();
    }

    private void onSignedOutCleanup() {
        mJournalAdapter.clear();
        detachDatabaseReadListener();
        finish();
    }

    private void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    JournalItem journalItem = dataSnapshot.getValue(JournalItem.class);
                    mJournalAdapter.add(journalItem);
                }

                public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
                public void onChildRemoved(DataSnapshot dataSnapshot) {}
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
                public void onCancelled(DatabaseError databaseError) {}
            };
            mMessagesDatabaseReference.addChildEventListener(mChildEventListener);
        }
    }


    private void detachDatabaseReadListener() {
        if (mChildEventListener != null) {
            mMessagesDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent;

        switch(view.getId()) {
            case R.id.fab:
                intent = new Intent(this, AddJournalItemActivity.class);
                intent.putExtra("car", car);
                startActivity(intent);
                break;
        }
    }
}