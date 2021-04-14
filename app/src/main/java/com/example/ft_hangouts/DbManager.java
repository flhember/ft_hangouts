package com.example.ft_hangouts;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DbManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ListContact.db";
    public static final int DATABASE_VERSION = 1;

    public DbManager(Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String strSqlContact = "create table Contact ("
                        +   "IdContact integer primary key autoincrement,"
                        +   "First text not null,"
                        +   "Name text not null,"
                        +   "Phone text not null,"
                        +   "Mail text not null,"
                        +   "Postal text"
                        + ")";
        String strSqlSms = "create table Sms ("
                +   "IdSms integer primary key autoincrement,"
                +   "Phone text not null,"
                +   "Message text not null,"
                +   "Who text not null"
                + ")";
        db.execSQL( strSqlContact );
        db.execSQL( strSqlSms );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void newContact(String first, String name, String phone, String mail, String postal) {
        name.replace("'", "''");
        String strSql = "insert into Contact (First, Name, Phone, Mail, Postal) values ('"
                        + first + "', '"
                        + name + "', '"
                        + phone + "', '"
                        + mail + "', '"
                        + postal + "'"
                        + ")";
        this.getWritableDatabase().execSQL( strSql );
    }

    // Contact
    public List<ContactData> readAll() {
        List<ContactData> contacts = new ArrayList<>();
        String strSql = "select * from Contact";
        Cursor cursor = this.getReadableDatabase().rawQuery(strSql, null);
        cursor.moveToFirst();

        while (! cursor.isAfterLast())
        {
            ContactData data = new ContactData(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
            contacts.add(data);
            cursor.moveToNext();
        }
        cursor.close();
        return contacts;
    }

    public int findId(int _id) {
        String strSql = "select * from Contact";
        Cursor cursor = this.getReadableDatabase().rawQuery(strSql, null);
        cursor.moveToFirst();
        int     idContact = 0;
        int     i = 1;

        while (!cursor.isAfterLast() && i < _id)
        {
            cursor.moveToNext();
            i++;
        }
        idContact = cursor.getInt(0);
        cursor.close();
        return idContact;
    }

    public ContactData readOne(int _id) {
        String strSql = "select * from Contact";
        Cursor cursor = this.getReadableDatabase().rawQuery(strSql, null);
        cursor.moveToFirst();
        int     i = 1;

        while (! cursor.isAfterLast() && i < _id)
        {
            cursor.moveToNext();
            i++;
        }
        ContactData data = new ContactData(cursor.getInt(0), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
        cursor.close();
        return data;
    }

    public String getNameContact(String NumeroPhone) {
        String strSql = "select * from Contact where phone ='" + NumeroPhone + "'";
        String nameReturn = "";
        Cursor cursor = this.getReadableDatabase().rawQuery(strSql, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            nameReturn = cursor.getString(1);
            cursor.moveToNext();
        }
        cursor.close();
        return nameReturn;
    }

    public void modifyContact(int _id, String first, String name, String phone, String mail, String postal) {
        int     idContact;
        idContact = findId(_id);

        String strSql = "update Contact set First = '" + first +
                                            "', Name = '" + name +
                                            "', Phone = '" + phone +
                                            "', Mail = '" + mail +
                                            "', Postal = '" + postal + "' where IdContact = " + idContact;
        this.getWritableDatabase().execSQL( strSql );
    }

    public void dropContact (int _id) {
        int     idContact;

        idContact = findId(_id);
        String strSql2 = "delete from Contact where IdContact =" + idContact;
        this.getWritableDatabase().execSQL( strSql2 );
    }

    // Sms
    public void newSms(String phone, String message, String who) {
        String strSql = "insert into Sms (Phone, Message, Who) values ('"
                + phone + "', '"
                + message + "', '"
                + who + "'"
                + ")";
        this.getWritableDatabase().execSQL( strSql );
    }

    public List<SmsData> getSms(String Phone) {
        List<SmsData> smsList = new ArrayList<>();
        String strSql = "select * from Sms where Phone ='" + Phone + "'";
        Cursor cursor = this.getReadableDatabase().rawQuery(strSql, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            SmsData data = new SmsData(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3));
            smsList.add(data);
            cursor.moveToNext();
        }
        cursor.close();
        return smsList;
    }
}
