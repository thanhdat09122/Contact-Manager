package com.example.mypc.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mypc.contactmanager.MessageSMS;
import com.example.mypc.contactmanager.R;
import com.example.mypc.model.Contact;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {
    Activity context;
    int resource;
    List<Contact> objects;

    public ContactAdapter(@NonNull Activity context, int resource, @NonNull List<Contact> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(this.resource, null);

        TextView txtName = view.findViewById(R.id.txtName);
        TextView txtPhone = view.findViewById(R.id.txtPhone);
        ImageButton imgCall = view.findViewById(R.id.imgCall);
        ImageButton imgSms = view.findViewById(R.id.imgSms);
        ImageButton imgDelete = view.findViewById(R.id.imgDelete);

        final Contact contact = this.objects.get(position);
        txtName.setText(contact.getName());
        txtPhone.setText(contact.getPhone());

        imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SolveCall(contact);
            }
        });
        imgSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SolveSms(contact);
            }
        });
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SolveDelete(contact);
            }
        });
        return view;
    }

    private void SolveDelete(Contact contact) {
        this.remove(contact);
    }

    private void SolveSms(Contact contact) {
        Intent intent = new Intent(this.context, MessageSMS.class);
        intent.putExtra("CONTACT",contact);
        this.context.startActivity(intent);
    }

    private void SolveCall(Contact contact) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri uri = Uri.parse("tel:" + contact.getPhone());
        intent.setData(uri);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        this.context.startActivity(intent);
    }
}
