package demo.tcpamos.net.amos.codes.com.tcpamosdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import tcpamos.net.amos.codes.com.libtcpamos.TCPAmosHelp;
import tcpamos.net.amos.codes.com.libtcpamos.TCPAmosHelpListener;

public class MainActivity extends AppCompatActivity implements TCPAmosHelpListener {
    private TCPAmosHelp tcpAmosHelp;
    private Button btn_Send,btn_QR,btn_Connect;
    private EditText txt_QRID,txt_IP,txt_Port,txt_SendValue;
    private ImageView imageView_QR;
    private TextView lbl_Received,lbl_State;
    private String str_sendValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lbl_State=findViewById(R.id.lbl_states);
        lbl_Received=findViewById(R.id.received);
        txt_IP=findViewById(R.id.txt_ip);
        txt_Port=findViewById(R.id.txt_port);
        txt_SendValue=findViewById(R.id.txt_send);
        str_sendValue=txt_SendValue.getText().toString()+"\r";

        tcpAmosHelp=new TCPAmosHelp(this);
        btn_Send=findViewById(R.id.send);
        btn_Send.setVisibility(View.INVISIBLE);
        btn_Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //手动发送
                tcpAmosHelp.sendData(str_sendValue);
            }
        });

        btn_Connect=findViewById(R.id.btn_connect);
        btn_Connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tcpAmosHelp.initConnect(txt_IP.getText().toString(),Integer.parseInt(txt_Port.getText().toString()),0);
            }
        });
        //自动发送
        //适合心跳包的方式
        //handler_Tpc.postDelayed(r_Tcp, 1000);//延时1000毫秒





        //二维码部分
        txt_QRID=findViewById(R.id.txt_MAC);
        InputMethodManager m = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        m .hideSoftInputFromWindow(txt_QRID.getWindowToken(), 0);
        imageView_QR=findViewById(R.id.imageView);
        btn_QR=findViewById(R.id.btn_qr);
        final AmosQRCodeUtils amosQRCodeUtils=new AmosQRCodeUtils();
        btn_QR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ss=txt_QRID.toString();
                Bitmap codeBitmap = amosQRCodeUtils.createQRCode(txt_QRID.getText().toString(),500,500,null);
                imageView_QR.setImageBitmap(codeBitmap);
            }
        });

    }
    final Handler handler_Tpc = new Handler();
    Runnable r_Tcp= new Runnable() {
        @Override
        public void run() {
            //每隔3s循环执行run方法
            tcpAmosHelp.sendData(str_sendValue);
            handler_Tpc.postDelayed(this, 3000);
        }
    };

    /**
     * 接受tcp数据
     * @param o
     * @param i
     */
    @Override
    public void onTCPAmosReceive(Object o, int i) {
        Log.e("reveive Amos  data:",o+"");
        final Object tempO=o;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lbl_Received.setText(tempO+"");
            }
        });

    }

    /**
     * 连接状态
     * @param i
     * @param i1
     */
    @Override
    public void onTCPAmosStatusChanged(int i, int i1) {
        switch (i) {
            case 0:
              //
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lbl_State.setText("没有连接");
                        btn_Send.setVisibility(View.INVISIBLE);
                    }
                });
            break;
            case 1:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lbl_State.setText("已经连接");
                        btn_Send.setVisibility(View.VISIBLE);
                    }
                });
              //
                break;
            default:
                break;
        }
    }






//点击空白处收起键盘
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (MainActivity.this.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }
}
