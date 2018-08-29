package com.jpdong.easy;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jpdong.easy.punch.PunchFragment;
import com.jpdong.easy.todo.TodoFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        //tabLayout.addTab(tabLayout.newTab().setText("打卡").setIcon(R.drawable.icon_punch));
        //tabLayout.addTab(tabLayout.newTab().setText("清单").setIcon(R.drawable.icon_check));
        ViewPager viewPager = findViewById(R.id.view_pager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager()));
        View punchTab = getLayoutInflater().inflate(R.layout.tab_item, null);
        ImageView punchIcon = punchTab.findViewById(R.id.tab_icon);
        punchIcon.setImageResource(R.drawable.icon_punch);
        TextView punchText = punchTab.findViewById(R.id.tab_text);
        punchText.setText("打卡");
        tabLayout.getTabAt(0).setCustomView(punchTab);
        View todoTab = getLayoutInflater().inflate(R.layout.tab_item, null);
        ImageView todoIcon = todoTab.findViewById(R.id.tab_icon);
        todoIcon.setImageResource(R.drawable.icon_check);
        TextView todoText = todoTab.findViewById(R.id.tab_text);
        todoText.setText("清单");
        tabLayout.getTabAt(1).setCustomView(todoTab);
    }

    static class PageAdapter extends FragmentPagerAdapter {

        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new PunchFragment();
                case 1:
                    return new TodoFragment();
                default:
                    return new PunchFragment();
            }
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "打卡";
                case 1:
                    return "清单";
                default:
                    return "打卡";
            }
        }


        @Override
        public int getCount() {
            return 2;
        }
    }
}
