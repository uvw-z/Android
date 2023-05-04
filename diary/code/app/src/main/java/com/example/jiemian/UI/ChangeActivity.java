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

public class ChangeActivity extends BaseActivity implements View.OnClickListener {
    private EditText tv_Timu;
    private EditText tv_xiangqing;



    Jishi medic;

   private TextView commit1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change;
    }

    @Override
    protected void init() {
        tv_Timu=findViewById(R.id.tv_name);

        tv_xiangqing=findViewById(R.id.tv_xiangqing);
        commit1=findViewById(R.id.commit1);
        medic= (Jishi) getIntent().getSerializableExtra("bean1");


        tv_Timu.setText(medic.getTimu());
        tv_xiangqing.setText(medic.getXiangqing());
        commit1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.commit1:
                String timu=tv_Timu.getText().toString();
                String xiangqing=tv_xiangqing.getText().toString();

                medic.setTimu(timu);
                medic.setTimest(getCurrentTime());
                medic.setXiangqing(xiangqing);
                JishiDbutils.getInstance(getApplicationContext()).change(getApplicationContext(),medic);
                showToast("修改成功");
                MediaPlayUtils.MediaPlay(this);
                finish();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaPlayUtils.stopVoice();
    }
}