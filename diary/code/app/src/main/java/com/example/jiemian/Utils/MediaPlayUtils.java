package com.example.jiemian.Utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.jiemian.R;


public class MediaPlayUtils {
    private static MediaPlayer mediaPlayer;

    public static void MediaPlay(Context context){
        try {
            mediaPlayer= MediaPlayer.create(context, R.raw.di);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mediaPlayer.start();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  static void stopVoice(){
        if(null!=mediaPlayer) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
