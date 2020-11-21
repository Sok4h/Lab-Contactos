package com.sokah.labcontactos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class ContactActivity extends AppCompatActivity {

    private EditText inputContacto, inputPhone;
    private String userName;
    private Button btnAdd;
    private User activeUser;
    private FirebaseDatabase db;
    private ContactAdapter contactAdapter;
    private ValueEventListener valueEventListener;
    private ListView listaContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Bundle bundle = getIntent().getExtras();
        db = FirebaseDatabase.getInstance();
        inputContacto = findViewById(R.id.inputContact);
        inputPhone = findViewById(R.id.inputPhone);
        btnAdd = findViewById(R.id.btnAddContact);
        listaContactos = findViewById(R.id.contactList);
        userName = bundle.getString("name", null);
        contactAdapter = new ContactAdapter();
        //UserExist();
        LoadDatabase();
        btnAdd.setOnClickListener(
                (v) -> {

                    if (inputContacto.getText().toString().isEmpty() || inputPhone.getText().toString().isEmpty()) {

                        Toast.makeText(this, "Por favor verifique los datos ingresados", Toast.LENGTH_SHORT).show();
                    } else {

                        db.getReference().child("Contacts").orderByChild("name").equalTo(inputContacto.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                if (snapshot.exists()) {

                                    Toast.makeText(ContactActivity.this, "Ya existe un contacto con este nombre", Toast.LENGTH_SHORT).show();
                                } else {
                                    String id = UUID.randomUUID().toString();
                                    Contact tempc = new Contact(id, activeUser.getId(), inputContacto.getText().toString(), inputPhone.getText().toString());
                                    db.getReference().child("Contacts").child(id).setValue(tempc);
                                    inputPhone.setText("");
                                    inputContacto.setText("");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }


                }
        );

        listaContactos.setAdapter(contactAdapter);
    }

    private void LoadDatabase() {

        valueEventListener = db.getReference().child("Contacts").orderByChild("idUser").equalTo(activeUser.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                contactAdapter.ClearContacts();
                for (DataSnapshot child : snapshot.getChildren()) {

                    Contact tempC = child.getValue(Contact.class);
                    Log.e("TAG", tempC.getName());
                    contactAdapter.AddContact(tempC);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    /*private void UserExist() {

        db.getReference().child("User").orderByChild("name").equalTo(userName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        activeUser = ds.getValue(User.class);
                    }

                } else {
                    String id = UUID.randomUUID().toString();
                    User tempUser = new User(id, userName);
                    db.getReference("User").child(id).setValue(tempUser);
                    activeUser = tempUser;
                    Log.e("TAG", activeUser.getId());
                }

                LoadDatabase();

            }

            @Override
            public void onCancelled(DatabaseError error) {

                throw error.toException();
            }
        });

    }

     */

    @Override
    protected void onPause() {
        db.getReference().removeEventListener(valueEventListener);
        super.onPause();
    }



}