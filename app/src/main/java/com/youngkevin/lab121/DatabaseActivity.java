package com.youngkevin.lab121;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class DatabaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        ListView listView = findViewById(R.id.db_info);

        SQLiteOpenHelper databaseHelper = new MyDBHelper(this);

        try{
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            //code to read from database
            Cursor cursor = db.query("COUNTRY",
                    new String[] { "_id", "NAME", "DESCRIPTION","IMAGE_RESOURCE_ID"},
                    null,
                    null,
                    null,
                    null,
                    null);

            SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cursor, new String[] {"NAME"},
                    new int[] {android.R.id.text1},
                    0);

            if(cursor != null) {
                for (String columnName : cursor.getColumnNames()) {
                    Log.i("Column name", columnName);
                }
                if (cursor.moveToFirst()) {
                    // Retrieve the data in each column
                    String name = cursor.getString(cursor.getColumnIndex("NAME"));
                    String description = cursor.getString(cursor.getColumnIndex("DESCRIPTION"));
                    int imageResourceId = cursor.getInt(cursor.getColumnIndex("IMAGE_RESOURCE_ID"));

                    // Do something with the data...
                }
            }

            listView.setAdapter(cursorAdapter);
        }
        catch(SQLiteException ex){
            Toast.makeText(this,"SQL Error ",Toast.LENGTH_SHORT).show();
        }

    }
}