/*
 * Copyright (c) 2017. Oleksandr Korneiko
 * This file is subject to the terms and conditions defined in
 * file "LICENSE", which is part of this source code package
 *
 */

package ua.pp.myprojects.zsudriver.fbdbs;


import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import ua.pp.myprojects.zsudriver.interfaces.Item;
import ua.pp.myprojects.zsudriver.interfaces.SnapshotRetrieveListener;

public class FirebaseChild {

    private static final FirebaseChild ourInstance = new FirebaseChild();

    public static final int TOP = 1;
    public static final int USERS = 2;
    public static final int ROLES = 3;
    public static final int VEHICLES = 4;
    public static final int MIL_UNIT = 5;

    public static final String USERS_CHILD = "users";
    public static final String ROLES_CHILD = "roles";
    public static final String VEHICLES_CHILD = "vehicles";
    public static final String MIL_UNIT_CHILD = "milUnit";
    public static final String JOURNAL_CHILD = "journal";
    public static final String LIST_CHILD = "list";

    private DatabaseReference mTopDatabaseReference;


    public static FirebaseChild getInstance() {
        return ourInstance;
    }

    private FirebaseChild() {
        // Initialize Firebase components
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        mTopDatabaseReference = mFirebaseDatabase.getReference();
    }


    public DatabaseReference getNodeReference(int node) {
        DatabaseReference mNodeDatabaseReference;
        switch (node) {
            case USERS:
                mNodeDatabaseReference = mTopDatabaseReference.child(USERS_CHILD);
                break;
            case ROLES:
                mNodeDatabaseReference = mTopDatabaseReference.child(ROLES_CHILD);
                break;
            case VEHICLES:
                mNodeDatabaseReference = mTopDatabaseReference.child(VEHICLES_CHILD);
                break;
            case MIL_UNIT:
                mNodeDatabaseReference = mTopDatabaseReference.child(MIL_UNIT_CHILD);
                break;
            default:
                mNodeDatabaseReference = mTopDatabaseReference;
                break;
        }
        return mNodeDatabaseReference;
    }


    public void getDataSnapshot(DatabaseReference node, final SnapshotRetrieveListener listener) {

        node.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    listener.onRetrieveDataSnapshot(dataSnapshot);
                } else {
                    listener.onDataSnapshotNonExists();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
                Log.d("MyLog", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }


        @SuppressWarnings("unchecked")
    public HashMap<String, Object> getSnapshotMap(DataSnapshot dataSnapshot) {
        return (HashMap<String, Object>) dataSnapshot.getValue();
    }

    public void pushToDb(DatabaseReference node, Item value) {
        node.push().setValue(value);
    }

    public void writeToDb(DatabaseReference node, String value) {
        node.setValue(value);
    }

//    public void getDatabaseReadListener(ChildEventListener mChildEventListener, DatabaseReference mDbActivityReference, final SnapshotRetrieveListener listener) {
//        if (mChildEventListener == null) {
//            mChildEventListener = new ChildEventListener() {
//                @Override
//                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                    listener.onRetrieveDataSnapshot(dataSnapshot);
//                }
//
//                public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
//                public void onChildRemoved(DataSnapshot dataSnapshot) {}
//                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
//                public void onCancelled(DatabaseError databaseError) {}
//            };
//            mDbActivityReference.addChildEventListener(mChildEventListener);
//        }
//    }
//
//    public void destroyDatabaseReadListener(ChildEventListener mChildEventListener, DatabaseReference mDbActivityReference) {
//        if (mChildEventListener != null) {
//            mDbActivityReference.removeEventListener(mChildEventListener);
//            mChildEventListener = null;
//        }
//    }
}
