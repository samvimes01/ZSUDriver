/*
 * Copyright (c) 2017. Oleksandr Korneiko
 * This file is subject to the terms and conditions defined in
 * file "LICENSE", which is part of this source code package
 *
 */

package ua.pp.myprojects.zsudriver.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

import ua.pp.myprojects.zsudriver.R;
import ua.pp.myprojects.zsudriver.adapters.JournalAdapter;
import ua.pp.myprojects.zsudriver.fbdbs.FirebaseChild;
import ua.pp.myprojects.zsudriver.items.JournalItem;
import ua.pp.myprojects.zsudriver.models.User;


public class JournalActivity extends ActivityBasic {


    private String car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        // Initialize references to views
        ProgressBar mProgressBar = findViewById(R.id.progressBar2);
        ListView mMessageListView = findViewById(R.id.lstV_month);
        FloatingActionButton mAddJItemButton = findViewById(R.id.fab);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        Intent intent = getIntent();
        car = intent.getStringExtra("car");
        mTitle.setText(" " + intent.getStringExtra("vn"));

        mDbActivityReference = mFbsNode.getNodeReference(FirebaseChild.MIL_UNIT).child(User.getMilUnit())
                                                                                .child(FirebaseChild.SUB_UNIT_CHILD)
                                                                                .child(User.getSubUnit())
                                                                                .child(FirebaseChild.JOURNAL_CHILD)
                                                                                .child(car);

        attachDatabaseReadListener();

        // Initialize message ListView and its adapter
        List<JournalItem> journalItems = new ArrayList<>();
        mAdapter = new JournalAdapter(this, R.layout.item_message, journalItems);
        mMessageListView.setAdapter(mAdapter);

        mMessageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(JournalActivity.this, getResources().getString(R.string.long_tap_to_edit), Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        mMessageListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                JournalItem itemRef = (JournalItem) mAdapter.getItem(position);

                Toast toast = Toast.makeText(JournalActivity.this, itemRef.getDate(), Toast.LENGTH_SHORT);
                toast.show();

//                Intent intent;
//
//                intent = new Intent(JournalActivity.this, EditJournalItemActivity.class);
//                intent.putExtra("car", itemRef.getCarId());
//                intent.putExtra("vn", itemRef.getVn());
//                startActivity(intent);

                return true;
            }
        });

        // Initialize progress bar
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        mAddJItemButton.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        Intent intent;

        switch(view.getId()) {
            case R.id.fab:
                intent = new Intent(this, AddJournalItemActivity.class);
                intent.putExtra("car", car);
                startActivity(intent);
                break;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addItemToAdapter(DataSnapshot dataSnapshot) {
        JournalItem journalItem = dataSnapshot.getValue(JournalItem.class);
        mAdapter.add(journalItem);
    }
}