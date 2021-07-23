package com.example.biyelunw;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.androidadvance.topsnackbar.TSnackbar;
import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.biyelunw.Frag.MainFragment;
import com.example.biyelunw.Frag.ThreeFragment;
import com.example.biyelunw.Frag.TwoFragment;

import java.util.List;

public class AppActivity  extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener  {

    BottomNavigationBar mBottomNavigationBar;
    private MainFragment mFragmentOne;
    private TwoFragment mFragmentTwo;
    private ThreeFragment mFragementThree;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        DecadeLoginState();

        //在第一次打开APP时，判断有没有登录

        //获取日记的数量 并且复制给这里
        /*BadgeItem badgeItem = new BadgeItem();
        badgeItem.setHideOnSelect(false)
                .setBackgroundColorResource(R.color.bule_2ead4d)
                .setBorderWidth(0);*/

      //  new riji_itemsActivity().onCreate(Bundle.EMPTY);

//.setBadgeItem(badgeItem)


    mBottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        mBottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.zhu_jilu, "日记本").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.drawable.zhu_rili, "日历").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.drawable.zhu_geren, "个人").setActiveColorResource(R.color.colorPrimary))
                .setFirstSelectedPosition(0)//设置默认选择item
                .initialise();//初始化
     /*   mFragmentOne = MainFragment.newInstance("111");
        if (mFragmentOne == null) {
            mFragmentOne = MainFragment.newInstance("111");
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
     transaction.replace(R.id.ll_content, mFragmentOne);*/

        mBottomNavigationBar.setTabSelectedListener( this);
        setDefaultFragment();

    }








    public static Handler handler = new Handler() {


        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0x50:
                    Toast.makeText(MyApplication.getAppContext(), "登录成功", Toast.LENGTH_SHORT).show();
                    break;
                case 0x1:

                    break;


            }

        }

    };










    private void setDefaultFragment() {  //设置默认布局

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mFragmentOne = MainFragment.newInstance();
        transaction.replace(R.id.ll_content, mFragmentOne).commit();

    }




    @Override
    public void onTabSelected(int position) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                if (mFragmentOne == null) {
                    mFragmentOne = MainFragment.newInstance();
                }
                transaction.replace(R.id.ll_content, mFragmentOne);
                break;

            case 1:
                if(mFragmentTwo==null){
                mFragmentTwo=TwoFragment.newInstance();
                }
                transaction.replace(R.id.ll_content,mFragmentTwo);
                break;
            case 2:
                if(mFragementThree==null){
                    mFragementThree=ThreeFragment.newInstance();
                 }
                transaction.replace(R.id.ll_content,mFragementThree);
               break;
        }
        transaction.commit();

    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }


    public void DecadeLoginState(){

        SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
        //第二个参数为缺省值，如果不存在该key，返回缺省值
        String data=sp.getString("username_save","");


        if(data!=""){


            Toast.makeText(this, "自动登录成功-"+data, Toast.LENGTH_SHORT).show();
            MainFragment.username=data;

        //    ThreeFragment.newInstance().alreadly(ThreeFragment.view);
        }else {

        }



    }

}
