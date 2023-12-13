package com.example.quote_of_the_day;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class QuotesDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "quotes.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_QUOTES = "quotes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_FAVORITE = "favorite";
    public Context context;
    private static final String CREATE_QUOTES_TABLE = "CREATE TABLE " + TABLE_QUOTES + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TEXT + " TEXT NOT NULL, " +
            COLUMN_AUTHOR + " TEXT, " +
            COLUMN_FAVORITE + " INTEGER DEFAULT 0);";

    public QuotesDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade logic if needed
    }

    // Add methods for CRUD operations

    public void addQuote(Quote quote) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TEXT, quote.getText());
        values.put(COLUMN_AUTHOR, quote.getAuthor());

        db.insert(TABLE_QUOTES, null, values);
        db.close();
    }

    public List<Quote> getAllQuotes() {
        List<Quote> quotes = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_QUOTES, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String text = cursor.getString(cursor.getColumnIndex(COLUMN_TEXT));
                String author = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR));
                int favorite = cursor.getInt(cursor.getColumnIndex(COLUMN_FAVORITE));

                quotes.add(new Quote(id, text, author, favorite));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
//        Toast.makeText(context, quotes.size()+"", Toast.LENGTH_SHORT).show();
        return quotes;
    }
    public Quote getQuoteByText(String quoteText) {
        SQLiteDatabase db = getReadableDatabase();

        String selection = COLUMN_TEXT + " = ?";
        String[] selectionArgs = {quoteText};
        Cursor cursor = db.query(TABLE_QUOTES, null, selection, selectionArgs, null, null, null);

        Quote quote = null;
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            String text = cursor.getString(cursor.getColumnIndex(COLUMN_TEXT));
            String author = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR));
            int favorite = cursor.getInt(cursor.getColumnIndex(COLUMN_FAVORITE));

            quote = new Quote(id, text, author, favorite );
        }
        cursor.close();
        db.close();

        return quote;
    }
    public void markFavorite(String quoteText,int q) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_FAVORITE, q);

        db.update(TABLE_QUOTES, values, COLUMN_TEXT + " = ?", new String[]{quoteText});
        db.close();
    }

    public List<Quote> getFavoriteQuotes() {
        List<Quote> quotes = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_QUOTES, null, COLUMN_FAVORITE + " = 1", null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String text = cursor.getString(cursor.getColumnIndex(COLUMN_TEXT));
                String author = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR));
                int favorite = cursor.getInt(cursor.getColumnIndex(COLUMN_FAVORITE));
                quotes.add(new Quote(id, text, author, favorite));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return quotes;
    }

    public void deleteQuote(int id) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(TABLE_QUOTES, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}
