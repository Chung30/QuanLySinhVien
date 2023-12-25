package vn.itplus.doanqlsinhvien.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQLiteAdapter {
    private SQLiteDatabase database;
    private final SQLiteHelper dbHelper;

    public SQLiteAdapter(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void Open() {
        database = dbHelper.getWritableDatabase();
    }

    public void Close() {
        dbHelper.close();
    }

    public void SQLRun(String sql){
        database.execSQL(sql);
    }

    public Cursor SQLTable(String sql){
        return database.rawQuery(sql, null);
    }

    public void Insert(String tableName, ContentValues values) {
        try {
            Open();
            database.insert(tableName, null, values);
        } catch (Exception e) {
            Log.e("SQLiteAdapter", "Error inserting data into " + tableName + ": " + e.getMessage());
        } finally {
            Close();
        }
    }
}
