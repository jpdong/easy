package com.jpdong.easy.todo;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jpdong.easy.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DoubleListViewController {

    private View mView;
    private Context mContext;
    private RecyclerView mCategoryView;
    private RecyclerView mThingView;
    private SimpleTextAdapter mThingAdaper;
    List<List<String>> things;

    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = mCategoryView.getChildLayoutPosition(v);
            mThingAdaper.setList(things.get(position));
        }
    };


    public DoubleListViewController(View view, Context context) {
        this.mView = view;
        this.mContext = context;
        initViews(view);
    }

    private void initViews(View view) {

        List<String> category = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            category.add("Category-" + i);
        }
        things = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            List<String> thingDetail = new ArrayList<>();
            for (int j = 0; j < 50; j++) {
                thingDetail.add(String.format(Locale.CHINA, "C%d-T%d", i, j));
            }
            things.add(thingDetail);
        }
        mCategoryView = view.findViewById(R.id.rv_category);
        mCategoryView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false));
        mCategoryView.setAdapter(new SimpleTextAdapter(category, mListener));
        mThingView = view.findViewById(R.id.rv_thing);
        mThingView.setLayoutManager(new GridLayoutManager(mContext, 3));
        mThingAdaper = new SimpleTextAdapter(things.get(0));
        mThingView.setAdapter(mThingAdaper);
    }

    public void start() {

    }

    public void stop() {

    }

    public void destory() {

    }

    static class SimpleTextAdapter extends RecyclerView.Adapter<SimpleTextViewHoler> {

        List<String> list = new ArrayList<>();
        View.OnClickListener listener;

        public SimpleTextAdapter(List<String> list) {
            this.list = list;
        }

        public SimpleTextAdapter(List<String> list, View.OnClickListener listener) {
            this.list = list;
            this.listener = listener;
        }

        public void setList(List<String> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public SimpleTextViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView textView = new TextView(parent.getContext());
            textView.setWidth(dp2px(60));
            textView.setHeight(dp2px(40));
            return new SimpleTextViewHoler(textView);
        }

        @Override
        public void onBindViewHolder(@NonNull SimpleTextViewHoler holder, int position) {
            holder.setText(list.get(position));
            if (listener != null) {
                holder.itemView.setOnClickListener(listener);
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private static class SimpleTextViewHoler extends RecyclerView.ViewHolder {

        public SimpleTextViewHoler(View itemView) {
            super(itemView);
        }

        public void setText(String s) {
            if (itemView instanceof TextView) {
                ((TextView) itemView).setText(s);
            }
        }
    }

    public static int dp2px(float dp) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        float px = displayMetrics.density * dp;
        return Math.round(px);
    }

}
