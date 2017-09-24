/*
 * Copyright (c) 2017. Oleksandr Korneiko
 * This file is subject to the terms and conditions defined in
 * file "LICENSE", which is part of this source code package
 *
 */

package ua.pp.myprojects.zsudriver;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.Calendar;

public class AddJournalItemActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    public static final String ANONYMOUS = "anonymous";
    public static final int RC_SIGN_IN = 1;
    private static final int RC_PHOTO_PICKER = 2;


    private ListView mMessageListView;
    private JournalAdapter mJournalAdapter;

    private EditText mDateEditText;
    private EditText mNumberEditText;
    private EditText mKmBeforeEditText;
    private EditText mKmAfterEditText;
    private EditText mFuelBeforeEditText;
    private EditText mFuelAddEditText;
    private EditText mFuelAfterEditText;
    private TextView mFuelConsumption;
    private TextView mKmDay;
    private Button mSendButton;
    static EditText mDatePeeker;

    private String car;

    // Firebase instance variables
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mJournalDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private static FirebaseChild mFbsNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_journal_item);


        // Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFbsNode = FirebaseChild.getInstance();


        Intent intent = getIntent();

        car = intent.getStringExtra("car");


        String milUnit = User.getMilUnitAccess().toString();
        String subUnit = User.getSubUnitAccess().toString();

        mJournalDatabaseReference = mFbsNode.getNodeReference(mFbsNode.getNodeReference(mFbsNode.MIL_UNIT, milUnit).child("subUnits"), subUnit).child("journal").child(car);

//        mJournalDatabaseReference = mFirebaseDatabase.getReference().child("milUnit").child("А4104").child("subUnits").child("medUnit").child("journal");


        // Initialize references to views
        mDatePeeker = (EditText) findViewById(R.id.trip_date);
        mSendButton = (Button) findViewById(R.id.sendButton);

        mDateEditText = (EditText) findViewById(R.id.trip_date);
        mNumberEditText = (EditText) findViewById(R.id.waybill_number);
        mKmBeforeEditText = (EditText) findViewById(R.id.input_km_before);
        mKmAfterEditText = (EditText) findViewById(R.id.input_km_after);
        mFuelBeforeEditText = (EditText) findViewById(R.id.input_fuel_before);
        mFuelAddEditText = (EditText) findViewById(R.id.input_fuel_add);
        mFuelAfterEditText = (EditText) findViewById(R.id.input_fuel_after);

        mFuelConsumption = (TextView) findViewById(R.id.dayFuel);
        mKmDay = (TextView) findViewById(R.id.carTypeView);

        // DatePicker listener
        mDatePeeker.setOnClickListener(this);

        mSendButton.setOnClickListener(this);


        mFuelBeforeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

            }
        });
        mFuelAfterEditText.setOnClickListener(this);

        TextWatcher inputTW = new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer fuel = Integer.parseInt(mFuelAfterEditText.getText().toString()) - Integer.parseInt(mFuelBeforeEditText.getText().toString()) - Integer.parseInt(mFuelAddEditText.getText().toString());
                Log.d("MyLog", fuel.toString());
                mFuelConsumption.setText(fuel.toString());
            }

        };

        mFuelAfterEditText.addTextChangedListener(inputTW);

//        mFuelAfterEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if (!b) {
//                    Integer fuel = Integer.parseInt(mFuelAfterEditText.getText().toString()) - Integer.parseInt(mFuelBeforeEditText.getText().toString()) - Integer.parseInt(mFuelAddEditText.getText().toString());
//                    Log.d("MyLog", fuel.toString());
//                    mFuelConsumption.setText(fuel.toString());
//                }
//            }
//        });


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    onSignedInInitialize(user.getDisplayName());
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

    private void onSignedInInitialize(String username) {
    }

    private void onSignedOutCleanup() {
        mJournalAdapter.clear();
    }


    @Override
    public void onClick(View view) {
        Intent intent;

        switch (view.getId()) {
            case R.id.sendButton:
                JournalItem journalItem = new JournalItem(mDateEditText.getText().toString(), Integer.parseInt(mNumberEditText.getText().toString()), Integer.parseInt(mKmBeforeEditText.getText().toString()), Integer.parseInt(mKmAfterEditText.getText().toString()), Integer.parseInt(mFuelBeforeEditText.getText().toString()), Integer.parseInt(mFuelAddEditText.getText().toString()), Integer.parseInt(mFuelAfterEditText.getText().toString()));
                mJournalDatabaseReference.push().setValue(journalItem);
                finish();
                break;
            case R.id.trip_date:
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
                break;
            case R.id.input_fuel_after:
                Integer km = Integer.parseInt(mKmAfterEditText.getText().toString()) - Integer.parseInt(mKmBeforeEditText.getText().toString());
                Log.d("MyLog", km.toString());
                mKmDay.setText(km.toString());
                break;
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            mDatePeeker.setText(day + "." + month+1 + "." + year);
        }
    }
}
