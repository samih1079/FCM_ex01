package com.abs.samih.fcm_ex01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {

    //1
    EditText etText;
    EditText etPhone;
    EditText etLocation;
    Button btnContcts, btnSave;
    ImageButton btnLocation;
    RatingBar rtPrio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        //2
        etText= (EditText) findViewById(R.id.etText);
        etPhone= (EditText) findViewById(R.id.etPhone);
        etLocation= (EditText) findViewById(R.id.etLocation);
        btnContcts= (Button) findViewById(R.id.btnContacts);
        btnSave= (Button) findViewById(R.id.btnSave);
        btnLocation= (ImageButton) findViewById(R.id.btnLocation);
        rtPrio= (RatingBar) findViewById(R.id.rtBarPriority);
        //7
        btnSave.setOnClickListener(clickListener);
        btnLocation.setOnClickListener(clickListener);
        btnContcts.setOnClickListener(clickListener);
    }

    //3
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //4
            if (v==btnSave) {
                //5
                String txt=etText.getText().toString();
                String phone=etPhone.getText().toString();
                String loc=etLocation.getText().toString();
                int prio=rtPrio.getProgress();
                Date date= Calendar.getInstance().getTime();
                MyTask myTask=new MyTask(txt,false,date,prio,loc,phone);

                //6 save
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
                //
                String email= FirebaseAuth.getInstance().getCurrentUser().getEmail();
                //all my task will be under my mail under the root MyTasks
               //child can not contain chars: $,#,[,], . ,.....
                reference.child(email.replace(".","_")).child("MyTasks").push().setValue(myTask, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError==null)
                        {
                            Toast.makeText(AddTaskActivity.this,"Save successful",
                                    Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(AddTaskActivity.this,"Save Failed"+databaseError.getMessage(),
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }

        }
    };
}
