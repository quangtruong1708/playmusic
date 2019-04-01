package com.example.myapplicationplaymusic;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

public class PlaySongService extends Service {
    private MediaPlayer mediaPlayer;
    private int currentPos;


    public PlaySongService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse("/storage/emulated/0/downloadedfile.mp3"));

    }


    public void checkMediaPlayer() {
        if (mediaPlayer != null) {

            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.start();
            }
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getIntExtra("PLAY", -1) == 1) {
            mediaPlayer.start();

            Log.e("play12", "onStop "+currentPos);

            if (currentPos != 0){
                mediaPlayer.seekTo(currentPos);
            }
        } else if (intent.getIntExtra("PAUSE", -1) == 0) {
            currentPos = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
        }



        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        currentPos = mediaPlayer.getCurrentPosition();
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onDestroy();
    }
}