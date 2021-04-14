package com.example.ft_hangouts;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class contactResume extends AppCompatActivity {
    private FloatingActionButton fab1;
    private DbManager DbManager;
    private TextView t1NameFirst;
    private TextView t2Phone;
    private TextView t3Mail;
    private TextView t4Postal;
    private long nbContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_resume);
        Intent intent = getIntent();

        Toolbar toolbar = findViewById(R.id.toolcustom);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        t1NameFirst = findViewById(R.id.NamFirstView);
        t2Phone = findViewById(R.id.PhoneView);
        t3Mail = findViewById(R.id.MailView);
        t4Postal = findViewById(R.id.PostalView);
        if (intent != null) {
            nbContact = intent.getLongExtra("contactNb", 0);
        }
        printContact();
        fab1 = findViewById(R.id.goChat);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(contactResume.this, sms_screen.class);
                i.putExtra("contactNb", nbContact);
                startActivity(i);
            }
        });
    }

    public void printContact() {
        DbManager = new DbManager(contactResume.this);
        ContactData contact =  DbManager.readOne((int) nbContact + 1);
        DbManager.close();

        t1NameFirst.setText(contact.getFirst().toString() + " " + contact.getName().toString());
        t2Phone.setText(contact.getPhone().toString());
        t3Mail.setText(contact.getMail().toString());
        t4Postal.setText(contact.getPostal().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contact, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                Intent i = new Intent(contactResume.this, editContact.class);
                i.putExtra("contactNb", nbContact);
                startActivity(i);
                return true;
            case R.id.deleteContact:
                DbManager = new DbManager(contactResume.this);
                DbManager.dropContact((int) nbContact + 1);
                DbManager.close();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        printContact();
    }
}