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

public class JournalAdapter extends ArrayAdapter<JournalItem> {
    public JournalAdapter(Context context, int resource, List<JournalItem> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_message, parent, false);
        }

        TextView dateTextView = (TextView) convertView.findViewById(R.id.accumDate);
        TextView numberTextView = (TextView) convertView.findViewById(R.id.engNmbView);
        TextView kmBeforeTextView = (TextView) convertView.findViewById(R.id.carcasNmbView);
        TextView kmAfterTextView = (TextView) convertView.findViewById(R.id.carNameView);
        TextView kmDayTextView = (TextView) convertView.findViewById(R.id.carTypeView);
        TextView fuelBeforeTextView = (TextView) convertView.findViewById(R.id.tyresDateView);
        TextView fuelAddTextView = (TextView) convertView.findViewById(R.id.tyresNmbsView);
        TextView fuelAfterTextView = (TextView) convertView.findViewById(R.id.fuelAfterView);

        JournalItem message = getItem(position);

        dateTextView.setText(message.getDate());
        numberTextView.setText(message.getNumber().toString());
        kmBeforeTextView.setText(message.getKmBefore().toString());
        kmAfterTextView.setText(message.getKmAfter().toString());
        kmDayTextView.setText(new Integer(message.getKmAfter() - message.getKmBefore()).toString());
        fuelBeforeTextView.setText(message.getFuelBefore().toString());
        fuelAddTextView.setText(message.getFuelAdd().toString());
        fuelAfterTextView.setText(message.getFuelAfter().toString());

        return convertView;
    }
}
