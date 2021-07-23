package com.example.biyelunw;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.androidadvance.topsnackbar.TSnackbar;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class RegActivity extends Activity {

    Button reg;
    EditText regNametext;
    EditText regPasstext;
    EditText regRepasstext;
    TextView zhuanlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        reg = this.findViewById(R.id.regbut2);
        regNametext = this.findViewById(R.id.regnametext2);
        regPasstext = this.findViewById(R.id.regpasstext2);
        regRepasstext=this.findViewById(R.id.regrepasstext2);
        zhuanlogin=this.findViewById(R.id.zhuandl);

        zhuanlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
            reg.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 final String username=regNametext.getText().toString();
                 final String password=regPasstext.getText().toString();
                 final String rePassword=regRepasstext.getText().toString();

                 final Handler handler = new Handler(){
                     @Override
                     public void handleMessage(Message msg) {

                         switch (msg.what){
                             case 0x11:
                                 TSnackbar snackbar =  TSnackbar.make(findViewById(android.R.id.content),"注册成功",TSnackbar.LENGTH_LONG);
                                 View snackbarView = snackbar.getView();
                                 snackbarView.setBackgroundColor(Color.parseColor("#00FF99"));
                                 TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
                                 textView.setTextColor(Color.WHITE);
                                 snackbar.show();
                                 break;
                             case 0x12:
                                 TSnackbar snackbar2 =  TSnackbar.make(findViewById(android.R.id.content),"注册失败",TSnackbar.LENGTH_LONG);
                                 View snackbarView2 = snackbar2.getView();
                                 snackbarView2.setBackgroundColor(Color.parseColor("#ff0000"));
                                 TextView textView2 = (TextView) snackbarView2.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
                                 textView2.setTextColor(Color.WHITE);
                                 snackbar2.show();
                                 break;
                             case 0x13:
                                 TSnackbar snackbar3 =  TSnackbar.make(findViewById(android.R.id.content),"两次密码输入不一致",TSnackbar.LENGTH_LONG);
                                 View snackbarView3 = snackbar3.getView();
                                 snackbarView3.setBackgroundColor(Color.parseColor("#ff0000"));
                                 TextView textView3 = (TextView) snackbarView3.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
                                 textView3.setTextColor(Color.WHITE);
                                 snackbar3.show();

                                 break;

                         }

                     }
                 };



                 final Message message = handler.obtainMessage();
                 //连接数据库进行操作需要在主线程操作
                 new Thread(new Runnable() {
                     @Override
                     public void run() {
                         Connection conn = null;

                         if(!password.equals(rePassword)){
                             message.what = 0x13;
                             handler.sendMessage(message);
                             return;
                         }


                         conn = (Connection) DBOpenHelper.getConn();
                         String sql = "insert into login values('"+username+"','"+password+"');";//SQL语句
                         Statement st;
                         try {
                             st = (Statement) conn.createStatement();
                             int rs = st.executeUpdate(sql);
                             if (rs>0) {
                                 message.what = 0x11;
                             }else
                             {
                                 message.what = 0x12;
                             }

                             handler.sendMessage(message);
                             st.close();
                             conn.close();
                         } catch (SQLException e) {
                             e.printStackTrace();

                             System.out.println("失败0"+e);
                         }
                     }
                 }).start();

            }
        });

    }
}
