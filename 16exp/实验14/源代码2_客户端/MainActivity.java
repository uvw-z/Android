package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Socket socket=new Socket("10.0.2.2",6100);
                    OutputStream os=socket.getOutputStream();
                    String s="12345";
                    os.write(s.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }





}