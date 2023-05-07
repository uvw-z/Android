package com.example.item;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import org.w3c.dom.Text;

import java.util.List;
import java.util.zip.Inflater;

public class myAdapter extends BaseAdapter{
    List<swt> info;
    Context context;
    dao d;
    LayoutInflater inflater;
    callback mcallback;
    swt swt1;

    public myAdapter(List<swt> info, Context context, dao d,callback callback) {
        this.info = info;
        this.context = context;
        this.d = d;
        mcallback=callback;
        inflater= LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return info.size();
    }

    @Override
    public Object getItem(int i) {
        return info.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewHold viewhold=null;
        if(view==null){
            viewhold=new viewHold();
            view=inflater.inflate(R.layout.view_item,null);
            viewhold.n=view.findViewById(R.id.n);
            viewhold.i=view.findViewById(R.id.i);
            viewhold.u=view.findViewById(R.id.u);
            viewhold.t=view.findViewById(R.id.t);
            viewhold.swc=view.findViewById(R.id.swc);

            view.setTag(viewhold);
        }else
            viewhold=(viewHold) view.getTag();
        viewhold.swc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mcallback.check(i,b);
            }
        });
        if(info.get(i).getFlag()==0)
            viewhold.swc.setChecked(false);
        else
            viewhold.swc.setChecked(true);
        viewhold.n.setText(info.get(i).getName());
        viewhold.i.setText(info.get(i).getI());
        viewhold.u.setText(info.get(i).getU());
        viewhold.t.setText(info.get(i).getT());
        return view;
    }

    public interface callback {
        public void check(int i, boolean isChecked);
    }

    void new_swt(String name,String i,String u,String t){
        System.out.println("在这里");
        swt1=new swt("","","","",1);
        swt1.setName(name);
        swt1.setI(i);
        swt1.setU(u);
        swt1.setT(t);
        info.add(swt1);
        d.new_swt(swt1);
        myAdapter.this.notifyDataSetChanged();
    }

    void update_swt(String name,String i,String u,String t){
        d.update_swt(name,i,u,t);
        for(int j=0;j<info.size();j++)
            if(info.get(j).getName().equals(name))
            {
                info.get(j).setI(i);
                info.get(j).setU(u);
                info.get(j).setT(t);
            }
        myAdapter.this.notifyDataSetChanged();
    }

    void cb(String n,int flag){
        for(int j=0;j<info.size();j++)
            if(info.get(j).getName().equals(n))
                info.get(j).setFlag(flag);
        d.cb(n,flag);
        myAdapter.this.notifyDataSetChanged();
    }

    class viewHold{
        TextView n;
        TextView i;
        TextView u;
        TextView t;
        Switch swc;
    }
}
