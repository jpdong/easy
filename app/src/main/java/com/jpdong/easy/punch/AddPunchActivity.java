package com.jpdong.easy.punch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.jpdong.easy.Global;
import com.jpdong.easy.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddPunchActivity extends Activity {

    public static final int FAIL = 0;
    public static final int SUCCESS = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_punch_add);
        final EditText titleText = findViewById(R.id.et_punch_add_name);
        final EditText timeText = findViewById(R.id.et_punch_add_time);
        Button addButton = findViewById(R.id.btn_add);
        final Intent result = new Intent();
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
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                OptionsPickerView pvOptions = new OptionsPickerBuilder(AddPunchActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        Log.d(Global.TAG, String.format("AddPunchFragment/onOptionsSelect:%d,%d,%d",options1,options2,options3));
                        /*String tx = timeNum.get(options1)
                                + tempList.get(0).get(0);
                        timeText.setText(tx);*/
                        int duration = Integer.valueOf(connectList.get(options1).get(options2))*(1+options1*59);
                        result.putExtra("duration", duration);
                        timeText.setText(String.format(Locale.CHINA,"%d分钟",duration));
                    }
                }).build();
                pvOptions.setPicker(units, connectList);
                pvOptions.show();
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleText.getText().toString().trim();
                if (title == null || "".equals(title)) {
                    Toast.makeText(getApplicationContext(),"活动不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                result.putExtra("title", title);
                AddPunchActivity.this.setResult(SUCCESS,result);
                finish();
            }
        });
    }
}
