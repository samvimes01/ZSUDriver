/*
 * Copyright (c) 2017. Oleksandr Korneiko
 * This file is subject to the terms and conditions defined in
 * file "LICENSE", which is part of this source code package
 *
 */

package ua.pp.myprojects.zsudriver.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.Map;

import ua.pp.myprojects.zsudriver.R;
import ua.pp.myprojects.zsudriver.fbdbs.FirebaseChild;
import ua.pp.myprojects.zsudriver.interfaces.SnapshotRetrieveListener;
import ua.pp.myprojects.zsudriver.models.User;

public class NewUserActivity extends ActivityBasic implements SnapshotRetrieveListener {

    ArrayList<String> data;
    Map<String, Object> nodeListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        TextView userIdNew = findViewById(R.id.userIdNew);
        userIdNew.setText(User.getUserId());

        TextView userNameNew = findViewById(R.id.userNameNew);
        userNameNew.setText(User.getDisplayName());

//        mFbsNode.getDataSnapshot(mFbsNode.getNodeReference(FirebaseChild.USERS).child(User.getUserId()), NewUserActivity.this);
        mFbsNode.getDataSnapshot(mFbsNode.getNodeReference(FirebaseChild.MIL_UNIT).child(FirebaseChild.LIST_CHILD), NewUserActivity.this);


        // Initialize message ListView and its adapter

    }

    @Override
    protected void onSignedInInitialize() {

    }


    @Override
    public void retrieveDataSnapshot(DataSnapshot dataSnapshot) {
        nodeListData = mFbsNode.getSnapshotMap(dataSnapshot);
        onSpinnerDataReady(nodeListData);
    }

    @Override
    public void onFailed(DatabaseError databaseError) {

    }

    @Override
    public void onDataSnapshotNonExists() {

    }

    public void onSpinnerDataReady(Map<String, Object> nodeListData) {

        data = new ArrayList<String>(nodeListData.keySet());

//        String[] data = {"one", "two", "three", "four", "five"};


        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Title");
        // выделяем элемент
//        spinner.setSelection(2);
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // показываем позиция нажатого элемента
                Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }
}
