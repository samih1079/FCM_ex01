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

import com.abs.samih.fcm_ex01.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by school on 27/06/2016.
 */
//1                         //2.
public class MyAdapterTask extends ArrayAdapter<MyTask>
{
    //9
    private DatabaseReference reference;

    //3.
    public MyAdapterTask(Context context, int resource) {
        super(context, resource);
        //10. get fixed email(replace '.' with '_')
        String email= FirebaseAuth.getInstance().getCurrentUser().getEmail().replace('.','_');
        //11.
         reference= FirebaseDatabase.getInstance().getReference(email+"/MyTasks");
       // reference.child("MyTasks")


    }

    //4
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //5
//        if(convertView==null)
//        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_my_task,parent,false);
//        }

        //row UI
        //6. to get reference to each ui object at the : R.layout.item_my_task
        CheckBox chbIscompleted= (CheckBox) convertView.findViewById(R.id.chbxIsComplated);
        TextView tvText= (TextView) convertView.findViewById(R.id.tvText);
        TextView tvDate= (TextView) convertView.findViewById(R.id.tvDate);
        ImageButton btnCall=(ImageButton)convertView.findViewById(R.id.btnCall);
        ImageButton btnLocation=(ImageButton)convertView.findViewById(R.id.btnLocation);

        //7  //data source
        final MyTask myTask=getItem(position);
        //8. Connection between the data source and athe UI
        chbIscompleted.setChecked(myTask.isComplated());
        tvText.setText(myTask.getText());
        tvDate.setText(myTask.getCreatedAt().toString());
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //make call
            }
        });
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to map
            }
        });

        chbIscompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                myTask.setComplated(isChecked);
                //12
                reference.child(myTask.getTaskKey()).setValue(myTask);

            }
        });
        return convertView;
    }
}
