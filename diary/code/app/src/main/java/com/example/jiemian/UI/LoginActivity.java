package com.example.jiemian.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiemian.R;
import com.example.jiemian.Utils.utils1;
import com.example.jiemian.base.BaseActivity;
import com.example.jiemian.sqlite.SqliteDBUtils;

import butterknife.BindView;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
/*绑定控件*/
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.tv_register)
    TextView tvRegister;

    @BindView(R.id.tv_login)
    TextView tvLogin;
    private int state=0;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }
    @Override
    protected void init() {
        tvRegister.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
/*跳转到注册界面*/
        switch (v.getId()){
            case R.id.tv_register:
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                return;
            case R.id.tv_login:
              String name=etPhone.getText().toString().trim();
                String pwd=etPwd.getText().toString().trim();
               if(TextUtils.isEmpty(name)||TextUtils.isEmpty(pwd)){
                   showToast("请检查输入内容");
                   return;
               }   else {
/*如果和数据库当中的保存的账号密码对应登录成功*/
/*在数据库中查找账号密码Quer*/
                   int i = SqliteDBUtils.getInstance(LoginActivity.this).Quer(name, pwd);
                   if (i==1) {
/*utils1建立的空账号密码与数据中的匹配*/
                       utils1.passname = name;
                       utils1.passpwd = pwd;
                       Intent intent2 = new Intent(LoginActivity.this, MainActivity.class);
                       startActivity(intent2);
                       showToast("登录成功");
                       SharedPreferences preferences = getSharedPreferences("user",MODE_PRIVATE);
                       SharedPreferences.Editor edit = preferences.edit();
                       edit.putString("isFirst","shi");
                       edit.commit();
                       finish();
                   }else if(i==-1){
                       showToast("密码错误");
                       return;

                   }else {
                       showToast("无此用户");
                       return;
                   }
               }
        }
    }

}