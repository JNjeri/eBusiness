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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class RegisterActivity extends AppCompatActivity {

    private Button createAccountButton;
    private EditText inputName, inputPhoneNumber, InputPassword;
    private ProgressDialog LoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        createAccountButton = findViewById(R.id.register_btn);
        inputName = findViewById(R.id.register_username_input);
        inputPhoneNumber  = findViewById(R.id.register_phone_number_input);
        InputPassword = findViewById(R.id.register_password);
        LoadingBar= new ProgressDialog(this);


        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                CreateAccount();
            }
        });
    }
    private void CreateAccount()
    {
        String Name = inputName.getText().toString();
        String Phone = inputPhoneNumber.getText().toString();
        String Password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(Name))
        {
            Toast.makeText(this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(Password))
        {
            Toast.makeText(this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(Phone))
        {
            Toast.makeText(this, "Please Enter Your Phone Number", Toast.LENGTH_SHORT).show();
        }

        else
        {
            LoadingBar.setTitle("CreateAccount");
            LoadingBar.setMessage("Please wait ,while we are checking credentials");
            LoadingBar.setCanceledOnTouchOutside(false);
            LoadingBar.show();

            ValidatePhoneNumber( Name,Phone,Password);
        }


    }

    @SuppressWarnings("deprecation")
    private void ValidatePhoneNumber(final String name, final String phone, final String password)
    {
        final DatabaseReference RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(!(dataSnapshot.child("User").child("Name").exists()))
                //putting data in the database//
                {
                    HashMap< String,Object> userDataMap=new HashMap<>();
                    userDataMap.put("Name", name);
                    userDataMap.put("Phone", phone);
                    userDataMap.put("Password", password);

                    RootRef.child("User").child(phone).updateChildren(userDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Account Succesfully created", Toast.LENGTH_SHORT).show();
                                        LoadingBar.dismiss();

                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }else
                                    {
                                        LoadingBar.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Network Error:Please Try again", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }


                else
                {
                    Toast.makeText(RegisterActivity.this, "This" +"Phone" +" Number Already Exists", Toast.LENGTH_SHORT).show();
                    LoadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please Try Again Using Another Number", Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent (RegisterActivity.this ,MainActivity.class) ;
                    startActivity(intent);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

            @SuppressWarnings("deprecation")
            public void fireBase(){
                final DatabaseReference RootRef = FirebaseDatabase.getInstance().getReference();
                RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
