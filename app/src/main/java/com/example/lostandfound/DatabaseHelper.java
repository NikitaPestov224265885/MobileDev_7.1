package com.example.lostandfound;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Info
    public static final String DATABASE_NAME = "LostFoundDB";
    public static final int DATABASE_VERSION = 1;

    // Table Names
    public static final String TABLE_ADVERTS = "adverts";

    // Advert Table Columns
    public static final String KEY_ADVERT_ID = "id";
    public static final String KEY_ADVERT_TYPE = "type";
    public static final String KEY_ADVERT_PHONE = "phone";
    public static final String KEY_ADVERT_DESCRIPTION = "description";
    public static final String KEY_ADVERT_DATE = "date";
    public static final String KEY_ADVERT_LOCATION = "location";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ADVERTS_TABLE = "CREATE TABLE " + TABLE_ADVERTS +
                "(" +
                KEY_ADVERT_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_ADVERT_TYPE + " TEXT," +
                KEY_ADVERT_PHONE + " TEXT," +
                KEY_ADVERT_DESCRIPTION + " TEXT," +
                KEY_ADVERT_DATE + " TEXT," +
                KEY_ADVERT_LOCATION + " TEXT" +
                ")";

        db.execSQL(CREATE_ADVERTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simple way to just drop the table and recreate it
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADVERTS);
            onCreate(db);
        }
    }

    // Insert a post into the database
    public void addAdvert(Advert advert) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_ADVERT_TYPE, advert.getType());
            values.put(KEY_ADVERT_PHONE, advert.getPhone());
            values.put(KEY_ADVERT_DESCRIPTION, advert.getDescription());
            values.put(KEY_ADVERT_DATE, advert.getDate());
            values.put(KEY_ADVERT_LOCATION, advert.getLocation());

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(TABLE_ADVERTS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    // Fetch all posts in the database
    public Cursor getAllAdverts() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_ADVERTS, null);
    }

    // Delete an advert from the database
    public boolean deleteAdvert(long advertId) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_ADVERTS, KEY_ADVERT_ID + " = ?", new String[]{String.valueOf(advertId)}) == 1;
    }

    public Advert getAdvert(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to select the advert row based on ID
        Cursor cursor = db.query(TABLE_ADVERTS, new String[] {KEY_ADVERT_ID, KEY_ADVERT_TYPE, KEY_ADVERT_PHONE, KEY_ADVERT_DESCRIPTION, KEY_ADVERT_DATE, KEY_ADVERT_LOCATION},
                KEY_ADVERT_ID + "=?", new String[] {String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // Assuming your cursor now holds the data of the advert and that you correctly handle cases where no data is found
        @SuppressLint("Range") Advert advert = new Advert(
                cursor.getLong(cursor.getColumnIndex(KEY_ADVERT_ID)),
                cursor.getString(cursor.getColumnIndex(KEY_ADVERT_TYPE)),
                cursor.getString(cursor.getColumnIndex(KEY_ADVERT_PHONE)),
                cursor.getString(cursor.getColumnIndex(KEY_ADVERT_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(KEY_ADVERT_DATE)),
                cursor.getString(cursor.getColumnIndex(KEY_ADVERT_LOCATION))
        );
        cursor.close();
        return advert;
    }


    public static class Advert {
        private long id;
        private String type;
        private String phone;
        private String description;
        private String date;
        private String location;

        // Constructor
        public Advert(long id, String type, String phone, String description, String date, String location) {
            this.id = id;
            this.type = type;
            this.phone = phone;
            this.description = description;
            this.date = date;
            this.location = location;
        }

        // ID getter and setter
        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        // Type getter and setter
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        // Phone getter and setter
        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        // Description getter and setter
        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        // Date getter and setter
        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        // Location getter and setter
        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }

}
