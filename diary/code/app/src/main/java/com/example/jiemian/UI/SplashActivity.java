package com.example.jiemian.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jiemian.R;

import butterknife.BindView;
import butterknife.OnClick;


public class SplashActivity extends AppCompatActivity {
    @BindView(R.id.tv_open)
    TextView tvOpen;
    private int time = 2;
    private Handler handler = new Handler();
    String shifou;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler.postDelayed(runnable, 2000);
        SharedPreferences preferences = getSharedPreferences("user",0);
        shifou = preferences.getString("isFirst", "");
    }




    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            time--;
            handler.postDelayed(this, 1500);
            if (time == 0) {
//                goToNextActivity();
//                SharedPreferences preferences = getSharedPreferences("user",MODE_PRIVATE);
//                SharedPreferences.Editor edit = preferences.edit();
//                edit.putString("isFirst","fou");
//                edit.commit();
                if(shifou.equals("fou")){
                    //结束线程
                    handler.removeCallbacks(runnable);
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    //结束线程
                    handler.removeCallbacks(runnable);
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            } else {

            }
        }
    };

    /**
     * 跳转界面
     */
    private void goToNextActivity() {
        //结束线程
        handler.removeCallbacks(runnable);
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    @OnClick(R.id.tv_open)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_open:
//                goToNextActivity();

                if(shifou.equals("fou")){
                    //结束线程
                    handler.removeCallbacks(runnable);
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    //结束线程
                    handler.removeCallbacks(runnable);
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }
}
