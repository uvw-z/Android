package com.example.jiemian.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.jiemian.R;
import com.example.jiemian.Utils.utils1;
import com.example.jiemian.base.BaseActivity;

import butterknife.BindView;

public class UserinfoActivity extends BaseActivity {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_pwd)
    TextView tvPwd;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_userinfo;
    }

    @Override
    protected void init() {
        tvName.setText("用户名:"+utils1.passname);
        tvPwd.setText("用户密码:"+utils1.passpwd);
    }
}