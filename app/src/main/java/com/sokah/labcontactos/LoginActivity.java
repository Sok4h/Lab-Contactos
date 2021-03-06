package com.sokah.labcontactos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText email,password;
    Button btnLogin;
    FirebaseAuth auth;
    TextView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email =findViewById(R.id.inputEmailLogin);
        password=findViewById(R.id.inputPasswordLogin);
        btnLogin=findViewById(R.id.btnLogin);
        auth= FirebaseAuth.getInstance();
        register=findViewById(R.id.textRegister);
        register.setOnClickListener(
                (v)->{
                    Intent intent = new Intent(this,RegisterActivity.class);
                    startActivity(intent);
                }
        );

        btnLogin.setOnClickListener(
                (v)->{

                    //verifica si hay algun campo vacio
                    if(email.getText().toString().isEmpty()||password.getText().toString().isEmpty()){

                        Toast.makeText(this, "Please fill all the inputs", Toast.LENGTH_SHORT).show();

                    }

                    else{
                        Log.e("TAG", password.getText().toString());
                        auth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(

                                task -> {

                                    //verifica si el login se realizo con exito
                                    if(task.isSuccessful()){

                                        Intent intent = new Intent(this,ContactActivity.class);
                                        startActivity(intent);

                                    }

                                    else{

                                        Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                        );

                    }
                }
        );

    }
}