package com.example.ebusiness;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.ebusiness.Model.users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

@SuppressWarnings("EqualsReplaceableByObjectsCall")
public class LoginActivity extends AppCompatActivity {

    private EditText inputPhoneNumber, InputPassword;
    private Button LoginButton;
    private ProgressDialog LoadingBar;
    private String parentDbName = "users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginButton = findViewById(R.id.login_btn);
        inputPhoneNumber  = findViewById(R.id.login_phone_number_input);
        InputPassword = findViewById(R.id.login_password);
        LoadingBar= new ProgressDialog(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loginUsers();
            }
        });
    }

     private void loginUsers()
    {
        String Phone = inputPhoneNumber.getText().toString();
        String Password = InputPassword.getText().toString();


        if (TextUtils.isEmpty(Password))
        {
            Toast.makeText(this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(Phone))
        {
            Toast.makeText(this, "Please Enter Your Phone Number", Toast.LENGTH_SHORT).show();
        }
        else{
            LoadingBar.setTitle("LoginAccount");
            LoadingBar.setMessage("Please wait ,while we are checking credentials");
            LoadingBar.setCanceledOnTouchOutside(false);
            LoadingBar.show();


            AllowAccessToAccount();
        }

    }

    private void AllowAccessToAccount()
    {


        final DatabaseReference RootRef = FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(!dataSnapshot.child("parentDbName").exists())
                {
                    users UserData = dataSnapshot.child(parentDbName).getValue(users.class);

                    if (UserData.getPhone().equals(phone.))
                    {
                        if (UserData.getPassword().equals("password"))
                        {
                            Toast.makeText(LoginActivity.this, "Login successful...", Toast.LENGTH_SHORT).show();
                            LoadingBar.dismiss();

                            Intent intent = new Intent (LoginActivity.this ,HomeActivity.class) ;
                            startActivity(intent);
                        }
                    }



                }
                else{
                    Toast.makeText(LoginActivity.this, "Account with this" +"phone"+"Number Do not exist", Toast.LENGTH_SHORT).show();
                    LoadingBar.dismiss();
                    Toast.makeText(LoginActivity.this, "Please Create Another Account", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
