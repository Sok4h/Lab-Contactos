package com.sokah.labcontactos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText inputNombre;
    private Button btnIngresar;
    private FirebaseDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputNombre=findViewById(R.id.inputName);
        btnIngresar=findViewById(R.id.btnIngresar);
        db= FirebaseDatabase.getInstance();
        btnIngresar.setOnClickListener(
                (v)->{

                    if(inputNombre.getText().toString().trim().length()==0){

                        Toast.makeText(this, "Por favor ingrese un nombre valido", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        Intent intent = new Intent(this,ContactActivity.class);
                        intent.putExtra("name",inputNombre.getText().toString());
                        startActivity(intent);

                    }
                }
        );
    }
}