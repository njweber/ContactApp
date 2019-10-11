package webersoftwaresolutions.contacts_nate_weber;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Contacts.db";
    private static final String TABLE_NAME = "contacts_table";
    private static final String COL1 = "NAME";
    private static final String COL2 = "STREET";
    private static final String COL3 = "CITY";
    private static final String COL4 = "STATE";
    private static final String COL5 = "ZIP";
    private static final String COL6 = "EMAIL";
    private static final String COL7 = "PHONE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE  " + TABLE_NAME + " ("+ COL1 + " TEXT," + COL2 + " TEXT," + COL3 + " TEXT," + COL4 + " TEXT," + COL5 + " TEXT," + COL6 + " TEXT," + COL7 + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String c1, String c2, String c3, String c4, String c5, String c6, String c7) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL1, c1);
        contentValues.put(COL2, c2);
        contentValues.put(COL3, c3);
        contentValues.put(COL4, c4);
        contentValues.put(COL5, c5);
        contentValues.put(COL6, c6);
        contentValues.put(COL7, c7);


        Log.d(DATABASE_NAME, "addData: Adding " + c1 +c2 +c3+c4+c5+c6+c7+ " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == 1) {
            return false;
        }else{
            return true;
        }
    }
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        return data;
    }
    public void delete(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME+ " WHERE "+ COL1 +"='"+id+"'");
    }
}
