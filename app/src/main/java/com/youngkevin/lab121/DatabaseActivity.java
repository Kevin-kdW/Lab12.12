package com.youngkevin.lab121;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DatabaseActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        SQLiteOpenHelper dbHelper = new MyDBHelper(this);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //ListView listView = findViewById(R.id.db_info);
        Intent intent = getIntent();
        int category = intent.getIntExtra(MainActivity.EXTRA_THING,0);
        try{
            SQLiteOpenHelper databaseHelper = new MyDBHelper(this);
            Cursor cursor = db.query("COUNTRY",
                    new String[]{"_id", "NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID"},
                    "_id = ?",
                    new String[]{String.valueOf(category)},
                    null,
                    null,
                    null);

            if(cursor.moveToFirst()){
                String name = cursor.getString(1);
                String desc = cursor.getString(2);
                int imageId = cursor.getInt(3);


                TextView textview = findViewById(R.id.textName);
                TextView textView2 = findViewById(R.id.textDescription);
                ImageView imageview = findViewById(R.id.imageView1);

                textview.setText(name);
                textView2.setText(desc);
                imageview.setImageResource(imageId);
            }



            db.close();
            cursor.close();
        }
        catch(Exception ex){
            Toast toast = Toast.makeText(this,"no", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
