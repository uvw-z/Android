package com.example.jiemian.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.jiemian.R;
import com.example.jiemian.base.BaseActivity;

import butterknife.BindView;

public class AboutActivity extends BaseActivity {
@BindView(R.id.rl_back)
RelativeLayout back;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void init() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}