package com.example.biyelunw.Frag;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.biyelunw.Bean.DayTaskBean;
import com.example.biyelunw.R;
import com.loopeer.cardstack.CardStackView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class TwoFragment extends Fragment {

   ListView listView;
    MaterialCalendarView calendarView;
    ArrayList<Map<String,Object>> listitem;
    Set<String> list2;
    Set<Map<String,String>> list3;
    Button add;
    CardStackView mStackView;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public int year;
    public int month;
    public int day;
    String  taskName;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_two, container, false);
        listView=view.findViewById(R.id.renwuListView);
        calendarView=view.findViewById(R.id.calendarView);
        add=view.findViewById(R.id.AddTask);



         sp=getContext().getSharedPreferences("data",MODE_PRIVATE);
         editor=sp.edit();   //获取编辑器
           // editor.putStringSet("saveRenwu",list2);
         //editor.commit();                //提交修改，否

        listitem=new ArrayList<>();
        //读取临时数据


        final Calendar c = Calendar.getInstance();// 获取当前系统日期
         year = c.get(Calendar.YEAR);// 获取年份
         month = c.get(Calendar.MONTH)+1;// 获取月份
         day = c.get(Calendar.DAY_OF_MONTH);// 获取天数


        calendarView.setSelectedDate(new Date());//日历设置时间

        taskName=year+month+day+"";
        Toast.makeText(getActivity(), taskName, Toast.LENGTH_SHORT).show();

        loadRenwuData(taskName);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert_edit(view);

            }
        });


        /*List<String> list = new ArrayList<>();
        list.addAll(list2);
        String[] content=new String[100];
        for(int i=0;i<list2.size();i++){
            Map map = new HashMap<>();
            map.put("content",list.get(i));
            listitem.add(map);
        }*/
        SimpleAdapter simpleAdapter = new SimpleAdapter(this.getActivity(), listitem, R.layout.renwu_items, new String[]{"content"}, new int[]{R.id.checkBox2});
        listView.setAdapter(simpleAdapter);

      calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
          @Override
          public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

              year= date.getYear();
              month= (date.getMonth()+1);
              day=date.getDay();
              String date2=year+month+day+"";
              Toast.makeText(getActivity(), date2, Toast.LENGTH_SHORT).show();
           //   listitem.clear();
              loadRenwuData(date2);

          }
      });
        return view;
    }



    public static TwoFragment newInstance(){
        TwoFragment fragment=new TwoFragment();
        return fragment;

    }




//弹窗
    public void alert_edit(View view){
        final EditText et = new EditText(getContext());
        new AlertDialog.Builder(getContext()).setTitle("请输入日程")
              //  .setIcon(android.R.drawable.sym_def_app_icon)
                .setIcon(R.drawable.renwu)
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //按下确定键后的事
                        //Set集合转换为list集合
                        list2.add(" "+et.getText().toString());
                        System.out.println("添加一个新的："+list2.size()+et.getText().toString());
                      //  Map map = new HashMap<>();      List<String> list = new ArrayList<>();         list.addAll(list2);        map.put("content",list.get(list2.size()-1));

                        editor.putStringSet("saveRenwu"+taskName,list2);
                        editor.commit();
                    loadRenwuData(taskName);
                    }
                }).setNegativeButton("取消",null).show();
    }



void  loadRenwuData(String date){


         sp=getContext().getSharedPreferences("data",MODE_PRIVATE);

         list2 = new HashSet<String>(sp.getStringSet("saveRenwu"+date,new HashSet<String>()));

      //   Set<String> list2=new HashSet<String>(sp.getStringSet("data", new HashSet<String>()));
         System.out.println("测试"+list2.size());
         if(list2.size()!=0){
             listitem.clear();
             List<String> list = new ArrayList<>();
             list.addAll(list2);
             for(int i=0;i<list2.size();i++){

                 Map map = new HashMap<>();
                 map.put("content",list.get(i));
                 listitem.add(map);
                 SimpleAdapter simpleAdapter = new SimpleAdapter(this.getActivity(), listitem, R.layout.renwu_items, new String[]{"content"}, new int[]{R.id.checkBox2});
                 listView.setAdapter(simpleAdapter);
             }
         }else{
             System.out.println("没有"+listitem.size());

             list2.clear();
             listitem.clear();

             SimpleAdapter simpleAdapter = new SimpleAdapter(this.getActivity(), listitem, R.layout.renwu_items, new String[]{"content"}, new int[]{R.id.checkBox2});
             listView.setAdapter(simpleAdapter);
         }

     }






}