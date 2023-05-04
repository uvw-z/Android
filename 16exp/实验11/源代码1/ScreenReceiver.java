package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenReceiver extends BroadcastReceiver {
    private static final String TAG = "ScreenReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        //[1]获取到当前广播的事件类型
        String action = intent.getAction();
        //[2]对当前广播事件类型做一个判断
        if ("android.intent.action.SCREEN_OFF".equals(action)) {
            Log.i(TAG, "屏幕锁屏了");
        } else if ("android.intent.action.SCREEN_ON".equals(action)) {
            Log.i(TAG, "屏幕解锁了");
        }
    }
}