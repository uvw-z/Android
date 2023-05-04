package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
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
                    ServerSocket serverSocket=new ServerSocket(7100);
                    Socket socket=serverSocket.accept();
                    InputStream is=socket.getInputStream();
                    byte[] buffer=new byte[1024];
                    int n=is.read(buffer);
                    String s=new String(buffer,0,n);
                    System.out.println(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }




}