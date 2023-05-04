package com.example.jiemian.UI;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jiemian.R;
import com.example.jiemian.Utils.MediaPlayUtils;
import com.example.jiemian.base.BaseActivity;
import com.example.jiemian.bean.Jishi;
import com.example.jiemian.sqlite.JishiDbutils;
import com.wildma.pictureselector.PictureBean;
import com.wildma.pictureselector.PictureSelector;

import butterknife.BindView;

public class AddcaidanActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_timju;
    private EditText et_xiangqing;


    private TextView commit1;
    int i=0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_addcaidan;
    }

    @Override
    protected void init() {
        et_timju=findViewById(R.id.tv_name);
        et_xiangqing=findViewById(R.id.tv_xiangqing);

        commit1=findViewById(R.id.commit1);

        commit1.setOnClickListener(this);


        commit1.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.commit1:
                String timu=et_timju.getText().toString();
                String xiangqing=et_xiangqing.getText().toString();
                Jishi medic=new Jishi();

                medic.setTimu(timu);
                medic.setTimest(getCurrentTime());
                medic.setXiangqing(xiangqing);
               int i= JishiDbutils.getInstance(getApplicationContext()).insert(medic);
                if(i==0){
                    MediaPlayUtils.MediaPlay(getApplicationContext());
            showToast("添加成功");
//                    Intent intent = new Intent();
//                    intent.putExtra("medic", medic);
//                    setResult(RESULT_OK,intent);
//                    finish();
                finish();
                }else {
                    showToast("添加失败");
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaPlayUtils.stopVoice();
    }
}