package com.example.biyelunw;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biyelunw.Bean.DiaryBean;
import com.example.biyelunw.Frag.MainFragment;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.zzhoujay.richtext.RichText;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import jp.wasabeef.richeditor.RichEditor;

public class WriteActivity extends Activity {

Button writebutt;
EditText titleText;
RichEditor contentText;
Message message;
DiaryBean diaryBean;
Handler handler;
TextView riqi;
TextView xinqing;
TextView tianqi;
ImageView xingqiView;
ImageView tianqiView;

ImageButton back;
ImageButton chexiao;
ImageButton delete;
TextView finish;




String title;
String content;
String time;
int mood=0;
int weather=0;

    DialogPlus dialog;
    DialogPlus dialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writediary);

        riqi=this.findViewById(R.id.textView7);
        tianqi=this.findViewById(R.id.textView9);
        xinqing=this.findViewById(R.id.textView8);
        xingqiView=this.findViewById(R.id.imageView7);
        tianqiView=this.findViewById(R.id.imageView2);


        back=this.findViewById(R.id.write_back);
        chexiao=this.findViewById(R.id.write_chexiao);
        delete=this.findViewById(R.id.write_delete);
        finish=this.findViewById(R.id.write_finish);



        writebutt=this.findViewById(R.id.writeBut);
        titleText=this.findViewById(R.id.biaotitxt);
        contentText=this.findViewById(R.id.neirongtxt);
        contentText.setPadding(10, 10, 10, 10);
        contentText.setPlaceholder("记下一天的生活吧...");




        initButton();
        initWeatherDialog();
        initDecadeDialog();

            initDay();
        final DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                riqi.setText(""+year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
            }
        };
        RichText.initCacheDir(this);



         handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {

                switch (msg.what){
                    case 0x11:
                        Toast.makeText(WriteActivity.this, "上传成功", Toast.LENGTH_SHORT).show();

                        System.out.println("上传成功");

                        Intent intent=new Intent(WriteActivity.this,AppActivity.class);

                        startActivity(intent);

                        break;
                    case 0x12:
                        Toast.makeText(WriteActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                        break;
                    case 0x13:
                        Toast.makeText(WriteActivity.this, "读取失败", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        };

       message= handler.obtainMessage();
        riqi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();// 获取当前系统日期
                int mYear = c.get(Calendar.YEAR);// 获取年份
                int mMonth = c.get(Calendar.MONTH);// 获取月份
                int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取天数
                new DatePickerDialog(WriteActivity.this, mDateSetListener, mYear, mMonth, mDay).show() ;
                riqi.setText(""+mYear+"-"+(mMonth+1)+"-"+mDay);
            }
        });



        writebutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设计
            String titile=titleText.getText().toString();
            String content=contentText.getContext().toString();

            String data=riqi.getText().toString();
                 diaryBean=new DiaryBean();

                 diaryBean.setUsername(DiaryDao.username);
                diaryBean.setTitle("1111");
                diaryBean.setContent("2222");
                diaryBean.setData("2021-7-16");
                diaryBean.setMood(1);
                diaryBean.setWeather(1);
               // MainFragment.list.add(diaryBean);
                uploadDiary();


            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title=titleText.getText().toString();
                content=contentText.getHtml();

                System.out.println(content);
                Toast.makeText(WriteActivity.this, content, Toast.LENGTH_SHORT).show();
                time=riqi.getText().toString();
                diaryBean=new DiaryBean();
                diaryBean.setUsername(DiaryDao.username);
                diaryBean.setTitle(title);
                diaryBean.setContent(content);

                diaryBean.setData(time);
                diaryBean.setMood(mood);
                diaryBean.setWeather(weather);
             //   MainFragment.list.add(diaryBean);

                uploadDiary();


            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contentText.setHtml("");
                titleText.setText("");
                Toast.makeText(WriteActivity.this, "删除成功", Toast.LENGTH_SHORT).show();


            }
        });



        chexiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contentText.undo();

            }
        });


        xinqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.show();


            }
        });


        tianqi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog2.show();
            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(WriteActivity.this,AppActivity.class);
                startActivity(intent);

            }
        });


    }







    public void initButton(){


    }






    public void initDecadeDialog(){
        int[] images = new int[]{R.drawable.kaixin, R.drawable.nanguo, R.drawable.wugan, R.drawable.biaoqing_yumeng
        };

        String[] names = new String[]{"开心", "难过","无感","郁闷"};
        ArrayList<Map<String, Object>>   listitem = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < names.length; i++) {
            Map map = new HashMap<>();
            map.put("images", images[i]);
            map.put("names", names[i]);
            listitem.add(map);
            //这里是第二个的界面以及界面的各种信息和资源
        }

        System.out.println("list"+listitem.size());
        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, listitem, R.layout.xinqing_items, new String[]{"images", "names",}, new int[]{R.id.xingqingtp, R.id.xinqingwenz});

         dialog = DialogPlus.newDialog(this)
                .setAdapter(simpleAdapter)
                .setGravity(Gravity.CENTER)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        switch (position){
                            case 0:
                                Toast.makeText(WriteActivity.this, "第一个", Toast.LENGTH_SHORT).show();
                                mood=1;
                                xingqiView.setImageResource(R.drawable.kaixin);
                                xinqing.setText("开心");
                                dialog.dismiss();
                                break;
                            case 1:
                                xingqiView.setImageResource(R.drawable.nanguo);
                                mood=2;
                                Toast.makeText(WriteActivity.this, "第二个", Toast.LENGTH_SHORT).show();
                                xinqing.setText("难过");
                                dialog.dismiss();
                                break;
                            case 2:
                                xinqing.setText("无感");
                                xingqiView.setImageResource(R.drawable.wugan);
                                mood=3;
                                dialog.dismiss();
                                break;
                            case 3:
                                xinqing.setText("郁闷");
                                xingqiView.setImageResource(R.drawable.biaoqing_yumeng);
                                mood=4;
                                dialog.dismiss();

                                break;


                        }

                    }
                })
                .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
                .create();


    }





    public void initWeatherDialog(){
        int[] images = new int[]{R.drawable.tianqi_qing, R.drawable.tianqi_yin, R.drawable.tianqi_yu, R.drawable.tianqi_xue
        };

        String[] names = new String[]{"晴天", "阴天","雨天","下雪"};
        ArrayList<Map<String, Object>>   listitem = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < names.length; i++) {
            Map map = new HashMap<>();
            map.put("images", images[i]);
            map.put("names", names[i]);
            listitem.add(map);
            //这里是第二个的界面以及界面的各种信息和资源
        }

        System.out.println("list"+listitem.size());
        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, listitem, R.layout.tianqi_items, new String[]{"images", "names",}, new int[]{R.id.tianqitp, R.id.tianqiwenzi});

        dialog2 = DialogPlus.newDialog(this)
                .setAdapter(simpleAdapter)
                .setGravity(Gravity.CENTER)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        switch (position){
                            case 0:
                                weather=1;
                                tianqiView.setImageResource(R.drawable.tianqi_qing);
                                tianqiView.setBackgroundResource(R.drawable.tianqi_qing);
                                tianqi.setText("晴天");
                                dialog2.dismiss();
                                break;
                            case 1:
                                tianqi.setText("阴天");
                                tianqiView.setImageResource(R.drawable.tianqi_yin);
                                tianqiView.setBackgroundResource(R.drawable.tianqi_yin);
                                weather=2;
                                Toast.makeText(WriteActivity.this, "第二个", Toast.LENGTH_SHORT).show();
                                dialog2.dismiss();
                                break;
                            case 2:
                                tianqi.setText("下雨");
                                tianqiView.setImageResource(R.drawable.tianqi_yu);
                                tianqiView.setBackgroundResource(R.drawable.tianqi_yu);
                                weather=3;
                                dialog2.dismiss();
                                break;

                            case 3:
                                tianqi.setText("下雪");
                                tianqiView.setImageResource(R.drawable.tianqi_xue);
                                tianqiView.setBackgroundResource(R.drawable.tianqi_xue);
                                weather=4;
                                dialog2.dismiss();

                                break;

                        }

                    }
                })
                .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
                .create();


    }




    public  void initDay(){
    final Calendar c = Calendar.getInstance();// 获取当前系统日期
    int mYear = c.get(Calendar.YEAR);// 获取年份
    int mMonth = c.get(Calendar.MONTH);// 获取月份
    int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取天数
    riqi.setText("  "+mYear+"-"+(mMonth+1)+"-"+mDay);
}


    public void uploadDiary(){
        System.out.println("当前封装的数据"+diaryBean.toString());

        //上传到服务器上去
        //连接数据库进行操作需要在主线程操作

        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Connection conn = null;
                    conn = (Connection) DBOpenHelper.getConn();
                    String sql = "insert into diary values(" + diaryBean.getUsername() + "," + diaryBean.getTitle() + "," + diaryBean.getContent() + "," + diaryBean.getData() + ",'" + diaryBean.getMood() + "','" + diaryBean.getWeather() + "');";


                    String a = "insert into diary VALUES(" + diaryBean.getUsername() + "," + diaryBean.getTitle() + "," + diaryBean.getContent() + "," + diaryBean.getData() + ",1,2)";

                    String b = "insert into diary VALUES(\"" + diaryBean.getUsername() + "\",\"" + diaryBean.getTitle() + "\",\"" + diaryBean.getContent() + "\",\"" + diaryBean.getData() + "\",1,2);";


                    Statement st;
                    try {
                        st = (Statement) conn.createStatement();
                        int rs = st.executeUpdate(b);
                        if (rs > 0) {
                            message.what = 0x11;
                        } else {
                            message.what = 0x12;
                        }

                        handler.sendMessage(message);
                        st.close();
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();

                        System.out.println("失败0" + e);
                    }
                }
            }).start();

        }catch (Exception e){


        }

    }



}
