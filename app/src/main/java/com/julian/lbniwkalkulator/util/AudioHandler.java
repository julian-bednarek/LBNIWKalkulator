package com.julian.lbniwkalkulator.util;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;

public class AudioHandler {
    private MediaPlayer mediaPlayer;
    private final Context context;

    public AudioHandler(Context context) {
        this.context = context.getApplicationContext();
    }

    public void play(int audioResId) {
        if (mediaPlayer != null) {
            stop();
        }
        mediaPlayer = MediaPlayer.create(context, audioResId);
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}
