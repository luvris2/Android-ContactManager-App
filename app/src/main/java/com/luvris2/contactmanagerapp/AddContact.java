package com.luvris2.contactmanagerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.luvris2.contactmanagerapp.data.DatabaseHandler;
import com.luvris2.contactmanagerapp.model.Contact;

import java.util.ArrayList;

public class AddContact extends AppCompatActivity {

    Button btnSave;
    EditText editName, editPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        btnSave = findViewById(R.id.btnEdit);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editName = findViewById(R.id.editName);
                editPhone = findViewById(R.id.editPhone);

                String name = editName.getText().toString();
                String phone = editPhone.getText().toString();

                if ( name.isEmpty() || phone.isEmpty() ){
                    Toast.makeText(getApplicationContext(), "데이터 입력을 올바르게 해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // DB Test, insert data
                DatabaseHandler db = new DatabaseHandler(AddContact.this);
                // insert
                Contact contact = new Contact(name, phone);
                db.addContact(contact);

                // data access
                ArrayList<Contact> contactList = db.getAllContact();
                for (Contact data : contactList) {
                    Log.i("MyContact", "id : " + data.id + ", name : " + data.name + ", phone : " + data.phone);
                }

                Toast.makeText(getApplicationContext(), "연락처가 추가되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}