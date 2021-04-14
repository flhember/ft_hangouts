package com.example.ft_hangouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class sms_screen extends AppCompatActivity {
    private long nbContact;
    private DbManager DbManager;
    private String msg;
    private ImageButton Ib1;
    private EditText sms;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    private ListView l1;
    private ContactData contact;
    private List<SmsData> smsList;
    IntentFilter intentFilter;

    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras().getString("number").equals(contact.getPhone())) {
                setList();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_screen);
        Intent intent = getIntent();
        if (intent != null) {
            nbContact = intent.getLongExtra("contactNb", 0);
        }

        l1 = findViewById(R.id.messages_view);
        setList();

        Toolbar toolbar = findViewById(R.id.toolcustom);
        toolbar.setTitle(contact.getFirst());
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Ib1 = findViewById(R.id.smsButton);
        Ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sms = findViewById(R.id.smsSend);
                msg = sms.getText().toString();
                sendMsg(contact.getPhone(), msg);
                DbManager.newSms(contact.getPhone(), msg, "me");
                setList();
                sms.setText("");
            }
        });
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");
        checkPermissions();
    }



    private void sendMsg(String number, String msg) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null, msg, null, null);
    }

    public void setList() {
        DbManager = new DbManager(sms_screen.this);
        contact = DbManager.readOne((int) nbContact + 1);
        smsList =  DbManager.getSms(contact.getPhone());
        SmsAdapter smsAdapter = new SmsAdapter(sms_screen.this, R.layout.sms_screen, smsList);
        l1.setAdapter(smsAdapter);
        smsAdapter.notifyDataSetChanged();
        scrollMyListViewToBottom();
        DbManager.close();
    }

    private void scrollMyListViewToBottom() {
        l1.post(new Runnable() {
            @Override
            public void run() {
                l1.setSelection(smsList.size() - 1);
            }
        });
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(getApplicationContext(), "Non", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }

    @Override
    protected void onResume() {
        registerReceiver(intentReceiver, intentFilter);
        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(intentReceiver);
        super.onPause();
    }
}