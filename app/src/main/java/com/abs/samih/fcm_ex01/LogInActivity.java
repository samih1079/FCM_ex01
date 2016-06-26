package com.abs.samih.fcm_ex01;

import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {

    //1.
    private FirebaseAuth auth;

    private EditText etEmail;
    private EditText etPassw;
    private Button btnSignIp, btnNewAcount;

    ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //2.
        auth=FirebaseAuth.getInstance();

        etEmail= (EditText) findViewById(R.id.etemail);
        etPassw= (EditText) findViewById(R.id.etpassword);
         btnSignIp= (Button) findViewById(R.id.btnSignIn);
        btnSignIp.setOnClickListener(clickLis);
        btnNewAcount=(Button)findViewById(R.id.btnNewAcount);
        btnNewAcount.setOnClickListener(clickLis);
    }
    private View.OnClickListener clickLis=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.btnSignIn)
            {
                //8
                String email=etEmail.getText().toString();
                String passw=etPassw.getText().toString();
                signIn(email,passw);
            }
            if(v==btnNewAcount)
            {
                Intent intent=new Intent(LogInActivity.this,SignUpActivity.class);
                startActivity(intent);
            }

        }
    };



    //3.
    private FirebaseAuth.AuthStateListener authStateListener=new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            //4.
            FirebaseUser user=firebaseAuth.getCurrentUser();
            if(user!=null)
            {
                //user is signed in
                Toast.makeText(LogInActivity.this, "user is signed in.", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(LogInActivity.this,MngTaskActivity.class);
                startActivity(intent);
                finish();
            }
            else
            {
                //user signed out
                Toast.makeText(LogInActivity.this, "user signed out.", Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(LogInActivity.this,MainFCMActivity.class);
//                startActivity(intent);
//                finish();
            }
        }
    };
    //5
    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }
    //6
    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener!=null)
            auth.removeAuthStateListener(authStateListener);
    }
    //7
    private void signIn(String email, String passw) {
        auth.signInWithEmailAndPassword(email,passw).addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    //8
                    Toast.makeText(LogInActivity.this, "signIn Successful.", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(LogInActivity.this,MngTaskActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(LogInActivity.this, "signIn failed."+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    task.getException().printStackTrace();
                }
            }
        });
    }
}
