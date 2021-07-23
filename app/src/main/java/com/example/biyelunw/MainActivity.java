package com.example.biyelunw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class MainActivity extends AppCompatActivity {

    Handler handler=new Handler()
    {

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            GetActivity();
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler.sendEmptyMessageDelayed(0x1,1500);

    }




    public void GetActivity()
    {
        Intent intent = new Intent(MainActivity.this, AppActivity.class);
        startActivity(intent);
    }


}
