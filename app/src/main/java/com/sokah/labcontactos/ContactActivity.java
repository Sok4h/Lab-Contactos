package com.sokah.labcontactos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class ContactActivity extends AppCompatActivity {

    private EditText inputContacto,inputPhone;
    private String userName;
    private Button btnAdd;
    private User activeUser;
    private FirebaseDatabase db;
    private  ValueEventListener valueEventListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Bundle bundle=getIntent().getExtras();
        inputContacto=findViewById(R.id.inputContact);
        inputPhone=findViewById(R.id.inputPhone);
        btnAdd=findViewById(R.id.btnAddContact);
        userName=bundle.getString("name",null);
        db=FirebaseDatabase.getInstance();
        UserExist();

    }

    private void LoadDatabase() {
       db.getReference().child("Contacts").orderByChild("id").equalTo(activeUser.getId()).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for (DataSnapshot child:snapshot.getChildren()) {

                   Contact tempC = child.getValue(Contact.class);
                   Log.e("TAG", tempC.getPhoneNumber() );
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
    }

    private void UserExist(){

        db.getReference().child("User").orderByChild("name").equalTo(userName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                activeUser=snapshot.getValue(User.class);
                if(activeUser==null){
                    String id = UUID.randomUUID().toString();
                    User tempUser = new User(id, userName);
                    db.getReference("User").child(id).setValue(tempUser);
                    activeUser=tempUser;
                    Log.e("TAG", activeUser.getId());
                }

                Contact contact = new Contact(activeUser.getId(),"xd","3116323350");
                db.getReference().child("Contacts").child(contact.id).setValue(contact);
                LoadDatabase();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onPause() {
        db.getReference().removeEventListener(valueEventListener);
        super.onPause();
    }
}