package com.example.mypc.contactmanager;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mypc.model.Contact;

public class MessageSMS extends AppCompatActivity {
    TextView txtReceiver;
    EditText editSend;
    ImageButton btnSend;

    Contact selectedContact;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_sms);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager sms = SmsManager.getDefault();
                Intent msgsent = new Intent("ACTION_MSG_SENT");

                PendingIntent pendingMsgSent = PendingIntent.getBroadcast(MessageSMS.this, 0, msgsent,0);

                registerReceiver(new BroadcastReceiver() {
                    public void onReceive(Context context, Intent intent) {
                        int result = getResultCode();
                        String msg="Send OK";
                        if (result != Activity.RESULT_OK) {
                            msg="Send failed";
                        }
                        Toast.makeText(MessageSMS.this, msg,
                                Toast.LENGTH_LONG).show();
                    }
                }, new IntentFilter("ACTION_MSG_SENT"));

                sms.sendTextMessage(selectedContact.getPhone(), null, editSend.getText().toString()+"",
                        pendingMsgSent, null);


            }
        });
    }

    private void addControls() {
        txtReceiver = (TextView) findViewById(R.id.txtReceiver);
        editSend    = (EditText) findViewById(R.id.editSend);
        btnSend     = (ImageButton) findViewById(R.id.btnSend);

        Intent intent = getIntent();
        selectedContact = (Contact) intent.getSerializableExtra("CONTACT");
        txtReceiver.setText(selectedContact.getName() + "-" + selectedContact.getPhone());

    }
}
