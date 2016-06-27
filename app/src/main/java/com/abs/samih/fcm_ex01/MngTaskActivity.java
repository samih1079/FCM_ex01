package com.abs.samih.fcm_ex01;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MngTaskActivity extends AppCompatActivity {

    //5 (you have to build MyAdapterTask before)
    private MyAdapterTask adapterTask;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mng_task_activity);
        //6
        listView = (ListView) findViewById(R.id.listView);
        adapterTask=new MyAdapterTask(this,R.layout.item_my_task);
        //7
        listView.setAdapter(adapterTask);

        //1. get fixed email(replace '.' with '_')
        String email=FirebaseAuth.getInstance().getCurrentUser().getEmail().replace('.','_');

        //2.
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference(email);
        reference.child("MyTasks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //8.
                adapterTask.clear();

                //3.
                for (DataSnapshot ds:dataSnapshot.getChildren())
                {
                    //4.
                    MyTask myTask=ds.getValue(MyTask.class);
                    myTask.setTaskKey(ds.getKey());
                    //9.
                    //Add myTask to Adatpter
                    adapterTask.add(myTask);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(MngTaskActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //all my task will be under my mail under the root MyTasks
        //child can not contain chars: $,#,[,], . ,.....



    }

}
