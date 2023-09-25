package com.sst.tutrify;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

import bg.devlabs.fullscreenvideoview.FullscreenVideoView;


public class TutorialActivity extends AppCompatActivity {
FullscreenVideoView videoView, videoView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        videoView = findViewById(R.id.videoView);
        videoView1 = findViewById(R.id.videoView1);


        videoView.videoUrl("https://drive.google.com/uc?export=download&id=1lZ7duVB3Y42Inh4EnhFK-7urIPsI82R_");
        videoView1.videoUrl("https://drive.google.com/uc?export=download&id=1I6wqbSUAhqBQDtVDRl-qWLn6VZKfkFJb");

    }
}