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


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseChild {

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    // Firebase instance variables
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    public FirebaseChild(DatabaseReference mRootRef) {
        this.mRootRef = mRootRef;

        // Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
//        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("milUnit").child("А4104").child("subUnits").child("medUnit").child("journal");

    }

    public DatabaseReference getChild(String node, String subNode) {

        switch (node) {
            case "users":
                mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("users");
                break;
            case "roles":
                mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("roles");
                break;
            case "vehicles":
                mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("vehicles");
                break;
            case "listMilUnit":
                mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("milUnit").child("list");
                break;
            case "listSubUnit":
                mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("milUnit").child(subNode).child("list");
                break;
            case "milUnit":
                mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("milUnit").child("list");
                break;
            case "subUnit":
                mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("milUnit").child(subNode).child("list");
                break;
        }

        return mMessagesDatabaseReference;
    }

    public DatabaseReference getSubUnit(String node) {

        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("milUnit").child(node).child("list");
        return mMessagesDatabaseReference;
    }

}
