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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

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

public class SubUnitCarsActivity extends AppCompatActivity implements View.OnClickListener{

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    private static final String TAG = "SubUnitCarActivity";

    private ListView mMessageListView;
    private CarAdapter mCarAdapter;
    private ProgressBar mProgressBar;

    // Firebase instance variables
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mCarsDatabaseReference1;
    private DatabaseReference mCarsDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private static FirebaseChild mFbsNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_unit_cars);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(this);

        // Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFbsNode = FirebaseChild.getInstance();

        Intent intent = getIntent();

        String milUnit = intent.getStringExtra("milUnit");
        String subUnit = intent.getStringExtra("subUnit");

        mCarsDatabaseReference = mFbsNode.getNodeReference(mFbsNode.getNodeReference(mFbsNode.MIL_UNIT, milUnit).child("subUnits"), subUnit).child("vehicles");
//        mCarsDatabaseReference = mFirebaseDatabase.getReference().child("milUnit").child("A4104").child("subUnits").child("medUnit").child("vehicles");


        attachDatabaseReadListener();

        // Initialize references to views
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar3);
        mMessageListView = (ListView) findViewById(R.id.lstV_cars);

        // Initialize message ListView and its adapter
        List<CarItem> carItems = new ArrayList<>();
        mCarAdapter = new CarAdapter(this, R.layout.item_car, carItems);
        mMessageListView.setAdapter(mCarAdapter);

        mMessageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CarItem itemRef = mCarAdapter.getItem(position);

                Toast toast = Toast.makeText(SubUnitCarsActivity.this, itemRef.getVn(), Toast.LENGTH_SHORT);
                toast.show();

                Intent intent;

                intent = new Intent(SubUnitCarsActivity.this, JournalActivity.class);
                intent.putExtra("car", itemRef.getCarId().toString());
                intent.putExtra("car", itemRef.getCarId().toString());
                startActivity(intent);

            }
        });


        // Initialize progress bar
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        // Check for authorization
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
        mCarAdapter.clear();
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
        mCarAdapter.clear();
        detachDatabaseReadListener();
        finish();
    }

    private void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    CarItem carItem = dataSnapshot.getValue(CarItem.class);
                    mCarAdapter.add(carItem);
                }

                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }

                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }

                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                public void onCancelled(DatabaseError databaseError) {
                }
            };
            mCarsDatabaseReference.addChildEventListener(mChildEventListener);

//            mFbsNode.getDataSnapshot(mCarsDatabaseReference, new SnapshotRetrieveListener() {
//                @Override
//                public void retrieveDataSnapshot(DataSnapshot dataSnapshot) {
//                    CarItem carItem = dataSnapshot.getValue(CarItem.class);
//                    mCarAdapter.add(carItem);
//                }
//
//                @Override
//                public void retrieveFbsNodeData(Map<String, Object> fbsNodeData) {
//                }
//
//                @Override
//                public void onFailed(DatabaseError databaseError) {
//                }
//            });
        }
    }


    private void detachDatabaseReadListener() {
        if (mChildEventListener != null) {
            mCarsDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        Log.d("My view", String.valueOf(view.getId()));

        switch (view.getId()) {
            case R.id.btnCarJournal:
//                intent = new Intent(this, JournalActivity.class);
//                intent.putExtra("car", "car1".toString());
//                startActivity(intent);
                String text = "hi";
                Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
                toast.show();
                break;
        }
    }

}