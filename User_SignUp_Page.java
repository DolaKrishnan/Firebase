package com.example.projecttest1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.FirebaseDatabase;

public class User_SignUp_Page extends AppCompatActivity {
    TextView tv;
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
        setContentView(R.layout.activity_user__sign_up__page);




        txt_email=findViewById(R.id.emailinput);
        txt_password=findViewById(R.id.passwordinput);
        txt_confirmpassword=findViewById(R.id.cnfirmpasswordinput);
        b=findViewById(R.id.nextpage);
        tv=findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();



        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(User_SignUp_Page.this, LoginPage.class);
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
                    conformpassword=txt_confirmpassword.getText().toString().trim();

                    if (TextUtils.isEmpty(email))
                    {
                        Toast.makeText(User_SignUp_Page.this, "Please Enter Ther Email", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(password)){
                        Toast.makeText(User_SignUp_Page.this, "Please Enter The Password", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(conformpassword)){
                        Toast.makeText(User_SignUp_Page.this, "Please Enter The ConformPassword", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (password.length()<6){
                        Toast.makeText(User_SignUp_Page.this, "Password is too Short", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (password.equals(conformpassword)) {


                        mAuth.fetchSignInMethodsForEmail(email)
                                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                        boolean check = !task.getResult().getSignInMethods().isEmpty();
                                        if (!check) {
                                            flag = 1;
                                        } else {
                                            Toast.makeText(User_SignUp_Page.this, "Email Already Present", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }
                                });
                        //Farmer Details Signup
                        if (flag == 1) {
                            final ProgressDialog progressDialog=new ProgressDialog(User_SignUp_Page.this);
                            progressDialog.setMessage("Please Wait");
                            progressDialog.show();
                            mAuth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(User_SignUp_Page.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                // Sign in success, update UI with the signed-in user's information
                                                progressDialog.dismiss();
                                                Toast.makeText(User_SignUp_Page.this, "Account Verified", Toast.LENGTH_SHORT).show();
                                                Intent intent=new Intent(User_SignUp_Page.this,LoginPage.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                // If sign in fails, display a message to the user.
                                                Toast.makeText(User_SignUp_Page.this, "Signup Error", Toast.LENGTH_SHORT).show();
                                                return;
                                            }

                                            // ...
                                        }
                                    });
                            //Farmer Image Upload
                            flag = 0;
                        }
                    }
                    else {
                        Toast.makeText(User_SignUp_Page.this, "Password Doesn't Match!!", Toast.LENGTH_SHORT).show();
                        return;
                    }


                }catch (Exception e)
                {
                    Toast.makeText(User_SignUp_Page.this, e+"", Toast.LENGTH_SHORT).show();
                }
            }
        });
        getWindow().setFlags(WindowManager.LayoutParams.FIRST_APPLICATION_WINDOW,WindowManager.LayoutParams.FIRST_APPLICATION_WINDOW);
    }
}
