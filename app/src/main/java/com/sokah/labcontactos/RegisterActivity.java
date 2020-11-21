package com.sokah.labcontactos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText name,email,password,password2;
    Button btnRegister;
    TextView login;
    FirebaseDatabase db;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=findViewById(R.id.inputNameRegister);
        email=findViewById(R.id.inputEmailRegister);
        password=findViewById(R.id.inputPasswordRegister);
        password2=findViewById(R.id.inputPaswwordRegister2);
        btnRegister=findViewById(R.id.btnRegister);
        login=findViewById(R.id.textLogin);
        db=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(
                (v)->{

                    //verifica si hay un campo vacio
                    if(name.getText().toString().isEmpty()||email.getText().toString().isEmpty() ||password.getText().toString().isEmpty()||password2.getText().toString().isEmpty()){

                        Toast.makeText(this, "Please fill all the inputs", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        //verifica si las contraseñas son las mismas
                        if(password.getText().toString().equals(password2.getText().toString())){

                            auth.createUserWithEmailAndPassword(email.getText().toString(),password.toString()).addOnCompleteListener(

                                    (task)->{

                                        if(task.isSuccessful()){



                                        }

                                    }
                            );

                        }

                        else{

                            Toast.makeText(this, "Passwords doesn´t match", Toast.LENGTH_SHORT).show();

                        }


                    }

                }
        );
    }
}