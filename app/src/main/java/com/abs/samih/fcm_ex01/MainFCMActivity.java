package com.abs.samih.fcm_ex01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainFCMActivity extends AppCompatActivity {
    int i=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fcm);
        Button button = (Button) findViewById(R.id.btnAdd);
        final TextView tvres= (TextView) findViewById(R.id.tvres);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "add", Toast.LENGTH_LONG).show();
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

//                reference.child("massage").push().setValue("hello world", new DatabaseReference.CompletionListener() {
//                    @Override
//                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                        if(databaseError==null)
//                        {
//                            Toast.makeText(getBaseContext(), "Saved", Toast.LENGTH_LONG).show();
//                        }
//                        else
//                        {
//                            Toast.makeText(getBaseContext(), "save Faild" + databaseError.getMessage(), Toast.LENGTH_LONG).show();
//                            databaseError.toException().printStackTrace();
//                        }
//                    }
//                });
                final Person person = new Person("himail" + i++, "hipassw" + 1);
                //check if the "child" is found
                reference.child(person.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            Toast.makeText(getBaseContext(), "person exists", Toast.LENGTH_LONG).show();

                        }else
                        {
                            Toast.makeText(getBaseContext(), "person not exists", Toast.LENGTH_LONG).show();

                            reference.child(person.getEmail()).setValue(person, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if (databaseError == null) {
                                        Toast.makeText(getBaseContext(), "save ok", Toast.LENGTH_LONG).show();

                                    } else {
                                        Toast.makeText(getBaseContext(), "save Err" + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                                        databaseError.toException().printStackTrace();

                                    }


                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tvres.setText("Users:"+dataSnapshot.getChildrenCount()+"\n");
                for (DataSnapshot dst:dataSnapshot.getChildren())
                {
                    Person person =dst.getValue(Person.class);
                    tvres.append(person.toString()+"\n");
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        reference.addValueEventListener(valueEventListener);
        Query query=reference.orderByChild("email").equalTo("samih1079@gmail.com");
        query.addValueEventListener(valueEventListener);
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                tvres.setText("Users:"+dataSnapshot.getChildrenCount()+"\n");
//                for (DataSnapshot dst:dataSnapshot.getChildren())
//                {
//                    Person user=dst.getValue(Person.class);
//                    tvres.append(user.toString()+"\n");
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.itmlogout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this,LogInActivity.class));
                finish();
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        myTaskAdapter = new MyTaskAdapter(this, R.layout.item_my_task);
//
//        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("samih@yahoo_com");
//        reference.child("MyTasks").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                myTaskAdapter.clear();
//                int i = 0;
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    MyTask myTask = snapshot.getValue(MyTask.class);
//                    myTaskAdapter.add(myTask);
////                    Toast.makeText(MngTaskActivity.this,"DataSnapshot"+(i++),
////                            Toast.LENGTH_LONG).show();
//                }
//                listView.setAdapter(myTaskAdapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }
}
