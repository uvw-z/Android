package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private ScreenReceiver mScreenReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        regist();
    }

    /**
     * 动态的去注册屏幕解锁和锁屏的广播
     */
    private void regist() {
        // [1]动态的去注册屏幕解锁和锁屏的广播
        mScreenReceiver = new ScreenReceiver();
        // [2]创建intent-filter对象
        IntentFilter filter = new IntentFilter();
        // [3]添加要注册的action
        filter.addAction("android.intent.action.SCREEN_OFF");
        filter.addAction("android.intent.action.SCREEN_ON");
        // [4]注册广播接收者
        registerReceiver(mScreenReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //当activity销毁的时候  取消注册广播接收者
        unregisterReceiver(mScreenReceiver);
    }
}