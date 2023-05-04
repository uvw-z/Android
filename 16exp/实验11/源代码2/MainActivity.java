package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private SmsReceiver mSmReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        regist();
    }

    private void regist() {
        //注册短信广播接收者
        IntentFilter smsFilter = new IntentFilter();
        smsFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(mSmReceiver, smsFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //当activity销毁的时候  取消注册广播接收者
        unregisterReceiver(mSmReceiver);
    }
}