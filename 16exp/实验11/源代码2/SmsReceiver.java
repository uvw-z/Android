package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import android.telephony.SmsMessage;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SmsReceiver extends BroadcastReceiver {
    final private int REQUEST_CODE_ASK_PERMISSION = 123;

    @Override
    public void onReceive(Context context, Intent intent) {
      //读取短信动态获取权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            int hasReadSmsPermission = context.checkSelfPermission(Manifest.permission.READ_SMS);
            if(hasReadSmsPermission != PackageManager.PERMISSION_GRANTED){
                ((Activity)context).requestPermissions(new String[]{Manifest.permission.READ_SMS}, REQUEST_CODE_ASK_PERMISSION);
                return;
            }
        }
        Object[] objects = (Object[]) intent.getExtras().get("pdus");
        for(Object pdu : objects){
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[])pdu);
            String body = smsMessage.getMessageBody();
            Date date = new Date(smsMessage.getTimestampMillis());
            SimpleDateFormat format = new SimpleDateFormat("yyyt-MM-dd HH:mm:ss");
            String address = smsMessage.getOriginatingAddress();
            String receiveTime = format.format(date);
            Log.e("SmsReceiver","body:"+body+"---"+address+"---"+receiveTime);
        }
    }
}