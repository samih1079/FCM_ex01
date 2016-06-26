package com.abs.samih.fcm_ex01;

import android.content.Intent;
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
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpActivity extends AppCompatActivity {

    //1.
    private FirebaseAuth auth;

    //0.
    EditText etEmail;
    EditText etFuName;
    EditText etPassw1;
    EditText etPassw2;
    Button btnSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //2.
        auth=FirebaseAuth.getInstance();

        //0.
         etEmail= (EditText) findViewById(R.id.etEmail);
         etFuName= (EditText) findViewById(R.id.etFulName);
         etPassw1= (EditText) findViewById(R.id.etPassw1);
         etPassw2= (EditText) findViewById(R.id.etPassw2);
         btnSignUp= (Button) findViewById(R.id.btnSignUp);
        //0.
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //9
                String email=etEmail.getText().toString();
                String passw=etPassw1.getText().toString();
                creatAcount(email,passw);

            }
        });

    }


        //3.
    private FirebaseAuth.AuthStateListener authStateListener=new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            //4.
            FirebaseUser user=firebaseAuth.getCurrentUser();
            if(user!=null)
            {
                //user is signed in
                Toast.makeText(SignUpActivity.this, "user is signed in.", Toast.LENGTH_SHORT).show();

            }
            else
            {
                //user signed out
                Toast.makeText(SignUpActivity.this, "user signed out.", Toast.LENGTH_SHORT).show();

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
    private void creatAcount(String email, String passw) {

        auth.createUserWithEmailAndPassword(email,passw).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    //8
                    Toast.makeText(SignUpActivity.this, "Authentication Successful.", Toast.LENGTH_SHORT).show();
                //11
                   updateUserProfile(task.getResult().getUser());

//                    Intent intent=new Intent(SignUpActivity.this,LogInActivity.class);
//                    startActivity(intent);
                    //return to logInActivity
                    finish();
                }
                else
                {
                    Toast.makeText(SignUpActivity.this, "Authentication failed."+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    task.getException().printStackTrace();
                }
            }
        });
    }
    //10
    private void updateUserProfile(FirebaseUser user)
    {


        UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder();
        builder.setDisplayName(etFuName.getText().toString());

        UserProfileChangeRequest profileUpdate=builder.build();

        user.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(SignUpActivity.this, "profileUpdate Successful.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(SignUpActivity.this, "profileUpdate failed."+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    task.getException().printStackTrace();
                }
            }
        });
    }
}
