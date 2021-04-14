package com.example.ft_hangouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class editContact extends AppCompatActivity {
    private DbManager DbManager;
    private EditText t1Name;
    private EditText t1First;
    private EditText t2Phone;
    private EditText t3Mail;
    private EditText t4Postal;
    private long nbContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_contact);

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


        t1Name = findViewById(R.id.EtName);
        t1First = findViewById(R.id.EtFirst);
        t2Phone = findViewById(R.id.EtPhone);
        t3Mail = findViewById(R.id.EtEmail);
        t4Postal = findViewById(R.id.EtPostal);
        if (intent != null) {
            nbContact = intent.getLongExtra("contactNb", 0);
        }
        DbManager = new DbManager(editContact.this);
        ContactData contact = DbManager.readOne((int) nbContact + 1);
        DbManager.close();

        t1First.setText(contact.getFirst().toString());
        t1Name.setText(contact.getName().toString());
        t2Phone.setText(contact.getPhone().toString());
        t3Mail.setText(contact.getMail().toString());
        t4Postal.setText(contact.getPostal().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                saveContact();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void saveContact() {
        int err;

        err = 1;
        if (t1First.length() == 0) {
            t1First.setError(getString(R.string.firstErreur));
            err = -1;
        }
        if (t1Name.length() == 0) {
            t1Name.setError(getString(R.string.nameErreur));
            err = -1;
        }
        if (t2Phone.length() == 0) {
            t2Phone.setError(getString(R.string.phoneErreur));
            err = -1;
        }
        if (t3Mail.length() == 0) {
            t3Mail.setError(getString(R.string.mailErreur));
            err = -1;
        }
        if (t4Postal.length() == 0) {
            t4Postal.setError(getString(R.string.postalErreur));
            err = -1;
        }
        if (err == 1) {
            DbManager = new DbManager(editContact.this);
            DbManager.modifyContact( (int) nbContact + 1, t1First.getText().toString(), t1Name.getText().toString(),
                    t2Phone.getText().toString(), t3Mail.getText().toString(), t4Postal.getText().toString());
            DbManager.close();
            this.finish();
        }
        this.finish();
    }
}