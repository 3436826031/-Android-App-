package com.example.biyelunw;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;
import com.androidadvance.topsnackbar.TSnackbar;
import com.github.rubensousa.raiflatbutton.RaiflatButton;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginActivity  extends Activity {
    Button login;
    EditText nametext;
    EditText passtext;
    RaiflatButton login2;
    TextView zhuce;
    String username;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         nametext=this.findViewById(R.id.nametext2);
         passtext=this.findViewById(R.id.passtext2);
         zhuce=this.findViewById(R.id.zhuce);
         login2=this.findViewById(R.id.normalButton);

        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegActivity.class);
                startActivity(intent);
            }
        });

        final Connection conn;
        login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //登录功能
                 username=nametext.getText().toString();
                 password=passtext.getText().toString();

                final Handler handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {

                        switch (msg.what){
                            case 0x11:
                                TSnackbar snackbar =  TSnackbar.make(findViewById(android.R.id.content),"登录成功",TSnackbar.LENGTH_LONG);
                                View snackbarView = snackbar.getView();
                                snackbarView.setBackgroundColor(Color.parseColor("#00ff99"));
                                TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
                                textView.setTextColor(Color.WHITE);
                                snackbar.show();
                                zhuanZhu();






                                break;
                            case 0x12:
                                TSnackbar snackbar2 =  TSnackbar.make(findViewById(android.R.id.content),"登录失败：用户名或密码不正确",TSnackbar.LENGTH_LONG);
                                View snackbarView2 = snackbar2.getView();
                                snackbarView2.setBackgroundColor(Color.parseColor("#ff0000"));
                                TextView textView2 = (TextView) snackbarView2.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
                                textView2.setTextColor(Color.WHITE);
                                snackbar2.show();
                                break;
                            case 0x13:
                                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
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
                        conn = (Connection) DBOpenHelper.getConn();
                        String sql = "Select * from login where username="+"'"+username+"';";//SQL语句
                        Statement st;
                        try {
                            st = (Statement) conn.createStatement();
                            ResultSet rs = st.executeQuery(sql);
                            if (rs.next()) {
                                //此部分为数据操作
                                // 注意：下标是从1开始的
                                message.what = 0x11;
                                System.out.println("第一步查询"+rs.getString(1));
                                sql="Select password from login where username="+"'"+username+"';";
                                 rs = st.executeQuery(sql);

                                if(rs.next()){
                                    System.out.println("密码获取"+rs.getString(1));
                                  if(password.equals(rs.getString(1))){
                                      message.what=0x11;
                                  }else{
                                      message.what=0x12;
                                  }
                                }
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


    void zhuanZhu(){

        //临时存储信息
        SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();   //获取编辑器
        editor.putString("username_save",username); //存入String型数据
        editor.putString("password_save",password);         //存入Int类型数据
        editor.commit();                //提交修改，否则不生效



        //发送数据到主页
        Message messags = new Message();
        messags.what = 0x55;
        AppActivity.handler.sendMessage(messags);

        //跳转页面传递信息（多余）
        Intent intent=new Intent(LoginActivity.this, AppActivity.class);
        intent.putExtra("EDusername", username);
        startActivity(intent);
    }



}
