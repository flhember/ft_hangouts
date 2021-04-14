package com.example.ft_hangouts;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class new_Contact2 extends AppCompatActivity {
    private EditText First, Name, Phone, Mail, Postal;
    private String firstCheck;
    private DbManager DbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_contact2);

        Toolbar toolbar = findViewById(R.id.toolcustom);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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

        First = findViewById(R.id.TeFirst);
        Name = findViewById(R.id.TeName);
        Phone = findViewById(R.id.TePhone);
        Mail = findViewById(R.id.TeEmail);
        Postal = findViewById(R.id.TePostal);
        err = 1;
        if (First.length() == 0) {
            First.setError(getString(R.string.firstErreur));
            err = -1;
        }
        if (Name.length() == 0) {
            Name.setError(getString(R.string.nameErreur));
            err = -1;
        }
        if (Phone.length() == 0) {
            Phone.setError(getString(R.string.phoneErreur));
            err = -1;
        }
        if (Mail.length() == 0) {
            Mail.setError(getString(R.string.mailErreur));
            err = -1;
        }
        if (Postal.length() == 0) {
            Postal.setError(getString(R.string.postalErreur));
            err = -1;
        }
        if (err == 1) {
            DbManager = new DbManager(new_Contact2.this);
            DbManager.newContact(First.getText().toString(), Name.getText().toString(),
                    Phone.getText().toString(), Mail.getText().toString(), Postal.getText().toString());
            DbManager.close();
            this.finish();
        }
    }
}