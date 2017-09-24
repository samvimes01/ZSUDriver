/*
 * Copyright (c) 2017. Oleksandr Korneiko
 * This file is subject to the terms and conditions defined in
 * file "LICENSE", which is part of this source code package
 *
 */

package ua.pp.myprojects.zsudriver;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

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
        TextView engNmbTextView = (TextView) convertView.findViewById(R.id.engNmbView);
        TextView carcasNmbTextView = (TextView) convertView.findViewById(R.id.carcasNmbView);
        TextView carNameTextView = (TextView) convertView.findViewById(R.id.carNameView);
        TextView carTypeTextView = (TextView) convertView.findViewById(R.id.carTypeView);
        TextView tyresDateTextView = (TextView) convertView.findViewById(R.id.tyresDateView);
        TextView tyresNmbsTextView = (TextView) convertView.findViewById(R.id.tyresNmbsView);
        TextView vnTextView = (TextView) convertView.findViewById(R.id.vnView);

        CarItem car = getItem(position);

        accumDateTextView.setText(car.getAccumDate());
        engNmbTextView.setText(car.getEngNmb().toString());
        carcasNmbTextView.setText(car.getCarcasNmb().toString());
        carNameTextView.setText(car.getName().toString());
        carTypeTextView.setText(car.getType());
        tyresDateTextView.setText(car.getTyresDate().toString());
        tyresNmbsTextView.setText(car.getTyresNmbs().toString());
        vnTextView.setText(car.getVn().toString());

        return convertView;
    }
}
