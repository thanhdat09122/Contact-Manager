package com.example.mypc.contactmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mypc.adapter.ContactAdapter;
import com.example.mypc.model.Contact;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText editName, editPhone;
    Button btnSave;
    ListView lvContact;
    ArrayList<Contact> arrayList;
    ContactAdapter contactAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();

    }

    private void addEvents() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact contact = new Contact(editName.getText().toString(), editPhone.getText().toString());
                arrayList.add(contact);
                contactAdapter.notifyDataSetChanged();
            }
        });
    }

    private void addControls() {
        editName  = (EditText) findViewById(R.id.editName);
        editPhone = (EditText) findViewById(R.id.editPhone);
        btnSave   = (Button) findViewById(R.id.btnSave);
        lvContact = (ListView) findViewById(R.id.lvContact);
        arrayList = new ArrayList<>();
        contactAdapter = new ContactAdapter(MainActivity.this, R.layout.item, arrayList);
        lvContact.setAdapter(contactAdapter);
    }
}
