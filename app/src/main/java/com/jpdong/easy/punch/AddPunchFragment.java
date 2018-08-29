package com.jpdong.easy.punch;

import android.app.Dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.jpdong.easy.Global;
import com.jpdong.easy.R;

import java.util.ArrayList;
import java.util.List;

public class AddPunchFragment extends DialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_MinWidth);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_NoTitleBar_Fullscreen);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_punch_add, container, false);
        EditText titleText = view.findViewById(R.id.et_punch_add_name);
        final EditText timeText = view.findViewById(R.id.et_punch_add_time);
        timeText.setFocusable(false);
        final List<String> hourNums = new ArrayList<>();
        for (int i = 1; i < 24; i++) {
            hourNums.add(String.valueOf(i));
        }
        final List<String> minNums = new ArrayList<>();
        for (int i = 1; i < 60; i++) {
            minNums.add(String.valueOf(i));
        }
        final List<String> units = new ArrayList<>();
        units.add("分钟");
        units.add("小时");
        final List<List<String>> connectList = new ArrayList<>();
        connectList.add(minNums);
        connectList.add(hourNums);
        timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        Log.d(Global.TAG, String.format("AddPunchFragment/onOptionsSelect:%d,%d,%d",options1,option2,options3));
                        /*String tx = timeNum.get(options1)
                                + tempList.get(0).get(0);
                        timeText.setText(tx);*/
                    }
                }).build();
                pvOptions.setPicker(units, connectList);
                pvOptions.show();
            }
        });
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
}
