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

public class NewUserActivity extends ActivityBasic {

    ArrayList<String> data;

    RetrieveListener milUnit;
    RetrieveListener subUnit;
    RetrieveListener role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        TextView userIdNew = findViewById(R.id.userIdNew);
        userIdNew.setText(User.getUserId());

        TextView userNameNew = findViewById(R.id.userNameNew);
        userNameNew.setText(User.getDisplayName());


        milUnit = new RetrieveListener(mFbsNode, (Spinner) findViewById(R.id.spinnerMilUnit), this, "unit");
        role = new RetrieveListener(mFbsNode, (Spinner) findViewById(R.id.spinnerRoleUnit), this, "role");

        mFbsNode.getDataSnapshot(mFbsNode.getNodeReference(FirebaseChild.MIL_UNIT).child(FirebaseChild.LIST_CHILD), milUnit);
        mFbsNode.getDataSnapshot(mFbsNode.getNodeReference(FirebaseChild.ROLES), role);

    }


    @Override
    protected void onSignedInInitialize() {

    }

    public void onSpinnerDataReady(Map<String, Object> nodeListData, Spinner spinner, final String mode) {

        data = new ArrayList<>(nodeListData.keySet());

        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//        spinner = (Spinner) findViewById(R.id.spinner);
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
                switch (mode) {
                    case "unit":
                        mFbsNode.writeToDb(mFbsNode.getNodeReference(FirebaseChild.USERS).child(User.getUserId()).child(FirebaseChild.MIL_UNIT_CHILD), parent.getItemAtPosition(position).toString());
                        break;
                    case "role":
                        mFbsNode.writeToDb(mFbsNode.getNodeReference(FirebaseChild.USERS).child(User.getUserId()).child("role"), parent.getItemAtPosition(position).toString());
                        break;
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }
}

class RetrieveListener implements SnapshotRetrieveListener {

    private  FirebaseChild node;
    private  Spinner spinner;
    private NewUserActivity act;
    private String mode;

    RetrieveListener(FirebaseChild node, Spinner spinner, NewUserActivity act, String mode) {
        this.node = node;
        this.spinner = spinner;
        this.act = act;
        this.mode = mode;
    }

    @Override
    public void onRetrieveDataSnapshot(DataSnapshot dataSnapshot) {
        act.onSpinnerDataReady(node.getSnapshotMap(dataSnapshot), spinner, mode);
    }

    @Override
    public void onFailed(DatabaseError databaseError) {

    }

    @Override
    public void onDataSnapshotNonExists() {

    }
}