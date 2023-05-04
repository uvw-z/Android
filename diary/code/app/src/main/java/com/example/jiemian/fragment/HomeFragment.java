package com.example.jiemian.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.jiemian.R;
import com.example.jiemian.UI.AddcaidanActivity;
import com.example.jiemian.UI.CaidandetailActivity;
import com.example.jiemian.UI.ChangeActivity;
import com.example.jiemian.Utils.MediaPlayUtils;
import com.example.jiemian.base.LazyFragment;
import com.example.jiemian.bean.Jishi;
import com.example.jiemian.sqlite.JishiDbutils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class HomeFragment extends LazyFragment {
    @BindView(R.id.gv)
    ListView gvShow;
    @BindView(R.id.wenben)
    TextView wenben;
    @BindView(R.id.tv_select)
    TextView tvselect;
    @BindView(R.id.login_et_zh)
    EditText etselect;

    Medicadapter medicadapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void loadData() {
        medicadapter=new Medicadapter(getActivity(), JishiDbutils.getInstance(getActivity()).load());
        gvShow.setAdapter(medicadapter);


        wenben.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), AddcaidanActivity.class);
                intent.putExtra("data",1);
                startActivityForResult(intent,102);

            }
        });
        tvselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aa=etselect.getText().toString();
                if(TextUtils.isEmpty(aa)){
                    showToast("请检查搜索内容");
                    return;
                }else
                {   medicadapter=new Medicadapter(getActivity(), JishiDbutils.getInstance(getActivity()).loadByName(aa));
                    gvShow.setAdapter(medicadapter);}
            }
        });
    }




   class Medicadapter extends BaseAdapter{
    private Context context;
    private List<Jishi>listdata;
    public Medicadapter(Context context,List<Jishi>listdata){
        this.context=context;
        this.listdata=listdata;
    }
       @Override
       public int getCount() {
           return listdata.size();
       }

       @Override
       public Object getItem(int position) {
           return listdata.get(position);
       }

       @Override
       public long getItemId(int position) {
           return position;
       }

       @Override
       public View getView(int position, View convertView, ViewGroup parent) {
           ViewHolder viewHolder=null;
          if(convertView==null){
              viewHolder=new ViewHolder();
              convertView=LayoutInflater.from(getActivity()).inflate(R.layout.item_memo,null);

              viewHolder.tv_biaoti=convertView.findViewById(R.id.tv_biaoti);
              viewHolder.tv_time=convertView.findViewById(R.id.tv_time);
              viewHolder.ll=convertView.findViewById(R.id.ll);

              convertView.setTag(viewHolder);
          }else {
              viewHolder= (ViewHolder) convertView.getTag();
          }
           viewHolder.tv_biaoti.setText(listdata.get(position).getTimu());
              viewHolder.tv_time.setText(listdata.get(position).getTimest());

              viewHolder.ll.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      showAlertDialog(listdata.get(position));
                  }
              });


           return convertView;
       }

       class ViewHolder{

          TextView tv_biaoti,tv_time;
          LinearLayout ll;
       }
   }

    private void showAlertDialog(Jishi jishi) {

        String[] item={"查看详情","修改","删除"};
        AlertDialog.Builder showAlertDialogbuilder=new AlertDialog.Builder(getActivity());
        showAlertDialogbuilder.setTitle("请选择操作")
                .setSingleChoiceItems(item, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String str=item[which];
                        switch (str){
                            case "查看详情":
                                Intent intent=new Intent(getActivity(), CaidandetailActivity.class);
                                intent.putExtra("bean",jishi);
                                startActivity(intent);
                                dialog.dismiss();
                                break;
                            case "修改":
                                Intent intent1=new Intent(getActivity(), ChangeActivity.class);
                                intent1.putExtra("bean1",jishi);
                                startActivityForResult(intent1,101);
                                dialog.dismiss();
                                break;
                            case "删除":
                                MediaPlayUtils.MediaPlay(getActivity());
                                JishiDbutils.getInstance(getActivity()).delete(getActivity(),jishi.getId()+"");
                                medicadapter=new Medicadapter(getActivity(),JishiDbutils.getInstance(getActivity()).load());
                                gvShow.setAdapter(medicadapter);
                                dialog.dismiss();
                                MediaPlayUtils.stopVoice();
                                break;
                        }
                    }
                });
        showAlertDialogbuilder.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        medicadapter=new Medicadapter(getActivity(),JishiDbutils.getInstance(getActivity()).load());
        gvShow.setAdapter(medicadapter);

    }
}