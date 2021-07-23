package com.example.biyelunw.Frag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.biyelunw.Constants;
import com.example.biyelunw.R;


/**
 * Created by Kevin on 2016/11/20.
 * Blog:http://blog.csdn.net/student9128
 * Describe：the BaseFragment
 */

public class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment,container,false);

       // TextView mFragmentText = (TextView) view.findViewById(R.id.tv_fragment_text);
        Bundle bundle = getArguments();
        String args = bundle.getString(Constants.KEY_ARGS);
     //   mFragmentText.setText(args);
        return view;

    }
}
