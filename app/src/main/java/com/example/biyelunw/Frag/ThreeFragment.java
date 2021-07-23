package com.example.biyelunw.Frag;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.androidadvance.topsnackbar.TSnackbar;
import com.example.biyelunw.LoginActivity;
import com.example.biyelunw.R;
import com.hb.dialog.dialog.ConfirmDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class ThreeFragment extends Fragment {
    private ListView listView;
    private static ArrayList<Map<String, Object>> listitem;
   String EDname;
   String logstate;

   public static View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_three, container, false);
        logstate="登录";
        SharedPreferences sp=getActivity().getSharedPreferences("data",MODE_PRIVATE);
        //第二个参数为缺省值，如果不存在该key，返回缺省值
        String data=sp.getString("username_save","");
        if(data!=""){
            alreadly(view);
        }else {

        }


        init(view);
        return view;

    }



    void init(View view){

        listView = view.findViewById(R.id.list2);
        int[] images = new int[]{R.drawable.three_denglu, R.drawable.three_guanyu
        };
        int you = R.drawable.you2;


        String[] names = new String[]{logstate, "关于软件"};
        listitem = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < names.length; i++) {
            Map map = new HashMap<>();
            map.put("images", images[i]);
            map.put("names", names[i]);
            map.put("yous",you);
            listitem.add(map);
            //这里是第二个的界面以及界面的各种信息和资源
        }

        System.out.println("list"+listitem.size());
        SimpleAdapter simpleAdapter = new SimpleAdapter(this.getActivity(), listitem, R.layout.items, new String[]{"images", "names","yous"}, new int[]{R.id.tb, R.id.wz,R.id.youimg});

        listView.setAdapter(simpleAdapter);

      listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            private Message messags;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);


                String name1 =(String) map.get("names");



                //  String[] names=new String[]{"观看历史","我的收藏","关于软件","软件设置"};
                switch (name1){
                    case "登录":
                        Toast.makeText(getContext(), "登录", Toast.LENGTH_SHORT).show();
                        login();
                        break;
                    case "已登录":
                        Toast.makeText(getContext(), "退出登录", Toast.LENGTH_SHORT).show();


                        TSnackbar snackbar2 =  TSnackbar.make(getActivity().findViewById(android.R.id.content),"退出登录成功",TSnackbar.LENGTH_LONG);
                        View snackbarView2 = snackbar2.getView();
                        snackbarView2.setBackgroundColor(Color.parseColor("#ff0000"));
                        TextView textView2 = (TextView) snackbarView2.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
                        textView2.setTextColor(Color.WHITE);
                        snackbar2.show();
                        outlogin();
                        break;

                    case "关于软件":

                        ConfirmDialog confirmDialog = new ConfirmDialog(getActivity());
                        confirmDialog.setLogoImg(R.mipmap.dialog_notice).setMsg("谢谢你使用此软件:D");
                        confirmDialog.setClickListener(new ConfirmDialog.OnBtnClickListener() {
                            @Override
                            public void ok() {

                            }

                            @Override
                            public void cancel() {

                            }
                        });
                        confirmDialog.show();


                        break;
                        default:

                            break;
            }
        }
        });


    }


public void outlogin(){

    SharedPreferences sp=getActivity().getSharedPreferences("data",MODE_PRIVATE);
    SharedPreferences.Editor editor=sp.edit();   //获取编辑器
    editor.remove("username_save");   //删除一条数据
    editor.clear();         //删除所有数据
    editor.commit();     //提交修改，否则不生效

    TextView loginState=view.findViewById(R.id.loginState);
    TextView alredyusername=view.findViewById(R.id.alredltusername);
    loginState.setText("未登录");
    alredyusername.setText("");
    logstate="登录";
    init(view);





}
    public void alreadly(View view){

    TextView loginState=view.findViewById(R.id.loginState);
    TextView alredyusername=view.findViewById(R.id.alredltusername);
        SharedPreferences sp=getActivity().getSharedPreferences("data",MODE_PRIVATE);
        //第二个参数为缺省值，如果不存在该key，返回缺省值
        String data=sp.getString("username_save","");
        loginState.setText("已登录");
    alredyusername.setText(data);

        logstate="已登录";


    }


    public void login(){

        Intent intent=new Intent(this.getActivity(), LoginActivity.class);
        startActivity(intent);

    }

    public static ThreeFragment newInstance(){
        ThreeFragment fragment=new ThreeFragment();
        return fragment;
    }






}
