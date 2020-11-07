package com.sokah.labcontactos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText inputNombre;
    Button btnIngresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputNombre=findViewById(R.id.inputName);
        btnIngresar=findViewById(R.id.btnIngresar);

        btnIngresar.setOnClickListener(
                (v)->{

                    //Codigo btn

                }
        );
    }
}