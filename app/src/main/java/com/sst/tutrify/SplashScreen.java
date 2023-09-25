package com.sst.tutrify;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import soup.neumorphism.NeumorphImageView;

public class SplashScreen extends AppCompatActivity {
    private static final int SPLASH_DURATION = 4000; // 4 seconds
    NeumorphImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imageView = findViewById(R.id.neumorphImageView2);

        SharedPreferences sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!sharedPreferences.getBoolean("login", false)){
                    ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation(SplashScreen.this,  imageView, "logo");
                    startActivity(new Intent(getApplicationContext(), LoginPage.class), options.toBundle());
                    finish(); // Close the splash activity

                }else{
                    startActivity(new Intent(getApplicationContext(), MainPage.class));
                    finish(); // Close the splash activity
                }

            }
        }, SPLASH_DURATION);



    }
}