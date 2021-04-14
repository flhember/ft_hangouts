package com.example.ft_hangouts;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SmsAdapter extends ArrayAdapter<SmsData> {
    Context context;
    List<SmsData> data = null;

    public SmsAdapter(Context context, int layoutResourceId, List<SmsData> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = ((Activity) context).getLayoutInflater();

        if (data.get(position).getWho().contains("me")) {
            Log.i("Sms", data.get(position).getMsg());
            convertView = inflater.inflate(R.layout.my_message, null);
            TextView text = (TextView) convertView.findViewById(R.id.my_message);
            text.setText(data.get(position).getMsg().toString());
        } else {
            DbManager DbManager = new DbManager(getContext());
            Log.i("Sms", data.get(position).getMsg());
            convertView = inflater.inflate(R.layout.other_message, null);
            TextView nameSms = (TextView) convertView.findViewById(R.id.name);
            TextView text = (TextView) convertView.findViewById(R.id.other_message);
            text.setText(data.get(position).getMsg().toString());
            nameSms.setText(DbManager.getNameContact(data.get(position).getPhone().toString()));
        }
        return (convertView);
    }
}
