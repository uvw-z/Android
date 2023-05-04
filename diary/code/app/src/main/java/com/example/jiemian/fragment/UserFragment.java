package com.example.jiemian.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Process;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jiemian.R;
import com.example.jiemian.UI.AboutActivity;
import com.example.jiemian.UI.LoginActivity;
import com.example.jiemian.UI.UserinfoActivity;
import com.example.jiemian.base.LazyFragment;

import butterknife.BindView;

import static android.content.Context.MODE_PRIVATE;

public class UserFragment extends LazyFragment implements View.OnClickListener {
    @BindView(R.id.rl_userinfo)
    RelativeLayout rlUserinfo;

    @BindView(R.id.rl_about)
    RelativeLayout rlAbout;
    @BindView(R.id.rl_restart)
    RelativeLayout rlRestart;

    @BindView(R.id.tv_next_step)
    TextView tvLogin;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void loadData() {
        rlUserinfo.setOnClickListener(this);
        rlAbout.setOnClickListener(this);
        rlRestart.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_userinfo:
                Intent intent=new Intent(getActivity(), UserinfoActivity.class);
                startActivity(intent);
                break;

            case R.id.rl_about:
                Intent intent2=new Intent(getActivity(), AboutActivity.class);
                startActivity(intent2);
                break;
            case R.id.rl_restart:
                showDialog();
                break;
            case R.id.tv_next_step:
                loginOut();
                SharedPreferences preferences = getActivity().getSharedPreferences("user",MODE_PRIVATE);
                SharedPreferences.Editor edit = preferences.edit();
                edit.putString("isFirst","fou");
                edit.commit();
                break;
        }
    }

    private void showDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("提示")
                .setMessage("确定切换账号吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create();
        builder.show();
    }
    protected void loginOut(){
        androidx.appcompat.app.AlertDialog.Builder builder=new androidx.appcompat.app.AlertDialog.Builder(getActivity());
        builder.setTitle("提示")
                .setMessage("确定退出吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                try {
                    //正常退出
                    Process.killProcess(Process.myPid());
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
