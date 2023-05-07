
package com.example.item;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.item.myAdapter.callback;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.w3c.dom.DOMImplementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements callback {


    protected boolean useThemestatusBarColor = false;
    protected boolean useStatusBarColor = true;
    private MqttClient client;
    private String productKey="h40flJmNNV3";
    private String deviceSecret="db736f2a312e0eb21812903ed574a2cd";
    private MqttConnectOptions options;
    private ScheduledExecutorService scheduler;
    private Handler handler;
    private String mqtt_id;
    private String mqtt_sub_topic;
    private String mqtt_pub_topic;
    private String host;
    private double maxi,maxu,maxt,ni,nu,nt;
    private myAdapter myadapter;
    private List<swt> info;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private myHelper mh;
    private SQLiteDatabase db;
    private Cursor c;
    private String s;
    private String cn;
    private String ct;
    private String cu;
    private String ci;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStatusBar();

        maxi=30;
        maxu=220;
        maxt=35;
        ni=0;
        nu=0;
        nt=0;
        builder = new AlertDialog.Builder(this);

        Mqtt_init();
        startReconnect();

        mh=new myHelper(this,"db",null,1);
        info=new ArrayList<swt>();

        dao d=new dao(this);
        d.select_all(info);
        System.out.println(info);
        myadapter=new myAdapter(info,this,d,this);
        ListView lv=findViewById(R.id.lv);
        lv.setAdapter(myadapter);


        handler = new Handler() {
            @SuppressLint("SetTextI18n")
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1: //开机校验更新回传
                        break;
                    case 2:  // 反馈回传
                        break;
                    case 3:  //MQTT 收到消息回传
                        s=msg.obj.toString();
                        cn=s.substring(s.indexOf("Name\":")+6,s.indexOf(","));
                        s=s.substring(s.indexOf(",")+1);
                        ct=s.substring(s.indexOf("Temperature\":")+13,s.indexOf(","));
                        s=s.substring(s.indexOf(",")+1);
                        cu=s.substring(s.indexOf("Volt\":")+6,s.indexOf(","));
                        s=s.substring(s.indexOf(",")+1);
                        ci=s.substring(s.indexOf("Current\":")+9,s.indexOf("}"));

                        db=mh.getReadableDatabase();
                        c=db.rawQuery("select * from infoo where name=?",new String[]{cn});
                        if(c.getCount()==0)
                            myadapter.new_swt(cn,ci,cu,ct);
                        else{
                            System.out.println("在这里！");
                            myadapter.update_swt(cn,ci,cu,ct);
                        }
//                        System.out.println(ci);

                        ni=Double.parseDouble(ci);
                        nu=Double.parseDouble(cu);
                        nt=Double.parseDouble(ct);
                        if(ni>=maxi){ makeDialog(cn+"电流过大"); myadapter.cb(cn,0);}
                        if(nu>=maxu){ makeDialog(cn+"电压过大"); myadapter.cb(cn,0);}
                        if(nt>=maxt){ makeDialog(cn+"温度过高"); myadapter.cb(cn,0);}
                        break;
                    case 30:  //连接失败
                        Toast.makeText(MainActivity.this,"连接失败" ,Toast.LENGTH_SHORT).show();
                        break;
                    case 31:   //连接成功
                        Toast.makeText(MainActivity.this,"连接成功" ,Toast.LENGTH_SHORT).show();
                        try {
                            client.subscribe(mqtt_sub_topic,1);
                        } catch (MqttException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
            }
        };
    }

    private void Mqtt_init() {
        try {
            String clientId = "12345"+ System.currentTimeMillis();

            Map<String, String> params = new HashMap<String, String>(16);
            params.put("productKey", productKey);
            params.put("deviceName", "soft");
            params.put("clientId", clientId);
            String timestamp = String.valueOf(System.currentTimeMillis());
            params.put("timestamp", timestamp);

            mqtt_id = clientId + "|securemode=3,signmethod=hmacsha1,timestamp=" + timestamp + "|";
            String userName="soft&" + productKey;
            String passWord=AliyunIoTSignUtil.sign(params, deviceSecret, "hmacsha1");
            mqtt_sub_topic = "/h40flJmNNV3/hard/user/data";
            mqtt_pub_topic = "/h40flJmNNV3/hard/user/cmd";
            host ="tcp://broker.emqx.io:1883";

            //host为主机名，mqtt_id为clientid即连接MQTT的客户端ID，一般以客户端唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
            client = new MqttClient(host, mqtt_id,
                    new MemoryPersistence());
            //MQTT的连接设置
            options = new MqttConnectOptions();
            //设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(false);
            //设置连接的用户名
            options.setUserName(userName);
            //设置连接的密码
            options.setPassword(passWord.toCharArray());
            // 设置超时时间 单位为秒
            options.setConnectionTimeout(3);
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(60);
            //设置回调
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    //连接丢失后，一般在这里面进行重连
                    System.out.println("connectionLost----------");
                    startReconnect();
                }
                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    //publish后会执行到这里
                    System.out.println("deliveryComplete---------"
                            + token.isComplete());
                }
                @Override
                public void messageArrived(String topicName, MqttMessage message)
                        throws Exception {
                    //subscribe后得到的消息会执行到这里面
                    System.out.println("messageArrived----------");
                    Message msg = new Message();
                    msg.what = 3;   //收到消息标志位
                    msg.obj =message.toString();
                    handler.sendMessage(msg);    // handler 回传
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Mqtt_connect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(!(client.isConnected()) )  //如果还未连接
                    {
                        client.connect(options);
                        Message msg = new Message();
                        msg.what = 31;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.what = 30;
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }

    private void startReconnect() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (!client.isConnected()) {
                    Mqtt_connect();
                }
            }
        }, 0 * 1000, 10 * 1000, TimeUnit.MILLISECONDS);
    }

    private void publishmessageplus(String topic, String message2) {
        if (client == null || !client.isConnected()) {
            return;
        }
        MqttMessage message = new MqttMessage();
        message.setPayload(message2.getBytes());
        try {
            client.publish(topic,message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    void makeDialog(String tip){
        builder.setMessage(tip+"，已断开");
        builder.setPositiveButton("知道了", null);
        builder.setNeutralButton("",null);
        dialog = builder.create();
        dialog.show();
    }
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            if (useThemestatusBarColor) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.colortheme));
            } else {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && useStatusBarColor) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    @Override
    public void check(int i,boolean isChecked) {
        String swcn=info.get(i).getName();
        String switch_off="{\"id\":"+swcn+",\"set_switch\"=0}";
        String switch_on="{\"id\":"+swcn+",\"set_switch\"=1}";
        if(isChecked){
            if(Double.parseDouble(info.get(i).getI())>=maxi || Double.parseDouble(info.get(i).getU())>=maxu || Double.parseDouble(info.get(i).getT())>=maxt){
                builder.setMessage("当前状态危险，是否继续？");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        publishmessageplus(mqtt_pub_topic,switch_on);
                        myadapter.cb(swcn,1);
                    }
                });
                builder.setNeutralButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        myadapter.cb(swcn,0);
                    }
                });
                dialog=builder.create();
                dialog.show();
            } else {
                publishmessageplus(mqtt_pub_topic,switch_on);
                myadapter.cb(swcn,1);
            }

        }else{
            publishmessageplus(mqtt_pub_topic,switch_off);
            myadapter.cb(swcn,0);
        }
    }
}

