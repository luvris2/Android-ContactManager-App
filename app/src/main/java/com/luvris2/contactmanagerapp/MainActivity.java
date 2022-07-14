package com.luvris2.contactmanagerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.luvris2.contactmanagerapp.adapter.ContactAdapter;
import com.luvris2.contactmanagerapp.data.DatabaseHandler;
import com.luvris2.contactmanagerapp.model.Contact;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;
    RecyclerView recyclerView;
    ContactAdapter adapter;
    ArrayList<Contact> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        // 연락처 추가 액티비티로 이동
        btnAdd = findViewById(R.id.btnEdit);
        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddContact.class);
            startActivity(intent);
        });
   }

    @Override
    protected void onResume() {
        super.onResume();

        // DB에 주소록을 가져와 리사이클러뷰에 출력
        DatabaseHandler db = new DatabaseHandler(MainActivity.this);
        contactList = db.getAllContact();

        adapter = new ContactAdapter(MainActivity.this, contactList);

        recyclerView.setAdapter(adapter);

        // data access
        //DatabaseHandler db = new DatabaseHandler(MainActivity.this);
        ArrayList<Contact> contactList = db.getAllContact();
        // data print
        for (Contact data : contactList) {
            Log.i("MyContact", "id : " + data.id + ", name : " + data.name + ", phone : " + data.phone);
        }
    }

    void dbTest_access() {
        // DB test, access data
        DatabaseHandler db = new DatabaseHandler(MainActivity.this);
        // data access : All
        ArrayList<Contact> contactList = db.getAllContact();
        for (Contact data : contactList) {
            Log.i("MyContact", "id : " + data.id + ", name : " + data.name + ", phone : " + data.phone);
        }

        // data access : specific
        Contact contact_id = db.getContact(1);
        Log.i("MyContact_id", "id : " + contact_id.id + ", name : " + contact_id.name + ", phone : " + contact_id.phone);
    }

    void dbTest_insert() {
       // DB Test, insert data
       DatabaseHandler db = new DatabaseHandler(MainActivity.this);
       // insert
       Contact contact = new Contact("홍길동", "010-1234-5678");
       db.addContact(contact);
    }

   void dbTest_update() {
        // DB test, update data
       DatabaseHandler db = new DatabaseHandler(MainActivity.this);
       // update
       Contact contact = new Contact(1, "김철수","010-1111-1111");
       db.updateContact(contact);
   }

   void dbTest_delete() {
        // DB test, delete data
       DatabaseHandler db = new DatabaseHandler(MainActivity.this);
       // delete, name&phone dummy
       Contact contact = new Contact(2, "test", "test");
       db.deleteContact(contact);
   }
}