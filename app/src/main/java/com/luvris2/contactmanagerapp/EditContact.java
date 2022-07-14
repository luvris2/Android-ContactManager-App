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

public class EditContact extends AppCompatActivity {

    Button btnEdit;
    EditText editName, editPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        btnEdit = findViewById(R.id.btnEdit);
        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);

        int index = getIntent().getIntExtra("index", 0);
        DatabaseHandler db = new DatabaseHandler(EditContact.this);
        Contact contact_id = db.getContact(index);
        editName.setText(contact_id.name);
        editPhone.setText(contact_id.phone);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String name = editName.getText().toString();
                String phone = editPhone.getText().toString();

                if ( name.isEmpty() || phone.isEmpty() ){
                    Toast.makeText(getApplicationContext(), "데이터 입력을 올바르게 해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // DB Test, insert data
                DatabaseHandler db = new DatabaseHandler(EditContact.this);
                // update
                Contact contact = new Contact(index, name, phone);
                db.updateContact(contact);

                // data access
                ArrayList<Contact> contactList = db.getAllContact();
                for (Contact data : contactList) {
                    Log.i("MyContact", "id : " + data.id + ", name : " + data.name + ", phone : " + data.phone);
                }

                Toast.makeText(getApplicationContext(), "연락처가 수정되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}