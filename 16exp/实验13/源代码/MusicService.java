package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;


import java.util.Timer;
import java.util.TimerTask;

public class MusicService extends Service {
    private MediaPlayer player;
    private Timer timer;
    public MusicService() {}
    @Override
    public IBinder onBind(Intent intent) {
        return new MusicControl();
    }
    @Override
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();//创建音乐播放器对象
    }
    class MusicControl extends Binder {
        public void play() {
            try {
                player.reset();//重置音乐播放器
                //加载多媒体文件
                player = MediaPlayer.create(getApplicationContext(), R.raw.kty);
                player.start();//播放音乐
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void pausePlay() {
            player.pause();           //暂停播放音乐
        }
        public void continuePlay() {
            player.start();           //继续播放音乐
        }
        public void seekTo(int progress) {
            player.seekTo(progress);//设置音乐的播放位置
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player == null) return;
        if (player.isPlaying()) player.stop();//停止播放音乐
        player.release();                         //释放占用的资源
        player = null;                            //将player置为空
    }
}
