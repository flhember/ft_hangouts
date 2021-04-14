package com.example.ft_hangouts;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class ListContact extends Fragment {
    private ListView l1;
    private DbManager DbManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate( R.layout.list_contact, container, true );

        l1 = ( ListView ) rootView.findViewById(R.id.ListContact);
        setListContact();

        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(rootView.getContext(), contactResume.class);
                i.putExtra("contactNb", id);
                startActivity(i);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void onResume() {
        super.onResume();
        setListContact();
    }

    public void setListContact() {
        DbManager = new DbManager(getContext());
        List<ContactData> contacts =  DbManager.readAll();

        ContactAdapter contactAdapter = new ContactAdapter(getContext(), R.layout.list_contact, contacts);
        l1.setAdapter(contactAdapter);
        contactAdapter.notifyDataSetChanged();
        DbManager.close();
    }
}