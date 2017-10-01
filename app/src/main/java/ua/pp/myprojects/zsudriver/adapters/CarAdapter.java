/*
 * Copyright (c) 2017. Oleksandr Korneiko
 * This file is subject to the terms and conditions defined in
 * file "LICENSE", which is part of this source code package
 *
 */

package ua.pp.myprojects.zsudriver.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import ua.pp.myprojects.zsudriver.R;
import ua.pp.myprojects.zsudriver.activities.JournalActivity;
import ua.pp.myprojects.zsudriver.items.CarItem;

public class CarAdapter extends ArrayAdapter<CarItem> {
    public CarAdapter(Context context, int resource, List<CarItem> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_car, parent, false);
        }

        TextView accumDateTextView = (TextView) convertView.findViewById(R.id.accumDate);
        TextView accumTypeTextView = (TextView) convertView.findViewById(R.id.accumType);
        TextView engNmbTextView = (TextView) convertView.findViewById(R.id.engNmbView);
        TextView carcasNmbTextView = (TextView) convertView.findViewById(R.id.carcasNmbView);
        TextView carNameTextView = (TextView) convertView.findViewById(R.id.carNameView);
        TextView carTypeTextView = (TextView) convertView.findViewById(R.id.carTypeView);
        TextView tyresDateTextView = (TextView) convertView.findViewById(R.id.tyresDateView);
        TextView tyresNmbsTextView = (TextView) convertView.findViewById(R.id.tyresNmbsView);
        TextView vnTextView = (TextView) convertView.findViewById(R.id.vnView);
//        Button btnJournal = (Button) convertView.findViewById(R.id.btnCarJournal);
        Button btnEditCar = (Button) convertView.findViewById(R.id.btnEditCar);

        final CarItem car = getItem(position);

        accumTypeTextView.setText(car.getAccumType());
        accumDateTextView.setText(car.getAccumDate());
        engNmbTextView.setText(car.getEngNmb().toString());
        carcasNmbTextView.setText(car.getCarcasNmb().toString());
        carNameTextView.setText(car.getName().toString());
        carTypeTextView.setText(car.getType());
        tyresDateTextView.setText(car.getTyresDate().toString());
        tyresNmbsTextView.setText(car.getTyresNmbs().toString());
        vnTextView.setText(car.getVn().toString());


//        btnJournal.setOnClickListener(new View.OnClickListener() {
//            Intent intent;
//            @Override
//            public void onClick(View view) {
//                intent = new Intent(getContext(), JournalActivity.class);
//                intent.putExtra("car", car.getCarId());
//                intent.putExtra("vn", car.getVn());
//                getContext().startActivity(intent);
//            }
//        });

        btnEditCar.setOnClickListener(new View.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(View view) {
                intent = new Intent(getContext(), JournalActivity.class);
                intent.putExtra("car", car.getCarId());
                intent.putExtra("vn", car.getVn());
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
