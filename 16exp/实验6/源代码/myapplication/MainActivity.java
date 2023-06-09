package com.example.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import com.example.myapplication.Account2;
import com.example.myapplication.AccountDao2;
import android.widget.AdapterView.OnItemClickListener;

import androidx.appcompat.app.AlertDialog;

public class MainActivity extends Activity {
    //需要适配的数据集合
    private List<Account2> list;
    //数据库增删改查操作类
    private AccountDao2 dao;
    //输入姓名的EditText
    private EditText nameET;
    //输入金额的EditText
    private EditText balanceET;
    //适配器
    private MyAdapter adapter;
    //ListView
    private ListView accountLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        initView();
        dao = new AccountDao2(this);
        //从数据库查询出所有数据
        list = dao.queryAll();
        adapter = new MyAdapter();
        accountLV.setAdapter(adapter);//给ListView添加适配器(自动把数据生成条目)
    }

    //初始化控件
    private void initView() {
        accountLV = (ListView) findViewById(R.id.accountLV);
        nameET = (EditText) findViewById(R.id.nameET);
        balanceET = (EditText) findViewById(R.id.balanceET);
        //添加监听器，监听条目点击事件
        accountLV.setOnItemClickListener(new MyOnItemClickListener());
    }

    //activity_main_xml对应ImageView的点击事件触发的方法
    public void add(View v) {
        String name = nameET.getText().toString().trim();
        String balance = balanceET.getText().toString().trim();
        //三木运算balance.equals("")则等于0
        //如果balance不是空字符串 则进行类型转换
        Account2 a = new Account2(name, balance.equals("") ? 0 : Integer.parseInt(balance));
        dao.insert(a);
        list.add(a);
        adapter.notifyDataSetChanged();//刷新界面
        //选中最后一个
        accountLV.setSelection(accountLV.getCount() - 1);
        nameET.setText("");
        balanceET.setText("");
    }


    //自定义一个适配器（把适配器装到ListView的工具)
    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {//获取条目总数
            return list.size();
        }

        @Override
        public Object getItem(int position) {//根据位置获取对象
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {//根据位置获取ID
            return position;
        }

        @Override
        //获取一个条目视图
        public View getView(int position, View convertView, ViewGroup parent) {
            //重用convertView
            View item = convertView != null ? convertView : View.inflate(getApplicationContext(), R.layout.item2, null);
            //获取视图中的TextView
            TextView idTV = (TextView) item.findViewById(R.id.idIV);
            TextView nameTV = (TextView) item.findViewById(R.id.nameTV);
            TextView balanceTV = (TextView) item.findViewById(R.id.balanceTV);
            //根据当前位置获取Account对象
            final Account2 a = list.get(position);
            //把Account对象中的数据放到TextView中
            idTV.setText(a.getId() + "");
            nameTV.setText(a.getName());
            balanceTV.setText(a.getBalance() + "");
            ImageView upIV = (ImageView) item.findViewById(R.id.upIV);
            ImageView downIV = (ImageView) item.findViewById(R.id.downIV);
            ImageView deleteIV = (ImageView) item.findViewById(R.id.deleteIV);
            //向上箭头的点击事件触发的情绪
            upIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    a.setBalance(a.getBalance() + 1);//修改值
                    notifyDataSetChanged();//刷新界面
                    dao.update(a);//更新数据库

                }
            });
            //向下箭头的点击事件触发的方法
            downIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    a.setBalance((a.getBalance() - 1));
                    notifyDataSetChanged();
                    dao.update(a);

                }
            });
            //删除图片的点击事件触发的方法
            deleteIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //删除数据之前首先先弹出一个对话框
                    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            list.remove(a);//从集合中删除
                            dao.delete(a.getId());//从数据库中删除
                            notifyDataSetChanged();//刷新界面
                        }
                    };
                    //创建对话框
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("确定要删除吗?");//设置标题
                    builder.setPositiveButton("确定", listener);
                    builder.setNegativeButton("取消", null);
                    builder.show();//显示对话框
                }

            });
            return item;

        }
    }

    //ListView的Item点击事件
    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Account2 a = (Account2) parent.getItemAtPosition(position);
            Toast.makeText(getApplicationContext(), a.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}