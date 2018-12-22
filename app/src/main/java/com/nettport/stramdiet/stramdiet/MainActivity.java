package com.nettport.stramdiet.stramdiet;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Stetho */
        Stetho.initializeWithDefaults(this);

        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        /* Database */
        DBAdapter db = new DBAdapter(this);
        db.open();

        /* Setup for food */
        // Count rows in food
        int numberRows = db.count("food");

        if(numberRows < 1){
            // Run setup
            // Toast.makeText(this, "Loading setup...", Toast.LENGTH_LONG).show();
            DBSetupInsert setupInsert = new DBSetupInsert(this);
            setupInsert.insertAllCategories();
            setupInsert.insertAllFood();
            // Toast.makeText(this, "Setup completed!", Toast.LENGTH_LONG).show();

        }

        /* Check if there is user in the user table */
        // Count rows in user table
        numberRows = db.count("users");
        if(numberRows < 1){
            // Sign up
            // Toast.makeText(this, "You are only few fields away from signing up...", Toast.LENGTH_LONG).show();
            Intent i = new Intent(MainActivity.this, SignUp.class);
            startActivity(i);
        }

        /* Close database */
        db.close();

    }

}