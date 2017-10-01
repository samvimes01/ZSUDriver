/*
 * Copyright (c) 2017. Oleksandr Korneiko
 * This file is subject to the terms and conditions defined in
 * file "LICENSE", which is part of this source code package
 *
 */

package ua.pp.myprojects.zsudriver.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import ua.pp.myprojects.zsudriver.R;
import ua.pp.myprojects.zsudriver.fbdbs.FirebaseChild;
import ua.pp.myprojects.zsudriver.models.ErrorDialogFragment;

public abstract class ActivityBasic extends AppCompatActivity implements View.OnClickListener {

    protected Intent intent;
    protected ArrayAdapter mAdapter;

    // Firebase instance variables
    protected DatabaseReference mDatabaseReference;
    protected DatabaseReference mDbActivityReference;
    protected ChildEventListener mChildEventListener;
    protected FirebaseAuth mFirebaseAuth;
    protected FirebaseAuth.AuthStateListener mAuthStateListener;

    protected FirebaseUser fbsUser;
    protected FirebaseChild mFbsNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase components
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFbsNode = FirebaseChild.getInstance();

        mDatabaseReference = mFbsNode.getNodeReference(FirebaseChild.TOP);
        //need to be changer in every activity
        //mDbActivityReference = mDatabaseReference;

        //check for authorization
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                fbsUser = firebaseAuth.getCurrentUser();
                if (fbsUser != null) {
                    // User is signed in
                    onSignedInInitialize();
                } else {
                    // User is signed out
                    onSignedOutCleanup();
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

//        try {
//            mAdapter.clear();
//        } catch (NullPointerException e) {}
//
//        detachDatabaseReadListener();
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
            case R.id.dialog:
                showMyDialog("test dialog","test");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void showMyDialog(String title, String msg) {
        DialogFragment dialog = new ErrorDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("msg", msg);
        dialog.setArguments(args);
        FragmentManager manager = getSupportFragmentManager();
        dialog.show(manager, "dialog");
    }

    protected void onSignedInInitialize() {
        attachDatabaseReadListener();
    }

    protected void onSignedOutCleanup() {
        detachDatabaseReadListener();
        finish();
    }

    protected void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    addItemToAdapter(dataSnapshot);
                }

                public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
                public void onChildRemoved(DataSnapshot dataSnapshot) {}
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
                public void onCancelled(DatabaseError databaseError) {}
            };
            mDbActivityReference.addChildEventListener(mChildEventListener);
        }
    }


    protected void detachDatabaseReadListener() {
        if (mChildEventListener != null) {
            mDbActivityReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }

    @Override
    public void onClick(View view) {}

    public void addItemToAdapter(DataSnapshot dataSnapshot) {}
}
