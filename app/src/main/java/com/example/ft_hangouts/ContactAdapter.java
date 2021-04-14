package com.example.ft_hangouts;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<ContactData> {
    Context context;
    List<ContactData> data = null;

    public ContactAdapter(Context context, int layoutResourceId, List<ContactData> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        final LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        final View outerContiner = inflater.inflate(R.layout.patternlist, parent, false);

        TextView text = (TextView) outerContiner.findViewById(R.id.list1);
        TextView phone = (TextView) outerContiner.findViewById(R.id.list2);

        text.setText(data.get(position).getFirst());
        phone.setText(data.get(position).getPhone());

        return outerContiner;
    }
}
