/*
 * Copyright (c) 2017. Oleksandr Korneiko
 * This file is subject to the terms and conditions defined in
 * file "LICENSE", which is part of this source code package
 *
 */

package ua.pp.myprojects.zsudriver.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

import ua.pp.myprojects.zsudriver.R;
import ua.pp.myprojects.zsudriver.adapters.CarAdapter;
import ua.pp.myprojects.zsudriver.fbdbs.FirebaseChild;
import ua.pp.myprojects.zsudriver.items.CarItem;
import ua.pp.myprojects.zsudriver.models.User;

public class SubUnitCarsActivity extends ActivityBasic {
    ListView mMessageListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_unit_cars);

        // Initialize references to views
        ProgressBar mProgressBar = findViewById(R.id.progressBar3);
        mMessageListView = findViewById(R.id.lstV_cars);

        String milUnit = User.getMilUnit();
        String subUnit = User.getSubUnit();

        mDbActivityReference = mFbsNode.getNodeReference(FirebaseChild.MIL_UNIT).child(milUnit)
                                                                                .child(FirebaseChild.SUB_UNIT_CHILD)
                                                                                .child(subUnit)
                                                                                .child(FirebaseChild.VEHICLES_CHILD);

        attachDatabaseReadListener();


        // Initialize message ListView and its adapter
        List<CarItem> carItems = new ArrayList<>();
        mAdapter = new CarAdapter(this, R.layout.item_car, carItems);
        mMessageListView.setAdapter(mAdapter);

        mMessageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CarItem itemRef = (CarItem) mAdapter.getItem(position);

                Toast toast = Toast.makeText(SubUnitCarsActivity.this, itemRef.getVn(), Toast.LENGTH_SHORT);
                toast.show();

                Intent intent;

                intent = new Intent(SubUnitCarsActivity.this, JournalActivity.class);
                intent.putExtra("car", itemRef.getCarId());
                intent.putExtra("vn", itemRef.getVn());
                startActivity(intent);

            }
        });


        // Initialize progress bar
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

    }


    @Override
    public void onClick(View view) {

    }

    @SuppressWarnings("unchecked")
    @Override
    public void addItemToAdapter(DataSnapshot dataSnapshot) {
        CarItem carItem = dataSnapshot.getValue(CarItem.class);
        mAdapter.add(carItem);
    }

}