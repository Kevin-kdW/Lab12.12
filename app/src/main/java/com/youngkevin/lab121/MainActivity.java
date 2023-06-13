package com.youngkevin.lab121;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor cursor;
    public static final String EXTRA_THING = "MainAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.listView);

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
                    cursor, new String[] {"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID"},
                    new int[] {android.R.id.text1, android.R.id.text2,},
                    0);

            listView.setAdapter(cursorAdapter);
        }
        catch(SQLiteException ex){
            Toast.makeText(this,"SQL Error ",Toast.LENGTH_SHORT).show();
        }
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DatabaseActivity.class);
                intent.putExtra(EXTRA_THING,i);
                startActivity(intent);
            }
        };

        listView.setOnItemClickListener(itemClickListener);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null) {
            cursor.close();
        }
        if (db != null) {
            db.close();
        }
    }
}