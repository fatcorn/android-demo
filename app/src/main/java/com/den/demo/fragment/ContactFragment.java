package com.den.demo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.den.demo.R;
import com.den.demo.adapter.HomePageActivityViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class ContactFragment extends Fragment {

    private ViewPager viewPager;
    private HomePageActivityViewPagerAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_frament, container, false);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.contact_tab_layout);
        viewPager = (ViewPager) view.findViewById(R.id.contact_view);
        adapter = new HomePageActivityViewPagerAdapter(getChildFragmentManager());


        // adapter 添加fragment页面 顺序与TabItem对应
        adapter.addFragment(new ContactFriendsFragment());
        adapter.addFragment(new ContactClubFragment());
        adapter.addFragment(new ContactSubFragment());

        //viewPager关联adapter
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        String[] navTitle = {"好友", "clubs", "订阅号"};
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setText(navTitle[i]);
        }


        // tabLayout 选中监听器
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

}
