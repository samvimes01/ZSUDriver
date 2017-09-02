/*
*    Copyright (C) 2017 Oleksandr Korneiko
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
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

        TextView dateTextView = (TextView) convertView.findViewById(R.id.dateView);
        TextView numberTextView = (TextView) convertView.findViewById(R.id.numberView);
        TextView kmBeforeTextView = (TextView) convertView.findViewById(R.id.kmBeforeView);
        TextView kmAfterTextView = (TextView) convertView.findViewById(R.id.kmAfterView);
        TextView kmDayTextView = (TextView) convertView.findViewById(R.id.kmDayView);
        TextView fuelBeforeTextView = (TextView) convertView.findViewById(R.id.fuelBeforeView);
        TextView fuelAddTextView = (TextView) convertView.findViewById(R.id.fuelAddView);
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
