package com.nettport.stramdiet.stramdiet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by bruker on 15.06.2017.
 */

public class DBAdapter {
    /* 01 Variables ---------------------------------------- */
    private static final String databaseName = "stramdiet";
    private static final int databaseVersion = 8;

    /* 02 Database variables ------------------------------- */
    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;


    /* 03 Class DbAdapter ---------------------------------- */
    public DBAdapter(Context ctx){
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    /* 04 DatabaseHelper ------------------------------------ */
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context){
            super(context, databaseName, null, databaseVersion);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            try{
                // Create tables
                db.execSQL("CREATE TABLE IF NOT EXISTS food (" +
                            " food_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            " food_name VARCHAR," +
                            " food_manufactor_name VARCHAR," +
                            " food_serving_size DOUBLE," +
                            " food_serving_mesurment VARCHAR," +
                            " food_serving_name_number DOUBLE," +
                            " food_serving_name_word VARCHAR," +
                            " food_energy DOUBLE," +
                            " food_proteins DOUBLE," +
                            " food_carbohydrates DOUBLE," +
                            " food_fat DOUBLE," +
                            " food_energy_calculated DOUBLE," +
                            " food_proteins_calculated DOUBLE," +
                            " food_carbohydrates_calculated DOUBLE," +
                            " food_fat_calculated DOUBLE," +
                            " food_user_id INT," +
                            " food_barcode VARCHAR," +
                            " food_category_id INT," +
                            " food_thumb VARCHAR," +
                            " food_image_a VARCHAR," +
                            " food_image_b VARCHAR," +
                            " food_image_c VARCHAR," +
                            " food_notes VARCHAR);");

            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


            // ! All tables that are going to be dropped need to be listed here
            db.execSQL("DROP TABLE IF EXISTS food");
            onCreate(db);

            String TAG = "Tag";
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");

        } // end public void onUpgrade
    } // DatabaseHelper


    /* 05 Open database --------------------------------------------------------- */
    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    /* 06 Close database --------------------------------------------------------- */
    public void close() {
        DBHelper.close();
    }

    /* 07 Insert data ------------------------------------------------------------ */
    public void insert(String table, String fields, String values){
        db.execSQL("INSERT INTO " + table +  "(" + fields + ") VALUES (" + values + ")");
    }

    /* 08 Count ------------------------------------------------------------------ */
    public int count(String table)
    {
        Cursor mCount = db.rawQuery("SELECT COUNT(*) FROM " + table + "", null);
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();
        return count;
    }

}
