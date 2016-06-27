package com.abs.samih.fcm_ex01.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.abs.samih.fcm_ex01.R;

/**
 * Created by school on 25/06/2016.
 */
public class MyTaskAdapter extends ArrayAdapter<MyTask>
{
    public MyTaskAdapter(Context context, int resource) {
        super(context, resource);


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final MyTask myTask = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_my_task, parent, false);
        }

        TextView tvText= (TextView) convertView.findViewById(R.id.tvText);
        CheckBox checkBox= (CheckBox) convertView.findViewById(R.id.chbxIsComplated);
        TextView tvDate= (TextView) convertView.findViewById(R.id.tvDate);
        ImageButton btnLoc= (ImageButton) convertView.findViewById(R.id.btnLocation);
        ImageButton btnCall= (ImageButton) convertView.findViewById(R.id.btnCall);

        tvText.setText(myTask.getText());
        checkBox.setChecked(myTask.isComplated());
        tvDate.setText(myTask.getCreatedAt().toString());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(getContext(),"isChecked:"+isChecked,Toast.LENGTH_LONG).show();
            }
        });
        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),myTask.getText()+":"+myTask.getLocation(),Toast.LENGTH_LONG).show();
            }
        });
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),myTask.getText()+":"+myTask.getPhone(),Toast.LENGTH_LONG).show();
            }
        });

        return convertView;
    }
}
