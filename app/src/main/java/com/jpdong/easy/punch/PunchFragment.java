package com.jpdong.easy.punch;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jpdong.easy.Global;
import com.jpdong.easy.R;
import com.jpdong.easy.punch.model.PunchRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PunchFragment extends Fragment {

    private static final int ADD_PUNCH = 1;

    private RecyclerView mPunchListView;
    private PunchAdapter mPunchAdapter;
    private PunchRepository mRepository;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = new PunchRepository(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_punch, container, false);
        setViews(view);
        return view;
    }

    private void setViews(View view) {
        mPunchListView = view.findViewById(R.id.rv_punchlist);
        mPunchListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mPunchAdapter = new PunchAdapter();
        mPunchListView.setAdapter(mPunchAdapter);
        FloatingActionButton addPunchButton = view.findViewById(R.id.fab_list_add);
        addPunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mPunchAdapter.addPunch(new PunchItem("test", System.currentTimeMillis() - 100000));
                showAddView();
            }
        });
        mPunchAdapter.setRepository(mRepository);
        mPunchAdapter.setData(mRepository.punchItemList());
    }

    private void showAddView() {
        /*AddPunchFragment fragment = new AddPunchFragment();
        //fragment.show(getFragmentManager(),"AddPunchFragment");
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(fragment,"AddPunchFragment");
        transaction.commit();*/
        Intent addPunchIntent = new Intent(getContext(), AddPunchActivity.class);
        startActivityForResult(addPunchIntent, ADD_PUNCH);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ADD_PUNCH:
                if (resultCode == AddPunchActivity.SUCCESS) {
                    String title = data.getStringExtra("title");
                    int duration = data.getIntExtra("duration",0);
                    PunchItem punchItem = new PunchItem(title, duration);
                    mPunchAdapter.addPunch(punchItem);
                    mRepository.addPunchItem(punchItem);
                }
                break;
            default:
                break;
        }
    }

    static class PunchAdapter extends RecyclerView.Adapter<PunchViewHolder> {

        private List<PunchItem> punchItems = new ArrayList<>();
        private PunchRepository repository;

        public PunchAdapter() {

        }

        public void setData(List<PunchItem> list) {
            if (list != null) {
                this.punchItems = list;
                notifyDataSetChanged();
            }
        }

        public void addPunch(PunchItem item) {
            punchItems.add(item);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public PunchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_punch, parent, false);
            return new PunchViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final PunchViewHolder holder, final int position) {
            final PunchItem item = punchItems.get(position);
            if (item.punchList == null) {
                item.punchList = repository.punchTimeList(item.getId());
                item.lastTime = item.punchList.get(item.punchList.size() - 1);
            }
            String title = punchItems.get(position).title;
            final int durationMin = punchItems.get(position).duration;
            title = String.format(Locale.CHINA,"%s%d分钟", title, durationMin);
            updatePunchHolder(holder, item, title, durationMin);
            final String finalTitle = title;
            holder.punchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long currentTime = System.currentTimeMillis();
                    item.punchList.add(currentTime);
                    item.lastTime = currentTime;
                    updatePunchHolder(holder,item, finalTitle, durationMin);
                    repository.addPunchTime(item.getId(), currentTime);
                    notifyItemChanged(position);
                }
            });
        }

        private void updatePunchHolder(@NonNull PunchViewHolder holder, PunchItem item, String title, int durationMin) {
            String timeStamp;
            long lastTime = item.lastTime;
            Log.d(Global.TAG, "PunchAdapter/updatePunchHolder:lase time:" + lastTime + ",current " + System.currentTimeMillis());
            long duration = System.currentTimeMillis() - lastTime;
            float hours = duration / Global.MILLIS_PER_HOUR;
            if (hours <= 24.0) {
                timeStamp = String.format("%s小时", Float.toString(hours));
            } else {
                int day = (int) (hours / 24);
                int hour = (int) (hours - 24 * day);
                timeStamp = String.format("%d天%d小时", day, hour);
            }
            holder.punchTitleView.setText(item.title);
            holder.punchLastTimeView.setText(String.format("上一次%s是%s前", title, timeStamp));
            holder.punchTotalView.setText(String.format(Locale.CHINA,"总共%d次 累计%d分钟",item.punchList.size(),item.punchList.size() * durationMin));
            if (duration < durationMin * Global.MILLIS_PER_MIN) {
                holder.punchButton.setEnabled(false);
            } else {
                holder.punchButton.setEnabled(true);
            }
        }

        @Override
        public int getItemCount() {
            return punchItems.size();
        }

        public void setRepository(PunchRepository repository) {
            this.repository = repository;
        }
    }

    static class PunchViewHolder extends RecyclerView.ViewHolder {

        public TextView punchTitleView;
        public TextView punchLastTimeView;
        public TextView punchTotalView;
        public Button punchButton;

        public PunchViewHolder(View itemView) {
            super(itemView);
            punchTitleView = itemView.findViewById(R.id.tv_punch_title);
            punchLastTimeView = itemView.findViewById(R.id.tv_punch_last);
            punchTotalView = itemView.findViewById(R.id.tv_punch_total);
            punchButton = itemView.findViewById(R.id.btn_punch);
        }
    }

}
