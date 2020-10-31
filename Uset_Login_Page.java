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

public class Uset_Login_Page extends AppCompatActivity {
    TextView tvTimeMsg,textView;
    FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;
    int flag=0;
    Button b;
    String len;
    EditText txt_confirmpassword,txt_email,txt_password;
    String conformpassword,email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uset__login__page);
        textView=findViewById(R.id.register);
        tvTimeMsg=findViewById(R.id.text);
        txt_email=findViewById(R.id.emailinput);
        txt_password=findViewById(R.id.passwordinput);
        txt_confirmpassword=findViewById(R.id.cnfirmpasswordinput);
        b=findViewById(R.id.nextpage);

        mAuth = FirebaseAuth.getInstance();


        Calendar calendar = Calendar.getInstance();
        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12) {

            tvTimeMsg.setText("HI! Good Morning");
        } else if (timeOfDay >= 12 && timeOfDay < 15) {

            tvTimeMsg.setText("HI! Good Afternoon");
        } else if (timeOfDay >= 15 && timeOfDay < 18) {

            tvTimeMsg.setText("HI! Good Evening");
        } else {
            tvTimeMsg.setText("HI! Good Night");
        }
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Uset_Login_Page.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        b.setOnClickListener(new View.OnClickListener() {
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
                                          Intent intent = new Intent(Uset_Login_Page.this, Demo.class);
                                            startActivity(intent);
                                            finish();
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
