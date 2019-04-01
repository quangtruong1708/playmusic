package com.example.myapplicationplaymusic;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public Button btnPlay;
    public Button btnStop;
    private PlaySongService service;

    private final String[] PERMISSION_LIST = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkPermissionAvailable() == false) {
            return;
        }

        service = new PlaySongService();

        initView();
    }

    private void initView() {
        btnPlay = findViewById(R.id.button_play);
        btnStop = findViewById(R.id.button_stop);
        btnPlay.setOnClickListener(this);
        btnStop.setOnClickListener(this);
    }


    private boolean checkPermissionAvailable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String p : PERMISSION_LIST) {
                if (checkSelfPermission(p) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(PERMISSION_LIST, 0);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("play12", "onDestroy");


    }

    @Override
    protected void onStop() {
        super.onStop();
//        stopSong();
    }


    public void playSong() {
        Intent myIntent = new Intent(MainActivity.this, PlaySongService.class);
        myIntent.putExtra("PLAY", 1);
        this.startService(myIntent);
    }

    public void pauseSong() {
        Intent myIntent = new Intent(MainActivity.this, PlaySongService.class);
        myIntent.putExtra("PAUSE", 0);
        this.startService(myIntent);
    }


    public void stopSong() {
        Intent myIntent = new Intent(MainActivity.this, PlaySongService.class);
        myIntent.putExtra("PAUSE", 0);

        this.stopService(myIntent);
    }


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_play:
                if (isMyServiceRunning(PlaySongService.class)) {
                    stopSong();
                    btnPlay.setText("Play");


                    Log.e("play12", "play");
                } else {
                    playSong();
                    btnPlay.setText("Pause");
                    Log.e("play12", "pause");
                }


//                Log.e("play12", "playvideo");
                break;
            case R.id.button_stop:
                stopSong();
                btnPlay.setText("Play");
                Log.e("play12", "stopvideo");
                break;
        }
    }
}
