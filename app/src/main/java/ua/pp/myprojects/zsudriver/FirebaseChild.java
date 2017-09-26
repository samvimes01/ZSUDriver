/*
 * Copyright (c) 2017. Oleksandr Korneiko
 * This file is subject to the terms and conditions defined in
 * file "LICENSE", which is part of this source code package
 *
 */

package ua.pp.myprojects.zsudriver;


import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class FirebaseChild {

    private static final FirebaseChild ourInstance = new FirebaseChild();


    public static final int USERS = 1;
    public static final int ROLES = 2;
    public static final int VEHICLES = 3;
    public static final int MIL_UNIT = 4;

    // Firebase instance variables
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mNodeDatabaseReference;


    static FirebaseChild getInstance() {
        return ourInstance;
    }

    private FirebaseChild() {
        // Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
    }

    public DatabaseReference getNodeReference(int node, String subNode) {

        switch (node) {
            case USERS:
                mNodeDatabaseReference = mFirebaseDatabase.getReference().child("users");
                break;
            case ROLES:
                mNodeDatabaseReference = mFirebaseDatabase.getReference().child("roles");
                break;
            case VEHICLES:
                mNodeDatabaseReference = mFirebaseDatabase.getReference().child("vehicles");
                break;
            case MIL_UNIT:
                mNodeDatabaseReference = mFirebaseDatabase.getReference().child("milUnit");
                break;
        }

        return mNodeDatabaseReference.child(subNode);
    }

    public DatabaseReference getNodeReference(DatabaseReference node, String subNode) {
        return node.child(subNode);
    }

    public void getDataSnapshot(DatabaseReference node, final SnapshotRetrieveListener listener, final FirebaseSnapshotMapSetter fbsSnapshotMapSetter) {

        node.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.retrieveDataSnapshot(dataSnapshot, fbsSnapshotMapSetter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
                Log.d("MyLog", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    public Map<String, Object> getSnapshotMap(DataSnapshot dataSnapshot) {
        return (Map<String, Object>) dataSnapshot.getValue();
    }
}
