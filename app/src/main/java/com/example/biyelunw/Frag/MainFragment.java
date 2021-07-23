package com.example.biyelunw.Frag;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.biyelunw.Bean.DiaryBean;
import com.example.biyelunw.Constants;
import com.example.biyelunw.DiaryDao;
import com.example.biyelunw.MyApplication;
import com.example.biyelunw.R;
import com.example.biyelunw.ReaddiaryActivity;
import com.example.biyelunw.WriteActivity;
import com.example.biyelunw.riji_itemsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hb.dialog.myDialog.MyAlertDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import es.dmoral.toasty.Toasty;

import static android.content.Context.MODE_PRIVATE;


public class MainFragment extends Fragment {

    public static ArrayList<DiaryBean> list=new ArrayList<>();
    static ListView listView;
    FloatingActionButton writeButXuanf;
    static ArrayList<Map<String, Object>> listitem;
    DiaryDao diaryDao;
    public static boolean loginState;
    public static String username;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        Button  upbut=view.findViewById(R.id.updata);
        list=new ArrayList<DiaryBean>();
        listView=view.findViewById(R.id.riji_list);
        writeButXuanf=view.findViewById(R.id.writeButXuanf);
        MainFragment.context = getContext();

        SharedPreferences sp= getActivity().getSharedPreferences("data",MODE_PRIVATE);
        //第二个参数为缺省值，如果不存在该key，返回缺省值
        String data=sp.getString("username_save","");
        if(data!=""){
            loginState=true;
           DiaryDao.username=data;

        }else{
            loginState=false;
            DiaryDao.username="";
        }

        loadDiaryData();

        upbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDiaryData();
            }
        });

        writeButXuanf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(loginState){
                    Intent intent=new Intent(getActivity(), WriteActivity.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }


            }
        });




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> map=(Map<String, Object>) parent.getItemAtPosition(position);
                //   Spinner spinner= parent.getEmptyView().findViewById(R.id.spinner2);
            /*    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String[] spinner_content = getResources().getStringArray(R.array.spinner_content);
                        Toast.makeText(getActivity(), "111111", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), "你点击的是:" + spinner_content[position],   Toast.LENGTH_SHORT).show();}
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) { } });*/



                String title2=(String) listitem.get(position).get("title");
                String content2=(String) listitem.get(position).get("content");
                String time2=(String) listitem.get(position).get("time");
                int mood2=(int)listitem.get(position).get("mood");
                int  weather2=(int)listitem.get(position).get("weather");

                System.out.println("点击的数组"+title2+"  "+content2+"   "+time2);
                Intent intent=new Intent(getActivity(), ReaddiaryActivity.class);
                intent.putExtra("title", title2);
                intent.putExtra("content", content2);
                intent.putExtra("time", time2);
                intent.putExtra("mood", mood2);
                intent.putExtra("weather", weather2);
                startActivity(intent);
            }
        });




        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String title3=(String) listitem.get(position).get("title");
                Toast.makeText(getActivity(), "长按测试", Toast.LENGTH_SHORT).show();
                MyAlertDialog myAlertDialog = new MyAlertDialog(getActivity()).builder()
                        .setTitle("确认吗？")
                        .setMsg("删除内容")
                        .setPositiveButton("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getActivity(), "确定", Toast.LENGTH_SHORT).show();
                                DiaryDao.newInstance().DeleteDiary(""+title3,"123");

                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getActivity(), "取消", Toast.LENGTH_SHORT).show();
                            }
                        });
                myAlertDialog.show();
                return true;
            }
        });

        return view;
    }





    public static Handler handler = new Handler() {


        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0x20:
                    System.out.println("没有数据");
                    listitem = new ArrayList<Map<String, Object>>();
                    String[] title2 = new String[100];
                    String[] content2 = new String[100];
                    String[] time2 = new String[100];
                    int[] mood2 = new int[100];
                    int[] weather2 = new int[100];


                    for (int i = 0; i < list.size(); i++) {
                        title2[i] = list.get(i).getTitle();
                        content2[i] = list.get(i).getContent();
                        time2[i] = list.get(i).getData();
                        mood2[i] = list.get(i).getMood();
                        weather2[i] = list.get(i).getWeather();

                        Map map = new HashMap<>();
                        map.put("title", title2[i]);
                        map.put("content", content2[i]);
                        map.put("time", time2[i]);
                        map.put("mood", mood2[i]);
                        map.put("weather", weather2[i]);
                        listitem.add(map);
                    }


                    SimpleAdapter simpleAdapter2 = new SimpleAdapter(getAppContext(), listitem, R.layout.riji_item, new String[]{"title", "content", "time"}, new int[]{R.id.item_title, R.id.item_content, R.id.item_time});
                    listView.setAdapter(simpleAdapter2);

                    break;



                case 0x30:
                    System.out.println("删除更新" + list.size());
                    System.out.println("当前数组长度" + list.size());
                    System.out.println("主线程获取到的长度是" + list.size());


                    listitem = new ArrayList<Map<String, Object>>();
                    String[] title = new String[100];
                    String[] content = new String[100];
                    String[] time = new String[100];
                    int[] mood = new int[100];
                    int[] weather = new int[100];

                    for (int i = 0; i < list.size(); i++) {
                        title[i] = list.get(i).getTitle();
                        content[i] = list.get(i).getContent();
                        time[i] = list.get(i).getData();
                        mood[i] = list.get(i).getMood();
                        weather[i] = list.get(i).getWeather();

                        Map map = new HashMap<>();
                        map.put("title", title[i]);
                        map.put("content", content[i]);
                        map.put("time", time[i]);
                        map.put("mood", mood[i]);
                        map.put("weather", weather[i]);
                        listitem.add(map);
                    }


                    SimpleAdapter simpleAdapter = new SimpleAdapter(getAppContext(), listitem, R.layout.riji_item, new String[]{"title", "content", "time"}, new int[]{R.id.item_title, R.id.item_content, R.id.item_time});
                    listView.setAdapter(simpleAdapter);

                    break;


            }

        }

    };


    private static Context context;




    public static Context getAppContext() {
        return MainFragment.context;
    }



    public static MainFragment newInstance(){
        MainFragment fragment=new MainFragment();
        return fragment;
    }


    private void delay(int ms){
        try {
            Thread.currentThread();
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    //读取日记的数据
    public static void loadDiaryData() {
        System.out.println("执行了没");
//        Toast.makeText(getActivity(), "执行", Toast.LENGTH_SHORT).show();
        list= DiaryDao.loadDiaryList();
    }






}
