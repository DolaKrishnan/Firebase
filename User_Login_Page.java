package com.example.projecttest1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class User_Login_Page extends AppCompatActivity {
   
    private FirebaseAuth mAuth;
    Button button;
    EditText txt_confirmpassword,txt_email,txt_password;
    String conformpassword,email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uset__login__page);

        txt_email=findViewById(R.id.emailinput);
        txt_password=findViewById(R.id.passwordinput);
        txt_confirmpassword=findViewById(R.id.cnfirmpasswordinput);
        button=findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    email = txt_email.getText().toString().trim();
                    password = txt_password.getText().toString().trim();
                    conformpassword = txt_confirmpassword.getText().toString().trim();

                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(Uset_Login_Page.this, "Please Enter Ther Email", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(password)) {
                        Toast.makeText(Uset_Login_Page.this, "Please Enter The Password", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(conformpassword)) {
                        Toast.makeText(Uset_Login_Page.this, "Please Enter The ConformPassword", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (password.length() < 6) {
                        Toast.makeText(Uset_Login_Page.this, "Password is too Short", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (password.equals(conformpassword)) {
                        mAuth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(Uset_Login_Page.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Toast.makeText(Uset_Login_Page.this, "Login Successes", Toast.LENGTH_SHORT).show();
                                       
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Toast.makeText(Uset_Login_Page.this, "Login Failed OR User Not Exist", Toast.LENGTH_SHORT).show();
                                        }// ...
                                    }
                                });
                    } else {
                        Toast.makeText(Uset_Login_Page.this, "Password Doesn't Match!!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
                    Toast.makeText(Uset_Login_Page.this, e + "", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
