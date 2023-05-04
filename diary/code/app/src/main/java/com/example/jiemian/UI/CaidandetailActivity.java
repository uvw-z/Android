package com.example.jiemian.UI;

import android.widget.TextView;

import com.example.jiemian.R;
import com.example.jiemian.base.BaseActivity;
import com.example.jiemian.bean.Jishi;

public class CaidandetailActivity extends BaseActivity {
private TextView tv_Timu;
private TextView tv_xiangqing;


Jishi medic;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_caidandetail;
    }

    @Override
    protected void init() {
        tv_Timu=findViewById(R.id.tv_name);
        tv_xiangqing=findViewById(R.id.tv_xiangqing);
        medic= (Jishi) getIntent().getSerializableExtra("bean");
        tv_Timu.setText(medic.getTimu());
        tv_xiangqing.setText(medic.getXiangqing());


    }

}