package sam.com.noteapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import sam.com.noteapp.pojo.Notes;

/**
 * Created by sam on 3/8/16.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "notes";
    private static final String TABLE = "note";

    //Column Names
    private static final String ID = "id";
    private static final String HEADER = "header";
    private static final String NOTE = "note";

    private static final String CREATE_TABLE = "CREATE TABLE " +
            TABLE + "( " + ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, " + HEADER + " TEXT, " + NOTE + " TEXT )";

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, version);
    }

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, DB_NAME, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // creating required tables
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);
    }

    public long createNote(Notes notes) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HEADER, notes.getHeader());
        contentValues.put(NOTE, notes.getNote());
        long id = sqLiteDatabase.insert(TABLE, null, contentValues);
        return id;
    }

    public Notes getNote(long id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE + " WHERE id = " + id;
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        if (cursor != null) cursor.moveToFirst();
        Notes notes = new Notes();
        notes.setId(cursor.getInt(cursor.getColumnIndex(ID)));
        notes.setHeader(cursor.getString(cursor.getColumnIndex(HEADER)));
        notes.setNote(cursor.getString(cursor.getColumnIndex(NOTE)));

        return notes;
    }

    public int updateNote(Notes notes) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOTE, notes.getNote());

        return sqLiteDatabase.update(TABLE, values, ID + " = ?",
                new String[]{String.valueOf(notes.getId())});
    }

    public List<Notes> getAllTags() {
        List<Notes> notesArrayList = new ArrayList<Notes>();
        String selectQuery = "SELECT  * FROM " + TABLE;


        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Notes notes = new Notes();
                notes.setId(cursor.getInt((cursor.getColumnIndex(ID))));
                notes.setHeader(cursor.getString(cursor.getColumnIndex(HEADER)));
                notes.setNote(cursor.getString(cursor.getColumnIndex(NOTE)));

                // adding to notesArrayList list
                notesArrayList.add(notes);
            } while (cursor.moveToNext());
        }
        return notesArrayList;
    }

    public void deleteNote(int id) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE, ID + " = ?",
                new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }
}
