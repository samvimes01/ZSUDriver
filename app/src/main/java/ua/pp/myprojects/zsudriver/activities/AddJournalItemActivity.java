/*
 * Copyright (c) 2017. Oleksandr Korneiko
 * This file is subject to the terms and conditions defined in
 * file "LICENSE", which is part of this source code package
 *
 */

package ua.pp.myprojects.zsudriver.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import ua.pp.myprojects.zsudriver.R;
import ua.pp.myprojects.zsudriver.fbdbs.FirebaseChild;
import ua.pp.myprojects.zsudriver.items.JournalItem;
import ua.pp.myprojects.zsudriver.models.DatePickerFragment;
import ua.pp.myprojects.zsudriver.models.User;

public class AddJournalItemActivity extends ActivityBasic implements DatePickerDialog.OnDateSetListener {

    private EditText mNumberEditText;
    private EditText mKmBeforeEditText;
    private EditText mKmAfterEditText;
    private EditText mFuelBeforeEditText;
    private EditText mFuelAddEditText;
    private EditText mFuelAfterEditText;
    private TextView mFuelConsumption;
    private TextView mKmDay;
    private EditText mDatePeeker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_journal_item);

        // Initialize references to views
        mDatePeeker = findViewById(R.id.trip_date);
        Button mSendButton = findViewById(R.id.sendButton);

        Intent intent = getIntent();
        String car = intent.getStringExtra("car");

        mDbActivityReference = mFbsNode.getNodeReference(FirebaseChild.MIL_UNIT).child(User.getMilUnit())
                                                                                .child(FirebaseChild.JOURNAL_CHILD)
                                                                                .child(car);

        mNumberEditText = findViewById(R.id.waybill_number);
        mKmBeforeEditText = findViewById(R.id.input_km_before);
        mKmAfterEditText = findViewById(R.id.input_km_after);
        mFuelBeforeEditText = findViewById(R.id.input_fuel_before);
        mFuelAddEditText = findViewById(R.id.input_fuel_add);
        mFuelAfterEditText = findViewById(R.id.input_fuel_after);

        mFuelConsumption = findViewById(R.id.dayFuel);
        mKmDay = findViewById(R.id.carTypeView);

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
                mFuelConsumption.setText(String.format(Locale.getDefault(), "%d", fuel));
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



    }




    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sendButton:

                JournalItem journalItem = new JournalItem(mDatePeeker.getText().toString(), Integer.parseInt(mNumberEditText.getText().toString()), Integer.parseInt(mKmBeforeEditText.getText().toString()), Integer.parseInt(mKmAfterEditText.getText().toString()), Integer.parseInt(mFuelBeforeEditText.getText().toString()), Integer.parseInt(mFuelAddEditText.getText().toString()), Integer.parseInt(mFuelAfterEditText.getText().toString()), Integer.parseInt(mFuelConsumption.getText().toString()));

//                mDatabaseReference.push().setValue(journalItem);
                try {
                    mFbsNode.pushToDb(mDbActivityReference, journalItem);
                } catch (DatabaseException dbe) {
                    showMyDialog( getResources().getString(R.string.error_msg), dbe.getMessage() );
                }

                finish();
                break;

            case R.id.trip_date:
                DatePickerFragment newFragment = new DatePickerFragment();
                newFragment.setOnDateSetListener(this);
                newFragment.show(getSupportFragmentManager(), "datePicker");
                break;

            case R.id.input_fuel_after:
                Integer km = Integer.parseInt(mKmAfterEditText.getText().toString()) - Integer.parseInt(mKmBeforeEditText.getText().toString());
                Log.d("MyLog", km.toString());
                mKmDay.setText(String.format(Locale.getDefault(), "%d", km));
                break;
        }
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        Calendar cal = new GregorianCalendar(year, month, day);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        mDatePeeker.setText(sdf.format(cal.getTime()));
    }
}
