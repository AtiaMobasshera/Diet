package com.nettport.stramdiet.stramdiet;

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
            DBSetupInsert setupInsert = new DBSetupInsert(this);
            setupInsert.insertAllFood();
        }



        /* Close database */
        db.close();



    }
}
