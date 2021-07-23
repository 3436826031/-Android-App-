package com.example.biyelunw;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import jp.wasabeef.richeditor.RichEditor;

public class ReaddiaryActivity extends Activity {


    TextView Title;
    RichEditor Content;
    TextView TimeText;
    TextView MoodText;
    TextView WeatherText;
    ImageView MoodImg;
    ImageView WeatherImg;
    ImageView ReadBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readdiary);
        init();

        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");
        String time = getIntent().getStringExtra("time");
        int mood =getIntent().getIntExtra("mood",0);
        int weather =(int)getIntent().getIntExtra("weather",0);

        ReadBack=this.findViewById(R.id.read_back);



        Decade(mood,weather);
        Title.setText(title);
        Content.setHtml(content);
        TimeText.setText(time);




        ReadBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ReaddiaryActivity.this,AppActivity.class);
                startActivity(intent);
            }
        });


    }



    public void Decade(int mood,int weather){


        switch (mood){
            case 0:
                MoodText.setText("无");

                break;


            case 1:
                MoodText.setText("开心");
                MoodImg.setBackgroundResource(R.drawable.kaixin);
                break;

            case 2:
                MoodText.setText("难过");
                MoodImg.setBackgroundResource(R.drawable.nanguo);
                break;

            case 3:
                MoodText.setText("无感");
                MoodImg.setBackgroundResource(R.drawable.wugan);
                break;

            case 4:
                MoodText.setText("郁闷");
                MoodImg.setBackgroundResource(R.drawable.biaoqing_yumeng);
                break;


        }


        switch (weather){


            case 0:
                WeatherText.setText("无");

                break;


            case 1:
                WeatherText.setText("晴天");
                WeatherImg.setBackgroundResource(R.drawable.tianqi_qing);
                break;

            case 2:
                WeatherText.setText("阴天");
                WeatherImg.setBackgroundResource(R.drawable.tianqi_yin);
                break;

            case 3:
                WeatherText.setText("下雨");
                WeatherImg.setBackgroundResource(R.drawable.tianqi_yu);
                break;

            case 4:
                WeatherText.setText("下雪");
                WeatherImg.setBackgroundResource(R.drawable.tianqi_xue);
                break;




        }


    }



    public void init(){

        Title=this.findViewById(R.id.read_title);
        Content=this.findViewById(R.id.read_content);


        Title.setFocusableInTouchMode(false);

        Content.setFocusableInTouchMode(false);
        TimeText=this.findViewById(R.id.read_time);
        WeatherText=this.findViewById(R.id.read_weather);
        WeatherImg=this.findViewById(R.id.read_weather_img);

        MoodText=this.findViewById(R.id.read_mood);
        MoodImg=this.findViewById(R.id.read_mood_img);


    }

}
