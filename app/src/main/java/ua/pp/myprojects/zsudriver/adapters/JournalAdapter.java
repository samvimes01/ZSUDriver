/*
 * Copyright (c) 2017. Oleksandr Korneiko
 * This file is subject to the terms and conditions defined in
 * file "LICENSE", which is part of this source code package
 *
 */

package ua.pp.myprojects.zsudriver.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ua.pp.myprojects.zsudriver.R;
import ua.pp.myprojects.zsudriver.items.JournalItem;

public class JournalAdapter extends ArrayAdapter<JournalItem> {
    public JournalAdapter(Context context, int resource, List<JournalItem> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_message, parent, false);
        }

        TextView dateTextView = (TextView) convertView.findViewById(R.id.dateTextView);
        TextView numberTextView = (TextView) convertView.findViewById(R.id.numberTextView);
        TextView kmBeforeTextView = (TextView) convertView.findViewById(R.id.kmBeforeTextView);
        TextView kmAfterTextView = (TextView) convertView.findViewById(R.id.kmAfterTextView);
        TextView kmDayTextView = (TextView) convertView.findViewById(R.id.kmDayTextView);
        TextView fuelBeforeTextView = (TextView) convertView.findViewById(R.id.fuelBeforeTextView);
        TextView fuelAddTextView = (TextView) convertView.findViewById(R.id.fuelAddTextView);
        TextView fuelAfterTextView = (TextView) convertView.findViewById(R.id.fuelAfterTextView);
        TextView fuelConsumptTextView = (TextView) convertView.findViewById(R.id.fuelConsumptTextView);

        JournalItem message = getItem(position);

        dateTextView.setText(message.getDate());
        numberTextView.setText(message.getNumber().toString());
        kmBeforeTextView.setText(message.getKmBefore().toString());
        kmAfterTextView.setText(message.getKmAfter().toString());
        kmDayTextView.setText(new Integer(message.getKmAfter() - message.getKmBefore()).toString());
        fuelBeforeTextView.setText(message.getFuelBefore().toString());
        fuelAddTextView.setText(message.getFuelAdd().toString());
        fuelAfterTextView.setText(message.getFuelAfter().toString());
        fuelConsumptTextView.setText(message.getFuelConsumpt().toString());

        return convertView;
    }
}
