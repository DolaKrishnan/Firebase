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
    
    private FirebaseAuth mAuth;
    int flag=0;
    Button button;
    EditText txt_confirmpassword,txt_email,txt_password;
    String conformpassword,email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__sign_up__page);

        txt_email=findViewById(R.id.emailinput);
        txt_password=findViewById(R.id.passwordinput);
        txt_confirmpassword=findViewById(R.id.cnfirmpasswordinput);
        button=findViewById(R.id.signup);
        mAuth = FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
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

			//This Step is used to check whether the email is already present in Database are not., if no Thn Flag=1 if already Presnt thn flag=0
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
                        //If email not alreday present in Firebase thn flag=1 conditioin satiesfies and will create an account 
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
                                                
                                                finish();
                                            } else {
                                                // If sign in fails, display a message to the user.
                                                Toast.makeText(User_SignUp_Page.this, "Signup Error", Toast.LENGTH_SHORT).show();
                                                return;
                                            }

                                            // ...
                                        }
                                    });
                            
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
	// These line is used for the application to occupy the entire window 
        getWindow().setFlags(WindowManager.LayoutParams.FIRST_APPLICATION_WINDOW,WindowManager.LayoutParams.FIRST_APPLICATION_WINDOW);
    }
}
