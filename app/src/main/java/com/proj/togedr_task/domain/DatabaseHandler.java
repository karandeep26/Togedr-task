package com.proj.togedr_task.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.proj.togedr_task.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karan on 23/4/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager.db";

    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_Email="email";
    private static final String KEY_ADDRESS="address";
    private static final String KEY_MOBILE_NO = "mobile";
    private static final String KEY_HOME_NO ="home";
    private static final String KEY_OFFICE_NO ="office";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " TEXT PRIMARY KEY," + KEY_NAME + " TEXT,"
                +KEY_ADDRESS+" TEXT,"
                +KEY_Email+" TEXT,"+ KEY_MOBILE_NO +" TEXT,"+ KEY_OFFICE_NO +" TEXT,"
                + KEY_HOME_NO + " TEXT)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
   public void  addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName()); // Contact Name
        values.put(KEY_HOME_NO, contact.getPhone().getHome()); // Contact Phone
        values.put(KEY_ID,contact.getId());
        values.put(KEY_ADDRESS,contact.getAddress());
        values.put(KEY_OFFICE_NO,contact.getPhone().getOffice());
        values.put(KEY_Email,contact.getEmail());
        values.put(KEY_MOBILE_NO,contact.getPhone().getMobile());
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact


    // Getting All Contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(cursor.getString(0));
                contact.setName(cursor.getString(1));
                contact.setAddress(cursor.getString(2));
                contact.setEmail(cursor.getString(3));
                Contact.Phone phone=new Contact.Phone();
                phone.setMobile(cursor.getString(4));
                phone.setHome(cursor.getString(5));
                phone.setOffice(cursor.getString(6));
                contact.setPhone(phone);
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // return contact list
        return contactList;
    }






    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}