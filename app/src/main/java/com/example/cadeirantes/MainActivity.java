package com.example.cadeirantes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Handler h = new Handler();
        h.postDelayed(() -> {
            Intent i  = new Intent(MainActivity.this,MainActivity2.class);
            startActivity(i);
            finish();
        }, 300);

        getSupportActionBar().hide();
        }
}
