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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    private static final String TAG = "MainActivity";

    public static final String ANONYMOUS = "anonymous";

    private ListView mMessageListView;
    private JournalAdapter mJournalAdapter;
    private ProgressBar mProgressBar;

    private EditText mDateEditText;
    private EditText mNumberEditText;
    private EditText mKmBeforeEditText;
    private EditText mKmAfterEditText;
    private EditText mFuelBeforeEditText;
    private EditText mFuelAddEditText;
    private EditText mFuelAfterEditText;
    private Button mSendButton;
    private Button mDatePeeker;

    private String mUsername;

    // Firebase instance variables
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mUsername = ANONYMOUS;

        // Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("milUnit").child("А4104").child("subUnits").child("medUnit").child("journal");
        attachDatabaseReadListener();



        // Initialize references to views
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mMessageListView = (ListView) findViewById(R.id.lstV_month);

        mSendButton = (Button) findViewById(R.id.sendButton);
        mDatePeeker = (Button) findViewById(R.id.datePicker);
        mDateEditText = (EditText) findViewById(R.id.dateTrip);
        mNumberEditText = (EditText) findViewById(R.id.numberEdit);
        mKmBeforeEditText = (EditText) findViewById(R.id.kmBeforeTrip);
        mKmAfterEditText = (EditText) findViewById(R.id.kmAfterTrip);
        mFuelBeforeEditText = (EditText) findViewById(R.id.fuelBefore);
        mFuelAddEditText = (EditText) findViewById(R.id.fuelAdd);
        mFuelAfterEditText = (EditText) findViewById(R.id.fuelAfter);


        // Initialize message ListView and its adapter
        List<JournalItem> journalItems = new ArrayList<>();
        mJournalAdapter = new JournalAdapter(this, R.layout.item_message, journalItems);
        mMessageListView.setAdapter(mJournalAdapter);

        // Initialize progress bar
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        // ImagePickerButton shows an image picker to upload a image for a message
        mDatePeeker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
            }
        });

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JournalItem journalItem = new JournalItem(mDateEditText.getText().toString(), Integer.parseInt(mNumberEditText.getText().toString()), Integer.parseInt(mKmBeforeEditText.getText().toString()), Integer.parseInt(mKmAfterEditText.getText().toString()), Integer.parseInt(mFuelBeforeEditText.getText().toString()), Integer.parseInt(mFuelAddEditText.getText().toString()), Integer.parseInt(mFuelAfterEditText.getText().toString()));
                mMessagesDatabaseReference.push().setValue(journalItem);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

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
}
