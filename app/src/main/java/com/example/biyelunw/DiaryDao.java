package com.example.biyelunw;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.biyelunw.Bean.DiaryBean;
import com.example.biyelunw.Frag.MainFragment;
import com.example.biyelunw.Frag.TwoFragment;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DiaryDao {

    Handler handler;
    Message message;
   public static String username;





    static ArrayList<DiaryBean> list;
    public DiaryDao(){
        list=new ArrayList<DiaryBean>();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {

                switch (msg.what){
                    case 0x11:
                        System.out.println("上传成功");
                        break;
                    case 0x12:
                        System.out.println("上传失败");
                        break;
                    case 0x13:

                        break;
                }

            }
        };

        message= handler.obtainMessage();


    }



    public static DiaryDao newInstance(){
        DiaryDao fragment=new DiaryDao();
        return fragment;

    }



    public static ArrayList<DiaryBean> loadDiaryList(){
       // list.clear();

        if(username==""||username==null){
            list.clear();
            System.out.println("你还没登录");
            DiaryBean diaryBean=new DiaryBean();
            diaryBean.setUsername(username);
            diaryBean.setTitle("你还没登录");
            diaryBean.setContent("请登录");
            diaryBean.setWeather(0);
            diaryBean.setMood(0);
            diaryBean.setData(" ");
            Log.d("执行测试3","数据返回"+diaryBean.toString());
            list.add(diaryBean);
            Message messags = new Message();
            messags.what = 0x20;

            MainFragment.newInstance().handler.sendMessage(messags);




        }else {



            new Thread(new Runnable() {

                @Override
                public void run() {
                    Connection conn = null;

                    conn = (Connection) DBOpenHelper.getConn();
                    String sql = "Select * from diary where username="+"'"+username+"';";//SQL语句
                    Statement st;
                    try {
                        st = (Statement) conn.createStatement();
                        ResultSet rs = st.executeQuery(sql);
                        list.clear();

                        while(rs.next()){


                            DiaryBean diaryBean=new DiaryBean();
                            diaryBean.setUsername(username);
                            diaryBean.setTitle(rs.getString(2));
                            diaryBean.setContent(rs.getString(3));
                            diaryBean.setWeather(rs.getInt(6));
                            diaryBean.setMood(rs.getInt(5));
                            diaryBean.setData(rs.getString(4));
                            list.add(diaryBean);
                        }



                        System.out.println("数据长度"+list.size());
                        Message messags = new Message();
                        messags.what = 0x30;
                        Looper.prepare();
                        MainFragment.newInstance().handler.sendMessage(messags);
                        Looper.loop();
                        //   handler.sendMessage(message);
                        st.close();
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("失败0"+e);
                    }
                }
            }).start();






        }

        return list;
    }



    public void DeleteDiary(final String title, final String username){


        new Thread(new Runnable() {
            @Override
            public void run() {

        Connection conn = null;
        conn = (Connection) DBOpenHelper.getConn();
        String sql = "DELETE FROM diary  where title='"+title+"'AND username='"+username+"'\n";
        Statement st;

        try {
            st = (Statement) conn.createStatement();
            int rs = st.executeUpdate(sql);

            if(rs>0){
                System.out.println("删除成功");
                loadDiaryList();
                Message messags = new Message();
                messags.what = 0x20;

                Looper.prepare();
                MainFragment.newInstance().handler.sendMessage(messags);
                Looper.loop();
                //     MainFragment.newInstance().loadDiaryData();

            }else {
                System.out.println("删除失败");

            }



        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("删除错误"+e);
        }
            }
        }).start();


    }




    public void mod(){






    }



static {
    list=new ArrayList<DiaryBean>();



}


}
