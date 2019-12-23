package com.den.demo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.den.demo.R;
import com.den.demo.adapter.HomePageActivityViewPagerAdapter;
import com.den.demo.fragment.CommunityFragment;
import com.den.demo.fragment.ContactFragment;
import com.den.demo.fragment.MessageFragment;
import com.den.demo.fragment.PersonalFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class HomePageActivity extends AppCompatActivity {

    private ViewPager mainActivityViewPager;
    private BottomNavigationView bottomNavView;
    private HomePageActivityViewPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mainActivityViewPager = (ViewPager) findViewById(R.id.main_viewpager);
        bottomNavView = (BottomNavigationView) findViewById(R.id.main_bottom_nav_view);

        adapter = new HomePageActivityViewPagerAdapter(getSupportFragmentManager());
//        为Adapter添加Fragment
        adapter.addFragment(new ContactFragment());
        adapter.addFragment(new MessageFragment());
        adapter.addFragment(new CommunityFragment());
        adapter.addFragment(new PersonalFragment());

        mainActivityViewPager.setAdapter(adapter);

//        为 BottomNavigationView 的菜单项  设置监听事件
        bottomNavView.setOnNavigationItemSelectedListener(item -> {
//               获取到菜单项的Id
            int itemId = item.getItemId();
            switch (itemId) {
                case R.id.tab_contact:
                    mainActivityViewPager.setCurrentItem(item.getOrder());
                    break;
                case R.id.tab_message:
                    mainActivityViewPager.setCurrentItem(item.getOrder());
                    break;

                case R.id.tab_community:
                    mainActivityViewPager.setCurrentItem(item.getOrder());
                    break;

                case R.id.tab_person:
                    mainActivityViewPager.setCurrentItem(item.getOrder());
                    break;

            }
            // true 会显示这个Item被选中的效果 false 则不会
            return true;
        });
//        为 ViewPager 设置监听事件
        mainActivityViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //当 ViewPager 滑动后设置BottomNavigationView 选中相应选项
                bottomNavView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
